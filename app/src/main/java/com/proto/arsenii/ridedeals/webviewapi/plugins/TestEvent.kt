package com.proto.arsenii.ridedeals.webviewapi.plugins

import com.proto.arsenii.ridedeals.webviewapi.WebViewApiArgument
import com.proto.arsenii.ridedeals.webviewapi.WebViewApiEventListenerInterface
import android.os.Handler
import com.proto.arsenii.ridedeals.webviewapi.WebViewApiApp

object TestEvent : WebViewApiEventListenerInterface {

    override fun handle(event: String, data: WebViewApiArgument) {

        Toast.show("Event Test Loaded")

        Handler().postDelayed({

            WebViewApiApp.send( "test.event", object {

                val aloha = 12

            })

        }, 2000)

    }

}