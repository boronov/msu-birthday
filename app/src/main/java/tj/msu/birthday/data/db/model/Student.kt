package tj.msu.birthday.data.db.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import tj.msu.birthday.*

@Entity(tableName = STUDENT_MAIN_TABLE)
class Student {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = STUDENT_COLUMN_ID)
    @NotNull
    var id: Int = 0

    @ColumnInfo(name = STUDENT_COLUMN_NAME)
    @NotNull
    var name: String = ""

    @ColumnInfo(name = STUDENT_COLUMN_START)
    @NotNull
    var start: Int = 0

    @ColumnInfo(name = STUDENT_COLUMN_NAPRAV)
    @NotNull
    var naprav: String = ""

    @ColumnInfo(name = STUDENT_COLUMN_BIRTHDAY)
    @NotNull
    var birthday: String = ""
}