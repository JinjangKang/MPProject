package com.example.pit_a_pet

import RVAdapter
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pit_a_pet.databinding.ActivityMainBinding
import com.example.pit_a_pet.databinding.FragmentPetBinding
import com.example.pit_a_pet.databinding.PetlistBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.values
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PetFragment : Fragment() {

    private lateinit var binding: FragmentPetBinding


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
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentPetBinding.inflate(inflater, container, false)

        val thread = NetworkThread()
        thread.start()
        thread.join()

        val recyclerView: RecyclerView = binding.petList
        val verticalSpaceItemDecoration = VerticalSpaceItemDecoration(20)
        recyclerView.addItemDecoration(verticalSpaceItemDecoration)

        return binding.root
    }

    inner class NetworkThread:Thread(){
        override fun run(){

            // 키 값
            val key = "Z2WBxekxGTIDegURqOBPHpoD8m6Dr6ojNR8Ridn6G9kUfku1afB2TOLmRsWB%2BMOukK%2FVCLKhxBnq9pWFSNy5kQ%3D%3D"

            // 현재 페이지번호
            val pageNo = "&pageNo=1"

            // 한 페이지 결과 수
            val numOfRows ="&numOfRows=10"

            // AND(안드로이드)
            val MobileOS = "&MobileOS=AND"

            // 서비스명 = 어플명
            val MobileApp = "&MobileApp=AppTest"

            val site = "http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic?serviceKey=\n" +
                    "\n"+key+pageNo+numOfRows+MobileOS+MobileApp+"&_type=json"

            val url = URL(site)
            val conn = url.openConnection()
            val input = conn.getInputStream()
            val isr = InputStreamReader(input)
            // br: 라인 단위로 데이터를 읽어오기 위해서 만듦
            val br = BufferedReader(isr)
            // Json 문서는 일단 문자열로 데이터를 모두 읽어온 후, Json에 관련된 객체를 만들어서 데이터를 가져옴
            var str: String? = null
            val buf = StringBuffer()

            do{
                str = br.readLine()

                if(str!=null){
                    buf.append(str)
                }
            }while (str!=null)

            // 전체가 객체로 묶여있기 때문에 객체형태로 가져옴
            val root = JSONObject(buf.toString())
            val response = root.getJSONObject("response").getJSONObject("body").getJSONObject("items")
            val item = response.getJSONArray("item") // 객체 안에 있는 item이라는 이름의 리스트를 가져옴

            val data = mutableListOf<petdata>()

            for(asd in 0..9){
                val jsonitem = item.getJSONObject(asd)
                data.add(petdata("${jsonitem.getString("happenDt")}",
                    "${jsonitem.getString("kindCd")}",
                    "${jsonitem.getString("happenPlace")}",
                    "${jsonitem.getString("noticeNo")}",
                    "${jsonitem.getString("popfile")}"
                ))

            }
            val rvAdapter = RVAdapter(data)
            rvAdapter.notifyDataSetChanged()

            val rv = binding.petList
            rv.adapter = rvAdapter
            rv.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int):
        RecyclerView.ItemDecoration(){

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)

            outRect.bottom = verticalSpaceHeight

        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PetFragment.
         */

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}