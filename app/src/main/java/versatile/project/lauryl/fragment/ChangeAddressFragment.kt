package versatile.project.lauryl.fragment

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.change_address_fragment.*
import timber.log.Timber
import versatile.project.lauryl.R
import versatile.project.lauryl.application.MyApplication
import versatile.project.lauryl.model.address.AddressModel
import versatile.project.lauryl.model.city.CityModel
import versatile.project.lauryl.screens.HomeScreen
import versatile.project.lauryl.utils.Constants
import versatile.project.lauryl.utils.Globals
import versatile.project.lauryl.view.model.ChangeAddressViewModel
import java.io.IOException


class ChangeAddressFragment : Fragment() {

    lateinit var myApplication: MyApplication
    lateinit var changeAddressViewModel: ChangeAddressViewModel
    lateinit var number: String
    var mAddress = AddressModel()

    lateinit var action: String
    lateinit var origin: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeAddressViewModel = ViewModelProvider(this)[ChangeAddressViewModel::class.java]
        myApplication = (activity?.applicationContext as MyApplication)
        number = myApplication.mobileNumber
        action = arguments?.getString(Constants.ACTION) as String
        origin = arguments?.getString(Constants.ORIGIN) as String

    }

    private fun validateFields() {
        val flatNo = flat_no.text.toString()
        val landMark = landmark.text.toString()
        val profileName = profile_name.text.toString()
        val completeAddress = complete_address.text.toString()
        if (flatNo.isNotEmpty()) {
            if (landMark.isNotEmpty()) {
                if (completeAddress.isNotEmpty()) {
                    Timber.e("validation success")
                    mAddress.landmark = landMark
                    mAddress.streetName = completeAddress
                    mAddress.address1 = flatNo
                    mAddress.city = addressModel?.city
                    mAddress.state = addressModel?.state
                    mAddress.pinCode = addressModel?.pinCode
                    mAddress.country = addressModel?.country
                    mAddress.addresType = if (profileName.isNotEmpty()) profileName else "Other"
                    mAddress.latitude = addressModel?.latitude
                    mAddress.longitude = addressModel?.longitude
                    fetchLatLongFromAddress(mAddress)

                } else {
                    Globals.showPopoUpDialog(
                        context!!,
                        getString(R.string.validation), "Address is Mandatory"
                    )
                }


            } else {
                Globals.showPopoUpDialog(
                    context!!,
                    getString(R.string.validation), "LandMark is Mandatory"
                )
            }

        } else {
            Globals.showPopoUpDialog(
                context!!,
                getString(R.string.validation), "Flat no is Mandatory"
            )
        }
    }

    private fun emptyFields() {
        flat_no.text.clear()
        landmark.text.clear()
        profile_name.text.clear()
        complete_address.text.clear()
    }

    private fun observeDataSources() {
        changeAddressViewModel.saveAddressLiveData.observe(this, Observer {
            if (it.status) {
                emptyFields()
                //continue to order use mAddress
                if (isEditing) {
                    (activity as HomeScreen).shout("Address Updated..")
                    activity?.onBackPressed()
                } else
                    (activity as HomeScreen).shout("Address saved..")
                //(activity as HomeScreen).setLocation(mAddress.city.toString())
                when {
                    origin == Constants.CNF -> {
                        val myApplication: MyApplication =
                            (activity!!.applicationContext as MyApplication)
                        myApplication.createOrderSerializdedAddressData = Gson().toJson(mAddress)
                        (activity as HomeScreen).displayCnfPckUpFragment()
                    }
                    origin == Constants.PAYMENT_FRAG -> {
                        (activity as HomeScreen).displayPaymentFragment()
                    }
                    action == Constants.ADD_LOCATION_ACTION -> {
                        (activity as HomeScreen).onBackPressed()
                        (activity as HomeScreen).onBackPressed()
                    }
                }
            } else {
                Globals.showPopoUpDialog(
                    context!!,
                    getString(R.string.validation), "Failed to add/update address"
                )
            }
        })

    }

    var addressModel: AddressModel? = null
    private var shouldValidte = true
    private var isEditing = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.change_address_fragment, container, false)
        addressModel = try {
            arguments?.getSerializable("address") as AddressModel
        } catch (e: Exception) {
            AddressModel()
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomeScreen).selectChangeAddressDashBoard()
        continue_location_btn.setOnClickListener {
            if (shouldValidte)
                validateFields()
            else {
                if (mAddress.city.isNullOrBlank()) {
                    (activity as HomeScreen).setLocation(mAddress.city.toString())
                }
                val myApplication: MyApplication = (activity?.applicationContext as MyApplication)
                Timber.e(Gson().toJson(mAddress))
                myApplication.createOrderSerializdedAddressData = Gson().toJson(mAddress)
                if (origin == Constants.PAYMENT_FRAG)
                    (activity as HomeScreen).displayPaymentFragment()
                else
                    (activity as HomeScreen).displayCnfPckUpFragment()
                //address selected so continue to order user mAddress as address
            }
        }
        if (addressModel?.id != null) {
            isEditing = true
            continue_location_btn.text = "Update address"
        }
        if (action == Constants.EDIT_ADDRESS_ACTION || action == Constants.ADD_LOCATION_ACTION) {
            continue_location_btn.text = "Confirm address"
            // address_spinner.visibility = View.GONE
        }

        addressModel?.streetName.let {
            complete_address.setText(it)
        }
        addressModel?.landmark.let {
            landmark.setText(it)
        }
        addressModel?.address1.let {
            flat_no.setText(it)
        }
        addressModel?.addresType.let {
            profile_name.setText(it)
        }
        observeDataSources()

    }

    private fun fetchLatLongFromAddress(mAddress: AddressModel) {
        if (myApplication.changeButtonClicked) {
            val coder = Geocoder(activity)
            try {
                val addresses: ArrayList<Address> =
                    coder.getFromLocationName(
                        "${mAddress.streetName}, ${mAddress.landmark}, ${mAddress.city}," +
                                "${mAddress.state}, ${if (mAddress.country.isNullOrBlank()) mAddress.country else "India"}, ${mAddress.pinCode}",
                        5
                    ) as ArrayList<Address>
                if (addresses.size > 0)
                    addresses[0]?.let {
                        mAddress.latitude = it.latitude.toString()
                        mAddress.longitude = it.longitude.toString()
                    }

                makeApiCall(mAddress)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            makeApiCall(mAddress)
        }
    }

    private fun makeApiCall(mAddress: AddressModel) {
        val inputJson = JsonObject()
        inputJson.addProperty("phoneNumber", number)
        val addressObj = JsonObject()
        addressObj.addProperty(
            "addresType",
            if (mAddress.addresType.isNullOrBlank()) "" else mAddress.addresType
        )
        addressObj.addProperty(
            "address1",
            if (mAddress.address1.isNullOrBlank()) "" else mAddress.address1
        )
        addressObj.addProperty(
            "streetName",
            if (mAddress.streetName.isNullOrBlank()) "" else mAddress.streetName
        )
        addressObj.addProperty(
            "landmark",
            if (mAddress.landmark.isNullOrBlank()) "" else mAddress.landmark
        )
        addressObj.addProperty("city", if (mAddress.city.isNullOrBlank()) "" else mAddress.city)
        addressObj.addProperty("state", if (mAddress.state.isNullOrBlank()) "" else mAddress.state)
        addressObj.addProperty(
            "country",
            if (mAddress.country.isNullOrBlank()) "" else mAddress.country
        )
        addressObj.addProperty(
            "pinCode",
            if (mAddress.pinCode.isNullOrBlank()) "" else mAddress.pinCode
        )

        addressObj.addProperty(
            "latitude",
            if (mAddress.latitude.isNullOrBlank()) "" else mAddress.latitude
        )
        addressObj.addProperty(
            "longitude",
            if (mAddress.longitude.isNullOrBlank()) "" else mAddress.longitude
        )

        if (isEditing) {
//updating address
            addressObj.addProperty("id", addressModel?.id)
        }
        val addresList = JsonArray()
        addresList.add(addressObj)
        inputJson.add("addressList", addresList)

        Timber.e(inputJson.toString())
        changeAddressViewModel.saveAddress(myApplication.userAccessToken, jsonObject = inputJson)
    }

    override fun onResume() {
        super.onResume()

        changeAddressViewModel.getAddress(
            access = myApplication.userAccessToken, number = myApplication.mobileNumber
        )
        changeAddressViewModel.getCities(myApplication.userAccessToken)
    }

}


