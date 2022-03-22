package com.neppplus.realtimedbtest_20220322

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

//        DB 연결 -> 값 기록 연습.

        val db = FirebaseDatabase.getInstance("https://realtimedbtest-20220322-default-rtdb.asia-southeast1.firebasedatabase.app/") // 싱가폴 DB주소 대입

//        DB의 하위 항목 (Reference) 설정
        val testRef = db.getReference("test")

//        test 항목에, "Hello World!!" 기록

        testRef.setValue("Hello World")

    }
}