package versatile.project.lauryl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.awaiting_compltd_lst_item.view.*
import kotlinx.android.synthetic.main.awaiting_pckup_lst_item.view.*
import kotlinx.android.synthetic.main.awaiting_pckup_lst_item.view.orderIdTxt
import kotlinx.android.synthetic.main.awaiting_pckup_lst_item.view.ordrDteTme
import kotlinx.android.synthetic.main.awaiting_pckup_lst_item.view.pckUpAdrsTxt
import versatile.project.lauryl.R
import versatile.project.lauryl.model.AwaitingCompleteModel
import versatile.project.lauryl.orders.history.model.OrderData
import versatile.project.lauryl.screens.HomeScreen
import versatile.project.lauryl.utils.AllConstants

class AwaitingCompleteAdapter(var activity: FragmentActivity?, var context: Context, var awtngCmpltdList:ArrayList<AwaitingCompleteModel>?): RecyclerView.Adapter<AwaitingCompleteAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView: View = LayoutInflater.from(context).inflate(R.layout.awaiting_compltd_lst_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return awtngCmpltdList!!.size
    }

    override fun onBindViewHolder(requiredViewHolder: MyViewHolder, position: Int) {
        requiredViewHolder.bindDta(awtngCmpltdList!![position])
        requiredViewHolder.mainLyot.setOnClickListener {
            val orderDate= OrderData()
            orderDate.orderIdVal=this.awtngCmpltdList!!.get(position).orderIdVal
            orderDate.date=this.awtngCmpltdList!!.get(position).date
            orderDate.orderStage=AllConstants.Orders.OrderStage.Completed_Delivery
            var jsonString =Gson().toJson(orderDate)
            (activity as HomeScreen).displayOrderHstryFragment(jsonString)
        }
    }

    fun setNewAwaitingCmpltdList(awtngCmpltdList: ArrayList<AwaitingCompleteModel>) {
        this.awtngCmpltdList = awtngCmpltdList
    }

    fun clearAwaitingCmpltdList() {
        this.awtngCmpltdList!!.clear()
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var mainLyot=itemView.linMain
        var orderIdTxt = itemView.orderIdTxt
        var ordrDteTme : TextView? = itemView.ordrDteTme
        var pckUpAdrsTxt = itemView.pckUpAdrsTxt
        var orderStage = itemView.txtOrderStage

        fun bindDta(awtngDlvry: AwaitingCompleteModel){
            if(awtngDlvry.orderStage.equals(itemView.context.resources.getString(R.string.model_cancelled))) {
                orderIdTxt.text = "Order Id. ${awtngDlvry.orderIdVal}"
                ordrDteTme!!.text = "${awtngDlvry.date}"
                pckUpAdrsTxt.text = awtngDlvry.pickUpAddress
                orderStage.text=itemView.context.resources.getString(R.string.cancelled_txt)
                orderStage.setBackgroundColor(itemView.context.resources.getColor(R.color.lit_red))
            }else{
                orderIdTxt.text = "Order Id. ${awtngDlvry.orderIdVal}"
                ordrDteTme!!.text = "${awtngDlvry.date}"
                pckUpAdrsTxt.text = awtngDlvry.pickUpAddress
                orderStage.text=itemView.context.resources.getString(R.string.completed_txt)
                orderStage.setBackgroundColor(itemView.context.resources.getColor(R.color.lit_green))

            }
        }

    }

}