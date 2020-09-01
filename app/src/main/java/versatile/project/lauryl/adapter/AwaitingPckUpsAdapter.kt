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
import versatile.project.lauryl.screens.HomeScreen

class AwaitingPckUpsAdapter(
    var activity: FragmentActivity?,
    var context: Context,
    var awtngPckUpList: ArrayList<AwaitingPickUpModel>?
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
        requiredViewHolder.mainLyot.setOnClickListener {
            (activity as HomeScreen).displayOrderHstryFragment()
        }
    }

    fun setNewAwaitingPckUpsList(awtngPckUpList: ArrayList<AwaitingPickUpModel>) {
        this.awtngPckUpList = awtngPckUpList
    }

    fun clearSAwaitingPckUpsList() {
        this.awtngPckUpList!!.clear()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mainLyot: LinearLayout = itemView.mainLyot
        var orderIdTxt = itemView.orderIdTxt
        var ordrDteTme: TextView? = itemView.ordrDteTme
        var pckUpAdrsTxt = itemView.pckUpAdrsTxt

        fun bindDta(awtngPckUp: AwaitingPickUpModel) {
            orderIdTxt.text = "Order No. ${awtngPckUp.orderIdVal}"
            ordrDteTme!!.text = "${awtngPckUp.date} & ${awtngPckUp.time}"
            pckUpAdrsTxt.text = awtngPckUp.pickUpAddress
        }

    }

}