package versatile.project.lauryl.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.home_fragment.*
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import versatile.project.lauryl.R
import versatile.project.lauryl.application.MyApplication
import versatile.project.lauryl.home.viewmodel.HomeFragmentViewModel
import versatile.project.lauryl.model.TopServicesResponse
import versatile.project.lauryl.profile.data.GetProfileResponse
import versatile.project.lauryl.screens.HomeScreen
import versatile.project.lauryl.utils.Globals


class HomeFragment : Fragment() {
    lateinit var homeFragmentViewModel: HomeFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeFragmentViewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        observeDataSources()
    }

    private fun getTopServices() {
        val inputJson = JsonObject()
        inputJson.addProperty("currentPage", 0)
        inputJson.addProperty("pageSize", 100)
        inputJson.addProperty("sort", "DESC")
        inputJson.addProperty("sortBy", "createdAt")
        //inputJson.addProperty("search","[]")
        val myApplication: MyApplication = (activity!!.applicationContext as MyApplication)
        if (myApplication != null) {
            homeFragmentViewModel.getTopServices(myApplication.accessToken, inputJson)
        }

    }

    private fun observeDataSources() {

        homeFragmentViewModel.geTopServicesToObserve().observe(this, Observer<TopServicesResponse> {

            if (it != null) {

                if (it.getData() != null) {
                    if (it.getData().totalCount == 4) {
                        linServiceOne.visibility = View.VISIBLE
                        linServiceTwo.visibility = View.VISIBLE
                        linServiceThree.visibility = View.VISIBLE
                        linServiceFour.visibility = View.VISIBLE

                        Glide.with(activity!!)
                            .load(it.getData().getList()[0].imgUrl)
                            .centerCrop()
                            .placeholder(R.drawable.wash_fold_icon)
                            .into(
                                imgServiceOne
                            )
                        Glide.with(activity!!)
                            .load(it.getData().getList()[1].imgUrl)
                            .centerCrop()
                            .placeholder(R.drawable.wash_iron_icon)
                            .into(
                                imgServiceTwo
                            )
                        Glide.with(activity!!)
                            .load(it.getData().getList()[2].imgUrl)
                            .centerCrop()
                            .placeholder(R.drawable.dry_clean_icon)
                            .into(
                                imgServiceThree
                            )
                        Glide.with(activity!!)
                            .load(it.getData().getList()[3].imgUrl)
                            .centerCrop()
                            .placeholder(R.drawable.premium_wash_icon)
                            .into(
                                imgServiceFour
                            )
                        servicesOneTxt.text = it.getData().getList()[0].productTitle
                        servicesTwoTxt.text = it.getData().getList()[1].productTitle
                        servicesThreeTxt.text = it.getData().getList()[2].productTitle
                        servicesFourTxt.text = it.getData().getList()[3].productTitle
                    } else if (it.getData().totalCount == 3) {
                        linServiceOne.visibility = View.VISIBLE
                        linServiceTwo.visibility = View.VISIBLE
                        linServiceThree.visibility = View.VISIBLE
                        linServiceFour.visibility = View.GONE

                        Glide.with(activity!!)
                            .load(it.getData().getList()[0].imgUrl)
                            .centerCrop()
                            .placeholder(R.drawable.wash_fold_icon)
                            .into(
                                imgServiceOne
                            )
                        Glide.with(activity!!)
                            .load(it.getData().getList()[1].imgUrl)
                            .centerCrop()
                            .placeholder(R.drawable.wash_iron_icon)
                            .into(
                                imgServiceTwo
                            )
                        Glide.with(activity!!)
                            .load(it.getData().getList()[2].imgUrl)
                            .centerCrop()
                            .placeholder(R.drawable.dry_clean_icon)
                            .into(
                                imgServiceThree
                            )
                        servicesOneTxt.text = it.getData().getList()[0].productTitle
                        servicesTwoTxt.text = it.getData().getList()[1].productTitle
                        servicesThreeTxt.text = it.getData().getList()[2].productTitle
                    } else if (it.getData().totalCount == 2) {
                        linServiceOne.visibility = View.VISIBLE
                        linServiceTwo.visibility = View.VISIBLE
                        linServiceThree.visibility = View.GONE
                        linServiceFour.visibility = View.GONE

                        Glide.with(activity!!)
                            .load(it.getData().getList()[0].imgUrl)
                            .centerCrop()
                            .placeholder(R.drawable.wash_fold_icon)
                            .into(
                                imgServiceOne
                            )
                        Glide.with(activity!!)
                            .load(it.getData().getList()[1].imgUrl)
                            .centerCrop()
                            .placeholder(R.drawable.wash_iron_icon)
                            .into(
                                imgServiceTwo
                            )
                        servicesOneTxt.text = it.getData().getList()[0].productTitle
                        servicesTwoTxt.text = it.getData().getList()[1].productTitle
                    } else if (it.getData().totalCount == 1) {
                        linServiceOne.visibility = View.VISIBLE
                        linServiceTwo.visibility = View.GONE
                        linServiceThree.visibility = View.GONE
                        linServiceFour.visibility = View.GONE

                        Glide.with(activity!!)
                            .load(it.getData().getList()[0].imgUrl)
                            .centerCrop()
                            .placeholder(R.drawable.wash_fold_icon)
                            .into(
                                imgServiceOne
                            )
                        servicesOneTxt.text = it.getData().getList()[0].productTitle
                    } else {
                        Globals.showToastMsg(
                            activity!!.applicationContext,
                            "${resources.getString(R.string.no_services)}"
                        )
                    }

                } else {
                    Globals.showToastMsg(
                        activity!!.applicationContext,
                        "${resources.getString(R.string.no_services)}"
                    )
                }

            } else {
                Globals.showToastMsg(
                    activity!!.applicationContext,
                    "${resources.getString(R.string.server_error_txt)}"
                )
            }

        })
        homeFragmentViewModel.getAwaitingPickupList()?.observe(this, Observer {
            linAwatingPickup.visibility = View.VISIBLE
            if (it != null && it.isNotEmpty()) {
                val ordersDataItem = it[0]
                txtPickUpProcess.visibility=View.VISIBLE
                txtNoAwaitingPickups.visibility=View.GONE
                txtOrderId.text = "Order Id: " + ordersDataItem.orderNumber
                txtOrderDate.text = getOrderDate(ordersDataItem.orderDateTime.toString())
                txtOrderTime.text = getOrderTime(ordersDataItem.orderDateTime.toString())
            }else{
                txtPickUpProcess.visibility=View.GONE
                txtNoAwaitingPickups.visibility=View.VISIBLE
            }
        })
        homeFragmentViewModel.getAwaitingDeliveryList()?.observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                val ordersDataItem = it[0]
                txtOrderDeliveryId.text = "Order Id: " + ordersDataItem.orderNumber
                txtOrderDeliveryDate.text = getOrderDate(ordersDataItem.orderDateTime.toString())
                txtOrderDeliveryTime.text = getOrderTime(ordersDataItem.orderDateTime.toString())
            }else{
                txtDeliveryProcess.visibility=View.GONE
                txtNoAwaitingDeliveryOrders.visibility=View.VISIBLE
            }
        })
        homeFragmentViewModel.getCompletedDeliveryList()?.observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                txtCompleted.visibility=View.VISIBLE
                txtNoCompletedDelivery.visibility=View.GONE
                val ordersDataItem = it[0]
                txtCompletedId.text = "Order Id: " + ordersDataItem.orderNumber
                txtCompletedDate.text = getOrderDate(ordersDataItem.orderDateTime.toString())
                txtCompletedTime.text = getOrderTime(ordersDataItem.orderDateTime.toString())
            }else{
                txtCompleted.visibility=View.GONE
                txtNoCompletedDelivery.visibility=View.VISIBLE
            }
        })
        homeFragmentViewModel.getMyOrderFetchError()?.observe(this, Observer {
            txtPickUpProcess.visibility=View.GONE
            txtNoAwaitingPickups.visibility=View.VISIBLE
            txtDeliveryProcess.visibility=View.GONE
            txtNoAwaitingDeliveryOrders.visibility=View.VISIBLE
            txtCompleted.visibility=View.GONE
            txtNoCompletedDelivery.visibility=View.VISIBLE
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomeScreen).selectHomeDashboard()
        linServiceOne.setOnClickListener {
            (activity as HomeScreen).displaySPFragment()
        }
        linServiceTwo.setOnClickListener {
            (activity as HomeScreen).displaySPFragment()
        }
        linServiceThree.setOnClickListener {
            (activity as HomeScreen).displaySPFragment()
        }
        linServiceFour.setOnClickListener {
            (activity as HomeScreen).displaySPFragment()
        }
        serviceVwAlHdngTxt.setOnClickListener {
            (activity as HomeScreen).displaySPFragment()
        }

        awtngPckupsVwAlHdngTxt.setOnClickListener {
            (activity as HomeScreen).displayMyOrdersFragment(0)
        }
        txtAwaitingDelivery.setOnClickListener {
            (activity as HomeScreen).displayMyOrdersFragment(1)
        }
        txtCompletedDelivery.setOnClickListener {
            (activity as HomeScreen).displayMyOrdersFragment(2)
        }


    }

    override fun onResume() {
        super.onResume()
        getProfile()
        getTopServices()
        getAllOrders()
    }

    private fun getProfile() {
        val myApplication: MyApplication = (activity!!.applicationContext as MyApplication)
        homeFragmentViewModel.getProfileInformation(myApplication.userAccessToken)
        homeFragmentViewModel.profileFetchSuccessHandler().observe(this,
            Observer { getProfileResponse: GetProfileResponse? ->
                if (getProfileResponse != null) {
                    (activity as HomeScreen).updateUserName(getProfileResponse)
                }
            }
        )
    }

    private fun getAllOrders() {
        val myApplication: MyApplication = (activity!!.applicationContext as MyApplication)
        val inputJson = JsonObject()
        inputJson.addProperty("currentPage", 0)
        inputJson.addProperty("pageSize", 100)
        inputJson.addProperty("sort", "DESC")
        inputJson.addProperty("sortBy", "orderDateTime")
        val searchJson = JsonObject()
        searchJson.addProperty("key", "phoneNumber")
        if (myApplication != null)
            searchJson.addProperty("value", myApplication.mobileNumber)
        searchJson.addProperty("operation", "eq")
        val searchArray = JsonArray()
        searchArray.add(searchJson)
        inputJson.add("search", searchArray)
        //inputJson.addProperty("search","[]")
        if (myApplication != null) {
            homeFragmentViewModel.getHomeOrder(myApplication.accessToken, inputJson)
        }
    }

    private fun getOrderDate(date: String): CharSequence? {
        try {
            val milliseconds = date
            val someDate =
                DateTime(java.lang.Long.valueOf(milliseconds), DateTimeZone.forID("Asia/Kolkata"))
            val dateTimeFormate = DateTimeFormat.forPattern("MMM dd,yyyy")
            return "Placed on " + someDate.toString(dateTimeFormate)
        } catch (e: Exception) {

        }
        return "";
    }

    private fun getOrderTime(date: String): CharSequence? {
        try {
            val milliseconds = date
            val someDate =
                DateTime(java.lang.Long.valueOf(milliseconds), DateTimeZone.forID("Asia/Kolkata"))
            val dateTimeFormate = DateTimeFormat.forPattern("hh:mm a")
            return "" + someDate.toString(dateTimeFormate).toUpperCase()
        } catch (e: Exception) {

        }
        return "";
    }
}