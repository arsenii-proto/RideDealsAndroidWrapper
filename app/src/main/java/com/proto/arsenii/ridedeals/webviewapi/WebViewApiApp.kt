package com.proto.arsenii.ridedeals.webviewapi

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@SuppressLint("StaticFieldLeak")
object WebViewApiApp {

    lateinit var onError:               Runnable
    lateinit var onConsole:             Runnable
    lateinit var onLoad:                Runnable
    lateinit var onFinish:              Runnable
    lateinit var lastError:             WebResourceError
    lateinit var lastHttpError:         WebResourceResponse
    lateinit var lastUrl:               String
    lateinit var lastConsoleMessage:    ConsoleMessage

    private val events      = mutableMapOf<String, MutableList<WebViewApiEventListenerInterface>>()
    private val commands    = mutableMapOf<String, WebViewApiCommandInterface>()

    private val converter   = Gson()

    private val webViewClient       = object : WebViewClient() {

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)

            if( error != null ) {

                this@WebViewApiApp.lastError = error
            }

            if( this@WebViewApiApp::onError.isInitialized ){

                this@WebViewApiApp.onError.run()
            }
        }

        override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
            super.onReceivedHttpError(view, request, errorResponse)

            if( errorResponse != null ) {

                this@WebViewApiApp.lastHttpError = errorResponse
            }

            if( this@WebViewApiApp::onError.isInitialized ){

                this@WebViewApiApp.onError.run()
            }
        }

        override fun onLoadResource(view: WebView?, url: String?) {
            super.onLoadResource(view, url)

            if( url != null ){

                this@WebViewApiApp.lastUrl = url
            }

            if( this@WebViewApiApp::onLoad.isInitialized ){

                this@WebViewApiApp.onLoad.run()
            }
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            if( url != null ){

                this@WebViewApiApp.lastUrl = url
            }

            if( this@WebViewApiApp::onFinish.isInitialized ){

                this@WebViewApiApp.onFinish.run()
            }
        }
    }

    private val webChromeClient     = object : WebChromeClient() {

        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {

            if( consoleMessage != null ){

                this@WebViewApiApp.lastConsoleMessage = consoleMessage
            }

            if( this@WebViewApiApp::onConsole.isInitialized ){

                this@WebViewApiApp.onConsole.run()
            }

            return super.onConsoleMessage(consoleMessage)
        }
    }

    fun registerCommand(pattern: String, cmd: WebViewApiCommandInterface): Boolean {

        if (! commands.containsKey( pattern ) ){

            commands.put( pattern, cmd )

            return true
        }

        return false
    }

    fun registerListenner( event: String, listener: WebViewApiEventListenerInterface) {

        if (! events.containsKey( event ) ){

            events.put( event, mutableListOf<WebViewApiEventListenerInterface>() )
        }

        events.get( event )?.add( listener )


    }

    fun send( event: String, data: Any? ) {

        if ( WebViewApiConfig.get( "wrapper" ) !== null ) {

            var module  = "window"
            var wrapper = (WebViewApiConfig.get( "wrapper") as WebView)

            if ( WebViewApiConfig.get( "osModule" ) !== null ){

                module = "window.${WebViewApiConfig.get( "osModule" )}"
            }

            wrapper.post {

                wrapper.loadUrl("javascript:(() => { $module.receive('$event', \"${converter.toJson( data )}\") })()")
            }

        }
    }

    fun init(useChromeClient: Boolean = true) {

        if( WebViewApiConfig.get( "wrapper" ) !== null ) {

            var wrapper = (WebViewApiConfig.get( "wrapper") as WebView)

            wrapper.settings.javaScriptEnabled  = true
            wrapper.webViewClient               = webViewClient

            if( useChromeClient ) {

                wrapper.webChromeClient         = webChromeClient
            }

            if ( WebViewApiConfig.get( "osModule" ) !== null ){

                wrapper.addJavascriptInterface( this, WebViewApiConfig.get( "osModule" ) as String )
            }

            if( WebViewApiConfig.get( "baseUrl" ) !== null ){

                wrapper.loadUrl( WebViewApiConfig.get( "baseUrl" ) as String )
            }
        }
    }

    @JavascriptInterface
    fun commandsList(): String {

        var data = mutableListOf<String>()

        commands.map {

            data.add( it.key )
        }

        return converter.toJson( data )
    }

    @JavascriptInterface
    fun dispatch( event: String, data: String ){

        if ( events.containsKey( event ) ) {

            val argument = WebViewApiArgument( data )

            events.get( event )?.map {

                it.handle( event, argument )
            }
        }
    }

    @JavascriptInterface
    fun run( cmd: String, data: String ): String? {

        if( commands.containsKey( cmd ) ){

            val argument = WebViewApiArgument( data )
            val name = argument.getString("name", "nothing")
            val response = commands.get(cmd)!!.handle( cmd, argument )
            val stringResponse = converter.toJson( response )

            return stringResponse
        }

        return null
    }
}