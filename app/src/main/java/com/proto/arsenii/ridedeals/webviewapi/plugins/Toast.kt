package com.proto.arsenii.ridedeals.webviewapi.plugins

import android.content.Context
import android.widget.Toast as TOAST
import com.proto.arsenii.ridedeals.webviewapi.WebViewApiArgument
import com.proto.arsenii.ridedeals.webviewapi.WebViewApiCommandInterface
import com.proto.arsenii.ridedeals.webviewapi.WebViewApiConfig

object Toast : WebViewApiCommandInterface {

    override fun handle(cmd: String, data: WebViewApiArgument): Boolean {

        val text = data.getString("text", "nothing")

        return when(cmd) {
            "toast.showLong"    -> showLong(text)
            "toast.show"        -> show(text)
            else -> false
        }
    }

    fun showLong( text: String ): Boolean {

        if( WebViewApiConfig.get( "context" ) !== null ){

            TOAST.makeText( WebViewApiConfig.get( "context" ) as Context, text, TOAST.LENGTH_LONG ).show()
            return true
        }

        return false
    }

    fun show( text: String ): Boolean {

        if( WebViewApiConfig.get( "context" ) !== null ){

            TOAST.makeText( WebViewApiConfig.get( "context" ) as Context, text, TOAST.LENGTH_SHORT ).show()
            return true
        }

        return false
    }
}