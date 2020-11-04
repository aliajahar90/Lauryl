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
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import kotlinx.android.synthetic.main.my_orders_fragment.*
import versatile.project.lauryl.R
import versatile.project.lauryl.adapter.AwaitingCompleteAdapter
import versatile.project.lauryl.adapter.AwaitingDevliveryAdapter
import versatile.project.lauryl.adapter.AwaitingPckUpsAdapter
import versatile.project.lauryl.adapter.RescheduleCancelListener
import versatile.project.lauryl.application.MyApplication
import versatile.project.lauryl.base.BaseActivity
import versatile.project.lauryl.model.AwaitingCompleteModel
import versatile.project.lauryl.model.AwaitingDeliveryModel
import versatile.project.lauryl.model.AwaitingPickUpModel
import versatile.project.lauryl.model.MyOrdersDataItem
import versatile.project.lauryl.pickup.data.PickUpSharedData
import versatile.project.lauryl.screens.HomeScreen
import versatile.project.lauryl.utils.Globals
import versatile.project.lauryl.view.model.MyOrdersViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyOrdersFragment : Fragment() {

    private var awtngPckUpAdapter: AwaitingPckUpsAdapter? = null
    private var awtngDlvryAdapter: AwaitingDevliveryAdapter? = null
    private var awtngCmpltdAdapter: AwaitingCompleteAdapter? = null
    lateinit var myOrdersViewModel: MyOrdersViewModel
    private var seletectTab = 0
    var mGson:Gson?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myOrdersViewModel = ViewModelProvider(this).get(MyOrdersViewModel::class.java)
        observeDataSources()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mGson= Gson();
        seletectTab = (activity as HomeScreen).myApplication.selectedOrderTab
        return inflater.inflate(R.layout.my_orders_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLyot.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Handle tab select
                when (tab?.position) {
                    0 -> {
                        setUpAwaitingPckUps(myOrdersViewModel.awaitingPckUpDtaLst)
                    }
                    1 -> {
                        setUpAwaitingDelvrs(myOrdersViewModel.awaitingPckUpDevryDtaLst)
                    }
                    2 -> {
                        setUpAwaitingCompleted(myOrdersViewModel.awaitingPckUpCompletedDtaLst)
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
                if (tab!!.position == 0) {
                    setUpAwaitingPckUps(myOrdersViewModel.awaitingPckUpDtaLst)
                }
                // Globals.showToastMsg(activity!!.applicationContext, "${tab!!.position} re-selected")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }

        })
        //  setUpAwaitingPckUps(myOrdersViewModel.awaitingPckUpDtaLst)
        (activity as HomeScreen).selectMyOrdersDashboard()
//        if (myOrdersViewModel.awaitingPckUpDtaLst.size > 0) {
//            setUpAwaitingPckUps(myOrdersViewModel.awaitingPckUpDtaLst)
//        }


    }

    private fun getMyOrdersData() {
        val myApplication: MyApplication = (activity!!.applicationContext as MyApplication)
        val inputJson = JsonObject()
        inputJson.addProperty("currentPage", 0)
        inputJson.addProperty("pageSize", 100)
        inputJson.addProperty("sort", "DESC")
        inputJson.addProperty("sortBy", "orderDateTime")
        val searchJson = JsonObject()
        searchJson.addProperty("key", "phoneNumber")
        if (myApplication != null)
            searchJson.addProperty("value", myApplication.mobileNumber)
        searchJson.addProperty("operation", "eq")
        val searchArray = JsonArray()
        searchArray.add(searchJson)
        inputJson.add("search", searchArray)
        //inputJson.addProperty("search","[]")
        if (myApplication != null) {
            myOrdersViewModel.getMyOrders(myApplication.accessToken, inputJson)
        }
    }

    private fun observeDataSources() {
        myOrdersViewModel.getMyOrdersResponseToObserver().observe(this, Observer {
            if (it != null) {

                if (it.data.totalCount > 0) {

                    setUpMyOrdersData(it.data.list)

                } else {
                    (activity as BaseActivity).hideLoading()

                    Globals.showToastMsg(
                        activity!!.applicationContext,
                        "${resources.getString(R.string.no_orders)}"
                    )
                }

            } else {
                (activity as BaseActivity).hideLoading()
                Globals.showToastMsg(
                    activity!!.applicationContext,
                    "${resources.getString(R.string.server_error_txt)}"
                )
            }
        })

        myOrdersViewModel.cancelOrderSuccessObserver().observe(this, Observer {
            if(it.status){
                getMyOrdersData()
                (activity as HomeScreen).shout("Cancelled order..")
            }else{
                (activity as HomeScreen).shout("Cancelled order failed")
            }
        })
        myOrdersViewModel.cancelOrderErrorObserver().observe(this, Observer {
            (activity as HomeScreen).shout(it)
        })
    }

    private fun setUpMyOrdersData(myOrdersDtaLst: MutableList<MyOrdersDataItem>) {
        myOrdersViewModel.clearAwaitingPckUpCmpltdDta()
        myOrdersViewModel.clearAwaitingPckUpDlvryDta()
        myOrdersViewModel.clearAwaitingPckUpDta()
        for (dataItem in myOrdersDtaLst) {
            when (dataItem.orderStage) {

                getString(R.string.model_awaiting_pckup) -> {
                   // val dateTimeString = getDateTimeString(dataItem.orderDateTime)
                    val dateTimeString =dataItem.pickupDate +" & "+ dataItem.pickupSlot

                    myOrdersViewModel.addAwaitingPckUpDta(
                        AwaitingPickUpModel(
                            (dataItem.orderNumber),
                            dateTimeString,
                            "",
                            "${dataItem.shippingAddress1},${dataItem.shippingAddress2},${dataItem.shippingAddress3},${dataItem.shippingCity},${dataItem.shippingState},${dataItem.shippingCountry},${dataItem.shippingPostCode}",
                            dataItem
                        )
                    )
                }

                getString(R.string.model_awaiting_dlvry) -> {
                   // val dateTimeString = getDateTimeString(dataItem.orderDateTime)
                    val dateTimeString =dataItem.pickupDate +" & "+ dataItem.pickupSlot
                    myOrdersViewModel.addAwaitingDlvryDta(
                        AwaitingDeliveryModel(
                            (dataItem.orderNumber),
                            dateTimeString,
                            "",
                            "${dataItem.shippingAddress1},${dataItem.shippingAddress2},${dataItem.shippingAddress3},${dataItem.shippingCity},${dataItem.shippingState},${dataItem.shippingCountry},${dataItem.shippingPostCode}"
                        )
                    )
                }

                getString(R.string.model_awaiting_cmpltd) -> {
                  //  val dateTimeString = getDateTimeString(dataItem.orderDateTime)
                    val dateTimeString =dataItem.pickupDate +" & "+ dataItem.pickupSlot
                    myOrdersViewModel.addAwaitingPckUpCmpltdDta(
                        AwaitingCompleteModel(
                            (dataItem.orderNumber),
                            dateTimeString,
                            "",
                            "${dataItem.shippingAddress1},${dataItem.shippingAddress2},${dataItem.shippingAddress3},${dataItem.shippingCity},${dataItem.shippingState},${dataItem.shippingCountry},${dataItem.shippingPostCode}",dataItem.orderStage
                        )
                    )
                }
                getString(R.string.model_cancelled) -> {
                    //val dateTimeString = getDateTimeString(dataItem.orderDateTime)
                    val dateTimeString =dataItem.pickupDate +" & "+ dataItem.pickupSlot
                    myOrdersViewModel.addAwaitingPckUpCmpltdDta(
                        AwaitingCompleteModel(
                            (dataItem.orderNumber),
                            dateTimeString,
                            "",
                            "${dataItem.shippingAddress1},${dataItem.shippingAddress2},${dataItem.shippingAddress3},${dataItem.shippingCity},${dataItem.shippingState},${dataItem.shippingCountry},${dataItem.shippingPostCode}",dataItem.orderStage
                        )
                    )
                }

            }
        }

        tabLyot.getTabAt(seletectTab)?.select()
        (activity as BaseActivity).hideLoading()
        //setUpAwaitingPckUps(myOrdersViewModel.awaitingPckUpDtaLst)

    }

    private fun getDateTimeString(orderDateTime: Long): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm aa")
        val date = Date(orderDateTime)
        return dateFormat.format(date)
    }

    private fun setUpAwaitingCompleted(awaitingPckUpCompletedDtaLst: ArrayList<AwaitingCompleteModel>) {

        if (awaitingPckUpCompletedDtaLst.size == 0) {
            myOrdrsRcyclrVw.visibility = View.GONE
            noDtaTxt.visibility = View.VISIBLE
            return
        } else {
            noDtaTxt.visibility = View.GONE
            myOrdrsRcyclrVw.visibility = View.VISIBLE
        }
        myOrdrsRcyclrVw.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        if (awtngCmpltdAdapter == null) {
            awtngCmpltdAdapter =
                AwaitingCompleteAdapter(
                    activity,
                    activity!!.applicationContext,
                    awaitingPckUpCompletedDtaLst
                )
        } else {
            awtngCmpltdAdapter!!.setNewAwaitingCmpltdList(awaitingPckUpCompletedDtaLst)
        }

        myOrdrsRcyclrVw.adapter = awtngCmpltdAdapter!!
        awtngCmpltdAdapter!!.notifyDataSetChanged()
    }

    private fun setUpAwaitingDelvrs(awaitingPckUpDevryDtaLst: ArrayList<AwaitingDeliveryModel>) {

        if (awaitingPckUpDevryDtaLst.size == 0) {
            myOrdrsRcyclrVw.visibility = View.GONE
            noDtaTxt.visibility = View.VISIBLE
            return
        } else {
            noDtaTxt.visibility = View.GONE
            myOrdrsRcyclrVw.visibility = View.VISIBLE
        }
        myOrdrsRcyclrVw.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        if (awtngDlvryAdapter == null) {
            awtngDlvryAdapter =
                AwaitingDevliveryAdapter(
                    activity,
                    activity!!.applicationContext,
                    awaitingPckUpDevryDtaLst
                )
        } else {
            awtngDlvryAdapter!!.setNewAwaitingPckUpsList(awaitingPckUpDevryDtaLst)
        }
        myOrdrsRcyclrVw.adapter = awtngDlvryAdapter!!
        awtngDlvryAdapter!!.notifyDataSetChanged()

    }

    private fun setUpAwaitingPckUps(awaitingPckUpDtaLst: ArrayList<AwaitingPickUpModel>) {
        if (awaitingPckUpDtaLst.size == 0) {
            myOrdrsRcyclrVw.visibility = View.GONE
            noDtaTxt.visibility = View.VISIBLE
            return
        } else {
            noDtaTxt.visibility = View.GONE
            myOrdrsRcyclrVw.visibility = View.VISIBLE
        }
        myOrdrsRcyclrVw.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        if (awtngPckUpAdapter == null) {
            awtngPckUpAdapter =
                AwaitingPckUpsAdapter(activity, activity!!.applicationContext, awaitingPckUpDtaLst,object :RescheduleCancelListener{
                    override fun rescheduleClicked(position: Int,myOrdersDataItem: MyOrdersDataItem) {
                        val pickUpSharedData = PickUpSharedData()
                        pickUpSharedData.myOrdersDataItem=myOrdersDataItem;
                        val orderData= this@MyOrdersFragment.mGson!!.toJson(pickUpSharedData)
                        (activity as HomeScreen).displayReschedulePickUpFragment(orderData)
                    }

                    override fun cancelClicked(position: Int,myOrdersDataItem: MyOrdersDataItem) {
                        val myApplication: MyApplication = (activity!!.applicationContext as MyApplication)
                        val inputJson = JsonObject()
                        val jArray = JsonArray()
                        val element = JsonPrimitive(myOrdersDataItem.orderNumber);
                        jArray.add(element)
                        inputJson.add("orderIds", jArray)
                        inputJson.addProperty("stage", "Canceled")
                        if (myApplication != null)
                        myOrdersViewModel.cancelOrder(myApplication.accessToken,inputJson)
                    }

                })
        } else {
            awtngPckUpAdapter!!.setNewAwaitingPckUpsList(awaitingPckUpDtaLst)
        }
        myOrdrsRcyclrVw.adapter = awtngPckUpAdapter!!
        awtngPckUpAdapter!!.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        (activity as HomeScreen).myApplication.selectedOrderTab = tabLyot.selectedTabPosition
    }

    override fun onResume() {
        super.onResume()
        (activity as BaseActivity).showLoading()
        getMyOrdersData()
    }

}