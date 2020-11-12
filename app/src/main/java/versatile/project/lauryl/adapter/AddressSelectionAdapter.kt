package versatile.project.lauryl.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import versatile.project.lauryl.R
import versatile.project.lauryl.fragment.AddressSelectionFragment
import versatile.project.lauryl.model.address.AddressModel


class AddressSelectionAdapter(
    private val list: List<AddressModel>,
    private var editDeleteListener: AddressSelectionListener
) : RecyclerView.Adapter<AddressSelectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressSelectionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AddressSelectionViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: AddressSelectionViewHolder, position: Int) {

        val addressModel: AddressModel = list[position]
        holder.bind(addressModel)
        holder.itemView.setOnClickListener {
            editDeleteListener.addressSelected(position)
        }
        holder.editBtn?.setOnClickListener {
            editDeleteListener.onEditSelected(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class AddressSelectionViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.address_selection_row, parent, false)) {
    private var mTitleView: RadioButton? = null
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
        mTitleView?.isChecked = addressModel.isSelected
        mTitleView?.text = addressModel.addresType
        address?.text = addressModel.toString()

    }

}