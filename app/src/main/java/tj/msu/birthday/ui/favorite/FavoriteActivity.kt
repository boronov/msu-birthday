package tj.msu.birthday.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import tj.msu.birthday.R
import tj.msu.birthday.adapter.StudentListAdapter
import tj.msu.birthday.data.db.model.Student
import tj.msu.birthday.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val studentLiveData = MutableLiveData<List<Student>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.favorite)
/*
        binding.studentList.layoutManager = LinearLayoutManager(this)

        studentLiveData.observe(this, Observer {
            binding.studentList.adapter = StudentListAdapter(it)
        })*/
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}