package com.proto.arsenii.ridedeals.webviewapi

object WebViewApiConfig {

    private val map = mutableMapOf<String, Any?>()

    fun put( key: String, value: Any? ) {

        map[ key ] = value
    }

    fun get( key: String, default: Any? = null ): Any? {

        if( map.containsKey( key ) ){

            return map[ key ]
        }

        return default
    }
}