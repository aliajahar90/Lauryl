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

class HomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        botmNavVw.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        displayHomeFragment()
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->

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
        botmNavVw.menu.findItem(R.id.schedulePckUpId).isChecked = true
    }

    fun displayMyOrdersFragment() {
        val fragment = MyOrdersFragment()
        loadMyFragment(fragment)
        botmNavVw.menu.findItem(R.id.myOrdersId).isChecked = true
    }

    private fun displayHomeFragment() {
        val fragment = HomeFragment()
        loadMyFragment(fragment)
    }

    private fun loadMyFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragContainer, fragment)
        //transaction.addToBackStack(null)
        transaction.commit()
    }


    fun displayCnfPckUpFragment() {
        val fragment = CnfSchedulePckUpFragment()
        loadMyFragment(fragment)
    }

    fun displayOrderHstryFragment() {
        val fragment = OrderHistoryFragment()
        loadMyFragment(fragment)
        botmNavVw.menu.findItem(R.id.myOrdersId).isChecked = true
    }

}
