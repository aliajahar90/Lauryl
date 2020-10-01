package versatile.project.lauryl.screens

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import versatile.project.lauryl.view.model.InteroScreenViewModel
import androidx.viewpager.widget.ViewPager
import versatile.project.lauryl.utils.PrefManager
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.os.Build
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_intro_screen.*
import versatile.project.lauryl.R
import android.view.WindowManager
import androidx.viewpager.widget.PagerAdapter
import android.view.LayoutInflater
import android.content.Context
import android.content.pm.ActivityInfo
import versatile.project.lauryl.base.BaseActivity

class IntroScreen : BaseActivity() {

    private var prefManager: PrefManager? = null
    private lateinit var dots: Array<TextView>
    private var layouts: IntArray? = null
    private lateinit var interoScreenViewModel:InteroScreenViewModel
    private var myViewPagerAdapter: MyViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        interoScreenViewModel = ViewModelProvider(this).get(InteroScreenViewModel::class.java)

        //showLoading()
        // Checking for first time launch - before calling setContentView()
        /*prefManager = PrefManager(this)
        if (!prefManager!!.isFirstTimeLaunch()) {
            launchHomeScreen()
            finish()
        }*/
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_STABLE or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        setContentView(R.layout.activity_intro_screen)

        layouts = intArrayOf(
            R.layout.welcome_side1,
            R.layout.welcome_slide2,
            R.layout.welcome_slide3,
            R.layout.welcome_slide4)

        nextBtn.setOnClickListener {
            // checking for last page
            // if last page home screen will be launched
            val current = getItem(+1)
            if (current < layouts!!.size) {
                // move to next screen
                viewPager.currentItem = current
            } else {
                launchHomeScreen()
            }
        }

        btnSkip.setOnClickListener {
            launchHomeScreen()
        }

        // adding bottom dots
        addBottomDots(0)
        // making notification bar transparent
        changeStatusBarColor()
        myViewPagerAdapter = MyViewPagerAdapter()
        viewPager!!.adapter = myViewPagerAdapter
        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {

                addBottomDots(position)
                // changing the next button text 'NEXT' / 'GOT IT'
                if (position == layouts!!.size - 1) {
                    // last page. make button text to GOT IT
                    btnSkip.visibility = View.GONE
                } else {
                    // still pages are left
                    btnSkip.visibility = View.VISIBLE
                }

            }

        })
    }

    private fun changeStatusBarColor() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }

    }

    private fun addBottomDots(currentPage: Int) {

        dots = Array(layouts!!.size) { TextView(this) }
        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)
        layoutDots.removeAllViews()

        for(pos in dots.indices){
            dots[pos].text = Html.fromHtml("&#8226;")
            dots[pos].textSize = 35F
            dots[pos].setTextColor(colorsInactive[currentPage])
            layoutDots.addView(dots[pos])
        }

        if (dots.isNotEmpty())
            dots[currentPage].setTextColor(colorsActive[currentPage])

    }

    private fun getItem(i: Int): Int {
        return viewPager!!.currentItem + i
    }

    private fun launchHomeScreen() {
//        prefManager!!.setFirstTimeLaunch(false)
        startActivity(Intent(this@IntroScreen,SignUpOrLoginScreen::class.java))
        finish()
    }

    inner class MyViewPagerAdapter: PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = layouts?.get(position)?.let { layoutInflater.inflate(it, container, false) }
            container.addView(view)
            return view!!
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

        override fun getCount(): Int {
            return layouts!!.size
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }

    }

}
