package tj.msu.birthday.ui.week

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import tj.msu.birthday.R
import tj.msu.birthday.databinding.ActivityWeekBinding

class WeekActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeekBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeekBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.week)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}