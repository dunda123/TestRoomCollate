package cz.dunda.android.testroomcollate

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import com.commonsware.cwac.saferoom.SafeHelperFactory
import cz.dunda.android.testy.roomtest.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private var db: AppDatabase? = null
        val APP_DB_NAME: String = "room_test.db"

        const val TAG = "XXX_MainActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this@MainActivity).get(UserViewModel::class.java)

        btn_query_data.setOnClickListener { viewModel.findAllUsers(getDB()) }
        btn_add_test_data.setOnClickListener { viewModel.addTestUsers(getDB()) }
        btn_test_raw_sql.setOnClickListener { viewModel.testRawSQL(getDB(),
            "CREATE TABLE IF NOT EXISTS User1 (uid INTEGER PRIMARY KEY AUTOINCREMENT, first_name TEXT NOT NULL, last_name TEXT NOT NULL COLLATE UNICODE)"
        ) }

        // Observe data from Room
        viewModel.allUsers.observe(this, Observer {
            // Event -> allUsers chage
            Log.d(TAG, "Users->$it")
        })

    }

    fun getDB(): AppDatabase {
        if (db != null) {
            return db!!
        }
        val shf = SafeHelperFactory("pass123".toCharArray());
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, APP_DB_NAME)
            .openHelperFactory(shf) // TODO When this commented, CWAC is disable and Error throw
            .build()

        if (db == null) {
            throw Exception("Room Database don't create")
        }
        return (db!!)
    }
}
