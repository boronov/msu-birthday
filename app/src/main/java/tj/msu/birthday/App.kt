package tj.msu.birthday

import android.app.Application
import androidx.room.Room
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import tj.msu.birthday.data.db.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        mFirebaseAnalytics = Firebase.analytics
        database = Room.databaseBuilder(this, AppDatabase::class.java, DATABASE_NAME)
            .createFromAsset("database/birthday.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        lateinit var database: AppDatabase private set
        private lateinit var mFirebaseAnalytics: FirebaseAnalytics
    }
}