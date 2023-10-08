package com.ngplus.storageapp

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ngplus.storageapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var _binding: ActivityMainBinding? = null
    var listOfflineMode = mutableListOf<BookNumber>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding?.root)

        _binding?.save?.setOnClickListener {
            val name = _binding?.etName?.text.toString()
            val telephone = _binding?.etTelephone?.text.toString()
            if (nameAlreadyExist(name)) {
                Toast.makeText(this, "The name already exist !", Toast.LENGTH_LONG).show()
            } else {
                listOfflineMode.add(
                    BookNumber(
                        name,
                        telephone
                    )
                )
                saveData()
                loadData()
                _binding?.rvList?.adapter?.notifyDataSetChanged()
            }
        }
        _binding?.rvList?.adapter = ItemNumberAdapter(this, listOfflineMode)
        _binding?.rvList?.setHasFixedSize(true)

    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        loadData()
        return super.onCreateView(parent, name, context, attrs)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    private fun saveData() {
        //
        val sharedPref = getSharedPreferences("data_store", MODE_PRIVATE)
        val editor = sharedPref.edit()
        //
        val objetString = Gson().toJson(listOfflineMode)
        editor.putString("data", objetString)
        editor.apply()
    }

    private fun loadData() {
        //
        val sharedPref = getSharedPreferences("data_store", MODE_PRIVATE)
        //
        val listNumbers = sharedPref.getString("data", null)
        listOfflineMode = Gson().fromJson(listNumbers, object : TypeToken<List<BookNumber>>(){}.type) ?: mutableListOf<BookNumber>()
    }

    private fun nameAlreadyExist(name: String): Boolean {
        return listOfflineMode.any { it.name == name }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
    }
}