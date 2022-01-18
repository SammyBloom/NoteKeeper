 package com.example.notekeeper

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.RequiresApi
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
            R.id.action_next -> {
                moveNext()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveNext() {
        ++notePosition
        displayNote()
        invalidateOptionsMenu()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(notePosition >= DataManager.notes.lastIndex){
            val menuItem = menu?.findItem(R.id.action_next)
            if(menuItem != null){
                menuItem.icon = getDrawable(R.drawable.ic_baseline_block_24)
                menuItem.isEnabled = false
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    private fun saveNote() {
       val note = DataManager.notes[notePosition]
       note.title = textNoteTitle?.text.toString()
        note.text = textNoteText?.text.toString()
       note.course = spinnerCourses?.selectedItem as CourseInfo
    }
}