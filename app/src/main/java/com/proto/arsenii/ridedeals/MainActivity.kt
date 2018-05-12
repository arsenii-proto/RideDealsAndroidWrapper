package com.proto.arsenii.ridedeals

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.webkit.ConsoleMessage
import android.webkit.WebView
import com.google.gson.Gson
import com.proto.arsenii.ridedeals.webviewapi.WebViewApiApp
import com.proto.arsenii.ridedeals.webviewapi.WebViewApiConfig
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initWebApp( web_wrapper )
    }

    override fun onResume() {
        super.onResume()

        fullScreenContent( fullscreen_content )
    }

    fun initWebApp(wrapper: WebView) {

        wrapper.visibility  = View.VISIBLE

        WebViewApiConfig.put( "context", this )
        WebViewApiConfig.put( "wrapper", wrapper )
        WebViewApiConfig.put( "osModule", getString( R.string.web_app_module ) )
        WebViewApiConfig.put( "baseUrl", getString( R.string.web_app_base_url ) )

        val firebase = mutableMapOf<String, Any?>()

        firebase.put("apiKey","AIzaSyAQb8NpWhD0S6GOO9V2XdbgaDNNVdLNWQ4")
        firebase.put("authDomain","arsenii-ride-deals.firebaseapp.com")
        firebase.put("databaseURL","https://arsenii-ride-deals.firebaseio.com")
        firebase.put("projectId","arsenii-ride-deals")
        firebase.put("storageBucket","arsenii-ride-deals.appspot.com")
        firebase.put("messagingSenderId","414909120149")

        WebViewApiConfig.put( "firebase", firebase)
        WebViewApiConfig.put( "language", Locale.getDefault())

        WebViewApiApp.onFinish = Runnable {

            wrapper.visibility  = View.VISIBLE
            fullScreenContent(wrapper)
        }

        WebViewApiApp.onConsole = Runnable {

            if( WebViewApiApp.lastConsoleMessage.messageLevel() == ConsoleMessage.MessageLevel.ERROR ){

                val error = WebViewApiApp.lastConsoleMessage
                Log.e( "ADVD", WebViewApiApp.lastConsoleMessage.message() )
//                showError()
            }
        }

        WebViewApiRegisterPlugins.register()


        WebViewApiApp.init()
    }

    fun fullScreenContent(view: View) {

        val mHideHandler = Handler()
        mHideHandler.postDelayed({
            supportActionBar?.hide()
            mHideHandler.postDelayed({
                view.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LOW_PROFILE or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            }, 300.toLong())
        }, 100.toLong())
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        WebViewApiApp.send( "test.event", "Nimic" )
    }

    fun showError() {

        error_page.visibility   = View.VISIBLE
        web_wrapper.visibility  = View.GONE
    }
}