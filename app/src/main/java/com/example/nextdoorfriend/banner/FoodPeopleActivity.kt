package com.example.nextdoorfriend.banner

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.databinding.ActivityFoodtogetherBinding

class FoodPeopleActivity :AppCompatActivity()
{
    private lateinit var binding: ActivityFoodtogetherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodtogetherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bloode.adapter = ArrayAdapter.createFromResource(
            this, R.array.bloodtype, android.R.layout.simple_list_item_1
        )

        binding.datecheckinglayer.setOnClickListener {
            val listener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                binding.birthe.text = "$year-${month.inc()}-$day"
            }
            DatePickerDialog(this, listener, 2000, 8, 19).show()

        }

        binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            binding.emergencye.isVisible = isChecked
        }

        binding.emergencye.isVisible = binding.checkbox.isChecked

        binding.floatingSaveActionButton.setOnClickListener {
            saveData()
            finish()
        }
    }

    private fun saveData() {


        Toast.makeText(this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun getBloodtype(): String {
        val bloodtype = binding.bloode.selectedItem.toString()
        val bloodpm = if (binding.bloodTypePlus.isChecked) "Rh+ " else "Rh- "
        return "$bloodpm$bloodtype"
    }

    private fun getWarn(): String {
        return if (binding.checkbox.isChecked) binding.emergencye.toString() else ""
    }
}
