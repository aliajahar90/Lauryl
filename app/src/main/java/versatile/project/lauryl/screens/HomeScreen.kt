package versatile.project.lauryl.screens

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home_screen.*
import timber.log.Timber
import versatile.project.lauryl.R
import versatile.project.lauryl.application.MyApplication
import versatile.project.lauryl.base.BaseActivity
import versatile.project.lauryl.base.HomeNavigationController
import versatile.project.lauryl.fragment.*
import versatile.project.lauryl.home.HomeFragment
import versatile.project.lauryl.model.address.AddressModel
import versatile.project.lauryl.orders.history.OrderHistoryFragment
import versatile.project.lauryl.pickup.CnfSchedulePckUpFragment
import versatile.project.lauryl.profile.data.GetProfileResponse
import versatile.project.lauryl.utils.AllConstants
import versatile.project.lauryl.utils.Constants
import versatile.project.lauryl.utils.Globals
import java.util.*

class HomeScreen : BaseActivity(), LocationListener {
    lateinit var homeNavigationController: HomeNavigationController
    private val REQUEST_CODE = 101
    private var locationManager: LocationManager? = null
    private val MIN_TIME: Long = 400
    private val MIN_DISTANCE = 1000f
    lateinit var myApplication: MyApplication
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        myApplication = MyApplication()
        Constants.CURRENT_AUTH_TOKEN = Globals.getStringFromPreferences(this, Constants.AUTH_TOKEN)
        botmNavVw.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        homeNavigationController = HomeNavigationController.getInstance(this)
        displayHomeFragment()
        bckBtn.setOnClickListener {

            if (supportFragmentManager.backStackEntryCount > 1) {
                supportFragmentManager.popBackStack()
            } else {
                finish()
            }

        }
        changeLocation.setOnClickListener {
            displayMapLocationFragment(Constants.CHANGE_LOCATION_ACTION)
        }

    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {

                R.id.homeId -> {
                    displayHomeFragment()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.myOrdersId -> {
                    displayMyOrdersFragment(0)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.schedulePckUpId -> {
                    displaySPFragment()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.paymentId -> {
                    displayPaymentFragment()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.profileId -> {
                    displayProfileFragment()
                    return@OnNavigationItemSelectedListener true
                }

            }
            false

        }

    private fun displayProfileFragment() {
        selectProfile()
        homeNavigationController.addProfileFragment();
    }

    private fun displayPaymentFragment() {
        selectPayment()
        homeNavigationController.addPaymentFragment()
    }

    fun displaySPFragment() {
        val fragment = SchedulePickUpFragment()
        loadMyFragment(fragment)
        selectShedulePckUpDashBoard()
    }

    fun selectShedulePckUpDashBoard() {
        homeNameMdlVwTxt.text = getString(R.string.chose_servcs_txt)
        homeNameMdlVwTxt.visibility = View.VISIBLE
        homeNameTxt.visibility = View.GONE
        filterTxt.visibility = View.GONE
        bckBtn.visibility = View.GONE
        botmNavVw.menu.findItem(R.id.schedulePckUpId).isChecked = true
    }

    fun selectMapLocationPickUpDashBoard() {
        homeNameMdlVwTxt.text = getString(R.string.set_location)
        homeNameMdlVwTxt.visibility = View.VISIBLE
        homeNameTxt.visibility = View.GONE
        filterTxt.visibility = View.GONE
        bckBtn.visibility = View.VISIBLE
        // botmNavVw.menu.findItem(R.id.schedulePckUpId).isChecked = true
    }

    fun displayMapLocationFragment(action: String) {
        val bundle = Bundle()
        bundle.putString(Constants.ACTION, action)
        val fragment = MapLocationFragment()
        fragment.arguments = bundle
        loadMyFragment(fragment)
        selectMapLocationPickUpDashBoard()
        // botmNavVw.menu.findItem(R.id.myOrdersId).isChecked = true
        //selectMyOrdersDashboard()
    }

    private fun selectChangeAddressDashBoard() {
        homeNameMdlVwTxt.text = getString(R.string.change_address)
        homeNameMdlVwTxt.visibility = View.VISIBLE
        homeNameTxt.visibility = View.GONE
        filterTxt.visibility = View.GONE
        bckBtn.visibility = View.VISIBLE
    }

    fun displayChangeAddressFragment(addressModel: AddressModel) {
        val bundle = Bundle()
        bundle.putSerializable("address", addressModel)
        val fragment = ChangeAddressFragment()
        fragment.arguments = bundle
        loadMyFragment(fragment)
        selectChangeAddressDashBoard()
    }

    private fun selectManageAddressDashBoard() {
        homeNameMdlVwTxt.text = getString(R.string.manage_address)
        homeNameMdlVwTxt.visibility = View.VISIBLE
        homeNameTxt.visibility = View.GONE
        filterTxt.visibility = View.GONE
        bckBtn.visibility = View.VISIBLE
    }

    fun displayManageAddressFragment() {
        val fragment = ManageAddressFragment()
        loadMyFragment(fragment)
        selectManageAddressDashBoard()
        // botmNavVw.menu.findItem(R.id.myOrdersId).isChecked = true
        //selectMyOrdersDashboard()
    }


    fun displayMyOrdersFragment(initPosition: Int) {
        myApplication.selectedOrderTab=initPosition
        val myOrdersFragment = MyOrdersFragment()
        loadMyFragment(myOrdersFragment)
        botmNavVw.menu.findItem(R.id.myOrdersId).isChecked = true
        //selectMyOrdersDashboard()
    }

    fun selectMyOrdersDashboard() {
        homeNameMdlVwTxt.text = getString(R.string.my_orders_txt)
        homeNameMdlVwTxt.visibility = View.VISIBLE
        homeNameTxt.visibility = View.GONE
        filterTxt.visibility = View.GONE
        bckBtn.visibility = View.VISIBLE
        botmNavVw.menu.findItem(R.id.myOrdersId).isChecked = true
        (application as MyApplication).activeSessionOrderNumber = ""
        (application as MyApplication).createOrderSerializdedAddressData = ""
        (application as MyApplication).createOrderSerializdedProfile = ""
        (application as MyApplication).createOrderSerializedService = ""
    }

    private fun displayHomeFragment() {
        val fm: FragmentManager = supportFragmentManager
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        val homeFragment = HomeFragment()
        homeNavigationController.addHomeFragment(homeFragment)
    }

    fun selectHomeDashboard() {
        homeNameTxt.text = getString(R.string.home_hdng_txt)
        homeNameTxt.visibility = View.VISIBLE
        homeNameMdlVwTxt.visibility = View.GONE
        bckBtn.visibility = View.GONE
        filterTxt.visibility = View.GONE
        botmNavVw.menu.findItem(R.id.homeId).isChecked = true
        (application as MyApplication).activeSessionOrderNumber = ""
        (application as MyApplication).createOrderSerializdedAddressData = ""
        (application as MyApplication).createOrderSerializdedProfile = ""
        (application as MyApplication).createOrderSerializedService = ""
    }

    private fun loadMyFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    fun displayCnfPckUpFragment() {
        selectCnfPckUp()
        val fragment = CnfSchedulePckUpFragment.newInstance()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragContainer, fragment)
        transaction.addToBackStack(fragment::class.java.name)
        transaction.commit()
    }

    fun selectCnfPckUp() {
        homeNameMdlVwTxt.text = getString(R.string.schedule_pckup_txt)
        homeNameMdlVwTxt.visibility = View.VISIBLE
        homeNameTxt.visibility = View.GONE
        bckBtn.visibility = View.VISIBLE
        botmNavVw.menu.findItem(R.id.schedulePckUpId).isChecked = true
    }

    fun displayOrderHstryFragment(jsonString: String) {
        val bundle = Bundle()
        bundle.putString(AllConstants.Orders.orderData, jsonString)
        val orderHistoryFragment = OrderHistoryFragment();
        orderHistoryFragment.arguments = bundle
        val fragment = orderHistoryFragment
        loadMyFragment(fragment)
        selectOrderHistory()
    }

    fun selectOrderHistory() {
        homeNameMdlVwTxt.text = getString(R.string.order_histry_txt)
        homeNameMdlVwTxt.visibility = View.VISIBLE
        homeNameTxt.visibility = View.GONE
        bckBtn.visibility = View.VISIBLE
        botmNavVw.menu.findItem(R.id.myOrdersId).isChecked = true
        (application as MyApplication).activeSessionOrderNumber = ""
        (application as MyApplication).createOrderSerializdedAddressData = ""
        (application as MyApplication).createOrderSerializdedProfile = ""
        (application as MyApplication).createOrderSerializedService = ""
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    fun setLocation(address: String) {
        homelocTxt.text = address
    }

    fun selectProfile() {
        homeNameMdlVwTxt.text = getString(R.string.my_profile)
        homeNameMdlVwTxt.visibility = View.VISIBLE
        homeNameTxt.visibility = View.GONE
        filterTxt.visibility = View.GONE
        rlChange.visibility = View.VISIBLE
        homeLocHdngTxt.text = getString(R.string.loc_hdng_txt)
        //   imgLoc.setImageResource(R.drawable.ic_name)
        botmNavVw.menu.findItem(R.id.profileId).isChecked = true
        bckBtn.visibility = View.VISIBLE;
        (application as MyApplication).activeSessionOrderNumber = ""
        (application as MyApplication).createOrderSerializdedAddressData = ""
        (application as MyApplication).createOrderSerializdedProfile = ""
        (application as MyApplication).createOrderSerializedService = ""

    }

    fun selectPayment() {
        homeNameMdlVwTxt.text = getString(R.string.payment_details)
        homeNameMdlVwTxt.visibility = View.VISIBLE
        homeNameTxt.visibility = View.GONE
        filterTxt.visibility = View.GONE
        rlChange.visibility = View.VISIBLE
        homeLocHdngTxt.text = getString(R.string.loc_hdng_txt)
        imgLoc.setImageResource(R.drawable.location_white_icon)
        botmNavVw.menu.findItem(R.id.paymentId).isChecked = true
        bckBtn.visibility = View.VISIBLE;
    }

    fun hideBackButton() {
        bckBtn.visibility = View.GONE;

    }

    fun showBackButton() {
        bckBtn.visibility = View.VISIBLE;

    }


    fun fetchLocation() {
        this.let {
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
            //  googleMap?.isMyLocationEnabled = true
            locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager!!.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                MIN_TIME,
                MIN_DISTANCE,
                this
            )
        }
    }

    fun fetchAddress(latitude: Double?, longitude: Double?) {
        Timber.e("latitude $latitude longitude $longitude")
        val addresses: List<Address>
        val geoCoder: Geocoder = Geocoder(this, Locale.getDefault())

        addresses = geoCoder.getFromLocation(
            latitude!!,
            longitude!!,
            1
        ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        try {

            val address: String = addresses[0]
                .getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            Timber.e(
                " address : $address, "
            )

            val city: String = addresses[0].locality
            setLocation(city)

            val state: String = addresses[0].adminArea
            val country: String = addresses[0].countryName
            val postalCode: String = addresses[0].postalCode
            val knownName: String = addresses[0].featureName

            Timber.e(
                " address : $address, " +
                        "city : $city, " +
                        "state : $state, " +
                        " country : $country," +
                        " postalcode :  $postalCode," +
                        " known address :  $knownName"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
            locationManager!!.removeUpdates(this)
        }
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
    }

    override fun onProviderEnabled(p0: String?) {
    }

    override fun onProviderDisabled(p0: String?) {
    }

    fun updateUserName(profileResponse: GetProfileResponse){
        (application as MyApplication).createOrderSerializdedProfile =Gson().toJson(profileResponse)
        homeNameTxt.text="Hello, " + profileResponse.profileData.firstName
    }

    override fun onResume() {
        super.onResume()
        fetchLocation()
    }
}
