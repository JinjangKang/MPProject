package com.example.pit_a_pet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pit_a_pet.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var auth : FirebaseAuth
private lateinit var binding: FragmentSignupBinding


/**
 * A simple [Fragment] subclass.
 * Use the [SignupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignupFragment : Fragment() {
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
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        val registerBtn = binding.registerBtn

        registerBtn.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val passwordcheck = binding.passwordcheck.text.toString()

            if (password == passwordcheck) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // 회원 가입 성공
                            val user = auth.currentUser
                            Toast.makeText(activity, "회원가입이 완료되었습니다. 자동으로 로그인 합니다.", Toast.LENGTH_LONG).show()

                            auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { signInTask ->
                                    if (signInTask.isSuccessful) {
                                        // 로그인 성공
                                        // 자동 로그인 후 수행할 작업 수행
                                        activity?.supportFragmentManager?.popBackStack()
                                        activity?.supportFragmentManager?.popBackStack()
                                    } else {
                                        // 로그인 실패
                                        Toast.makeText(activity, "자동 로그인 실패", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            // 추가 작업 수행
                        } else {
                            // 회원 가입 실패
                            try {
                                throw task.exception!!
                            } catch (e: FirebaseAuthUserCollisionException) {
                                Toast.makeText(activity, "이메일이 중복되었습니다.", Toast.LENGTH_SHORT).show()
                            } catch (e: FirebaseAuthWeakPasswordException){
                                Toast.makeText(activity, "비밀번호는 최소 6자리 이상이어야 합니다.", Toast.LENGTH_SHORT).show()

                            }
                            catch (e: Exception) {
                                // 기타 예외 처리
                                Toast.makeText(activity, "회원 가입 실패", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            } else {
                Toast.makeText(requireContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
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
         * @return A new instance of fragment SignupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}