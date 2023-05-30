package com.example.pit_a_pet

import RVAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pit_a_pet.databinding.FragmentZzimListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var binding: FragmentZzimListBinding
private lateinit var rvAdapter: RVAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [ZzimListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ZzimListFragment : Fragment() , RVAdapter.OnItemClickListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var rdb = FirebaseDatabase.getInstance("https://mpproject-ba19a-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference()
    private lateinit var auth: FirebaseAuth
    private val data = mutableListOf<Petdata>()

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

        binding = FragmentZzimListBinding.inflate(inflater,container,false)
        auth = FirebaseAuth.getInstance()

        val recyclerView: RecyclerView = binding.zzimrv
        val verticalSpaceItemDecoration = PetFragment.VerticalSpaceItemDecoration(20)
        recyclerView.addItemDecoration(verticalSpaceItemDecoration)


        rvAdapter = RVAdapter(data, this)


        val userRef = rdb.child("USER").child(auth.currentUser!!.uid)
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                for (childSnapshot in dataSnapshot.children) {
                    data.add(
                        Petdata(
                            "${childSnapshot.child("postperiod").value}",
                            "${childSnapshot.child("postperiod2").value}",
                            "${childSnapshot.child("type").value}",
                            "${childSnapshot.child("region").value}",
                            "${childSnapshot.child("rescueplace").value}",
                            "${childSnapshot.child("img").value}",
                            "${childSnapshot.child("CODE").value}",
                            "${childSnapshot.child("gender").value}",
                            "${childSnapshot.child("color").value}",
                            "${childSnapshot.child("birth").value}",
                            "${childSnapshot.child("weight").value}",
                            "${childSnapshot.child("date").value}",
                            "${childSnapshot.child("detail").value}",
                            "${childSnapshot.child("center").value}",
                            "${childSnapshot.child("buseo").value}",
                        )
                    )
                }
                rvAdapter.notifyDataSetChanged() // 어댑터에 데이터 변경 알림
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터 읽기가 취소되었을 때 실행되는 콜백 메서드
            }
        })


        val rv = binding.zzimrv
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    private fun fetchData(){
        val newData = mutableListOf<Petdata>()
        val userRef = rdb.child("USER").child(auth.currentUser!!.uid)
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                for (childSnapshot in dataSnapshot.children) {
                    newData.add(
                        Petdata(
                            "${childSnapshot.child("postperiod").value}",
                            "${childSnapshot.child("postperiod2").value}",
                            "${childSnapshot.child("type").value}",
                            "${childSnapshot.child("region").value}",
                            "${childSnapshot.child("rescueplace").value}",
                            "${childSnapshot.child("img").value}",
                            "${childSnapshot.child("CODE").value}",
                            "${childSnapshot.child("gender").value}",
                            "${childSnapshot.child("color").value}",
                            "${childSnapshot.child("birth").value}",
                            "${childSnapshot.child("weight").value}",
                            "${childSnapshot.child("date").value}",
                            "${childSnapshot.child("detail").value}",
                            "${childSnapshot.child("center").value}",
                            "${childSnapshot.child("buseo").value}",
                        )
                    )
                }
                data.clear()
                data.addAll(newData)
                rvAdapter.notifyDataSetChanged() // 어댑터에 데이터 변경 알림
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터 읽기가 취소되었을 때 실행되는 콜백 메서드
            }
        })
        rvAdapter.notifyDataSetChanged()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val rv = binding.zzimrv
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(requireContext())


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ZzimListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ZzimListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}