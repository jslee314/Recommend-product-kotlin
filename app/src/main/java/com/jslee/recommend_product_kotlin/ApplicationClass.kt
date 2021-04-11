package com.jslee.recommend_product_kotlin

import android.app.Application
import android.util.DisplayMetrics

/**
 * @내용  : 안드로이드 애플리케이션에 '단 하나'를 지정할 수 있는 객체이다.
 *         이 객체는 같은 안드로이드 application 이라면 어디서든 주소 값을 가져올 수 있다.
 *         이를 통해 안드로이드의 다양한 구성요소에서 공통적으로 사용하는 데이터를 관리할 수 있다.
 * @수정  :
 * @버젼  : 0.0.0
 * @작성일 : 2021/04/11
 * @작성자 : 이재선
 **/
class ApplicationClass : Application() {

    fun test() : String{

        return "application 호출"

    }

    /**
    * @내용  : 화면 사이즈에 따라서 폰트 크기를 조절하기위한 함수
    * @작성일 : 2021/04/11
    * @작성자 : 이재선
    **/
    fun getConvertDpByRes(dpSize: Float): Float{
        var weight = 0f

        val displayMetrics = DisplayMetrics()
        display!!.getRealMetrics(displayMetrics)

        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels

        val wi = width.toDouble() / displayMetrics.xdpi.toDouble()
        val hi = height.toDouble() / displayMetrics.ydpi.toDouble()
        val x = Math.pow(wi, 2.0)
        val y = Math.pow(hi, 2.0)
        val screenInches = Math.sqrt(x + y)

        if (screenInches < 5.0) {
            weight = 5.6f
        } else if (screenInches >= 5.0 && screenInches < 6.0) {
            weight = 5.5f
        } else if (screenInches >= 6.0 && screenInches < 7.0) {
            weight = 5.4f
        } else if (screenInches >= 7.0 && screenInches < 8.0) {
            weight = 5.3f
        } else if (screenInches >= 8.0 && screenInches < 9.0) {
            weight = 5.2f
        } else if (screenInches >= 9.0 && screenInches < 10.0) {
            weight = 5.1f
        } else if (screenInches >= 10.0 && screenInches < 11.0) {
            weight = 5.0f
        } else if (screenInches >= 11.0 && screenInches < 12.0) {
            weight = 4.9f
        } else if (screenInches >= 12.0 && screenInches < 13.0) {
            weight = 4.8f
        } else if (screenInches >= 13.0) {
            weight = 4.7f
        }

        return dpSize * (screenInches / weight).toFloat()

    }


}