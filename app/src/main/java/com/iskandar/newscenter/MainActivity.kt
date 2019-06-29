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
import android.view.LayoutInflater
import android.view.View.LAYOUT_DIRECTION_LTR
import android.view.View.TEXT_DIRECTION_LTR
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.action_bar_title.*
import kotlinx.android.synthetic.main.action_bar_title.view.*


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

        // to FORCE LTR for action bar also !
        window.decorView.layoutDirection = LAYOUT_DIRECTION_LTR

        supportActionBar?.run {
            setBackgroundDrawable(ColorDrawable(Color.BLACK))
            val v = LayoutInflater.from(this@MainActivity).inflate(R.layout.action_bar_title,null)
            title = ""
            setDisplayShowCustomEnabled(true)
            v.setOnClickListener{
                showAboutDialog()
            }
            v.btnCancel.setOnClickListener{
                finish()
            }
            customView = v
        }
    }

    private fun showAboutDialog() {
        // for check
        Toast.makeText(this@MainActivity,"Hello There !",Toast.LENGTH_LONG).show()
    }

    private fun loadSitesList(): List<SiteItem> {

        val defaultList = getDefaultList()

        return defaultList


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


