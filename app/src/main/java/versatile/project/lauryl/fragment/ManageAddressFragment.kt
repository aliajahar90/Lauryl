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
import kotlinx.android.synthetic.main.manage_address_fragment.*
import versatile.project.lauryl.R
import versatile.project.lauryl.adapter.EditAddressAdapter
import versatile.project.lauryl.adapter.EditDeleteListener
import versatile.project.lauryl.application.MyApplication
import versatile.project.lauryl.model.address.AddressModel
import versatile.project.lauryl.screens.HomeScreen
import versatile.project.lauryl.view.model.ManageAddressViewModel


class ManageAddressFragment : Fragment(), EditDeleteListener {

    private lateinit var myApplication: MyApplication
    lateinit var manageAddressViewModel: ManageAddressViewModel
    var addressList = ArrayList<AddressModel>()
    lateinit var editAddressAdapter: EditAddressAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manageAddressViewModel = ViewModelProvider(this)[ManageAddressViewModel::class.java]
        myApplication = (activity?.applicationContext as MyApplication)
        observeDataSources()

        manageAddressViewModel.getAddress(
            access = myApplication.userAccessToken, number = myApplication.mobileNumber
        )
    }

    private fun observeDataSources() {
        manageAddressViewModel.getAddressesToObserve().observe(this, Observer {
            addressList = it
            editAddressAdapter = EditAddressAdapter(it, this)
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
        return inflater.inflate(R.layout.manage_address_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun editClicked(position: Int) {
        val activity = activity as HomeScreen
        activity.displayChangeAddressFragment(addressList[position])

    }

    override fun deleteClicked(id: String, position: Int) {
        manageAddressViewModel.deleteAddress(myApplication.userAccessToken, id)
        manageAddressViewModel.deleteObserver.observe(this, Observer {
            if (it.status) {
                shout("Address deleted")
                manageAddressViewModel.getAddress(access = myApplication.userAccessToken, number = myApplication.mobileNumber)
            }
        })
    }

    private fun shout(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}