package com.iskandar.newscenter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.view.Gravity
import android.widget.RelativeLayout




data class SiteItem(val title:String, val url:String)
lateinit var newsList:List<SiteItem>

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initialize()
    }

    private fun initialize() {

        newsList = loadSitesList()
        rvNewsSites.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)

        init_ActionBar()

    }

    private fun init_ActionBar() {
        val bar = actionBar
        actionBar!!.setBackgroundDrawable(ColorDrawable(Color.BLACK))
        val textview = TextView(this@MainActivity)
        textview.text = getString(R.string.app_name)
        textview.gravity = Gravity.CENTER
        textview.setTextColor(Color.parseColor("#00ff00"))
        textview.textSize = 22f
        actionBar!!.setDisplayShowCustomEnabled(true)
        actionBar!!.customView = textview
    }

    private fun loadSitesList(): List<SiteItem> {

        val defaultList = getDefaultList()



        TODO()
        // first check shared preferences DEFAULT flag (custom made value!)
        // if DEFAULT flag exists , then load the above default list
        // else load shared preferences data !


    }

    private fun getDefaultList(): List<SiteItem> {
        val tmp = mutableListOf<SiteItem>()
        tmp.add(SiteItem("موقع PANET","http://www.panet.co.il/"))
        tmp.add(SiteItem("ynet","https://www.ynet.co.il/"))
        tmp.add(SiteItem("Reuters","https://www.reuters.com/"))
        return tmp
    }
}


