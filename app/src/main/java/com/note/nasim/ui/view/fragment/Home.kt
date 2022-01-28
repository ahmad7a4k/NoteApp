package com.note.nasim.ui.view.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.note.nasim.R
import com.note.nasim.data.database.NoteDB
import com.note.nasim.data.model.Note
import com.note.nasim.data.repository.NoteRepository
import com.note.nasim.databinding.FragmentHomeBinding
import com.note.nasim.ui.adapter.NotesAdapter
import com.note.nasim.ui.viewmodel.NoteViewModel
import com.note.nasim.util.ViewModelFactory

class Home : Fragment() {

    private lateinit var _binding: FragmentHomeBinding

    private lateinit var repository: NoteRepository
    private lateinit var viewModel: NoteViewModel

    private var oldNotes = arrayListOf<Note>()
    private lateinit var adapter: NotesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        val dao = NoteDB(requireContext()).notesDao()
        repository = NoteRepository(dao)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(), factory).get(NoteViewModel::class.java)

        viewModel.getNotes().observe(viewLifecycleOwner, { notesList ->
            getNote(requireContext(), notesList)
        })

        _binding.btnAdd.setOnClickListener { view ->
            Navigation.findNavController(view).navigate(R.id.action_home_to_add_note)
        }

        _binding.all.setOnClickListener {
            viewModel.getNotes().observe(viewLifecycleOwner, { notesList ->
                getNote(requireContext(), notesList)
            })
            changeButtons(
                R.color.colorBlack, R.drawable.bg_yellow_25,
                R.color.colorWhite, R.drawable.bg_blue_stroke_25,
                R.color.colorWhite, R.drawable.bg_green_stroke_25,
                R.color.colorWhite, R.drawable.bg_orange_stroke_25
            )
        }

        _binding.high.setOnClickListener {
            viewModel.getNotesByPriority(3).observe(viewLifecycleOwner, { notesList ->
                getNote(requireContext(), notesList)
            })

            changeButtons(
                R.color.colorWhite, R.drawable.bg_yellow_stroke_25,
                R.color.colorBlack, R.drawable.bg_blue_25,
                R.color.colorWhite, R.drawable.bg_green_stroke_25,
                R.color.colorWhite, R.drawable.bg_orange_stroke_25
            )
        }

        _binding.medium.setOnClickListener {
            viewModel.getNotesByPriority(2).observe(viewLifecycleOwner, { notesList ->
                getNote(requireContext(), notesList)
            })

            changeButtons(
                R.color.colorWhite, R.drawable.bg_yellow_stroke_25,
                R.color.colorWhite, R.drawable.bg_blue_stroke_25,
                R.color.colorBlack, R.drawable.bg_green_25,
                R.color.colorWhite, R.drawable.bg_orange_stroke_25
            )
        }

        _binding.low.setOnClickListener {
            viewModel.getNotesByPriority(1).observe(viewLifecycleOwner, { notesList ->
                getNote(requireContext(), notesList)
            })

            changeButtons(
                R.color.colorWhite, R.drawable.bg_yellow_stroke_25,
                R.color.colorWhite, R.drawable.bg_blue_stroke_25,
                R.color.colorWhite, R.drawable.bg_green_stroke_25,
                R.color.colorBlack, R.drawable.bg_orange_25
            )
        }


        _binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                filterNotes(s.toString().lowercase())
            }
        })


        return _binding.root
    }

    private fun changeButtons(
        allColor: Int, allBg: Int,
        blueColor: Int, blueBg: Int,
        greenColor: Int, greenBg: Int,
        orangeColor: Int, orangeBg: Int
    ) {
        _binding.all.setTextColor(resources.getColor(allColor))
        _binding.all.setBackgroundResource(allBg)
        _binding.high.setTextColor(resources.getColor(blueColor))
        _binding.high.setBackgroundResource(blueBg)
        _binding.medium.setTextColor(resources.getColor(greenColor))
        _binding.medium.setBackgroundResource(greenBg)
        _binding.low.setTextColor(resources.getColor(orangeColor))
        _binding.low.setBackgroundResource(orangeBg)
    }

    private fun getNote(context: Context, notesList: List<Note>?) {
        oldNotes = notesList as ArrayList<Note>
        val layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        _binding.recyclerView.layoutManager = layoutManager
        adapter = NotesAdapter(context, notesList)
        _binding.recyclerView.adapter = adapter

        _binding.noNote.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
    }

    private fun filterNotes(txt: String) {
        val filteredNotes = arrayListOf<Note>()

        for (item in oldNotes) {
            if (item.title.lowercase().contains(txt) || item.subtitle.lowercase().contains(txt)) {
                filteredNotes.add(item)
            }
        }
        _binding.noNote.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
        adapter.filterNotes(filteredNotes)
    }


}