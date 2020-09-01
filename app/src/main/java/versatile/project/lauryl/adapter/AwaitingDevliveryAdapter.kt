package versatile.project.lauryl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.awaiting_pckup_lst_item.view.*
import versatile.project.lauryl.R
import versatile.project.lauryl.model.AwaitingDeliveryModel
import versatile.project.lauryl.model.AwaitingPickUpModel

class AwaitingDevliveryAdapter(
    var context: Context,
    var awtngDlvryList: ArrayList<AwaitingDeliveryModel>?
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
    }

    fun setNewAwaitingPckUpsList(awtngDlvryList: ArrayList<AwaitingDeliveryModel>) {
        this.awtngDlvryList = awtngDlvryList
    }

    fun clearSAwaitingPckUpsList() {
        this.awtngDlvryList!!.clear()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var orderIdTxt = itemView.orderIdTxt
        var ordrDteTme: TextView? = itemView.ordrDteTme
        var pckUpAdrsTxt = itemView.pckUpAdrsTxt

        fun bindDta(awtngDlvry: AwaitingDeliveryModel) {
            orderIdTxt.text = "Order No. ${awtngDlvry.orderIdVal}"
            ordrDteTme!!.text = "${awtngDlvry.date} & ${awtngDlvry.time}"
            pckUpAdrsTxt.text = awtngDlvry.pickUpAddress
        }

    }

}