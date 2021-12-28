package tj.msu.birthday.ui.timetable

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import tj.msu.birthday.*
import tj.msu.birthday.databinding.ActivityTimetableBinding
import tj.msu.birthday.interfaces.ChooseCursListener
import tj.msu.birthday.interfaces.ChooseNapravListener
import tj.msu.birthday.ui.dialog.ChooseCursDialog
import tj.msu.birthday.ui.dialog.ChooseNapravDialog

class TimetableActivity : AppCompatActivity(),
    ChooseNapravListener,
    ChooseCursListener {
    private lateinit var binding: ActivityTimetableBinding
    private val cursLiveData = MutableLiveData<String>("")
    private val napravLiveData = MutableLiveData<String>("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimetableBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.elevation = 0.0f
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#002147")))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.timetable)

        changeStatusBarColor(binding.root, this, R.color.blue)

        binding.choosePanel.isVisible = !intent.hasExtra(STUDENT_DIRECTION)

        binding.root.setOnRefreshListener {
            binding.root.isRefreshing = false
            binding.webviewTimetable.loadUrl("https://test-msu-back.herokuapp.com/?direction=${napravLiveData.value}&course=${cursLiveData.value}")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.webviewTimetable.setOnScrollChangeListener { _, _, scrollY, _, _ ->
                binding.root.isEnabled = scrollY == 0
                Log.d("TAG_TEST", "onProgressChanged: $scrollY")
            }
        } else {
            binding.root.isEnabled = false
        }

        if (intent.hasExtra(STUDENT_DIRECTION)) {
            supportActionBar?.title = "${getString(R.string.timetable)} (${
                intent.getStringExtra(
                    STUDENT_FIO
                )
            })"
            napravLiveData.value = intent.getStringExtra(STUDENT_DIRECTION)
            cursLiveData.value = intent.getIntExtra(STUDENT_COURSE, 0).toString()
            binding.webviewTimetable.loadUrl("https://test-msu-back.herokuapp.com/?direction=${napravLiveData.value}&course=${cursLiveData.value}")
        } else {
            binding.webviewTimetable.loadUrl("https://test-msu-back.herokuapp.com/")
        }

        binding.webviewTimetable.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                Log.d("TAG_TEST", "onProgressChanged: $progress")
                binding.webviewTimetable.isVisible = progress == 100
                binding.progressCircular.isVisible = progress != 100
            }
        }

        binding.buttonNaprav.setOnClickListener {
            ChooseNapravDialog(this, false).show(supportFragmentManager, null)
        }
        binding.buttonCurs.setOnClickListener {
            ChooseCursDialog(this, false).show(supportFragmentManager, null)
        }

        binding.buttonSend.setOnClickListener {
            binding.webviewTimetable.loadUrl("https://test-msu-back.herokuapp.com/?direction=${napravLiveData.value}&course=${cursLiveData.value}")
        }
    }

    override fun callbackNaprav(naprav: String, napravID: String) {
        napravLiveData.value = napravID
        binding.buttonNaprav.text = naprav
    }

    override fun callbackCurs(curs: String, cursID: Int) {
        cursLiveData.value = if (cursID != 0) cursID.toString() else ""
        binding.buttonCurs.text = curs
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}