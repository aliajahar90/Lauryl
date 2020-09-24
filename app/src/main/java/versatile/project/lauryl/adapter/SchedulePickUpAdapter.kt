package versatile.project.lauryl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.awaiting_pckup_lst_item.view.*
import kotlinx.android.synthetic.main.schedule_pick_up_lst_item.view.*
import versatile.project.lauryl.R
import versatile.project.lauryl.model.AwaitingCompleteModel
import versatile.project.lauryl.model.AwaitingDeliveryModel
import versatile.project.lauryl.model.AwaitingPickUpModel
import versatile.project.lauryl.model.TopServicesDataItem
import versatile.project.lauryl.screens.HomeScreen

class SchedulePickUpAdapter(var context: Context, var topServcsList:ArrayList<TopServicesDataItem>?): RecyclerView.Adapter<SchedulePickUpAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView: View = LayoutInflater.from(context).inflate(R.layout.schedule_pick_up_lst_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return topServcsList!!.size
    }

    override fun onBindViewHolder(requiredViewHolder: MyViewHolder, position: Int) {
        requiredViewHolder.bindDta(topServcsList!![position])
    }

    fun setNewTopSrvcsList(topServcsList:ArrayList<TopServicesDataItem>) {
        this.topServcsList = topServcsList
    }

    fun clearTopSrvcsList() {
        this.topServcsList!!.clear()
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var srvcsItemImg = itemView.srvcsItemImg
        var srvcTitleTxt : TextView? = itemView.srvcTitleTxt
        var srvcDescTxt = itemView.srvcDescTxt

        fun bindDta(topSrvcsDtaItem: TopServicesDataItem){
            srvcTitleTxt!!.text = "${topSrvcsDtaItem.productTitle}"
            srvcDescTxt.text = "${topSrvcsDtaItem.description}"
        }

    }

}