package tj.msu.birthday.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.joda.time.Days
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import tj.msu.birthday.R
import tj.msu.birthday.data.db.model.Student

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
        viewHolder.id.text = position.toString()
        viewHolder.name.text = student[position].name
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

        viewHolder.dayToBirthday.text = "$p дней до ДР"
        viewHolder.napravCurs.text =
            "${student[position].naprav} - ${nowData.year - ((student[position].start) + 2000)}"

        /*viewHolder.itemView.setOnClickListener {
            Toast.makeText(it.context, "work", Toast.LENGTH_LONG).show()
        }*/

    }

    override fun getItemCount() = student.size

}
