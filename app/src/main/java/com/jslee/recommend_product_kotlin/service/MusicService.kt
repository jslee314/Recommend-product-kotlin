package com.jslee.recommend_product_kotlin.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlin.concurrent.thread

/**
* @내용 : 음악 재생에 대한 기능을 수행하는 서비스 클래스
*       Activity나 Fragment와는 달리 화면이 없기때문에 생명주기는
*       시작(onStartCommand()), 종료(onDestroy()) 두개만 있음
* @수정 :
* @버젼 : 0.0.0
* @작성일/작성자 : 2021/04/11 이재선
**/
class MusicService : Service() {

    var value = 0
    var isRunning = false
    var binder = LocalBinder()

    /**
    * @내용 : 외부에서 해당 서비스에 접속하면 호출되는 함수
    *       엑티비티에서 서비스를 호출하면 이 매소드가 자동으로 호출된다.
    *       IPC : Activity 에서 실행중인 서비스를 제어하거나 데이터를 사용하는 등의 작업이 필요할때 사용하는 개념
    *       현재 실행중인 서비스에 접속하고 서비스가 가지고 있는 메서드를 호룰할 수 있는 개념. 이때 데이터를 반환받아 사용할 수 있
    * @작성일 : 2021/04/11
    * @작성자 : 이재선
    **/
    override fun onBind(intent: Intent): IBinder {
        return binder
    }


    /**
    * @내용 : 서비스가 시작할 때 호출되는 함수
    * @작성일 : 2021/04/11
    * @작성자 : 이재선
    **/
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        // android 8.0 부터 서비스를 실행하기위해서는 Foreground에서 실행하고 사용자에게 알림을 띄워야 함
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel("service", "service", NotificationManager.IMPORTANCE_HIGH)
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            manager.createNotificationChannel(channel)

            val builder = NotificationCompat.Builder(this, "service")
            builder.setSmallIcon(android.R.drawable.ic_menu_camera)
            builder.setContentTitle("음악 재생 중")
            builder.setContentText("현재 음악서비스가 재생중 입니다 :) ")
            val notification = builder.build()

            startForeground(10, notification)   // foreground 에서 실해하기 위해 알림 메시지를 띄
        }

        isRunning = true

        thread {
            while (isRunning){

                // todo: 음악 재생에 관한 기능 구현 예정

                SystemClock.sleep(1000)
                Log.d("jslee", "음악이 재생된 시간 : $value")
                value++
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }


    /**
    * @내용 : 서비스가 종료할 때 호출되는 함수
    * @작성일 : 2021/04/11
    * @작성자 : 이재선
    **/
    override fun onDestroy() {
        super.onDestroy()
        isRunning = false


    }

    /**
    * @내용 : 음악 서비스가 얼마동안 재생됬는지를 반환하는 함수
    * @작성일 : 2021/04/11
    * @작성자 : 이재선
    **/
    fun getMusicProgressRate() : Int{
        return value
    }


    /**
    * @내용 : Activity에서 해당 서비스를 추출하기위한 클래스(객체)
    * @수정 :
    * @버젼 : 0.0.0
    * @작성일 : 2021/04/11
    * @작성자 : 이재선
    **/
    inner class LocalBinder : Binder(){
        fun getService() : MusicService{
            return this@MusicService
        }
    }

}