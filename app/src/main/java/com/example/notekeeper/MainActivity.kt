 package com.example.notekeeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.example.notekeeper.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var notePosition = POSITION_NOT_SET

    private var textNoteTitle : TextView? = null
    var textNoteText : TextView? = null
    var spinnerCourses: Spinner? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val mainBinding = binding.mainContainer

        textNoteTitle = findViewById(R.id.textNoteTitle)
        textNoteText = findViewById(R.id.textNoteText)
        val spinnerCourses : Spinner = findViewById(R.id.spinnerCourses)

        val adapterCourses = ArrayAdapter<CourseInfo>(this,
                android.R.layout.simple_spinner_item,
                DataManager.courses.values.toList())
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerCourses.adapter = adapterCourses

        notePosition = intent.getIntExtra(EXTRA_NOTE_POSITION, POSITION_NOT_SET)

        if (notePosition != POSITION_NOT_SET){
            displayNote()
        }
    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition]
        textNoteTitle?.text = note.title
        textNoteText?.text = note.text

        val coursePosition = DataManager.courses.values.indexOf(note.course)
        spinnerCourses?.setSelection(coursePosition)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}