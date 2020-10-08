package versatile.project.lauryl.screens

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home_screen.*
import versatile.project.lauryl.R
import versatile.project.lauryl.base.BaseActivity
import versatile.project.lauryl.base.HomeNavigationController
import versatile.project.lauryl.fragment.*
import versatile.project.lauryl.payment.PaymentFragment
import versatile.project.lauryl.utils.Constants
import versatile.project.lauryl.utils.Globals

class HomeScreen : AppCompatActivity() {
    lateinit var homeNavigationController: HomeNavigationController;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        Constants.CURRENT_AUTH_TOKEN = Globals.getStringFromPreferences(this, Constants.AUTH_TOKEN)
        botmNavVw.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        displayHomeFragment()
        homeNavigationController= HomeNavigationController.getInstance(this)
                bckBtn.setOnClickListener {

            if (supportFragmentManager.backStackEntryCount > 1){
                supportFragmentManager.popBackStack()
            }else{
                finish()
            }

        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->

        when (menuItem.itemId) {

            R.id.homeId -> {
                displayHomeFragment()
                return@OnNavigationItemSelectedListener true
            }

            R.id.myOrdersId -> {
                displayMyOrdersFragment()
                return@OnNavigationItemSelectedListener true
            }

            R.id.schedulePckUpId -> {
                displaySPFragment()
                return@OnNavigationItemSelectedListener true
            }

            R.id.paymentId -> {
                displayPaymentFragment()
                return@OnNavigationItemSelectedListener true
            }

            R.id.profileId -> {
                displayProfileFragment()
                return@OnNavigationItemSelectedListener true
            }

        }
        false

    }

    private fun displayProfileFragment() {
        bckBtn.visibility = View.VISIBLE;
        val fragment = ProfileFragment()
        loadMyFragment(fragment)
    }

    private fun displayPaymentFragment() {
        selectPayment()
       homeNavigationController.addPaymentFragment()
    }

    fun displaySPFragment() {
        val fragment = SchedulePickUpFragment()
        loadMyFragment(fragment)
        selectShedulePckUpDashBoard()
    }

    fun selectShedulePckUpDashBoard() {
        homeNameMdlVwTxt.text = getString(R.string.chose_servcs_txt)
        homeNameMdlVwTxt.visibility = View.VISIBLE
        homeNameTxt.visibility = View.GONE
        filterTxt.visibility = View.GONE
        bckBtn.visibility = View.GONE
        botmNavVw.menu.findItem(R.id.schedulePckUpId).isChecked = true
    }

    fun displayMyOrdersFragment() {
        val fragment = MyOrdersFragment()
        loadMyFragment(fragment)
        botmNavVw.menu.findItem(R.id.myOrdersId).isChecked = true
        selectMyOrdersDashboard()
    }

    fun selectMyOrdersDashboard() {
        homeNameMdlVwTxt.text = getString(R.string.my_orders_txt)
        homeNameMdlVwTxt.visibility = View.VISIBLE
        homeNameTxt.visibility = View.GONE
        filterTxt.visibility = View.GONE
        bckBtn.visibility = View.GONE
        botmNavVw.menu.findItem(R.id.myOrdersId).isChecked = true
    }

    private fun displayHomeFragment() {
        val fragment = HomeFragment()
        loadMyFragment(fragment)
        selectHomeDashboard()
    }

    fun selectHomeDashboard() {
        homeNameTxt.text = getString(R.string.home_hdng_txt)
        homeNameTxt.visibility = View.VISIBLE
        homeNameMdlVwTxt.visibility = View.GONE
        filterTxt.visibility = View.VISIBLE
        bckBtn.visibility = View.GONE
        botmNavVw.menu.findItem(R.id.homeId).isChecked = true
    }

    private fun loadMyFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    fun displayCnfPckUpFragment(){
       selectCnfPckUp()
        val fragment = CnfSchedulePckUpFragment()
        loadMyFragment(fragment)
    }

    fun selectCnfPckUp() {
        homeNameMdlVwTxt.text = getString(R.string.schedule_pckup_txt)
        homeNameMdlVwTxt.visibility = View.VISIBLE
        homeNameTxt.visibility = View.GONE
        bckBtn.visibility = View.VISIBLE
        botmNavVw.menu.findItem(R.id.schedulePckUpId).isChecked = true
    }

    fun displayOrderHstryFragment(){
        val fragment = OrderHistoryFragment()
        loadMyFragment(fragment)
        selectOrderHistory()
    }

    fun selectOrderHistory(){
        homeNameMdlVwTxt.text = getString(R.string.order_histry_txt)
        homeNameMdlVwTxt.visibility = View.VISIBLE
        homeNameTxt.visibility = View.GONE
        bckBtn.visibility = View.VISIBLE
        botmNavVw.menu.findItem(R.id.myOrdersId).isChecked = true
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1){
            supportFragmentManager.popBackStack()
        }else{
            finish()
        }
    }
    fun selectPayment() {
        homeNameMdlVwTxt.text = getString(R.string.payment_details)
        homeNameMdlVwTxt.visibility = View.VISIBLE
        homeNameTxt.visibility = View.GONE
        filterTxt.visibility = View.GONE
        rlChange.visibility=View.VISIBLE
        homeLocHdngTxt.text=getString(R.string.loc_hdng_txt)
        homelocTxt.text="Hydrabad"
        imgLoc.setImageResource(R.drawable.location_white_icon)
        botmNavVw.menu.findItem(R.id.paymentId).isChecked = true
        bckBtn.visibility = View.VISIBLE;
    }
}
