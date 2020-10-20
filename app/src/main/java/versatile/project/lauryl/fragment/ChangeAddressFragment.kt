package versatile.project.lauryl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.change_address_fragment.*
import timber.log.Timber
import versatile.project.lauryl.R
import versatile.project.lauryl.adapter.AddressSpinnerAdapter
import versatile.project.lauryl.application.MyApplication
import versatile.project.lauryl.model.address.AddressModel
import versatile.project.lauryl.model.city.CityModel
import versatile.project.lauryl.utils.Globals
import versatile.project.lauryl.view.model.ChangeAddressViewModel


class ChangeAddressFragment : Fragment() {

    lateinit var myApplication: MyApplication
    lateinit var changeAddressViewModel: ChangeAddressViewModel
    lateinit var city: CityModel
    lateinit var state: CityModel
    lateinit var cityList: List<CityModel>
    lateinit var number: String
    var addressType: String = "Home"
    var addreList = ArrayList<AddressModel>()
    var mAddress = AddressModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeAddressViewModel = ViewModelProvider(this)[ChangeAddressViewModel::class.java]
        myApplication = (activity?.applicationContext as MyApplication)
        number = myApplication.mobileNumber
        changeAddressViewModel.getAddress(
            access = myApplication.userAccessToken, number = myApplication.mobileNumber
        )
        changeAddressViewModel.getCities(myApplication.userAccessToken)

    }

    private fun isAddressTypeUsed(addressTpe: String): String? {
        for (item in addreList) {
            if (addressTpe == item.addresType)
                return item.id
        }
        return "-"
    }

    private fun validateFields() {
        val flatNo = flat_no.text.toString()
        val streetName = street_name.text.toString()
        val landMark = landmark.text.toString()
        val pin_code = pincode.text.toString()
        if (flatNo != null && flatNo.isNotEmpty()) {
            if (streetName != null && streetName.isNotEmpty()) {
                if (landMark != null && landMark.isNotEmpty()) {
                    if (pin_code != null && pin_code.isNotEmpty()) {
                        if (pin_code != null && pin_code.length == 6) {


                            Timber.e("validation success")
                            val inputJson = JsonObject()
                            inputJson.addProperty("phoneNumber", number)
                            val addressObj = JsonObject()
                            addressObj.addProperty("addresType", addressType)
                            addressObj.addProperty("address1", "${addressType}1")
                            addressObj.addProperty("streetName", streetName)
                            addressObj.addProperty("landmark", landMark)
                            addressObj.addProperty("city", city.city)
                            addressObj.addProperty("state", state.state)
                            addressObj.addProperty("country", state.country)
                            addressObj.addProperty("pinCode", pin_code)

                            mAddress.landmark = landMark
                            mAddress.streetName = streetName
                            mAddress.city = city.city
                            mAddress.state = state.state
                            mAddress.pinCode = pin_code
                            mAddress.country = state.country
                            mAddress.addresType = addressType

                            if (isEditing) {
                                //updating address
                                addressObj.addProperty("id", addressModel.id)
                            } else {
                                if (isAddressTypeUsed(addressType) != "-") {
                                    //overriding previos address
                                    addressObj.addProperty("id", isAddressTypeUsed(addressType))
                                }
                            }
                            val addresList = JsonArray()
                            addresList.add(addressObj)
                            inputJson.add("addressList", addresList)
                            changeAddressViewModel.saveAddress(
                                myApplication.userAccessToken,
                                inputJson
                            )

                        } else {
                            Globals.showPopoUpDialog(
                                context!!,
                                getString(R.string.validation), "Enter a valid 6 digit pincode"
                            )
                        }

                    } else {
                        Globals.showPopoUpDialog(
                            context!!,
                            getString(R.string.validation), "Pincode is Mandatory"
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
                    getString(R.string.validation), "Street name is Mandatory"
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
        street_name.text.clear()
        landmark.text.clear()
        pincode.text.clear()
    }

    private fun observeDataSources() {
        changeAddressViewModel.saveAddressLiveData.observe(this, Observer {
            if (it.status) {
                emptyFields()
                //continue to order use mAddress
                if(isEditing) {
                    shout("Address Updated")
                    activity?.onBackPressed()
                }
            } else {
                Globals.showPopoUpDialog(
                    context!!,
                    getString(R.string.validation), "Failed to add/update address"
                )
            }
        })

        changeAddressViewModel.getCitiesToObserve().observe(this, Observer { list ->
            cityList = list
            val adapter: ArrayAdapter<String> = ArrayAdapter(
                this.context!!,
                R.layout.state_spinner_item, list.map { it.city }
            )
            city = cityList.first()
            adapter.setDropDownViewResource(R.layout.state_spinner_item)
            city_spinner.adapter = adapter
            city_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    state = cityList[position]

                }

            }

            val stateAdapter: ArrayAdapter<String> = ArrayAdapter(
                this.context!!,
                R.layout.state_spinner_item,
                list.map { it.state }
            )
            state = cityList.first()
            stateAdapter.setDropDownViewResource(R.layout.state_spinner_item)
            state_spinner.adapter = stateAdapter
            state_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    state = cityList[position]

                }

            }
        })
        changeAddressViewModel.getStatesToObserve().observe(this, Observer {

        })

        changeAddressViewModel.getAddressesToObserve().observe(this, Observer {
            addreList = it
            val adapter = AddressSpinnerAdapter(this.context!!, it)
            address_spinner.adapter = adapter
            address_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (mSpinnerInitialized) {
                        mAddress = addreList[position]
                        emptyFields()
                        shouldValidte = false
                        shout("Previous address selected so continue to order")
                    }
                    mSpinnerInitialized = true
                }
            }
        })

    }

    var addressModel: AddressModel = AddressModel()
    private var mSpinnerInitialized = false
    private var shouldValidte = true
    private var isEditing = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.change_address_fragment, container, false)
        addressModel = arguments?.getSerializable("address") as AddressModel
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        continue_location_btn.setOnClickListener {
            if (shouldValidte)
                validateFields()
            else {
                //address selected so continue to order user mAddress as address
            }
        }
        if (addressModel.id != null) {
            isEditing = true
            continue_location_btn.text="Update address"
            address_spinner.visibility=View.GONE
        }

        addressType = addressModel.addresType.toString()
        when (addressModel.addresType.toString()) {
            "Home" -> {
                address_type_radio.check(R.id.home)
            }
            "Work" -> {
                address_type_radio.check(R.id.work)
            }
            else -> {
                address_type_radio.check(R.id.other)

            }
        }
        addressModel.pinCode.let {
            pincode.setText(it)
        }
        addressModel.streetName.let {
            street_name.setText(it)
        }
        addressModel.landmark.let {
            landmark.setText(it)
        }

        observeDataSources()

        address_type_radio.setOnCheckedChangeListener { radioGroup, _ ->
            run {
                addressType =
                    (radioGroup?.findViewById(radioGroup.checkedRadioButtonId) as RadioButton).text as String
            }
        }
    }


    private fun shout(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}