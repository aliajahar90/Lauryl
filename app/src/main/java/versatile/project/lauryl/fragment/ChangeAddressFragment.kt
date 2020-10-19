package versatile.project.lauryl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.change_address_fragment.*
import versatile.project.lauryl.R
import versatile.project.lauryl.adapter.AddressSpinnerAdapter
import versatile.project.lauryl.application.MyApplication
import versatile.project.lauryl.utils.Constants
import versatile.project.lauryl.utils.Globals
import versatile.project.lauryl.view.model.ChangeAddressViewModel


class ChangeAddressFragment : Fragment() {


    lateinit var changeAddressViewModel: ChangeAddressViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeAddressViewModel = ViewModelProvider(this)[ChangeAddressViewModel::class.java]
        observeDataSources()
        changeAddressViewModel.getStates()
        val myApplication: MyApplication = (activity?.applicationContext as MyApplication)
        changeAddressViewModel.getAddress(
            access = myApplication.accessToken, number = Globals.getStringFromPreferences(
                myApplication,
                Constants.MOBILE_NUMBER
            )
        )
        changeAddressViewModel.getCities()
    }

    private fun observeDataSources() {
        changeAddressViewModel.getCitiesToObserve().observe(this, Observer {
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this.context!!,
                R.layout.state_spinner_item,
                it
            )
            adapter.setDropDownViewResource(R.layout.state_spinner_item)
            city_spinner.adapter = adapter
        })
        changeAddressViewModel.getStatesToObserve().observe(this, Observer {

            val adapter: ArrayAdapter<String> = ArrayAdapter(
                this.context!!,
                R.layout.state_spinner_item,
                it
            )
            adapter.setDropDownViewResource(R.layout.state_spinner_item)
            state_spinner.adapter = adapter
        })

        changeAddressViewModel.getAddressesToObserve().observe(this, Observer {
            val adapter = AddressSpinnerAdapter(this.context!!, it)
            address_spinner.adapter = adapter

        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.change_address_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}