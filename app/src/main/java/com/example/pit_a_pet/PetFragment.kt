package com.example.pit_a_pet

import RVAdapter
import android.graphics.Rect
import android.graphics.Region
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
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
import java.lang.reflect.Type
import java.net.URL

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private val data = mutableListOf<Petdata>()

private lateinit var binding: FragmentPetBinding

class PetFragment : Fragment(), FilterListener,  RVAdapter.OnItemClickListener{


    private var param1: String? = null
    private var param2: String? = null

    var DefaultFilterData = FilterData("전체","전체")
    private lateinit var rvAdapter : RVAdapter

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

        val thread = FilterdNetworkThread(DefaultFilterData)
        thread.start()
        thread.join()

        val recyclerView: RecyclerView = binding.petList
        val verticalSpaceItemDecoration = VerticalSpaceItemDecoration(20)
        recyclerView.addItemDecoration(verticalSpaceItemDecoration)

        rvAdapter = RVAdapter(data, this)

        val rv = binding.petList
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(requireContext())


        val filterSetTV = binding.filterTV

        filterSetTV.setOnClickListener {
            val fragmentmanger = parentFragmentManager
            val transaction = fragmentmanger.beginTransaction()

            val filterSettingFragment = FilterSettingFragment.newInstance("", "")
            filterSettingFragment.setFilterListener(this) // FilterListener 설정

            transaction.replace(R.id.mainFragment, filterSettingFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val rv = binding.petList
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onItemClicked(item: Petdata) {

        val fragment = PetDetailFragment()
        (fragment as? OnItemClickListener)?.onItemClick(item)
        val fragmentManager = parentFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.mainFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onFilterApplied(filterData: FilterData) {

        DefaultFilterData = FilterData(filterData.region,filterData.type)

    }

    inner class FilterdNetworkThread(private val filterData: FilterData) : Thread() {
        override fun run() {

            data.clear()

            val region = filterData.region
            val type = filterData.type

            fun regionTransform(region: String?): Int? {
                return when (region) {
                    "서울특별시" -> return 6110000
                    "부산광역시" -> return 6260000
                    "대구광역시" -> return 6270000
                    "인천광역시" -> return 6280000
                    "광주광역시" -> return 6290000
                    "세종특별자치시" -> return 5690000
                    "대전광역시" -> return 6300000
                    "울산광역시" -> return 6310000
                    "경기도" -> return 6410000
                    "강원도" -> return 6420000
                    "충청북도" -> return 6430000
                    "충청남도" -> return 6440000
                    "전라북도" -> return 6450000
                    "전라남도" -> return 6460000
                    "경상북도" -> return 6470000
                    "경상남도" -> return 6480000
                    "제주특별자치도" -> return 6500000

                    else -> 0
                }
            }

            fun typeTransform(type: String): Int {
                return when (type) {
                    "개" -> 417000
                    "고양이" -> 422400
                    "기타축종" -> 429900
                    else -> 0
                }
            }

            val regionCode = regionTransform(region)
            val typeCode = typeTransform(type)

            var regionSelector = ""
            if (regionCode != 0) {
                regionSelector = "&upr_cd=$regionCode"
            }
            var typeSelector = ""
            if (typeCode != 0) {
                typeSelector = "&upkind=$typeCode"
            }

            // 키 값
            val key =
                "Z2WBxekxGTIDegURqOBPHpoD8m6Dr6ojNR8Ridn6G9kUfku1afB2TOLmRsWB%2BMOukK%2FVCLKhxBnq9pWFSNy5kQ%3D%3D"

            // 한 페이지 결과 수
            val numOfRows = "&numOfRows=100"

            // 서비스명 = 어플명
            val MobileApp = "&MobileApp=AppTest"
            val site =
                "http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic?serviceKey=\n" +
                        "\n" + key + "&pageNo=1" + numOfRows + regionSelector + typeSelector + "&MobileOS=AND" + MobileApp + "&_type=json"

            val url = URL(site)
            val conn = url.openConnection()
            val input = conn.getInputStream()
            val isr = InputStreamReader(input)
            // br: 라인 단위로 데이터를 읽어오기 위해서 만듦
            val br = BufferedReader(isr)
            // Json 문서는 일단 문자열로 데이터를 모두 읽어온 후, Json에 관련된 객체를 만들어서 데이터를 가져옴
            var str: String? = null
            val buf = StringBuffer()

            do {
                str = br.readLine()

                if (str != null) {
                    buf.append(str)
                }
            } while (str != null)

            // 전체가 객체로 묶여있기 때문에 객체형태로 가져옴
            val root = JSONObject(buf.toString())
            val response =
                root.getJSONObject("response").getJSONObject("body").getJSONObject("items")
            val item = response.getJSONArray("item") // 객체 안에 있는 item이라는 이름의 리스트를 가져옴

            var itemlen = 99
            if(item.length() < itemlen){
                itemlen = item.length()
            }

            for (asd in 0 until itemlen) {
                val jsonitem = item.getJSONObject(asd)
                data.add(
                    Petdata(
                        "${jsonitem.getString("noticeSdt")}",
                        "${jsonitem.getString("noticeEdt")}",
                        "${jsonitem.getString("kindCd")}",
                        "${jsonitem.getString("orgNm")}",
                        "${jsonitem.getString("happenPlace")}",
                        "${jsonitem.getString("popfile")}",
                        "${jsonitem.getString("noticeNo")}",
                        "${jsonitem.getString("sexCd")}",
                        "${jsonitem.getString("colorCd")}",
                        "${jsonitem.getString("age")}",
                        "${jsonitem.getString("weight")}",
                        "${jsonitem.getString("noticeNo")}",
                        "${jsonitem.getString("specialMark")}",
                        "${jsonitem.getString("careNm")}",
                        "${jsonitem.getString("noticeNo")}",
                    )
                )
            }
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