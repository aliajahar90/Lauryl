package versatile.project.lauryl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.GlobalScope
import versatile.project.lauryl.R
import versatile.project.lauryl.application.MyApplication
import versatile.project.lauryl.base.HomeNavigationController
import versatile.project.lauryl.model.TopServicesResponse
import versatile.project.lauryl.screens.HomeScreen
import versatile.project.lauryl.utils.Constants
import versatile.project.lauryl.utils.Globals
import versatile.project.lauryl.view.model.HomeFragmentViewModel
import versatile.project.lauryl.view.model.OtpVerificationViewModel

class HomeFragment: Fragment() {
    lateinit var homeFragmentViewModel: HomeFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeFragmentViewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        observeDataSources()
    }

    private fun getTopServices() {
        val inputJson = JsonObject()
        inputJson.addProperty("currentPage",0)
        inputJson.addProperty("pageSize",100)
        inputJson.addProperty("sort","DESC")
        inputJson.addProperty("sortBy","createdAt")
        //inputJson.addProperty("search","[]")
        val myApplication:MyApplication = (activity!!.applicationContext as MyApplication)
        if(myApplication != null){
            homeFragmentViewModel.getTopServices(myApplication.accessToken,inputJson)
        }

    }

    private fun observeDataSources() {

        homeFragmentViewModel.geTopServicesToObserve().observe(this,Observer<TopServicesResponse>{

            if(it != null){

                if(it.getData().totalCount > 0){

                    servicesOneTxt.text = it.getData().getList()[0].productTitle
                    servicesTwoTxt.text = it.getData().getList()[1].productTitle
                    servicesThreeTxt.text = it.getData().getList()[2].productTitle
                    servicesFourTxt.text = it.getData().getList()[3].productTitle

                }else{
                    Globals.showToastMsg(activity!!.applicationContext,"${resources.getString(R.string.no_services)}")
                }

            }else{
                Globals.showToastMsg(activity!!.applicationContext,"${resources.getString(R.string.server_error_txt)}")
            }

        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomeScreen).selectHomeDashboard()
        serviceVwAlHdngTxt.setOnClickListener {
            (activity as HomeScreen).displaySPFragment()
        }

        awtngPckupsVwAlHdngTxt.setOnClickListener {
            (activity as HomeScreen).displayMyOrdersFragment()
        }

    }

    override fun onResume() {
        super.onResume()
        getTopServices()
    }

}