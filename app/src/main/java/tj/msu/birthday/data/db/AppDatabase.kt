package tj.msu.birthday.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import tj.msu.birthday.data.db.dao.StudentDAO
import tj.msu.birthday.data.db.model.Student

@Database(
    entities = [Student::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDAO(): StudentDAO
}