package versatile.project.lauryl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.awaiting_pckup_lst_item.view.*
import versatile.project.lauryl.R
import versatile.project.lauryl.model.AwaitingCompleteModel
import versatile.project.lauryl.model.AwaitingDeliveryModel
import versatile.project.lauryl.model.AwaitingPickUpModel
import versatile.project.lauryl.screens.HomeScreen

class AwaitingCompleteAdapter(var context: Context, var awtngCmpltdList:ArrayList<AwaitingCompleteModel>?): RecyclerView.Adapter<AwaitingCompleteAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView: View = LayoutInflater.from(context).inflate(R.layout.awaiting_compltd_lst_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return awtngCmpltdList!!.size
    }

    override fun onBindViewHolder(requiredViewHolder: MyViewHolder, position: Int) {
        requiredViewHolder.bindDta(awtngCmpltdList!![position])
    }

    fun setNewAwaitingCmpltdList(awtngCmpltdList: ArrayList<AwaitingCompleteModel>) {
        this.awtngCmpltdList = awtngCmpltdList
    }

    fun clearAwaitingCmpltdList() {
        this.awtngCmpltdList!!.clear()
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var orderIdTxt = itemView.orderIdTxt
        var ordrDteTme : TextView? = itemView.ordrDteTme
        var pckUpAdrsTxt = itemView.pckUpAdrsTxt

        fun bindDta(awtngDlvry: AwaitingCompleteModel){
            orderIdTxt.text = "Order Id. ${awtngDlvry.orderIdVal}"
            ordrDteTme!!.text = "${awtngDlvry.date}"
            pckUpAdrsTxt.text = awtngDlvry.pickUpAddress
        }

    }

}