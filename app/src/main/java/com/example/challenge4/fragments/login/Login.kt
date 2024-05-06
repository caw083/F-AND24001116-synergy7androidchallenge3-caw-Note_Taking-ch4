package com.example.challenge4.fragments.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.challenge4.R
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import android.widget.Button
import android.widget.Toast
import android.content.SharedPreferences

import com.example.challenge4.data.UserData
import com.example.challenge4.databinding.FragmentLoginBinding
import androidx.fragment.app.viewModels

class Login : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        setLoginPreference()
        var emailPassword = viewModel.getEmailPasswordFromPreferences(requireContext())
        val loginBinding = LoginBinding().apply {
            userEmailt = emailPassword.first
            userPassword = emailPassword.second
            loginText = "Login"
            alreadyHaveAccount = "Already \n have an \n Account?"
            newAccountText = "New User ? Register Now"
        }
        binding.loginDataBinding = loginBinding

        val view = binding.root
        val toRegister = view.findViewById<TextView>(R.id.toRegister)

        // set preference if null
        setLoginPreference()
        if (getUserDataFromPreferences().isLogin) {
            findNavController().navigate(R.id.action_login_to_list2)
        }

        toRegister.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }

        val toHome = view.findViewById<Button>(R.id.cirLoginButton)
        toHome.setOnClickListener {
//           checkLogin(view)
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            if (viewModel.checkLogin(email, password, requireContext())) {
                findNavController().navigate(R.id.action_login_to_list2)
            } else {
                Toast.makeText(requireContext(), "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }


        return binding.root
    }


    // getprefrence from register
    fun getUserDataFromPreferences(): UserData {
        val sharedPreferences = requireActivity().getSharedPreferences("UserData", 0)
        return UserData(
            sharedPreferences.getString("name", "") ?: "",
            sharedPreferences.getString("email", "") ?: "",
            sharedPreferences.getString("password", "") ?: "",
            sharedPreferences.getString("mobilePhone", "") ?: "",
            sharedPreferences.getBoolean("isLogin", false)
        )
    }

    fun setLoginPreference(){
        // check if its email paassword and username null then set preferennce
        if (getUserDataFromPreferences().email == "" || getUserDataFromPreferences().password == "" || getUserDataFromPreferences().name == "") {
            val sharedPreferences = requireActivity().getSharedPreferences("UserData", 0)
            val editor = sharedPreferences.edit()
            editor.putString("name", "Christopher")
            editor.putString("email", "christopher083@gmail.com")
            editor.putString("password", "christo083")
            editor.putString("mobilePhone", "88216323520")
            editor.putBoolean("isLogin", false)
            editor.apply()
        }
    }

    fun showToast(message: String) {
        val toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        toast.show()
    }
}