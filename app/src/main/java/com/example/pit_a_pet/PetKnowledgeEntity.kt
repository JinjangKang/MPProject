package com.example.pit_a_pet

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "SongTable")
data class Song(
    var title : String = "식사관리",
    var singer : String = "동물체의 주요한 구성성분은 수분, 단백질, 지방, 광물질 그리고 극히 소량의 탄수화물이며, 각각의 구성비율은 동물품종, 연령, 성별 및 동물의 상태에 따라 다르다.\n" +
            "지방이나 소화관의 내용물을 제외하고는 동물체의 조성은 거의 물이 75%, 단백질 20%, 광물질 5% 및 탄수화물이 1% 이하로 되어 있다.\n" +
            "음식물은 에너지를 공급하는 고유의 음식물(탄수화물, 지방 및 단백질)과 생명에는 반드시 필요하나 에너지를 공급하지 않는 수분, 무기 염류 및 비타민으로 분류한다.\n" +
            "음식물은 다른 영양소와 함께 에너지를 공급해야 되는데, 에너지는 근육운동과 체온을 유지시킬 뿐 아니라 호흡이나 심장기능의 유지 등에도 필요하다.\n" +
            "한편, 에너지가 많은 음식물을 다량으로 섭취하면 체내에 체지방이 축적되어 비만의 원인이 되기 쉽고 반대로 필요량에 부족하게 되면 성장이 불량하거나,\n" +
            "체중이 감소되어 야위고 쉽게 병에 걸리게 된다.\n" +
            "개는 엄격한 의미에서 육식성 동물이 아니므로 육류만으로는 생존할 수 없다.",
    var asdnf : String = "sadkfnlsdan"
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}