package versatile.project.lauryl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.home_fragment.*
import versatile.project.lauryl.R
import versatile.project.lauryl.screens.HomeScreen

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        /*sevrcsLyot.setOnClickListener {
            //(activity as HomeScreen).sampleMethod()
        }*/

        serviceVwAlHdngTxt.setOnClickListener {
            (activity as HomeScreen).displaySPFragment()
        }

        awtngPckupsVwAlHdngTxt.setOnClickListener {
            (activity as HomeScreen).displayMyOrdersFragment()
        }

    }

}