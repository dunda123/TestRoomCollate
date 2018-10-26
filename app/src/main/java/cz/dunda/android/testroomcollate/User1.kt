package cz.dunda.android.testy.roomtest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class User1(
        @PrimaryKey(autoGenerate = true) var uid: Int? = null, // Zde musime umoznit NULL a zaroven vychozi nastavit na NULL, jinak autogenerate nefunguje
        @ColumnInfo(name = "first_name") var firstName: String = "",
        // TODO When i use collate, CWAC-SafeRoom throw Eception -> if i use clear Room, all is ok
        @ColumnInfo(collate = ColumnInfo.UNICODE, name="last_name") var lastName: String = "")
        //@ColumnInfo(name="last_name") var lastName: String = "")
