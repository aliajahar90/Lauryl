package versatile.project.lauryl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.recyclerview_lyout.*
import kotlinx.android.synthetic.main.schedule_pick_up_fragment.*
import versatile.project.lauryl.R
import versatile.project.lauryl.adapter.SchedulePickUpAdapter
import versatile.project.lauryl.application.MyApplication
import versatile.project.lauryl.model.TopServicesDataItem
import versatile.project.lauryl.model.TopServicesResponse
import versatile.project.lauryl.screens.HomeScreen
import versatile.project.lauryl.utils.Globals
import versatile.project.lauryl.view.model.SchedulePickUpFragmentViewModel

class SchedulePickUpFragment: Fragment() {

    lateinit var schedulePickUpViewModel: SchedulePickUpFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        schedulePickUpViewModel = ViewModelProvider(this).get(SchedulePickUpFragmentViewModel::class.java)
        observeDataSources()
    }

    private fun observeDataSources() {

        schedulePickUpViewModel.geTopServicesToObserve().observe(this, Observer<TopServicesResponse>{

            if(it != null){
                if(it.getData().totalCount > 0){
                    progressLyot.visibility = View.GONE
                    schdlePckUpBtn.visibility = View.VISIBLE
                    val schedulePickUpAdapter = SchedulePickUpAdapter(activity!!.applicationContext,it.getData().list as ArrayList<TopServicesDataItem>)
                    recyclerVw.layoutManager = LinearLayoutManager(activity!!.applicationContext)
                    recyclerVw.adapter = schedulePickUpAdapter!!
                }else{
                    progressLyot.visibility = View.GONE
                    Globals.showToastMsg(activity!!.applicationContext,"${resources.getString(R.string.no_services)}")
                }

            }else{
                progressLyot.visibility = View.GONE
                Globals.showToastMsg(activity!!.applicationContext,"${resources.getString(R.string.server_error_txt)}")
            }

        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.schedule_pick_up_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomeScreen).selectShedulePckUpDashBoard()
        schdlePckUpBtn.setOnClickListener {
            (activity as HomeScreen).displayCnfPckUpFragment()
        }
        getTopServices()
    }

    private fun getTopServices() {
        schdlePckUpBtn.visibility = View.GONE
        progressLyot.visibility = View.VISIBLE
        val inputJson = JsonObject()
        inputJson.addProperty("currentPage",0)
        inputJson.addProperty("pageSize",100)
        inputJson.addProperty("sort","DESC")
        inputJson.addProperty("sortBy","createdAt")
        //inputJson.addProperty("search","[]")
        val myApplication: MyApplication = (activity!!.applicationContext as MyApplication)
        if(myApplication != null){
            schedulePickUpViewModel.getTopServices(myApplication.accessToken,inputJson)
        }
    }

}