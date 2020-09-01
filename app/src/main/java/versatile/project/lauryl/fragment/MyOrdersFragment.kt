package versatile.project.lauryl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.my_orders_fragment.*
import versatile.project.lauryl.R
import versatile.project.lauryl.adapter.AwaitingCompleteAdapter
import versatile.project.lauryl.adapter.AwaitingDevliveryAdapter
import versatile.project.lauryl.adapter.AwaitingPckUpsAdapter
import versatile.project.lauryl.model.AwaitingCompleteModel
import versatile.project.lauryl.model.AwaitingDeliveryModel
import versatile.project.lauryl.model.AwaitingPickUpModel
import versatile.project.lauryl.utils.Globals

class MyOrdersFragment : Fragment() {

    private var awtngPckUpAdapter: AwaitingPckUpsAdapter? = null
    private var awtngDlvryAdapter: AwaitingDevliveryAdapter? = null
    private var awtngCmpltdAdapter: AwaitingCompleteAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_orders_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLyot.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Handle tab select
                when (tab!!.position) {
                    0 -> {
                        setUpAwaitingPckUps()
                    }
                    1 -> {
                        setUpAwaitingDelvrs()
                    }
                    2 -> {
                        setUpAwaitingCompleted()
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
                Globals.showToastMsg(activity!!.applicationContext, "${tab!!.position} re-selected")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }

        })
        setUpAwaitingPckUps()
    }

    private fun setUpAwaitingCompleted() {
        if (awtngCmpltdAdapter == null) {
            awtngCmpltdAdapter =
                AwaitingCompleteAdapter(activity!!.applicationContext, getAwtngCmpltdInfoDta())
        }
        myOrdrsRcyclrVw.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        myOrdrsRcyclrVw.adapter = awtngCmpltdAdapter!!
    }

    private fun setUpAwaitingDelvrs() {
        if (awtngDlvryAdapter == null) {
            awtngDlvryAdapter =
                AwaitingDevliveryAdapter(activity!!.applicationContext, getAwtngDlvryInfoDta())
        }
        myOrdrsRcyclrVw.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        myOrdrsRcyclrVw.adapter = awtngDlvryAdapter!!
    }

    private fun setUpAwaitingPckUps() {
        if (awtngPckUpAdapter == null) {
            awtngPckUpAdapter = AwaitingPckUpsAdapter(
                activity,
                activity!!.applicationContext,
                getAwtngPckUpInfoDta()
            )
        }
        myOrdrsRcyclrVw.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        myOrdrsRcyclrVw.adapter = awtngPckUpAdapter!!
    }

    private fun getAwtngPckUpInfoDta(): ArrayList<AwaitingPickUpModel>? {
        var awtngPickUpDtaLst = ArrayList<AwaitingPickUpModel>()
        val itemOne = AwaitingPickUpModel()
        itemOne.orderIdVal = "1252"
        itemOne.date = "08 Dec 2019"
        itemOne.time = "11:00 - 12:30 PM"
        itemOne.pickUpAddress =
            "16-12-489, Sri Ram Nagar, Gowilpuram road, MCH Colony, Hyderabad 500053"
        awtngPickUpDtaLst.add(itemOne)
        awtngPickUpDtaLst.add(itemOne)
        awtngPickUpDtaLst.add(itemOne)
        awtngPickUpDtaLst.add(itemOne)
        awtngPickUpDtaLst.add(itemOne)
        awtngPickUpDtaLst.add(itemOne)
        return awtngPickUpDtaLst
    }

    private fun getAwtngDlvryInfoDta(): ArrayList<AwaitingDeliveryModel>? {
        var getAwtngDlvryInfoDta = ArrayList<AwaitingDeliveryModel>()
        val itemOne = AwaitingDeliveryModel()
        itemOne.orderIdVal = "1252"
        itemOne.date = "08 Dec 2019"
        itemOne.time = "11:00 - 12:30 PM"
        itemOne.pickUpAddress =
            "16-12-489, Sri Ram Nagar, Gowilpuram road, MCH Colony, Hyderabad 500053"
        getAwtngDlvryInfoDta.add(itemOne)
        getAwtngDlvryInfoDta.add(itemOne)
        getAwtngDlvryInfoDta.add(itemOne)
        getAwtngDlvryInfoDta.add(itemOne)
        getAwtngDlvryInfoDta.add(itemOne)
        getAwtngDlvryInfoDta.add(itemOne)
        return getAwtngDlvryInfoDta
    }

    private fun getAwtngCmpltdInfoDta(): ArrayList<AwaitingCompleteModel>? {
        var getAwtngCmpltdInfoDta = ArrayList<AwaitingCompleteModel>()
        val itemOne = AwaitingCompleteModel()
        itemOne.orderIdVal = "1252"
        itemOne.date = "08 Dec 2019"
        itemOne.time = "11:00 - 12:30 PM"
        itemOne.pickUpAddress =
            "16-12-489, Sri Ram Nagar, Gowilpuram road, MCH Colony, Hyderabad 500053"
        getAwtngCmpltdInfoDta.add(itemOne)
        getAwtngCmpltdInfoDta.add(itemOne)
        getAwtngCmpltdInfoDta.add(itemOne)
        getAwtngCmpltdInfoDta.add(itemOne)
        getAwtngCmpltdInfoDta.add(itemOne)
        getAwtngCmpltdInfoDta.add(itemOne)
        return getAwtngCmpltdInfoDta
    }

}