package versatile.project.lauryl.orders.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.order_history_fragment.*
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import versatile.project.lauryl.R
import versatile.project.lauryl.application.MyApplication
import versatile.project.lauryl.base.BaseActivity
import versatile.project.lauryl.base.BaseFragment
import versatile.project.lauryl.model.AwaitingDeliveryModel
import versatile.project.lauryl.orders.history.adapter.OrderListAdapter
import versatile.project.lauryl.orders.history.model.ServiceItemType
import versatile.project.lauryl.orders.history.model.ServiceType
import versatile.project.lauryl.screens.HomeScreen
import versatile.project.lauryl.utils.AllConstants
import java.lang.Exception
import java.lang.Long


class OrderHistoryFragment: BaseFragment<OrderHistoryFragmentViewModel>() {
    val serviceList = ArrayList<ServiceType>()
    var adapter: OrderListAdapter? = null;
    lateinit var orderHistoryFragmentViewModel: OrderHistoryFragmentViewModel
    var orderModel= JsonObject()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        try {
            orderModel = Gson().fromJson(
                arguments!!.getString(AllConstants.Orders.orderData, ""),
                JsonObject::class.java
            )
        }catch (e: Exception){

        }
        return inflater.inflate(R.layout.order_history_fragment,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomeScreen).selectOrderHistory()
        txtOrderNumber.text="Order Id - "+orderModel.get("orderIdVal").asString
        txtOrderDateTime.text=orderModel.get("date").asString
        txtOrderStage.text=orderModel.get("OrderStage").asString

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val recyclerView = listOrderHistory as RecyclerView
        // recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator=DefaultItemAnimator()
        val myApplication: MyApplication = (activity!!.applicationContext as MyApplication)
        val inputJson = JsonObject()
        inputJson.addProperty("currentPage", 0)
        inputJson.addProperty("pageSize", 100)
        inputJson.addProperty("sort", "DESC")
        inputJson.addProperty("sortBy", "orderDateTime")
        val searchJson = JsonObject()
        searchJson.addProperty("key", "orderNumber")
        if (myApplication != null)
            searchJson.addProperty("value",orderModel.get("orderIdVal").asString)
        searchJson.addProperty("operation", "eq")
        val searchArray = JsonArray()
        searchArray.add(searchJson)
        inputJson.add("search", searchArray)
        //inputJson.addProperty("search","[]")
        orderHistoryFragmentViewModel.getOrderItems(myApplication.accessToken,inputJson)
        orderHistoryFragmentViewModel.onOrderItemReceived().observe(this, Observer {it
            serviceList.addAll(it)
            adapter =
                OrderListAdapter(
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
        orderHistoryFragmentViewModel=ViewModelProvider(this).get(OrderHistoryFragmentViewModel::class.java)
    }

    fun updatePriceInUi(){
        var total :Double=0.0
        for(s in serviceList){
           for(itemType in s.childList) {
               total += itemType.productPrice.toDouble()
           }
        }
        txtTotal.text="\u20B9 "+total.toString()
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