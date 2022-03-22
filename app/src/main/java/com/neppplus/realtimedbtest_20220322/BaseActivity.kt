package com.neppplus.realtimedbtest_20220322

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

abstract class BaseActivity : AppCompatActivity() {

    lateinit var mContext: Context

//    멤버변수로 RealtimeDB에 연결
    val realtimeDB = FirebaseDatabase.getInstance("https://realtimedbtest-20220322-default-rtdb.asia-southeast1.firebasedatabase.app/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this
    }

    abstract fun setupEvents()
    abstract fun setValues()

}