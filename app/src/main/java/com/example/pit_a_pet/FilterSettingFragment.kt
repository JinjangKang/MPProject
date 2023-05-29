package com.example.pit_a_pet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.example.pit_a_pet.databinding.FragmentFilterSettingBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FilterSettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

interface FilterListener {
    fun onFilterApplied(filterData: FilterData)
}

class FilterSettingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding : FragmentFilterSettingBinding

    private var listener: FilterListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentFilterSettingBinding.inflate(inflater,container,false)

        // 스피너에 표시할 카테고리 리스트
        val regionList = listOf("전체", "서울특별시", "인천광역시", "대전광역시", "대구광역시", "울산광역시", "광주광역시", "부산광역시", "세종특별자치시",
            "경기도", "강원도", "충청북도", "충청남도", "경상북도", "경상남도", "전라북도", "전라남도", "제주특별자치도")
        val typeList = listOf("전체", "개", "고양이", "기타축종")

        // 스피너 어댑터 생성
        val regionspinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, regionList)
        regionspinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val typespinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, typeList)
        typespinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // 스피너 초기화 및 어댑터 설정
        val regionSpinner: Spinner = binding.regionspinner
        regionSpinner.adapter = regionspinnerAdapter
        val typeSpinner: Spinner = binding.typespinner
        typeSpinner.adapter = typespinnerAdapter


        // 필터 설정 버튼 클릭 이벤트 처리
        val btnApplyFilter: Button = binding.btnApplyFilter
        btnApplyFilter.setOnClickListener {
            val filterData = createFilterData()
            listener?.onFilterApplied(filterData)
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFilterButton()
    }

    private fun setupFilterButton() {
        val btnApplyFilter = binding.btnApplyFilter

        btnApplyFilter.setOnClickListener {
            val filterData = createFilterData()
            listener?.onFilterApplied(filterData)



            parentFragmentManager.popBackStack()

        }
    }

    private fun createFilterData(): FilterData {

        val regionSpinner: Spinner = binding.regionspinner
        val regionselectedCategory = regionSpinner.selectedItem.toString()
        val typeSpinner: Spinner = binding.typespinner
        val typeselectedCategory = typeSpinner.selectedItem.toString()



        return FilterData(regionselectedCategory, typeselectedCategory)
    }

    fun setFilterListener(listener: FilterListener) {
        this.listener = listener
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FilterSettingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                FilterSettingFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}