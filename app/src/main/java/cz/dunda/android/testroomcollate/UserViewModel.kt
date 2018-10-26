package cz.dunda.android.testy.roomtest

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.dunda.android.testroomcollate.AppDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import androidx.sqlite.db.SimpleSQLiteQuery




class UserViewModel(): ViewModel() {

    companion object {
        const val TAG = "XXX_ViewModel.class"
    }


    var allUsers: MutableLiveData<List<User>> = MutableLiveData<List<User>>()

    private var dispSelAllUser: Disposable? = null
    private var dispInsUser: Disposable? = null

    init {

    }


    fun findAllUsers(db: AppDatabase) {
        Log.d(TAG, "Run Query on Room...")
        dispSelAllUser = Observable.create<List<User>> { emitter ->
            emitter.onNext(db.userDao().all) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { users ->
                allUsers.postValue(users)
            }
    }

    fun testRawSQL(db: AppDatabase, sql: String) {
        Log.d(TAG, "Test RAW sql->$sql")

        val query = SimpleSQLiteQuery(sql)

        dispInsUser  =  Observable.create<Int> { emitter ->
            emitter.onNext(db.userDao().testRawSQL(query)) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { user ->
                Log.d(TAG, "SQL ok")
            }
    }

    fun addTestUsers(db: AppDatabase) {
        Log.d(TAG, "Run ADD Test Users...")

        // TODO This is correct ordering by lastName
        addUser(db, User(firstName = "Pavel", lastName = "Cerhas"))
        addUser(db, User(firstName = "Pavel", lastName = "Česka"))
        addUser(db, User(firstName = "Pavel", lastName = "Drahos"))
        addUser(db, User(firstName = "Pavel", lastName = "Havrda"))
        addUser(db, User(firstName = "Pavel", lastName = "Moulis"))
        addUser(db, User(firstName = "Pavel", lastName = "Navratil"))
        addUser(db, User(firstName = "Pavel", lastName = "Netrval"))
        addUser(db, User(firstName = "Pavel", lastName = "Němec"))
        addUser(db, User(firstName = "Pavel", lastName = "Ňoufa"))
        addUser(db, User(firstName = "Pavel", lastName = "Sebastian"))
        addUser(db, User(firstName = "Pavel", lastName = "Šebesta"))


    }

    fun addUser(db: AppDatabase, user: User) {
        Log.d(TAG, "Add User to Room->$user")
        dispInsUser  =  Observable.create<Unit> { emitter ->
            emitter.onNext(db.userDao().insert(user)) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { user ->
                Log.d(TAG, "Add Ok")
            }
    }

    override fun onCleared() {

        dispSelAllUser?.let { it.dispose() }
        dispInsUser?.let { it.dispose() }

        super.onCleared()
    }
}