package com.iskandar.newscenter

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import android.view.LayoutInflater
import android.view.View
import android.view.View.LAYOUT_DIRECTION_LTR
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.action_bar_title.view.*
import kotlinx.android.synthetic.main.item_site.view.*
import java.io.InputStream
import java.net.URL
import android.os.StrictMode
import android.webkit.WebViewClient
import android.graphics.Rect
import android.support.v7.app.AlertDialog
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.ImageButton
import kotlinx.android.synthetic.main.item_other.view.*


data class SiteItem(val title:String, val url:String, val favIconURL:String)
lateinit var newsList:List<SiteItem>
lateinit var context:Context

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initialize()
    }

    private fun initialize() {

        context = this@MainActivity

        // no action bar // FORSAKEN
        // init_ActionBar()

        newsList = loadSitesList()

        init_webView()

        rvNewsSites.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        rvNewsSites.adapter = SiteListAdapter(webView,context)

    }

    private fun init_webView() {
        with(webView){
            with(settings){
                setSupportZoom(true)
                useWideViewPort = true
                displayZoomControls = true
                javaScriptEnabled = true
                domStorageEnabled = true
            }

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?) = false
            }

            loadUrl(newsList[0].url)
        }
    }


    override fun onBackPressed() {
        if(webView.canGoBack()) webView.goBack()
        else super.onBackPressed()
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

        tmp.add(SiteItem("موقع PANET","http://m.panet.co.il/",
            "http://www.panet.co.il/apple-touch-icon-144x144.png"))
        tmp.add(SiteItem("ynet","https://www.ynet.co.il/",
            "https://www.ynet.co.il/images/favicon/favicon_1.ico"))
        tmp.add(SiteItem("Reuters","https://mobile.reuters.com/",
            "https://s3.reutersmedia.net/resources_v2/images/favicon/favicon-96x96.png"))

        return tmp
    }
}

class SiteListAdapter(val webView: WebView, val context:Context) : RecyclerView.Adapter<SiteListAdapter.CustomViewHolder>() {

    override fun getItemCount(): Int {
        return newsList.count()+2   //  + 2 , to add Exit & About buttons, at end
    }

    class CustomViewHolder(v: View) : RecyclerView.ViewHolder(v)

    override fun getItemViewType(position: Int) = when(position){
        newsList.count(), newsList.count()+1 -> 2
        else -> 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when(viewType) {
            1 -> {
                CustomViewHolder(LayoutInflater.from(parent.context).
                    inflate(R.layout.item_site, parent, false))
            }
            else -> {
                CustomViewHolder(LayoutInflater.from(parent.context).
                    inflate(R.layout.item_other, parent, false))
            }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, pos: Int) {
        when(holder.itemViewType){
            1->{
                holder.itemView.txtItem.text = newsList[pos].title
                holder.itemView.imgItem.setImageBitmap(getFavIcon(pos))
                holder.itemView.setOnClickListener {
                    webView.loadUrl(newsList[pos].url)
                }
            }
            else->{
                when(pos){
                    newsList.count() -> {  // about button
                        holder.itemView.imgOtherButton.setImageResource(R.drawable.ic_info)
                        holder.itemView.setOnClickListener {
                            showInfoDialog()
                        }
                    }
                    newsList.count()+1 -> {  // exit button
                        holder.itemView.imgOtherButton.setImageResource(R.drawable.ic_exit)
                        holder.itemView.setOnClickListener {
                            (context as AppCompatActivity).finish()
                        }
                    }
                }
            }
        }
    }

    private fun showInfoDialog() {
        val alert = AlertDialog.Builder(context)
            .setIcon(R.drawable.ic_info)
            .setTitle("News Center")
            .setMessage("by Iskandar Mazzawi \u00A9")
            .setPositiveButton("OK", DialogInterface.OnClickListener {
                dialog, _ -> dialog.dismiss()
            })
            .create() as Dialog

        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    private fun getFavIcon(pos: Int): Bitmap? {

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val url = newsList[pos].favIconURL
        val inSt = URL(url).content as InputStream
        val options = BitmapFactory.Options()
        options.inSampleSize = 2
        return  inSt.use { BitmapFactory.decodeStream(it,Rect(0,0,40,40),options) }
    }

}
