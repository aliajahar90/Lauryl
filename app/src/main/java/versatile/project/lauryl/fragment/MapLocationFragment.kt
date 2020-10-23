package versatile.project.lauryl.fragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.map_location_fragment.*
import timber.log.Timber
import versatile.project.lauryl.R
import versatile.project.lauryl.application.MyApplication
import versatile.project.lauryl.model.address.AddressModel
import versatile.project.lauryl.screens.HomeScreen
import versatile.project.lauryl.utils.Constants
import versatile.project.lauryl.view.model.MapLocationViewModel
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


open class MapLocationFragment : Fragment(), OnMapReadyCallback, LocationListener {
    private var googleMap: GoogleMap? = null
    private val REQUEST_CODE = 101
    private var locationManager: LocationManager? = null
    private val MIN_TIME: Long = 400
    private val MIN_DISTANCE = 1000f
    private var addressModel: AddressModel = AddressModel()
    lateinit var myApplication: MyApplication
    lateinit var mapLocationViewModel: MapLocationViewModel
    var pinCodes = ArrayList<String>()
   lateinit var action :String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapLocationViewModel = ViewModelProvider(this)[MapLocationViewModel::class.java]
        myApplication = (activity?.applicationContext as MyApplication)
        mapLocationViewModel.getCities(myApplication.userAccessToken)
        (activity as HomeScreen).showLoading()
        action = arguments?.getString(Constants.ACTION) as String

    }

    private fun observeDataSources() {
        mapLocationViewModel.getCitiesToObserve().observe(this, androidx.lifecycle.Observer {
            for (city in it) {
                for (pin in city.pinCode)
                    pinCodes.add(pin)
            }
        })
        (activity as HomeScreen).hideLoading()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inview = inflater.inflate(R.layout.map_location_fragment, container, false)
        (childFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment).getMapAsync(
            this
        )
        return inview
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confirm_location_btn.setOnClickListener {
            if (action==Constants.CHANGE_LOCATION_ACTION)
            {
                (activity as HomeScreen).onBackPressed()
            }else{
                (activity as HomeScreen).displayChangeAddressFragment(addressModel)
            }
           // if (isServiceable)
//            else{
//                (activity as HomeScreen).scream("Our services are not available in this province !!")
//
//            }
        }
        observeDataSources()
    }

    private fun fetchLocation() {
        context?.let {
            if (ActivityCompat.checkSelfPermission(
                    it, Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    it, Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    it as Activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE
                )
                return
            }
            googleMap?.isMyLocationEnabled = true
            locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager!!.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                MIN_TIME,
                MIN_DISTANCE,
                this
            )
        }

        googleMap?.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragStart(marker: Marker) {

            }

            override fun onMarkerDragEnd(marker: Marker) {
                googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 15f))
                fetchAddress(marker.position.latitude, marker.position.longitude)
            }

            override fun onMarkerDrag(arg0: Marker?) {
                //  val message = arg0!!.position.latitude.toString() + "" + arg0.position.longitude.toString()
                // Log.d(TAG + "_DRAG", message)
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation()
            }
        }
    }

    var isServiceable = false
    private fun validateServiceAvailability(pinCode: String) {
        if (pinCodes.contains(pinCode)) {
            isServiceable = true
            availabiltyMessage.visibility = View.GONE
        } else {
            isServiceable = false
            availabiltyMessage.visibility = View.VISIBLE
            availabiltyMessage.text = "Our services are not available in this province !!"
        }
    }

    fun fetchAddress(latitude: Double?, longitude: Double?) {
        Timber.e("latitude $latitude longitude $longitude")

        try {
            val addresses: List<Address>
            val geoCoder = Geocoder(context!!, Locale.ENGLISH)
            addresses = geoCoder.getFromLocation(
                latitude!!,
                longitude!!,
                1
            ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            val address: String = addresses[0]
                .getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            addresses[0].locality.let {
                addressModel.city = it
                Timber.e("city ${addressModel.city}")
            }
            addresses[0].adminArea.let {
                addressModel.state = it
                Timber.e("state ${addressModel.state}")
            }
            addresses[0].countryName.let {
                addressModel.country = it
                Timber.e("country ${addressModel.country}")

            }
            addresses[0].postalCode.let {
                addressModel.pinCode = it
                Timber.e("pinCode ${addressModel.pinCode}")
                validateServiceAvailability(pinCode = it)
            }
            addresses[0].featureName.let {
                addressModel.landmark = it
                Timber.e("landmark ${addressModel.landmark}")

            }
            addresses[0].thoroughfare.let {
                addressModel.streetName = it
                Timber.e("streetName ${addressModel.streetName}")

            }
            // Toast.makeText(context, address, Toast.LENGTH_SHORT).show()
            val city: String = addresses[0].locality
            city_name.text = city
            address_geo.text = address
            (activity as HomeScreen).setLocation(city)
//            val state: String = addresses[0].adminArea
//            val country: String = addresses[0].countryName
//            val postalCode: String = addresses[0].postalCode
//            val knownName: String = addresses[0].featureName
//

//            Timber.e(
//                " address : $address, " +
//                        "city : $city, " +
//                        "state : $state, " +
//                        " country : $country," +
//                        " postalcode :  $postalCode," +
//                        " known address :  $knownName"
//            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        googleMap = p0
        googleMap?.mapType = GoogleMap.MAP_TYPE_TERRAIN
        fetchLocation()
    }

    override fun onLocationChanged(location: Location?) {
        fetchAddress(location?.latitude, location?.longitude)
        location.let {
            val latLng = it?.latitude?.let { it1 ->
                location?.longitude?.let { it2 ->
                    LatLng(
                        it1,
                        it2
                    )

                }
            }
            googleMap?.addMarker(
                latLng?.let { it1 ->
                    MarkerOptions()
                        .position(it1).draggable(true)
                }
            )
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
            googleMap?.animateCamera(cameraUpdate)

            locationManager!!.removeUpdates(this)
        }

    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
    }

    override fun onProviderEnabled(p0: String?) {
    }

    override fun onProviderDisabled(p0: String?) {
    }


}