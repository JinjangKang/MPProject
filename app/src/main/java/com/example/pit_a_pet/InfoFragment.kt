package com.example.pit_a_pet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pit_a_pet.databinding.FragmentInfoBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class InfoFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentInfoBinding

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
    ): View {
        binding = FragmentInfoBinding.inflate(inflater,container,false)
        val petKnowledgeDB = PetKnowledgeDatabase.getInstance(requireContext())!!

        // static data 삽입
//        val mealManagement: MealManagement = MealManagement()
//        val dangerFoodList: List<DangerFood> = listOf(
//            DangerFood("양파", "어떤 식으로 요리해도 양파의 독성은 없어지지 않는다. 양파의 강한 독성은 개나 고양이의 적혈구를 녹여 버리며, 심한 경우에는 급성 빈혈을 일으켜 죽기도 한다."),
//            DangerFood("초콜렛", "초콜렛은 중독을 일으킨다. 섭취 시 지나치게 활동적이거나 흥분상태를 보이기도 하고, 다른 음식은 먹지도 않아도 구토를 자주 하게 되고 노란색의 점액질을 토해낸다."),
//            DangerFood("우유", "우유에는 모유에는 없는 유당이 함유되어 있으나, 강아지는 선천적으로 유당을 분해할 수 있는 효소가 없다. 어린 강아지에게 설사를 일으키는 원인이 되기도 하며 설사는 2차 감염원이 되기도 하므로 특별한 경우가 아니면 급여를 삼가해야 한다."),
//            DangerFood("생선", "등푸른 생선에는 DHA가 많이 함유되어 있지만 어린강아지는 DHA를 분해하는 효소가 없어서 소화가 되지 않고 바로 배설된다. 그리고 생선가시는 소화되지 않고 소화기관에 상처나 염증을 유발 할 수도 있다. 기름이 많이 함유된 생선통조림은 설사와 구토를 일으키고 많은 양의 기름은 강아지에게 소화장애를 일으키므로 급여를 삼가해야 한다."),
//            DangerFood("닭뼈", "소화가 되었을 때 뼈가 날카롭게 분해되면서 소화기관에 상처를 내어 염증이나 혈변, 심한 경우에는 죽음에 이르게 할 수 있다."),
//            DangerFood("마른 오징어", "개들은 음식을 씹지 않고 바로 소화기관으로 넘기므로 오징어나 쥐포 등을 먹으면 입과 식도, 위까지 손상될 수 있으므로 주지 않는다."),
//            DangerFood("채소류의 과잉섭취", "어느 정도의 채소류의 섭취는 섬유질이 있어 소화흡수에 도움이 되지만, 많은 양의 채소류는 공급과잉이 되며 체외로 배출되므로 적당량을 준다.")
//        )
//        petKnowledgeDB.mealManagementDao().insert(mealManagement)
//        for (dangerFood in dangerFoodList) {
//            petKnowledgeDB.dangerFoodDao().insert(dangerFood)
//        }

        // 식사관리
         Log.d("dbdb", petKnowledgeDB.mealManagementDao().getMealManagement().toString())

        // 반려견이 먹으면 안되는 음식
         Log.d("dbdb", petKnowledgeDB.dangerFoodDao().getDangerFood().toString())

        return binding.root
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}