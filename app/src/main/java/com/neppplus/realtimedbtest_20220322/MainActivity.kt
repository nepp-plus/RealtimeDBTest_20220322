package com.neppplus.realtimedbtest_20220322

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.neppplus.realtimedbtest_20220322.adapters.ChattingRecyclerAdapter
import com.neppplus.realtimedbtest_20220322.datas.ChattingData
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : BaseActivity() {

    var messageCount = 0L  // DB에 저장된 채팅 갯수를 담을 변수.  Long 타입으로 저장.

    val mChattingList = ArrayList<ChattingData>()

    lateinit var mAdapter: ChattingRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

//        realtimeDb의 항목중, message 항목에 변화가 생길때.
        realtimeDB.getReference("message").addValueEventListener( object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

//                파이어베이스의 DB내용 변경 => 이 함수를 실행시켜줌.

//                snapshot변수 : 현재 변경된 상태 => 자녀가 몇개인지 추출.

                messageCount = snapshot.childrenCount

//                snapshot => 마지막 자녀 (최신 채팅 메세지) 추출 => ChattingData로 변환 + 목록에 추가.

                mChattingList.add(  ChattingData(
                    snapshot.children.last().child("content").value.toString(),
                    snapshot.children.last().child("createdAt").value.toString()
                )  )


                mAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        btnSend.setOnClickListener {

            val inputContent = edtContent.text.toString()

            val now = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyy년 M월 d일 a h:mm")
            val nowStr = sdf.format( now.time )

//            inputContent, nowStr 두개의 데이터를 한번에 묶어서 (HashMap) 기록. => onDataChanged 함수도 한번만 실행.

            val inputMap = hashMapOf< String, String >(
                "content" to inputContent,
                "createdAt" to nowStr
            )

            realtimeDB.getReference("message").child(messageCount.toString()).setValue(inputMap)

        }

    }

    override fun setValues() {

        mAdapter = ChattingRecyclerAdapter(mContext, mChattingList)
        chattingRecyclerView.adapter = mAdapter
        chattingRecyclerView.layoutManager = LinearLayoutManager(mContext)

    }
}