package versatile.project.lauryl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.awaiting_dlvry_lst_item.view.*
import kotlinx.android.synthetic.main.awaiting_pckup_lst_item.view.orderIdTxt
import kotlinx.android.synthetic.main.awaiting_pckup_lst_item.view.ordrDteTme
import kotlinx.android.synthetic.main.awaiting_pckup_lst_item.view.pckUpAdrsTxt
import timber.log.Timber
import versatile.project.lauryl.R
import versatile.project.lauryl.application.MyApplication
import versatile.project.lauryl.model.AwaitingDeliveryModel
import versatile.project.lauryl.model.MyOrdersDataItem
import versatile.project.lauryl.orders.history.model.OrderData
import versatile.project.lauryl.screens.HomeScreen
import versatile.project.lauryl.utils.AllConstants


interface AwaitingDevliveryListener {
    fun paynow(position: Int, myOrdersDataItem: MyOrdersDataItem)
}


class AwaitingDevliveryAdapter(
    var activity: FragmentActivity?,
    var context: Context, var awtngDlvryList: ArrayList<AwaitingDeliveryModel>?
) : RecyclerView.Adapter<AwaitingDevliveryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.awaiting_dlvry_lst_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return awtngDlvryList!!.size
    }

    override fun onBindViewHolder(requiredViewHolder: MyViewHolder, position: Int) {
        requiredViewHolder.bindDta(awtngDlvryList!![position])
        requiredViewHolder.mainLyot.setOnClickListener {
            val orderDate = OrderData()
            orderDate.orderIdVal = this.awtngDlvryList!!.get(position).orderIdVal
            orderDate.date = this.awtngDlvryList!!.get(position).date
            orderDate.orderStage = AllConstants.Orders.OrderStage.Awaiting_Delivery
            var jsonString = Gson().toJson(orderDate)
            (activity as HomeScreen).displayOrderHstryFragment(jsonString)
        }
        requiredViewHolder.paynow.setOnClickListener {
            val dataItem= awtngDlvryList!![position].dataItem
            val pickupTimingJson = JsonObject()
            pickupTimingJson.addProperty(AllConstants.Orders.totalOrderValue, dataItem.balanceAmount!!)
            (activity?.application as MyApplication).activeSessionOrderValue = Gson().toJson(pickupTimingJson)
            (activity as HomeScreen).displayPaymentPayNowFragment(Gson().toJson(dataItem))
        }
    }

    fun setNewAwaitingPckUpsList(awtngDlvryList: ArrayList<AwaitingDeliveryModel>) {
        this.awtngDlvryList = awtngDlvryList
    }

    fun clearSAwaitingPckUpsList() {
        this.awtngDlvryList!!.clear()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mainLyot: LinearLayout = itemView.linMain
        var orderIdTxt = itemView.orderIdTxt
        var ordrDteTme: TextView? = itemView.ordrDteTme
        var pckUpAdrsTxt = itemView.pckUpAdrsTxt
        var otp = itemView.otp
        var paynow = itemView.paynow_btn

        fun bindDta(awtngDlvry: AwaitingDeliveryModel) {

            Timber.e("pay now status ${awtngDlvry.dataItem.payNow}")
            if (awtngDlvry.dataItem.deliveryOtp !=null && awtngDlvry.dataItem.deliveryOtp.isNotEmpty()) {
                otp.visibility = View.VISIBLE
                otp.text = "OTP: ${awtngDlvry.dataItem.deliveryOtp}"
            } else {
                otp.visibility = View.GONE
                otp.text = ""
            }
            if (awtngDlvry.dataItem.payNow)
            {
                paynow.visibility = View.VISIBLE
            }else
                paynow.visibility = View.GONE

            orderIdTxt.text = "Order Id. ${awtngDlvry.orderIdVal}"
            ordrDteTme!!.text = "${awtngDlvry.date}"
            pckUpAdrsTxt.text = awtngDlvry.pickUpAddress
        }

    }

}