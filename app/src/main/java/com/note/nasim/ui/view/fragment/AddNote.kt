package com.note.nasim.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.note.nasim.R
import android.text.format.DateFormat
import android.widget.Toast

import com.note.nasim.data.database.NoteDB
import com.note.nasim.data.model.Note
import com.note.nasim.data.repository.NoteRepository
import com.note.nasim.databinding.FragmentAddNoteBinding
import com.note.nasim.databinding.FragmentHomeBinding
import com.note.nasim.ui.viewmodel.NoteViewModel
import com.note.nasim.util.ViewModelFactory
import java.util.*


class AddNote : Fragment() {


    var priority : Int = 0

    private lateinit var binding: FragmentAddNoteBinding

    private lateinit var repository: NoteRepository
    private lateinit var viewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddNoteBinding.inflate(layoutInflater, container, false)

        val dao = NoteDB(requireContext()).notesDao()
        repository = NoteRepository(dao)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(), factory).get(NoteViewModel::class.java)


        binding.btnBack.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_addNote_to_home)
        }

        binding.blue.setOnClickListener(View.OnClickListener {
            priority = 1
            binding.blue.setImageResource(R.drawable.ic_done)
            binding.orange.setImageResource(0)
            binding.green.setImageResource(0)
        })

        binding.green.setOnClickListener {
            priority =2
            binding.green.setImageResource(R.drawable.ic_done)
            binding.orange.setImageResource(0)
            binding.blue.setImageResource(0)
        }

        binding.orange.setOnClickListener{
            priority = 3
            binding.orange.setImageResource(R.drawable.ic_done)
            binding.blue.setImageResource(0)
            binding.green.setImageResource(0)
        }

        binding.btnSave.setOnClickListener{ view ->
            createNotes(view)
        }

        return binding.root
    }
    private fun createNotes(view: View,){
        val title = binding.title.text.toString()
        val subtitle = binding.subtitle.text.toString()
        val description = binding.description.text.toString()
        val d = Date()
        val date : CharSequence = DateFormat.format("dd-MMM-yyyy", d.time)

        val data = Note(
            null,
            title = title,
            subtitle = subtitle,
            description = description,
            date = date.toString(),
            priority
        )

        when (data.inputValidate()){
            0 -> {
                viewModel.insertOrUpdateNote(data)
                Toast.makeText(requireContext(), "Note created successfully", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view).navigate(R.id.action_addNote_to_home)
            }

            1 -> {
                binding.title.error = "Define a title"
            }

            2 -> {
                binding.subtitle.error = "Define a subtitle"
            }

            3 -> {
                Toast.makeText(requireContext(), "Note field cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

}