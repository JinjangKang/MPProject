package com.example.pit_a_pet

import RVAdapter
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
        val firedb = FirebaseDatabase.getInstance("https://mpproject-ba19a-default-rtdb.asia-southeast1.firebasedatabase.app")
        val ref = firedb.reference.child("pets")

        binding = FragmentPetBinding.inflate(inflater, container, false)
        val rv = binding.petList

        rv.layoutManager = LinearLayoutManager(requireContext())

        val items = mutableListOf<petdata>()
        val rvAdapter = RVAdapter(items)
        rv.adapter = rvAdapter

        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                items.clear()

                for (childSnapshot in snapshot.children) {
                    val petInfo = childSnapshot.getValue(petdata::class.java)
                    petInfo?.let {
                        items.add(it)
                    }
                }
                rvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("asd", "failed")
            }
        })

        return binding.root
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