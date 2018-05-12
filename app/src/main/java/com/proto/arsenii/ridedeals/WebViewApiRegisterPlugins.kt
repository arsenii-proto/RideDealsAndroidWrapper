package com.proto.arsenii.ridedeals

import com.proto.arsenii.ridedeals.webviewapi.WebViewApiApp
import com.proto.arsenii.ridedeals.webviewapi.plugins.Config
import com.proto.arsenii.ridedeals.webviewapi.plugins.TestEvent
import com.proto.arsenii.ridedeals.webviewapi.plugins.Toast

object WebViewApiRegisterPlugins {

    fun register() {

        WebViewApiApp.registerCommand( "toast.show", Toast )
        WebViewApiApp.registerCommand( "toast.showLong", Toast )
        WebViewApiApp.registerCommand( "config.get", Config )

        WebViewApiApp.registerListenner( "test.event", TestEvent)
    }
}