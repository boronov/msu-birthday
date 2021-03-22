package tj.msu.birthday.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import tj.msu.birthday.*
import tj.msu.birthday.data.db.model.Student

@Dao
interface StudentDAO {
    @Query(
        "SELECT * FROM $STUDENT_MAIN_TABLE " +
                "ORDER BY $STUDENT_COLUMN_NAME"
    )
    suspend fun getAll(): List<Student>

    @Query(
        "SELECT * FROM $STUDENT_MAIN_TABLE " +
                "WHERE $STUDENT_COLUMN_NAME LIKE :name " +
                "AND ($STUDENT_COLUMN_NAPRAV LIKE :naprav) " +
                "AND ($STUDENT_COLUMN_START LIKE :curs) " +
                "AND ($STUDENT_COLUMN_BIRTHDAY LIKE :date) " +
                "ORDER BY $STUDENT_COLUMN_NAME"
    )
    suspend fun getFiltered(name: String, curs: String, naprav: String, date: String): List<Student>
/*
    @Query(
        "SELECT * FROM $STUDENT_MAIN_TABLE " +
                "WHERE $STUDENT_COLUMN_FAVORITE = 1 " +
                "ORDER BY $STUDENT_COLUMN_NAME"
    )
    suspend fun getFavorite(): List<Student>

    @Query(
        "UPDATE $STUDENT_MAIN_TABLE " +
                "SET $STUDENT_COLUMN_FAVORITE = :isFavorite " +
                "WHERE $STUDENT_COLUMN_ID = :id"
    )
    suspend fun setFavorite(id: Int, isFavorite: Int)*/
}