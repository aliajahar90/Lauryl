package versatile.project.lauryl.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import versatile.project.lauryl.R
import versatile.project.lauryl.model.address.AddressModel


class EditAddressAdapter(
    private val list: List<AddressModel>,
    private var editDeleteListener: EditDeleteListener
) : RecyclerView.Adapter<EditAddressViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditAddressViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EditAddressViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: EditAddressViewHolder, position: Int) {

        val addressModel: AddressModel = list[position]
        holder.bind(addressModel)
        holder.editBtn?.setOnClickListener { editDeleteListener.editClicked(position) }
        holder.deleteBtn?.setOnClickListener { editDeleteListener.deleteClicked(addressModel.id!!,position) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class EditAddressViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.edit_address_item, parent, false)) {
    private var mTitleView: TextView? = null
    private var address: TextView? = null
    var editBtn: TextView? = null
    var deleteBtn: TextView? = null


    init {
        mTitleView = itemView.findViewById(R.id.address_type)
        address = itemView.findViewById(R.id.address)
        editBtn = itemView.findViewById(R.id.edit_address_btn)
        deleteBtn = itemView.findViewById(R.id.delete_address_btn)
    }

    fun bind(addressModel: AddressModel) {
        mTitleView?.text = addressModel.addresType
        address?.text = "${addressModel.address1},${addressModel.landmark},${addressModel.streetName},"
    }

}