package versatile.project.lauryl.fragment

import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.recyclerview_lyout.*
import kotlinx.android.synthetic.main.schedule_pick_up_fragment.*
import timber.log.Timber
import versatile.project.lauryl.R
import versatile.project.lauryl.adapter.SchedulePickUpAdapterJava
import versatile.project.lauryl.application.MyApplication
import versatile.project.lauryl.model.TopServicesDataItem
import versatile.project.lauryl.model.TopServicesResponse
import versatile.project.lauryl.profile.data.GetProfileResponse
import versatile.project.lauryl.screens.HomeScreen
import versatile.project.lauryl.services.ServiceListAdapter
import versatile.project.lauryl.services.ServiceModel
import versatile.project.lauryl.utils.AllConstants
import versatile.project.lauryl.utils.Constants
import versatile.project.lauryl.utils.Globals
import versatile.project.lauryl.view.model.SchedulePickUpFragmentViewModel

class SchedulePickUpFragment : Fragment(), ServiceListAdapter.OnItemClickListener {

    lateinit var schedulePickUpViewModel: SchedulePickUpFragmentViewModel
    private var selectedItems = SparseBooleanArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        schedulePickUpViewModel =
            ViewModelProvider(this).get(SchedulePickUpFragmentViewModel::class.java)
        observeDataSources()
    }

    private fun observeDataSources() {

        schedulePickUpViewModel.geTopServicesToObserve()
            .observe(this, Observer<List<ServiceModel>> {

                if (it != null) {
                    if (it.size > 0) {
                        progressLyot.visibility = View.GONE
                        schdlePckUpBtn.visibility = View.VISIBLE
                       // selectedItems.clear()

                        val serviceListAdapter = ServiceListAdapter(
                           it,
                            this,selectedItems
                        )
                        recyclerVw.layoutManager =
                            LinearLayoutManager(activity!!.applicationContext)
                            recyclerVw.adapter = serviceListAdapter
                    } else {
                        progressLyot.visibility = View.GONE
                        Globals.showToastMsg(
                            activity!!.applicationContext,
                            "${resources.getString(R.string.no_services)}"
                        )
                    }

                } else {
                    progressLyot.visibility = View.GONE
                    Globals.showToastMsg(
                        activity!!.applicationContext,
                        "${resources.getString(R.string.server_error_txt)}"
                    )
                }

            })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        selectedItems=(activity as HomeScreen).myApplication.selectedServiceArray
        return inflater.inflate(R.layout.schedule_pick_up_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomeScreen).selectShedulePckUpDashBoard()
        schdlePckUpBtn.setOnClickListener {
            if (selectedItems.size() > 0) {
                val selectedServices=ArrayList<TopServicesDataItem>()
                var allServices= (recyclerVw.adapter as ServiceListAdapter).topServicesDataItems as ArrayList<TopServicesDataItem>
                for(item in allServices){
                    if(selectedItems.get(allServices.indexOf(item))){
                        selectedServices.add(item)
                    }
                }
                val myApplication: MyApplication = (activity!!.applicationContext as MyApplication)
                if (myApplication != null) {
                    myApplication.createOrderSerializedService=Gson().toJson(selectedServices)
                }
               // (activity as HomeScreen).displayMapLocationFragment(Constants.SELECT_ADDRESS_ACTION,Constants.CNF)
                (activity as HomeScreen).displayAddressSelectionFragment()
            } else {
                Globals.showToastMsg(activity!!.applicationContext, getString(R.string.select_service_msg))
            }
        }
        getTopServices()
    }

    override fun onPause() {
        super.onPause()
        (activity as HomeScreen).myApplication.selectedServiceArray = selectedItems
        Timber.e("selected items count : %s", (activity as HomeScreen).myApplication.selectedServiceArray.size())

    }

    private fun getTopServices() {
        schdlePckUpBtn.visibility = View.GONE
        progressLyot.visibility = View.VISIBLE
        val inputJson = JsonObject()
        inputJson.addProperty("currentPage", 0)
        inputJson.addProperty("pageSize", 100)
        inputJson.addProperty("sort", "DESC")
        inputJson.addProperty("sortBy", "createdAt")
        //inputJson.addProperty("search","[]")
        val myApplication: MyApplication = (activity?.applicationContext as MyApplication)
        if (myApplication != null) {
            schedulePickUpViewModel.getTopServices(myApplication.accessToken, inputJson)
        }
    }

    override fun onItemClicked(sItem: SparseBooleanArray) {
        selectedItems = sItem
    }

    override fun onDetach() {
        super.onDetach()
        (activity as HomeScreen).myApplication.selectedServiceArray.clear()
    }



}