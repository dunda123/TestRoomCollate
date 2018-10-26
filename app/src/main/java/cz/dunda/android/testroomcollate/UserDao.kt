package cz.dunda.android.testy.roomtest

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface UserDao {
    @get:Query("SELECT * FROM user ORDER BY last_name")
    val all: List<User>

    @Insert
    fun insert(vararg users: User)

    @RawQuery
    fun testRawSQL(query: SupportSQLiteQuery): Int

}