package com.ngplus.storageapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ngplus.storageapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var _binding: ActivityMainBinding? = null
    var myDataset = mutableListOf<BookNumber>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding?.root)

        val sharedPref = getSharedPreferences("data_store", MODE_PRIVATE)
        sharedPref.edit()

        _binding?.save?.setOnClickListener {
            val name = _binding?.etName?.text.toString()
            val telephone = _binding?.etTelephone?.text.toString()
            if (nameAlreadyExist(name)) {
                Toast.makeText(this, "The name already exist !", Toast.LENGTH_LONG).show()
            } else {
                myDataset.add(
                    BookNumber(
                        name,
                        telephone
                    )
                )
            }

        }

        _binding?.rvList?.adapter = ItemNumberAdapter(this, myDataset)
        _binding?.rvList?.setHasFixedSize(true)
    }

    private fun nameAlreadyExist(name: String): Boolean {
        return myDataset.any { it.name == name }
    }
}