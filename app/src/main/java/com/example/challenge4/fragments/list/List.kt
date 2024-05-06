package com.example.challenge4.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.challenge4.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.Button
import androidx.fragment.app.viewModels
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge4.databinding.FragmentListBinding
import com.example.challenge4.databinding.FragmentLoginBinding

class List : Fragment() {
    private val viewModel: ListViewModel by viewModels()
    private lateinit  var binding : FragmentListBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val addBinding = ListBinding().apply {
            logoutBindingText = "Log Out"
        }
        binding =  FragmentListBinding.inflate(inflater, container, false)
        binding.listBindingModel = addBinding
        // Inflate the layout for this fragment
      //  val view = inflater.inflate(R.layout.fragment_list, container, false)

        val view = binding.root

        val fab: FloatingActionButton = view.findViewById(R.id.floatingActionButton)
        // Set OnClickListener to handle click events
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_list2_to_add3)
        }
        val notesData = viewModel.getAllNotes()
        var notecount = 0
        notesData.observe(viewLifecycleOwner) { notes ->
            // Size of the list
            val size = notes.size
            showToast("Size of notes list: $size")
            val stringBuilder = StringBuilder()
            for (note in notes) {
                stringBuilder.append("Title: ${note.title}, Description: ${note.description}, Priority: ${note.priority}\n")
            }
            val dataString = stringBuilder.toString()
            showToast(dataString)
            notecount ++

            val notesAdapter = NotesAdapter(requireContext(), notes, viewModel)
            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerviewNotes)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = notesAdapter
        }

        showToast("masuk pak eka $notesData.toString()")
        showToast("notecount $notecount")

        //check notedatasize
        //write in recycle list



//        recyclerView.adapter = notesAdapter
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val logout: Button = view.findViewById(R.id.logoutButton)
        logout.setOnClickListener {
            logoutLogic()
        }
        return binding.root
    }

    fun logoutLogic() {
        val sharedPreferences = requireActivity().getSharedPreferences("UserData", 0)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLogin", false)
        editor.apply()
        findNavController().navigate(R.id.action_list2_to_login)
    }
    fun showToast(message: String) {
        val toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        toast.show()
    }
}