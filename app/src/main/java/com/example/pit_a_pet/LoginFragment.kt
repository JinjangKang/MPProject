package com.example.pit_a_pet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.pit_a_pet.databinding.FragmentLoginBinding
import com.example.pit_a_pet.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var auth : FirebaseAuth
private lateinit var binding: FragmentLoginBinding

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
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

        binding = FragmentLoginBinding.inflate(layoutInflater,container,false)
        auth = FirebaseAuth.getInstance()


        val loginBtn = binding.loginBtn
        loginBtn.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{task ->
                    if (task.isSuccessful) {
                        // 로그인 성공
                        val user = auth.currentUser
                        // 추가적인 작업 수행
                        activity?.supportFragmentManager?.popBackStack()

                    } else {
                        // 로그인 실패
                        Toast.makeText(requireContext(), "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        val singup = binding.signup
        singup.setOnClickListener{
            val fragmentmanger = parentFragmentManager
            val transaction = fragmentmanger.beginTransaction()
            transaction.replace(R.id.mainFragment, SignupFragment())
            transaction.addToBackStack(null)
            transaction.commit()

        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}