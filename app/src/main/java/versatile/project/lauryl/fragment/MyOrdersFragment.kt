package versatile.project.lauryl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.my_orders_fragment.*
import versatile.project.lauryl.R
import versatile.project.lauryl.adapter.AwaitingCompleteAdapter
import versatile.project.lauryl.adapter.AwaitingDevliveryAdapter
import versatile.project.lauryl.adapter.AwaitingPckUpsAdapter
import versatile.project.lauryl.application.MyApplication
import versatile.project.lauryl.model.AwaitingCompleteModel
import versatile.project.lauryl.model.AwaitingDeliveryModel
import versatile.project.lauryl.model.AwaitingPickUpModel
import versatile.project.lauryl.model.MyOrdersDataItem
import versatile.project.lauryl.screens.HomeScreen
import versatile.project.lauryl.utils.Globals
import versatile.project.lauryl.view.model.MyOrdersViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyOrdersFragment: Fragment() {

    private var awtngPckUpAdapter:AwaitingPckUpsAdapter? = null
    private var awtngDlvryAdapter:AwaitingDevliveryAdapter? = null
    private var awtngCmpltdAdapter:AwaitingCompleteAdapter? = null
    lateinit var myOrdersViewModel: MyOrdersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myOrdersViewModel = ViewModelProvider(this).get(MyOrdersViewModel::class.java)
        observeDataSources()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_orders_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLyot.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Handle tab select
                when(tab!!.position){
                    0 ->{
                        setUpAwaitingPckUps(myOrdersViewModel.awaitingPckUpDtaLst)
                    }
                    1 ->{
                        setUpAwaitingDelvrs(myOrdersViewModel.awaitingPckUpDevryDtaLst)
                    }
                    2->{
                        setUpAwaitingCompleted(myOrdersViewModel.awaitingPckUpCompletedDtaLst)
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
                Globals.showToastMsg(activity!!.applicationContext,"${tab!!.position} re-selected")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }

        })
        setUpAwaitingPckUps(myOrdersViewModel.awaitingPckUpDtaLst)
        (activity as HomeScreen).selectMyOrdersDashboard()
        if(myOrdersViewModel.awaitingPckUpDtaLst.size > 0){
            setUpAwaitingPckUps(myOrdersViewModel.awaitingPckUpDtaLst)
        }
    }

    private fun getMyOrdersData() {
        val inputJson = JsonObject()
        inputJson.addProperty("currentPage",0)
        inputJson.addProperty("pageSize",100)
        inputJson.addProperty("sort","DESC")
        inputJson.addProperty("sortBy","createdAt")
        //inputJson.addProperty("search","[]")
        val myApplication: MyApplication = (activity!!.applicationContext as MyApplication)
        if(myApplication != null){
            myOrdersViewModel.getMyOrders(myApplication.accessToken,inputJson)
        }
    }

    private fun observeDataSources() {
        myOrdersViewModel.getMyOrdersResponseToObserver().observe(this, Observer {
            if(it != null){

                if(it.data.totalCount > 0){

                    setUpMyOrdersData(it.data.list)

                }else{
                    Globals.showToastMsg(activity!!.applicationContext,"${resources.getString(R.string.no_orders)}")
                }

            }else{
                Globals.showToastMsg(activity!!.applicationContext,"${resources.getString(R.string.server_error_txt)}")
            }
        })
    }

    private fun setUpMyOrdersData(myOrdersDtaLst: MutableList<MyOrdersDataItem>) {

        for(dataItem in myOrdersDtaLst){
            when(dataItem.orderStage){

                getString(R.string.model_awaiting_pckup) -> {
                    val dateTimeString = getDateTimeString(dataItem.orderDateTime)
                    myOrdersViewModel.addAwaitingPckUpDta(AwaitingPickUpModel((dataItem.orderNumber),dateTimeString,"","${dataItem.shippingAddress1},${dataItem.shippingAddress2},${dataItem.shippingAddress3},${dataItem.shippingCity},${dataItem.shippingState},${dataItem.shippingCountry},${dataItem.shippingPostCode}"))
                }

                getString(R.string.model_awaiting_dlvry) ->{
                    val dateTimeString = getDateTimeString(dataItem.orderDateTime)
                    myOrdersViewModel.addAwaitingDlvryDta(AwaitingDeliveryModel((dataItem.orderNumber),dateTimeString,"","${dataItem.shippingAddress1},${dataItem.shippingAddress2},${dataItem.shippingAddress3},${dataItem.shippingCity},${dataItem.shippingState},${dataItem.shippingCountry},${dataItem.shippingPostCode}"))
                }

                getString(R.string.model_awaiting_cmpltd) ->{
                    val dateTimeString = getDateTimeString(dataItem.orderDateTime)
                    myOrdersViewModel.addAwaitingPckUpCmpltdDta(AwaitingCompleteModel((dataItem.orderNumber),dateTimeString,"","${dataItem.shippingAddress1},${dataItem.shippingAddress2},${dataItem.shippingAddress3},${dataItem.shippingCity},${dataItem.shippingState},${dataItem.shippingCountry},${dataItem.shippingPostCode}"))
                }

            }
        }
        setUpAwaitingPckUps(myOrdersViewModel.awaitingPckUpDtaLst)

    }

    private fun getDateTimeString(orderDateTime: Long): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm aa")
        val date = Date(orderDateTime)
        return dateFormat.format(date)
    }

    private fun setUpAwaitingCompleted(awaitingPckUpCompletedDtaLst: ArrayList<AwaitingCompleteModel>) {

        if(awaitingPckUpCompletedDtaLst.size == 0){
            myOrdrsRcyclrVw.visibility = View.GONE
            noDtaTxt.visibility = View.VISIBLE
            return
        }else{
            noDtaTxt.visibility = View.GONE
            myOrdrsRcyclrVw.visibility = View.VISIBLE
        }

        if(awtngCmpltdAdapter == null){
            awtngCmpltdAdapter = AwaitingCompleteAdapter(activity!!.applicationContext,awaitingPckUpCompletedDtaLst)
            myOrdrsRcyclrVw.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        }else{
            awtngCmpltdAdapter!!.setNewAwaitingCmpltdList(awaitingPckUpCompletedDtaLst)
        }

        myOrdrsRcyclrVw.adapter = awtngCmpltdAdapter!!
        awtngCmpltdAdapter!!.notifyDataSetChanged()
    }

    private fun setUpAwaitingDelvrs(awaitingPckUpDevryDtaLst: ArrayList<AwaitingDeliveryModel>) {

        if(awaitingPckUpDevryDtaLst.size == 0){
            myOrdrsRcyclrVw.visibility = View.GONE
            noDtaTxt.visibility = View.VISIBLE
            return
        }else{
            noDtaTxt.visibility = View.GONE
            myOrdrsRcyclrVw.visibility = View.VISIBLE
        }

        if(awtngDlvryAdapter == null){
            awtngDlvryAdapter = AwaitingDevliveryAdapter(activity!!.applicationContext,awaitingPckUpDevryDtaLst)
            myOrdrsRcyclrVw.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        }else{
            awtngDlvryAdapter!!.setNewAwaitingPckUpsList(awaitingPckUpDevryDtaLst)
        }
        myOrdrsRcyclrVw.adapter = awtngDlvryAdapter!!
        awtngDlvryAdapter!!.notifyDataSetChanged()

    }

    private fun setUpAwaitingPckUps(awaitingPckUpDtaLst: ArrayList<AwaitingPickUpModel>) {

        if(awaitingPckUpDtaLst.size == 0){
            myOrdrsRcyclrVw.visibility = View.GONE
            noDtaTxt.visibility = View.VISIBLE
            return
        }else{
            noDtaTxt.visibility = View.GONE
            myOrdrsRcyclrVw.visibility = View.VISIBLE
        }

        if(awtngPckUpAdapter == null){
            awtngPckUpAdapter = AwaitingPckUpsAdapter(activity,activity!!.applicationContext,awaitingPckUpDtaLst)
            myOrdrsRcyclrVw.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        }else{
            awtngPckUpAdapter!!.setNewAwaitingPckUpsList(awaitingPckUpDtaLst)
        }
        myOrdrsRcyclrVw.adapter = awtngPckUpAdapter!!
        awtngPckUpAdapter!!.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        getMyOrdersData()
    }

}