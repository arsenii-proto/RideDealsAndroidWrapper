package com.proto.arsenii.ridedeals.webviewapi

interface WebViewApiEventListenerInterface {

    fun handle( event: String, data: WebViewApiArgument )

}