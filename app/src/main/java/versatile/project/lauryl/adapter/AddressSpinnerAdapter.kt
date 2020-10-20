package versatile.project.lauryl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import versatile.project.lauryl.R
import versatile.project.lauryl.model.address.AddressModel

class AddressSpinnerAdapter(val context: Context, private var dataSource: List<AddressModel>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.address_item, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.type.text = dataSource[position].addresType
        vh.address.text = dataSource[position].toString()



        return view
    }

    override fun getItem(position: Int): Any? {
        return dataSource[position];
    }

    override fun getCount(): Int {
        return dataSource.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View?) {
        val type: TextView = row?.findViewById(R.id.address_type) as TextView
        val address: TextView = row?.findViewById(R.id.address) as TextView

    }


}