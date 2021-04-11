package com.jslee.recommend_product_kotlin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.jslee.recommend_product_kotlin.Settings.MusicServicdActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var navController : NavController
    val SETTING_ACTIVITY = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        navController = nav_host_fragment.findNavController()


        val app = application as ApplicationClass
        main_textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, app.getConvertDpByRes(24.0f))


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d("jslee", "onCreateOptionsMenu")
        // XML로 메뉴를 구성
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       // 메뉴의 id 별로 분기한다.
        when(item.itemId){
            R.id.setting_item -> {

                val setting_Intent = Intent(this, MusicServicdActivity::class.java)
                // startActivity(second_intent)
                startActivityForResult(setting_Intent, SETTING_ACTIVITY)
            }
            R.id.geo_item -> {
                val uri = Uri.parse("geo:37.243243,131.861601")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            R.id.https_item -> {
                val uri = Uri.parse("https://jade314.tistory.com/")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            R.id.tel_item -> {
                val uri = Uri.parse("tel:12341234")
                val intent = Intent(Intent.ACTION_DIAL, uri)
                startActivity(intent)
            }
            R.id.item3 -> {
            }
        }

        return super.onOptionsItemSelected(item)
    }

}