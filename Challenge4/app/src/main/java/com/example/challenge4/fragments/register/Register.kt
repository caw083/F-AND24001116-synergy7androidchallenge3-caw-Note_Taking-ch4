package com.example.challenge4.fragments.register

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.challenge4.R
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.challenge4.databinding.FragmentRegisterBinding
import androidx.lifecycle.viewModelScope
import androidx.fragment.app.viewModels


class Register : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        val view = binding.root

        // Initialize your properties
        binding.registerDataBinding = RegisterBinding().apply {
            headerText = "Here’s\nyour first\nstep with \nus! "
            useOtherMethodsText = "Use other Methods"
            loginText = "Already have an account? Login"
            registerButtonText = "Register"
        }

        viewModel.alertUser(requireContext(),"Sudah bisa View Model")
        // use launch to call alertUser
        lifecycleScope.launch {
            viewModel.alertUser(requireContext(),"Sudah bisa launch")
        }

        val registerButton = view.findViewById<Button>(R.id.cirRegisterButton)
        registerButton.setOnClickListener {
            showToast("Hello world")
            setNewCookie(view, requireContext())
        }

        val toLoginTextView = view.findViewById<TextView>(R.id.toLoginTextView)
        toLoginTextView.setOnClickListener {
            showToast("sudah bisa cok")
            findNavController().navigate(R.id.action_register_to_login)
        }

        return binding.root
    }

    fun setNewCookie(view : View, context: Context ) {
        val name = view.findViewById<TextView>(R.id.registerName).text.toString()
        val email = view.findViewById<TextView>(R.id.registerEmail).text.toString()
        val password = view.findViewById<TextView>(R.id.registerPassword).text.toString()
        val mobilePhone = view.findViewById<TextView>(R.id.registerMobilePhone).text.toString()

        // set SharedPreferences
        viewModel.viewModelScope.launch {
            // Inside this coroutine, you can safely call registerUser
            val success = viewModel.registerUser(context, name, email, password, mobilePhone)
            if (success) {
                // If registration is successful, store user data and navigate
                findNavController().navigate(R.id.action_register_to_login)
            }
        }
    }

    fun showToast(message: String) {
        val toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        toast.show()
    }

}

