package versatile.project.lauryl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.awaiting_pckup_lst_item.view.*
import versatile.project.lauryl.R
import versatile.project.lauryl.model.AwaitingPickUpModel
import versatile.project.lauryl.model.MyOrdersDataItem
import versatile.project.lauryl.screens.HomeScreen

interface RescheduleCancelListener {
    fun rescheduleClicked(position: Int, myOrdersDataItem: MyOrdersDataItem)
    fun cancelClicked(position: Int, myOrdersDataItem: MyOrdersDataItem)
}

class AwaitingPckUpsAdapter(
    var activity: FragmentActivity?,
    var context: Context,
    var awtngPckUpList: ArrayList<AwaitingPickUpModel>?,
    var rescheduleCancelListener: RescheduleCancelListener
) : RecyclerView.Adapter<AwaitingPckUpsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.awaiting_pckup_lst_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return awtngPckUpList!!.size
    }

    override fun onBindViewHolder(requiredViewHolder: MyViewHolder, position: Int) {
        requiredViewHolder.bindDta(awtngPckUpList!![position])
//        requiredViewHolder.mainLyot.setOnClickListener {
//
//            (activity as HomeScreen).displayOrderHstryFragment()
//        }
        requiredViewHolder.cancelBtn.setOnClickListener {
            rescheduleCancelListener.cancelClicked(
                position,
                awtngPckUpList!!.get(position).myOrdersDataItem
            )
        }
        requiredViewHolder.reschedule_pickup_btn.setOnClickListener {
            rescheduleCancelListener.rescheduleClicked(
                position,
                awtngPckUpList!!.get(position).myOrdersDataItem
            )
        }

    }

    fun setNewAwaitingPckUpsList(awtngPckUpList: ArrayList<AwaitingPickUpModel>) {
        this.awtngPckUpList = awtngPckUpList
    }

    fun clearSAwaitingPckUpsList() {
        this.awtngPckUpList?.clear()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mainLyot: LinearLayout = itemView.mainLyot
        var orderIdTxt = itemView.orderIdTxt
        var ordrDteTme: TextView? = itemView.ordrDteTme
        var pckUpAdrsTxt = itemView.pckUpAdrsTxt
        var reschedule_pickup_btn = itemView.reschedule_pickup_btn
        var cancelBtn: TextView = itemView.cancel_order_btn
        var otp: TextView = itemView.otp

        fun bindDta(awtngPckUp: AwaitingPickUpModel) {

            if (awtngPckUp.myOrdersDataItem.pickupOtp!=null && awtngPckUp.myOrdersDataItem.pickupOtp.isNotEmpty()) {
                otp.visibility=View.VISIBLE
                otp.text = "OTP: ${awtngPckUp.myOrdersDataItem.pickupOtp}"
            }else
                otp.visibility=View.GONE

            orderIdTxt.text = "Order Id. ${awtngPckUp.orderIdVal}"
            ordrDteTme!!.text = "${awtngPckUp.date}"
            pckUpAdrsTxt.text = awtngPckUp.pickUpAddress
        }

    }

}