package cz.dunda.android.testroomcollate

import androidx.room.Database
import androidx.room.RoomDatabase
import cz.dunda.android.testy.roomtest.User
import cz.dunda.android.testy.roomtest.User1
import cz.dunda.android.testy.roomtest.UserDao

@Database(entities = arrayOf(User::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao


}
