package com.example.pit_a_pet

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.pit_a_pet.databinding.FragmentPetDetailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.shashank.sony.fancytoastlib.FancyToast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [PetDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PetDetailFragment() : Fragment(), OnItemClickListener  {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var rdb = FirebaseDatabase.getInstance("https://mpproject-ba19a-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference()
    private lateinit var auth:FirebaseAuth

    private lateinit var binding: FragmentPetDetailBinding
    private lateinit var item: Petdata

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)



        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentPetDetailBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        binding.typeDetail.text = item.type
        binding.gender.text = item.gender
        binding.color.text = item.color
        binding.birth.text = item.birth
        binding.weight.text = item.weight

        binding.postNum.text = item.CODE
        binding.postPeriod.text = item.postperiod
        binding.postPeriod2.text = item.postperiod2
        binding.place.text = item.rescueplace
        binding.detail.text = item.detail
        binding.center.text = item.center
        binding.buseo.text = item.buseo

        Glide.with(binding.imageDetail)
            .load(item.img)
            .error(R.drawable.ic_launcher_background)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imageDetail)


        if(auth.currentUser != null){
            val zzimRef = rdb.child("USER").child(auth.currentUser!!.uid).child(item.CODE)
            zzimRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // 이미 데이터베이스에 존재하는 경우
                        binding.zzimbutton.setBackgroundColor(Color.parseColor("#FF0000"))
                        binding.zzimbutton.text = "삭제하기"
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    // 에러 처리 로직 추가
                }
            })

            binding.zzimbutton.setOnClickListener {
                val userRef = rdb.child("USER").child(auth.currentUser!!.uid).child(item.CODE)
                userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // 이미 찜 목록에 존재하는 경우, 데이터 삭제
                            userRef.removeValue()
                            binding.zzimbutton.setBackgroundColor(Color.parseColor("#6200ee"))
                            binding.zzimbutton.text = "찜하기"
                            FancyToast.makeText(requireContext(), "찜 목록에서 삭제되었습니다.", FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show()
                        } else {
                            // 찜 목록에 존재하지 않는 경우, 데이터 추가
                            val petData = mapOf(
                                "type" to item.type,
                                "gender" to item.gender,
                                "color" to item.color,
                                "birth" to item.birth,
                                "weight" to item.weight,
                                "CODE" to item.CODE,
                                "postperiod" to item.postperiod,
                                "postperiod2" to item.postperiod2,
                                "rescueplace" to item.rescueplace,
                                "detail" to item.detail,
                                "center" to item.center,
                                "buseo" to item.buseo,
                                "region" to item.region,
                                "img" to item.img,
                                "date" to item.date
                            )
                            userRef.setValue(petData)
                            binding.zzimbutton.setBackgroundColor(Color.parseColor("#FF0000"))
                            binding.zzimbutton.text = "삭제하기"
                            FancyToast.makeText(requireContext(), "찜 목록에 추가되었습니다.", FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show()

                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // 데이터 읽기가 취소되었을 때 실행되는 콜백 메서드
                    }
                })
            }
        } else{
            binding.zzimbutton.setOnClickListener{
                FancyToast.makeText(requireContext(),"로그인이 필요합니다.",FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show()
            }
        }






        return binding.root
    }

    override fun onItemClick(item: Petdata) {
        this.item = item
    }

    interface OnItemClickListener {
        fun onItemClick(item: Petdata)
    }
}