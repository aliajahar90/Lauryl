package versatile.project.lauryl.orders.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.order_history_fragment.*
import versatile.project.lauryl.R
import versatile.project.lauryl.base.BaseFragment
import versatile.project.lauryl.orders.history.adapter.OrderListAdapter
import versatile.project.lauryl.orders.history.model.ServiceItemType
import versatile.project.lauryl.orders.history.model.ServiceType
import versatile.project.lauryl.screens.HomeScreen


class OrderHistoryFragment: BaseFragment<OrderHistoryFragmentViewModel>() {
    val serviceList = ArrayList<ServiceType>()
    lateinit var orderHistoryFragmentViewModel: OrderHistoryFragmentViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.order_history_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomeScreen).selectOrderHistory()

        washFoldImg.setOnClickListener {
            if(washNFoldItmLyot.visibility == View.VISIBLE){
                washNFoldItmLyot.visibility = View.GONE
                washFoldImg.setImageResource(R.drawable.down_arrow_icon)
            }else{
                washNFoldItmLyot.visibility = View.VISIBLE
                washFoldImg.setImageResource(R.drawable.up_arrow_icon)
            }
        }

        washNIronImg.setOnClickListener {
            if(washNIronItmLyot.visibility == View.VISIBLE){
                washNIronItmLyot.visibility = View.GONE
                washNIronImg.setImageResource(R.drawable.down_arrow_icon)
            }else{
                washNIronItmLyot.visibility = View.VISIBLE
                washNIronImg.setImageResource(R.drawable.up_arrow_icon)
            }
        }

        val recyclerView = listOrderHistory as RecyclerView
       // recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity)

        //instantiate your adapter with the list of genres
        //instantiate your adapter with the list of genres
        val adapter =
            OrderListAdapter(
                serviceList
            )
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator=DefaultItemAnimator()
        recyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
      //  orderHistoryFragmentViewModel.getOrderItems()
    }

    override fun initializeViewModel() {
        orderHistoryFragmentViewModel=ViewModelProvider(this).get(OrderHistoryFragmentViewModel::class.java)
    }


}