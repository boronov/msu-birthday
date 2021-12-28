package tj.msu.birthday.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.joda.time.Days
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import tj.msu.birthday.R
import tj.msu.birthday.STUDENT_COURSE
import tj.msu.birthday.STUDENT_DIRECTION
import tj.msu.birthday.STUDENT_FIO
import tj.msu.birthday.data.db.model.Student
import tj.msu.birthday.ui.timetable.TimetableActivity

class StudentListAdapter(
    private val student: List<Student>
) :
    RecyclerView.Adapter<StudentListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.id)
        val name: TextView = view.findViewById(R.id.name)
        val birthday: TextView = view.findViewById(R.id.birthday)
        val dayToBirthday: TextView = view.findViewById(R.id.day_to_birthday)
        val napravCurs: TextView = view.findViewById(R.id.naprav_curs)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.birthday_list_item, viewGroup, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val isClickable = student[position].name.last() == '.'
        viewHolder.itemView.isLongClickable = isClickable
        if (isClickable) {
            viewHolder.name.text =
                student[position].name.substring(0 until student[position].name.lastIndex)

            viewHolder.itemView.setOnLongClickListener {
                val vibrator =
                    viewHolder.itemView.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= 26) {
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            50,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                } else {
                    vibrator.vibrate(50)
                }
                return@setOnLongClickListener true
            }
        } else {
            viewHolder.name.text = student[position].name
            viewHolder.itemView.setOnLongClickListener(null)
        }

        viewHolder.id.text = position.toString()
        viewHolder.birthday.text = student[position].birthday

        val formatter: DateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy")
        val dateTime: LocalDate = LocalDate.parse(student[position].birthday, formatter)
        val nowData: LocalDate = LocalDate.now()

        var nextBDay: LocalDate = dateTime.withYear(nowData.year)
        // Если в этом году День Рождения уже прошёл, то добавляем один год.
        if (nextBDay.isBefore(nowData) || nextBDay.isEqual(nowData)) {
            nextBDay = nextBDay.plusYears(1)
        }
        val p = Days.daysBetween(nowData, nextBDay).days

        viewHolder.dayToBirthday.text = "Осталось $p до дня рождения!"

        val course = nowData.year - ((student[position].start) + 2000) + 1
        if (course < 5) {
            viewHolder.itemView.isClickable = true
            viewHolder.itemView.setOnClickListener {
                val startTimetableActivity =
                    Intent(it.context, TimetableActivity::class.java).apply {
                        putExtra(STUDENT_DIRECTION, student[position].naprav)
                        putExtra(STUDENT_COURSE, course)
                        putExtra(STUDENT_FIO, viewHolder.name.text.toString())
                    }
                it.context.startActivity(startTimetableActivity)
            }
            viewHolder.napravCurs.text =
                "${student[position].naprav} - $course"
        } else {
            viewHolder.itemView.isClickable = false
            viewHolder.itemView.setOnClickListener(null)
            viewHolder.napravCurs.text =
                "Выпускник: ${student[position].naprav}-${(student[position].start) + 2000 + 4}"
        }

    }

    override fun getItemCount() = student.size

}
