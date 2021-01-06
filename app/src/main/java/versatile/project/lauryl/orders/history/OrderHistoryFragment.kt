package versatile.project.lauryl.orders.history

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.thefinestartist.finestwebview.FinestWebView
import kotlinx.android.synthetic.main.order_history_fragment.*
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber
import versatile.project.lauryl.R
import versatile.project.lauryl.application.MyApplication
import versatile.project.lauryl.base.BaseFragment
import versatile.project.lauryl.model.MyOrdersDataItem
import versatile.project.lauryl.network.api.RetrofitObj
import versatile.project.lauryl.orders.history.adapter.OrderListAdapter
import versatile.project.lauryl.orders.history.model.ServiceType
import versatile.project.lauryl.screens.HomeScreen
import versatile.project.lauryl.utils.AllConstants
import java.lang.Long


class OrderHistoryFragment : BaseFragment<OrderHistoryFragmentViewModel>() {
    val serviceList = ArrayList<ServiceType>()
    var adapter: OrderListAdapter? = null;
    lateinit var orderHistoryFragmentViewModel: OrderHistoryFragmentViewModel
    var orderModel = JsonObject()
    var ordersDataItem= MyOrdersDataItem()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try {
            orderModel = Gson().fromJson(
                arguments!!.getString(AllConstants.Orders.orderData, ""),
                JsonObject::class.java
            )
            Timber.e(orderModel.toString())
            Timber.e(
                orderModel.get("OrderStage").asString + AllConstants.Orders.OrderStage.Completed_Delivery + "${
                    orderModel.get(
                        "OrderStage"
                    ).asString == AllConstants.Orders.OrderStage.Completed_Delivery
                }"
            )

        } catch (e: Exception) {

        }
        //if(orderModel)
        return inflater.inflate(R.layout.order_history_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomeScreen).selectOrderHistory()
        ordersDataItem= Gson().fromJson(orderModel.get("myOrdersDataItem").asString,MyOrdersDataItem::class.java)
        txtOrderNumber.text = "Order Id - " + orderModel.get("orderIdVal").asString
        txtOrderDateTime.text = orderModel.get("date").asString
        txtOrderStage.text = orderModel.get("OrderStage").asString
        if (orderModel.has("zohoInvoiceId") && orderModel.get("zohoInvoiceId").asString.isNotEmpty()) {
            Timber.e("Invoice available")
            invoice_btn.visibility = View.VISIBLE
            invoice_btn.setOnClickListener {
                fetchInvoice(orderModel.get("zohoInvoiceId").asString)
            }

        } else {
            invoice_btn.visibility = View.GONE
        }
    }

    private fun fetchInvoice(invoice: String) {
        (activity as HomeScreen).showLoading()
        RetrofitObj.getVersatileApiObj()?.getInvoice(invoice, myApplication.accessToken)
            ?.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    (activity as HomeScreen).hideLoading()
                    openURL(response.body().toString());
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    (activity as HomeScreen).hideLoading()
                }

            })
    }

    private fun openURL(s: String) {
        activity?.let { FinestWebView.Builder(it).show(s) }
  }
    lateinit var myApplication: MyApplication
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val recyclerView = listOrderHistory as RecyclerView
        // recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        myApplication = (activity!!.applicationContext as MyApplication)
        val inputJson = JsonObject()
        inputJson.addProperty("currentPage", 0)
        inputJson.addProperty("pageSize", 100)
        inputJson.addProperty("sort", "DESC")
        inputJson.addProperty("sortBy", "orderDateTime")
        val searchJson = JsonObject()
        searchJson.addProperty("key", "orderNumber")
        if (myApplication != null)
            searchJson.addProperty("value", orderModel.get("orderIdVal").asString)
        searchJson.addProperty("operation", "eq")
        val searchArray = JsonArray()
        searchArray.add(searchJson)
        inputJson.add("search", searchArray)
        //inputJson.addProperty("search","[]")

        orderHistoryFragmentViewModel.getOrderItems(myApplication.accessToken, inputJson,ordersDataItem)
        orderHistoryFragmentViewModel.onOrderItemReceived().observe(this, Observer {
            it
            serviceList.addAll(it)
            adapter = OrderListAdapter(
                serviceList
                )
            recyclerView.adapter = adapter
            updatePriceInUi()
        })
        orderHistoryFragmentViewModel.onItemError().observe(this, Observer {
            (activity as HomeScreen).shout(it)
        })

    }

    override fun initializeViewModel() {
        orderHistoryFragmentViewModel =
            ViewModelProvider(this).get(OrderHistoryFragmentViewModel::class.java)
    }

    fun updatePriceInUi() {
//        var total: Double = 0.0
//        for (s in serviceList) {
//            for (itemType in s.childList) {
//                total += itemType.productPrice.toDouble()
//            }
//        }
        txtTotal.text = "\u20B9 " + ordersDataItem.orderTotal.toString()
    }

    private fun getOrderDate(date: String): CharSequence? {
        try {
            val milliseconds = date
            val someDate =
                DateTime(Long.valueOf(milliseconds), DateTimeZone.forID("Asia/Kolkata"))
            val dateTimeFormate = DateTimeFormat.forPattern("MMM dd,yyyy")
            return "Placed on " + someDate.toString(dateTimeFormate)
        } catch (e: Exception) {

        }
        return "";
    }

    private fun getOrderTime(date: String): CharSequence? {
        try {
            val milliseconds = date
            val someDate =
                DateTime(Long.valueOf(milliseconds), DateTimeZone.forID("Asia/Kolkata"))
            val dateTimeFormate = DateTimeFormat.forPattern("hh:mm a")
            return "" + someDate.toString(dateTimeFormate).toUpperCase()
        } catch (e: Exception) {

        }
        return "";
    }

}