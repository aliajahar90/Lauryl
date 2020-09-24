package versatile.project.lauryl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.order_history_fragment.*
import versatile.project.lauryl.R
import versatile.project.lauryl.screens.HomeScreen

class OrderHistoryFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

    }

}