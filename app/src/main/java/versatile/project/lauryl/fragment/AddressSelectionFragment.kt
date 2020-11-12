package versatile.project.lauryl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.address_selection_fragment.*
import kotlinx.android.synthetic.main.manage_address_fragment.*
import kotlinx.android.synthetic.main.manage_address_fragment.add_address_button
import kotlinx.android.synthetic.main.manage_address_fragment.address_rv
import versatile.project.lauryl.R
import versatile.project.lauryl.adapter.AddressSelectionAdapter
import versatile.project.lauryl.adapter.AddressSelectionListener
import versatile.project.lauryl.adapter.EditDeleteListener
import versatile.project.lauryl.application.MyApplication
import versatile.project.lauryl.model.address.AddressModel
import versatile.project.lauryl.screens.HomeScreen
import versatile.project.lauryl.utils.Constants
import versatile.project.lauryl.view.model.ManageAddressViewModel


class AddressSelectionFragment : Fragment(), AddressSelectionListener {

    private lateinit var myApplication: MyApplication
    lateinit var manageAddressViewModel: ManageAddressViewModel
    var addressList = ArrayList<AddressModel>()
    lateinit var editAddressAdapter: AddressSelectionAdapter
    lateinit var action: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manageAddressViewModel = ViewModelProvider(this)[ManageAddressViewModel::class.java]
        myApplication = (activity?.applicationContext as MyApplication)
        observeDataSources()
    }

    override fun onResume() {
        super.onResume()
        manageAddressViewModel.getAddress(
            access = myApplication.userAccessToken, number = myApplication.mobileNumber
        )
    }

    private fun observeDataSources() {
        manageAddressViewModel.getAddressesToObserve().observe(this, Observer {
            addressList = it
            if (it.isEmpty())
                continue_button.visibility=View.GONE
            it.first().isSelected=true
            editAddressAdapter = AddressSelectionAdapter(list = it, editDeleteListener = this)
            val linearLayoutManager = LinearLayoutManager(context)
            address_rv.layoutManager = linearLayoutManager
            address_rv.adapter = editAddressAdapter
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.address_selection_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomeScreen).selectManageAddressDashBoard()
        add_address_button.setOnClickListener {
            (activity as HomeScreen).displayMapLocationFragment(
                action = Constants.ADD_LOCATION_ACTION,
                origin = Constants.MANAGE_ADDRESS
            )
        }
        continue_button.setOnClickListener {
            myApplication.createOrderSerializdedAddressData = Gson().toJson(addressList[selectedAddressIndex])
            (activity as HomeScreen).displayCnfPckUpFragment()
        }
    }

    var selectedAddressIndex = 0
    override fun addressSelected(rowIndex: Int) {
        selectedAddressIndex=rowIndex
        addressList.forEach {
            it.isSelected = false
        }
        addressList[rowIndex].isSelected = true
        editAddressAdapter.notifyDataSetChanged()
    }

    override fun onEditSelected(rowIndex: Int) {
        val activity = activity as HomeScreen
        activity.displayChangeAddressFragment(
            addressList[rowIndex],
            action = Constants.EDIT_ADDRESS_ACTION,
            origin = Constants.MANAGE_ADDRESS
        )
    }
}