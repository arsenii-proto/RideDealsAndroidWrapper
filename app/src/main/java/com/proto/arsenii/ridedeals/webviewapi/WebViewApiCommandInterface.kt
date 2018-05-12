package com.proto.arsenii.ridedeals.webviewapi

interface WebViewApiCommandInterface {

    fun handle( cmd: String, data: WebViewApiArgument): Any?

}