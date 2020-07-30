package com.sendyit.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sendy.co.ke.sendyy.R
import com.sendyit.utilities.Utils.hideView
import com.sendyit.utilities.Utils.showView
import kotlinx.android.synthetic.main.fragment_app_tour.view.*

class AppTourFragment : Fragment() {

    //Literals
    private lateinit var title: String
    private lateinit var description: String
    private var imageResource = 0
    private var isTopImage: Boolean = true
    private var isSwipeRightVisible: Boolean = false
    private var position: Int = 0

    //Views
    private lateinit var tvTitle: AppCompatTextView
    private lateinit var tvTitle2: AppCompatTextView
    private lateinit var tvDescription: AppCompatTextView
    private lateinit var tvDescription2: AppCompatTextView
    private lateinit var imageTop: ImageView
    private lateinit var imageBottom: ImageView
    private lateinit var imageBottom2: ImageView
    private lateinit var cvImageOnBoardingTop: CardView
    private lateinit var cvImageOnBoardingBottom: CardView
    private lateinit var cvImageOnBoardingBottom2: CardView
    private lateinit var tvSwipeRight: AppCompatTextView
    private lateinit var layout: RelativeLayout
    private lateinit var mFakeStatusBar: View
    private lateinit var llTexts1: LinearLayout
    private lateinit var llTexts2: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            title = arguments!!.getString(ARG_PARAM1)!!
            description = arguments!!.getString(ARG_PARAM2)!!
            imageResource = arguments!!.getInt(ARG_PARAM3)
            isTopImage = arguments!!.getBoolean(ARG_PARAM4)
            isSwipeRightVisible = arguments!!.getBoolean(ARG_PARAM5)
            position = arguments!!.getInt(ARG_PARAM6)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootLayout: View =
                inflater.inflate(R.layout.fragment_app_tour, container, false)

        imageTop = rootLayout.ivOnboardingTop
        imageBottom = rootLayout.ivOnboardingBottom
        imageBottom2 = rootLayout.ivOnboardingBottom2
        cvImageOnBoardingTop = rootLayout.cvImageOnboarding
        cvImageOnBoardingBottom = rootLayout.cvImageOnboardingBottom
        cvImageOnBoardingBottom2 = rootLayout.cvImageOnboardingBottom2
        tvSwipeRight = rootLayout.tvSwipeRight
        llTexts1 = rootLayout.llInformationDescription
        llTexts2 = rootLayout.llInformationDescription2

        imageBottom.setImageResource(imageResource)
        imageBottom2.setImageResource(imageResource)
        imageTop.setImageResource(imageResource)

        tvTitle = rootLayout.tvOnboardingTitle
        tvTitle2 = rootLayout.tvOnboardingTitle2
        tvDescription = rootLayout.tvOnboardingDescription
        tvDescription2 = rootLayout.tvOnboardingDescription2
        tvTitle.text = title
        tvDescription.text = description

        layout = rootLayout.layout_container
        mFakeStatusBar = rootLayout.viewFakeStatusBar
        layout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_app_tour_background))
        mFakeStatusBar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_app_tour_background))

        if (!isTopImage) {
            hideView(imageTop)
            hideView(cvImageOnBoardingTop)
            showView(cvImageOnBoardingBottom)
        }
        if (isSwipeRightVisible) {
            showView(tvSwipeRight)
        }

        if (position == 7 || position == 8) {
            hideView(cvImageOnBoardingBottom)
            showView(cvImageOnBoardingBottom2)
        }

        if (position == 9 || position == 10 || position == 4) {
            tvTitle2.text = title
            tvDescription2.text = description
            hideView(llTexts1)
            showView(llTexts2)
        }

        if (position == 10) {
            hideView(tvSwipeRight)
            hideView(cvImageOnBoardingTop)
            hideView(cvImageOnBoardingBottom)
        }

        return rootLayout
    }

    companion object {
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val ARG_PARAM3 = "param3"
        private const val ARG_PARAM4 = "param4"
        private const val ARG_PARAM5 = "param5"
        private const val ARG_PARAM6 = "param6"

        fun newInstance(
                title: String?,
                description: String?,
                imageResource: Int,
                isTopImage: Boolean,
                isSwipeRightVisible: Boolean,
                position: Int
        ): AppTourFragment {
            val fragment = AppTourFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, title)
            args.putString(ARG_PARAM2, description)
            args.putInt(ARG_PARAM3, imageResource)
            args.putBoolean(ARG_PARAM4, isTopImage)
            args.putBoolean(ARG_PARAM5, isSwipeRightVisible)
            args.putInt(ARG_PARAM6, position)

            fragment.arguments = args
            return fragment
        }
    }
}