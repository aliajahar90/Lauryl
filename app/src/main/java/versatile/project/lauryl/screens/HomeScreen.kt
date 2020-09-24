package versatile.project.lauryl.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home_screen.*
import versatile.project.lauryl.R
import versatile.project.lauryl.fragment.*
import versatile.project.lauryl.utils.Constants
import versatile.project.lauryl.utils.Globals

class HomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        Constants.CURRENT_AUTH_TOKEN = Globals.getStringFromPreferences(this, Constants.AUTH_TOKEN)
        botmNavVw.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        displayHomeFragment()
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
        val fragment = ProfileFragment()
        loadMyFragment(fragment)
    }

    private fun displayPaymentFragment() {
        val fragment = PaymentFragment()
        loadMyFragment(fragment)
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

}
