package com.jslee.recommend_product_kotlin

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.jslee.recommend_product_kotlin.service.MusicService
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {


    // 접속할 서비스 객체 선언
    var ipcService:MusicService? = null


    /**
    * @내용  : 서비스 접속을 관리하는 객체
    * @수정  :
    * @버젼  : 0.0.0
    * @작성일 :
    * @작성자 : 이재선
    **/
    val connection = object : ServiceConnection {
        /**
        * @내용  : 서비스에 접속이 성공했을 때
        *        p1: IBinder 파라미터 > 서비스의 onBind 메서드가 반환하는 객체를 받는다
        * @작성일 : 2021/04/11
        * @작성자 : 이재선
        **/
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            // 서비스를 추출
            val binder = p1 as MusicService.LocalBinder
            ipcService = binder.getService()
        }

        /**
        * @내용  : 서비스 접속을 해제했을 때
        * @작성일 : 2021/04/11
        * @작성자 : 이재선
        **/
        override fun onServiceDisconnected(p0: ComponentName?) {
            ipcService = null
        }
    }

    /**
    * @내용  : Activity가 만들어질 때 최초 한번만 실행
    * @작성일 : 2021/04/11
    * @작성자 : 이재선
    **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        val serviceIntent = Intent(this, MusicService::class.java)

        // '음악 서비스 실행' 버튼 클릭
        start_music_btn.setOnClickListener {

            // 서비스가 가동중이 아닐때만 서비스를 가동 (가동여부를 체크하지 않으면 버튼 누를 때 마다 서비스가 새로 시작하기 때문)
            val chk = isServiceRunning("kr.co.softcampus.ipc.TestService")

            if(chk == false){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    startForegroundService(serviceIntent)
                } else {
                    // 서비스 시작
                    startService(serviceIntent)
                }
            }
            // 서비스에 접속
            bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)

        }


        // '음악 서비스 종료' 버튼 클
        stop_mucic_btn.setOnClickListener{
            // 접속한 서비스에 접속을 해제
            unbindService(connection)

            // 서비스 종료
            stopService(serviceIntent)
        }

        take_time_btn.setOnClickListener{
            // 음악 서비스가 얼마나 됬는지 진행상태값을 반환하여 music_textView림 에 뿌
            var value = ipcService?.getMusicProgressRate()
            music_textView.text = "value : $value"
        }


    }

    override fun onDestroy() {
        super.onDestroy()
    }


    /**
    * @내용  : 서비스 실행 여부를 검사하는 함수
    * @작성일 : 2021/04/11
    * @작성자 : 이재선
    **/
    fun isServiceRunning(name:String) : Boolean{
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        // 현재 실행중인 서비스들을 모두 가져와서 리스트에 담는다
        val serviceList = manager.getRunningServices(Int.MAX_VALUE)

        for(serviceInfo in serviceList){
            // 만약 원하는 서비스의 이름이 있다면 true 를 반환
            if(serviceInfo.service.className == name){
                return true
            }
        }
        return false
    }
}