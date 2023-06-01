package com.example.pit_a_pet

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.pit_a_pet.databinding.FragmentInfoBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet

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
        binding.mealManagementDescription.text = petKnowledgeDB.mealManagementDao().getMealManagement()[0].description

        // 반려견이 먹으면 안되는 음식
        val dangerFoodLists = petKnowledgeDB.dangerFoodDao().getDangerFood()
        for (dangerFood in dangerFoodLists) {
            val tableRow = TableRow(requireContext())

            val foodTextView = TextView(requireContext())
            foodTextView.text = dangerFood.food
            foodTextView.setPadding(16, 28, 16, 28)
            foodTextView.gravity = Gravity.CENTER
            foodTextView.maxLines = 10
            foodTextView.ellipsize = TextUtils.TruncateAt.END
            foodTextView.width = 0 // 너비를 0으로 설정하여 가중치에 따라 동적으로 조정
            foodTextView.background = ContextCompat.getDrawable(requireContext(), R.drawable.table_item_border)
            foodTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.2f) // 20% 너비로 설정
            tableRow.addView(foodTextView)

            val descriptionTextView = TextView(requireContext())
            descriptionTextView.text = dangerFood.description
            descriptionTextView.setPadding(16, 28, 16, 28)
            descriptionTextView.gravity = Gravity.CENTER
            descriptionTextView.maxLines = 10
            descriptionTextView.ellipsize = TextUtils.TruncateAt.END
            descriptionTextView.width = 0 // 너비를 0으로 설정하여 가중치에 따라 동적으로 조정
            descriptionTextView.background = ContextCompat.getDrawable(requireContext(), R.drawable.table_item_border)
            descriptionTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.8f) // 80% 너비로 설정
            tableRow.addView(descriptionTextView)

            binding.dangerFoodTableLayout.addView(tableRow)
        }

        // 차트 UI 생성
        var barChart: BarChart = binding.barChart

        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(1f,7458f))
        entries.add(BarEntry(2f,6411f))
        entries.add(BarEntry(3f,7899f))
        entries.add(BarEntry(4f,9375f))
        entries.add(BarEntry(5f,11769f))
        entries.add(BarEntry(6f,11319f))
        entries.add(BarEntry(7f,11209f))
        entries.add(BarEntry(8f,10645f))
        entries.add(BarEntry(9f,10118f))
        entries.add(BarEntry(10f,9783f))
        entries.add(BarEntry(11f,9023f))
        entries.add(BarEntry(12f,7187f))

        barChart.run {
            description.isEnabled = false // 차트 옆에 별도로 표기되는 description을 안보이게 설정 (false)
            setMaxVisibleValueCount(12) // 최대 보이는 그래프 개수를 7개로 지정
            setPinchZoom(false) // 핀치줌(두손가락으로 줌인 줌 아웃하는것) 설정
            setDrawBarShadow(false) //그래프의 그림자
            setDrawGridBackground(false)//격자구조 넣을건지
            axisLeft.run { //왼쪽 축. 즉 Y방향 축을 뜻한다.
                axisMaximum = 12000f //100 위치에 선을 그리기 위해 101f로 맥시멈값 설정
                axisMinimum = 5000f // 최소값 0
                granularity = 2000f // 50 단위마다 선을 그리려고 설정.
                setDrawLabels(true) // 값 적는거 허용 (0, 50, 100)
                setDrawGridLines(true) //격자 라인 활용
                setDrawAxisLine(false) // 축 그리기 설정
                textSize = 10f //라벨 텍스트 크기
            }
            xAxis.run {
                position = XAxis.XAxisPosition.BOTTOM //X축을 아래에다가 둔다.
                granularity = 1f // 1 단위만큼 간격 두기
                setDrawAxisLine(true) // 축 그림
                setDrawGridLines(false) // 격자
                textSize = 10f // 텍스트 크기
                valueFormatter = MyXAxisFormatter() // X축 라벨값(밑에 표시되는 글자) 바꿔주기 위해 설정
            }
            axisRight.isEnabled = false // 오른쪽 Y축을 안보이게 해줌.
            setTouchEnabled(false) // 그래프 터치해도 아무 변화없게 막음
            animateY(1000) // 밑에서부터 올라오는 애니매이션 적용
            legend.isEnabled = false //차트 범례 설정
        }

        var set = BarDataSet(entries,"DataSet") // 데이터셋 초기화
        set.color = ContextCompat.getColor(requireContext()!!,R.color.purple_500) // 바 그래프 색 설정

        val dataSet :ArrayList<IBarDataSet> = ArrayList()
        dataSet.add(set)
        val data = BarData(dataSet)
        data.barWidth = 0.7f //막대 너비 설정
        barChart.run {
            this.data = data //차트의 데이터를 data로 설정해줌.
            setFitBars(true)
            invalidate()
        }

        return binding.root
    }

    class MyXAxisFormatter : ValueFormatter() {
        private val days = arrayOf("1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return days.getOrNull(value.toInt()-1) ?: value.toString()
        }
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