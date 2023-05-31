package com.example.pit_a_pet

import ImagePagerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.pit_a_pet.databinding.FragmentMypageBinding
import com.google.firebase.auth.FirebaseAuth
import com.shashank.sony.fancytoastlib.FancyToast
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MypageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MypageFragment : Fragment() {

    private lateinit var binding: FragmentMypageBinding
    lateinit var viewPager: ViewPager2

    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {



        binding = FragmentMypageBinding.inflate(inflater,container,false)
        val loginTextview: TextView = binding.loginTV
        val logoutTextview : TextView = binding.logoutTV
        val zzimList: TextView = binding.zzimList


        val fragmentManager = parentFragmentManager
        val transaction = fragmentManager.beginTransaction()

        // 로그인 상태
        if(currentUser != null){

            //로그인 텍스트뷰
            loginTextview.text = currentUser.email
            loginTextview.isClickable = false


            //로그아웃 텍스트뷰
            logoutTextview.visibility = View.VISIBLE
            logoutTextview.setOnClickListener{
                auth.signOut()

                FancyToast.makeText(requireContext(), "로그아웃 되었습니다.", FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show()

                transaction.replace(R.id.mainFragment, LoadingFragment())
                transaction.commit()
            }

            //찜리스트
            zzimList.visibility = View.VISIBLE
            zzimList.setOnClickListener{
                transaction.replace(R.id.mainFragment, ZzimListFragment())
                transaction.commit()
            }

        }
        //로그인 하지 않은 상태
        else{

            //로그인 텍스트 뷰
            loginTextview.setOnClickListener{
                transaction.replace(R.id.mainFragment, LoginFragment())
                transaction.addToBackStack(null)
                transaction.commit()
            }

            //로그아웃 텍스트뷰
            logoutTextview.visibility = View.GONE
            //찜리스트
            zzimList.visibility = View.GONE
        }

        viewPager = binding.viewPager

        val imageList = getImage()
        val urlList = getUrl() // 각 사진에 대한 URL 리스트

        viewPager.adapter = ImagePagerAdapter(imageList, urlList) // 어댑터 생성
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL // 방향을 가로로

        val timer = Timer()
        val delayInMillis: Long = 3000
        val numPages = getImage().size
        var currentPage = 0

        val updateCurrentPage = Runnable {
            if (currentPage == numPages - 1) {
                viewPager.setCurrentItem(0, true) // 첫 번째 페이지로 이동
            } else {
                viewPager.setCurrentItem(currentPage + 1, true) // 다음 페이지로 이동
            }
        }

        val timerTask = object : TimerTask() {
            override fun run() {
                viewPager.post(updateCurrentPage)
            }
        }

        timer.schedule(timerTask, delayInMillis, delayInMillis)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPage = position
            }
        })



        return binding.root
    }

    private fun getImage(): ArrayList<Int> {
        return arrayListOf(
            R.drawable.imageview1,
            R.drawable.imageview2,
            R.drawable.imageview3,
        )
    }

    private fun getUrl(): ArrayList<String> {
        return arrayListOf(
            "https://animallovecontest.com/",
            "",
            "",
        )
    }








    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MypageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MypageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}