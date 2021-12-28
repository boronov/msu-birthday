package tj.msu.birthday

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity

const val DATABASE_NAME = "birthday"

const val STUDENT_MAIN_TABLE = "main"
const val STUDENT_COLUMN_ID = "_id"
const val STUDENT_COLUMN_NAME = "name"
const val STUDENT_COLUMN_BIRTHDAY = "birthday"
const val STUDENT_COLUMN_START = "start"
const val STUDENT_COLUMN_NAPRAV = "naprav"
const val STUDENT_COLUMN_FAVORITE = "isFavorite"

const val STUDENT_COURSE = "course"
const val STUDENT_DIRECTION = "direction"
const val STUDENT_FIO = "fio"


fun changeStatusBarColor(
    view: View,
    activity: AppCompatActivity,
    @ColorRes color: Int,
    lightStatusBar: Boolean = false
) {

    if (lightStatusBar) {
        setLightStatusBar(view, activity)
        return
    }

    val window = activity.window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.statusBarColor = activity.resources.getColor(color)
}


fun setLightStatusBar(view: View, activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags: Int = view.systemUiVisibility
        flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        view.systemUiVisibility = flags
        activity.window.statusBarColor = Color.WHITE
    } else {
        activity.window.statusBarColor = activity.resources.getColor(R.color.main)
    }
}
