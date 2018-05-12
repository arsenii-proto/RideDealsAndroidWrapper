package com.proto.arsenii.ridedeals.webviewapi.plugins

import com.proto.arsenii.ridedeals.webviewapi.WebViewApiArgument
import com.proto.arsenii.ridedeals.webviewapi.WebViewApiCommandInterface
import com.proto.arsenii.ridedeals.webviewapi.WebViewApiConfig

object Config : WebViewApiCommandInterface {

    override fun handle(cmd: String, data: WebViewApiArgument): Any? {

        val name = data.getString("name", "nothing")

        return when(cmd) {
            "config.get"    -> get(name)
            else -> object { }
        }
    }

    fun get( name: String ): Any? {

        val value = WebViewApiConfig.get( name, null )

        if( value !== null ){

            return value
        }

        return object { }
    }
}