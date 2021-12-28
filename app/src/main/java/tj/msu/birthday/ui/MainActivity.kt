package tj.msu.birthday.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.niwattep.materialslidedatepicker.SlideDatePickerDialog
import com.niwattep.materialslidedatepicker.SlideDatePickerDialogCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tj.msu.birthday.App
import tj.msu.birthday.R
import tj.msu.birthday.adapter.StudentListAdapter
import tj.msu.birthday.changeStatusBarColor
import tj.msu.birthday.data.db.model.Student
import tj.msu.birthday.databinding.ActivityMainBinding
import tj.msu.birthday.interfaces.ChooseCursListener
import tj.msu.birthday.interfaces.ChooseNapravListener
import tj.msu.birthday.ui.dialog.ChooseCursDialog
import tj.msu.birthday.ui.dialog.ChooseNapravDialog
import tj.msu.birthday.ui.favorite.FavoriteActivity
import tj.msu.birthday.ui.timetable.TimetableActivity
import tj.msu.birthday.ui.week.WeekActivity
import java.util.*


class MainActivity : AppCompatActivity(),
    ChooseNapravListener,
    ChooseCursListener,
    SlideDatePickerDialogCallback {
    private lateinit var binding: ActivityMainBinding

    private val studentLiveData = MutableLiveData<List<Student>>()

    private val fioLiveData = MutableLiveData<String>("%")
    private val cursLiveData = MutableLiveData<String>("%")
    private val napravLiveData = MutableLiveData<String>("%")
    private val dateLiveData = MutableLiveData<String>("%")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        changeStatusBarColor(binding.root, this, R.color.main)

        binding.studentList.layoutManager = LinearLayoutManager(this)

        binding.buttonNaprav.setOnClickListener {
            ChooseNapravDialog(this).show(supportFragmentManager, null)
        }
        binding.buttonCurs.setOnClickListener {
            ChooseCursDialog(this).show(supportFragmentManager, null)
        }

        binding.buttonRestore.setOnClickListener {
            binding.textViewFio.text.clear()
            binding.buttonCurs.text = getString(R.string.curs)
            binding.buttonDate.text = getString(R.string.date)
            binding.buttonNaprav.text = getString(R.string.naprav)

            cursLiveData.value = "%"
            napravLiveData.value = "%"
            dateLiveData.value = "%"
            fioLiveData.value = "%"
        }

        binding.buttonDate.setOnClickListener {
            SlideDatePickerDialog.Builder()
                .setLocale(Locale("ru"))
                .setHeaderDateFormat("dd MMMM")
                .setShowYear(false)
                .setCancelText("Отмена")
                .setConfirmText("Ок")
                .build()
                .show(supportFragmentManager, null)
        }

        studentLiveData.observe(this, Observer {
            binding.studentList.adapter = StudentListAdapter(it)
        })

        val observer = Observer<String> {
            CoroutineScope(Dispatchers.IO).launch {
                val data =
                    App.database.studentDAO().getFiltered(
                        name = "%${fioLiveData.value.toString()}%",
                        curs = "%${cursLiveData.value.toString()}%",
                        date = "%${dateLiveData.value.toString()}%",
                        naprav = "%${napravLiveData.value.toString()}%"
                    )
                withContext(Dispatchers.Main) {
                    studentLiveData.value = data
                }
            }
        }

        fioLiveData.observe(this, observer)
        cursLiveData.observe(this, observer)
        napravLiveData.observe(this, observer)
        dateLiveData.observe(this, observer)

        binding.textViewFio.addTextChangedListener {
            fioLiveData.value = "%$it%"
        }

        CoroutineScope(Dispatchers.IO).launch {
            val data = App.database.studentDAO().getAll()
            withContext(Dispatchers.Main) {
                studentLiveData.value = data
            }
        }

        binding.buttonWeek.setOnClickListener {
            val intent = Intent(this, WeekActivity::class.java)
            startActivity(intent)
        }

        binding.buttonTimetable.setOnClickListener {
            val intent = Intent(this, TimetableActivity::class.java)
            startActivity(intent)
        }

        binding.buttonFavorite.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun callbackNaprav(naprav: String, napravID: String) {
        napravLiveData.value = napravID
        binding.buttonNaprav.text = naprav
    }

    override fun callbackCurs(curs: String, cursID: Int) {
        val cursid = cursID + 1
        cursLiveData.value = if (cursid != 0) cursid.toString() else ""
        binding.buttonCurs.text = curs
    }

    @SuppressLint("SetTextI18n")
    override fun onPositiveClick(day: Int, month: Int, year: Int, calendar: Calendar) {
        val myDay = if (day < 10) "0$day" else day.toString()
        val myMonth = if (month < 10) "0$month" else month.toString()

        binding.buttonDate.text = "$myDay.$myMonth"
        dateLiveData.value = "$myDay.$myMonth"
    }
}