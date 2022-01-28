package com.note.nasim.ui.view.fragment

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.note.nasim.R
import com.note.nasim.data.database.NoteDB
import com.note.nasim.data.model.Note
import com.note.nasim.data.repository.NoteRepository
import com.note.nasim.databinding.FragmentHomeBinding
import com.note.nasim.databinding.FragmentUpdateNoteBinding
import com.note.nasim.ui.viewmodel.NoteViewModel
import com.note.nasim.util.ViewModelFactory
import java.util.*

class UpdateNote : Fragment() {

    private lateinit var binding: FragmentUpdateNoteBinding
    val notes by navArgs<UpdateNoteArgs>()

    private lateinit var repository: NoteRepository
    private lateinit var viewModel: NoteViewModel

    var priority : Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUpdateNoteBinding.inflate(layoutInflater, container, false)
        val dao = NoteDB(requireContext()).notesDao()
        repository = NoteRepository(dao)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(), factory).get(NoteViewModel::class.java)


        binding.title.setText(notes.note.title)
        binding.subtitle.setText(notes.note.subtitle)
        binding.description.setText(notes.note.description)


        when (notes.note.priority){
            1 -> {
                priority = 1
                binding.blue.setImageResource(R.drawable.ic_done)
                binding.orange.setImageResource(0)
                binding.green.setImageResource(0)
            }

           2 -> {
                priority = 2
                binding.green.setImageResource(R.drawable.ic_done)
                binding.orange.setImageResource(0)
                binding.blue.setImageResource(0)
            }

            3 -> {
                priority = 3
                binding.orange.setImageResource(R.drawable.ic_done)
                binding.blue.setImageResource(0)
                binding.green.setImageResource(0)
            }
        }

        binding.blue.setOnClickListener{
            priority =1
            binding.blue.setImageResource(R.drawable.ic_done)
            binding.orange.setImageResource(0)
            binding.green.setImageResource(0)
        }

        binding.green.setOnClickListener {
            priority = 2
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

        binding.btnUpdate.setOnClickListener{
            updateNote(it)
        }

        binding.btnBack.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_updateNote_to_home)
        }

        binding.btnDelete.setOnClickListener{
            val bottomSheet = BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.delete_dialog)

            val btnYes = bottomSheet.findViewById<TextView>(R.id.btn_yes)
            val btnNo = bottomSheet.findViewById<TextView>(R.id.btn_no)

            btnNo?.setOnClickListener{
                bottomSheet.dismiss()
            }

            btnYes?.setOnClickListener{
                viewModel.deleteNotes(notes.note.id!!)
                Toast.makeText(parentFragment?.requireContext(), "Note deleted successfully", Toast.LENGTH_SHORT).show()
                bottomSheet.dismiss()
                parentFragment?.view
                    ?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.action_updateNote_to_home) }
            }

            bottomSheet.show()
        }


        return binding.root
    }

    private fun updateNote(view: View?) {
        val title = binding.title.text.toString()
        val subtitle = binding.subtitle.text.toString()
        val description = binding.description.text.toString()
        val d = Date()
        val date : CharSequence = DateFormat.format("dd-MMM-yyyy", d.time)

        val data = Note(
            notes.note.id,
            title = title,
            subtitle = subtitle,
            description = description,
            date = date.toString(),
            priority
        )

        when (data.inputValidate()){
            0 -> {
                viewModel.insertOrUpdateNote(data)
                Toast.makeText(requireContext(), "Note updated successfully", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(requireView()).navigate(R.id.action_updateNote_to_home)
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