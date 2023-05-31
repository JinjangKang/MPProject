package com.example.pit_a_pet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.pit_a_pet.databinding.FragmentLoginBinding
import com.example.pit_a_pet.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.shashank.sony.fancytoastlib.FancyToast

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
        val fragmentManager = parentFragmentManager
        val transaction = fragmentManager.beginTransaction()

        loginBtn.setOnClickListener {
            if(binding.email.text.toString().isEmpty() || binding.password.text.toString().isEmpty()){
               FancyToast.makeText(requireContext(),"아이디와 비밀번호를 입력하세요.",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show()
            }else{
                val email = binding.email.text.toString()
                val password = binding.password.text.toString()

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // 로그인 성공
                            val user = auth.currentUser
                            // 추가적인 작업 수행
                            transaction.replace(R.id.mainFragment, LoadingFragment())
                            transaction.commit()

                            FancyToast.makeText(
                                requireContext(),
                                "로그인에 성공하였습니다.",
                                FancyToast.LENGTH_SHORT,
                                FancyToast.SUCCESS,
                                false
                            ).show()

                        } else {
                            // 로그인 실패
                            FancyToast.makeText(
                                requireContext(),
                                "로그인에 실패하였습니다.",
                                FancyToast.LENGTH_SHORT,
                                FancyToast.ERROR,
                                false
                            ).show()
                        }
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
            fragmentmanger.findFragmentByTag("MypageFragment")?.onResume()

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