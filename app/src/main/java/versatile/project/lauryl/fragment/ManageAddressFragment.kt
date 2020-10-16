package versatile.project.lauryl.fragment

import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.change_address_fragment.*
import kotlinx.android.synthetic.main.manage_address_fragment.*
import versatile.project.lauryl.R
import versatile.project.lauryl.adapter.AddressSpinnerAdapter
import versatile.project.lauryl.adapter.EditAddressAdapter
import versatile.project.lauryl.adapter.EditDeleteListener
import versatile.project.lauryl.view.model.ChangeAddressViewModel
import versatile.project.lauryl.view.model.ManageAddressViewModel


class ManageAddressFragment : Fragment(), EditDeleteListener {


    lateinit var manageAddressViewModel: ManageAddressViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeDataSources()
        manageAddressViewModel = ViewModelProvider(this)[ManageAddressViewModel::class.java]
        manageAddressViewModel.getAddress()
    }

    private fun observeDataSources() {
        manageAddressViewModel.getAddressesToObserve().observe(this, Observer {
            val editAddressAdapter=EditAddressAdapter(it,this)
            val linearLayoutManager = LinearLayoutManager(context)
            address_rv.layoutManager=linearLayoutManager
            address_rv.adapter=editAddressAdapter
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
        Toast.makeText(context,"edit clicked",Toast.LENGTH_SHORT).show()
    }

    override fun deleteClicked(position: Int) {
        Toast.makeText(context,"delete clicked",Toast.LENGTH_SHORT).show()

    }

}