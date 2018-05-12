package com.proto.arsenii.ridedeals.webviewapi

import android.widget.Toast

class WebViewApiArgument {

    private val values = mutableMapOf<String, WebViewApiArgumentValue>()

    constructor( data: String ) {

        (data + "&&").split( "&&" ).map {

            if(! it.isEmpty() ){

                val value = WebViewApiArgumentValue( it, this )
                values.put( value.namespace, value )
            }
        }
    }

    fun has( namespace: String )        = values.containsKey( namespace )
    fun get( namespace: String )        = values.get( namespace )

    fun hasBool( namespace: String )    = has( namespace ) && values.get( namespace )!!.isBool()
    fun hasInt( namespace: String )     = has( namespace ) && values.get( namespace )!!.isInt()
    fun hasShort( namespace: String )   = has( namespace ) && values.get( namespace )!!.isShort()
    fun hasLong( namespace: String )    = has( namespace ) && values.get( namespace )!!.isLong()
    fun hasFloat( namespace: String )   = has( namespace ) && values.get( namespace )!!.isFloat()
    fun hasDouble( namespace: String )  = has( namespace ) && values.get( namespace )!!.isDouble()
    fun hasString( namespace: String )  = has( namespace ) && values.get( namespace )!!.isString()
    fun hasArray( namespace: String )   = has( namespace ) && values.get( namespace )!!.isArray()
    fun hasMap( namespace: String )     = has( namespace ) && values.get( namespace )!!.isMap()

    fun getBool( namespace: String, default: Boolean = false ): Boolean {

        if( hasBool( namespace ) ){

            return values.get( namespace )!!.toBool()
        }

        return default
    }

    fun getInt( namespace: String, default: Int = 0 ): Int{

        if( hasInt( namespace ) ){

            return values.get( namespace )!!.toInt()
        }

        return default
    }

    fun getShort( namespace: String, default: Short = 0 ): Short{

        if( hasShort( namespace ) ){

            return values.get( namespace )!!.toShort()
        }

        return default
    }

    fun getLong( namespace: String, default: Long = 0 ): Long{

        if( hasLong( namespace ) ){

            return values.get( namespace )!!.toLong()
        }

        return default
    }

    fun getFloat( namespace: String, default: Float = 0.toFloat() ): Float{

        if( hasFloat( namespace ) ){

            return values.get( namespace )!!.toFloat()
        }

        return default
    }

    fun getDouble( namespace: String, default: Double = 0.toDouble() ): Double{

        if( hasDouble( namespace ) ){

            return values.get( namespace )!!.toDouble()
        }

        return default
    }

    fun getString( namespace: String, default: String = "" ): String{

        if( hasString( namespace ) ){

            return values.get( namespace )!!.toString()
        }

        return default
    }

    fun getArray( namespace: String, default: MutableList<WebViewApiArgumentValue?> = mutableListOf() ): MutableList<WebViewApiArgumentValue?> {

        var resultArray: MutableList<WebViewApiArgumentValue?> = mutableListOf()

        if( hasArray( namespace ) ){

            values.keys.map {

                if( it.startsWith( namespace ) && it !== namespace ) {

                    val subspace = it.replace( namespace, "" )

                    if( """^[A-z0-9_]+$""".toRegex().matches( subspace ) ) {

                        resultArray.add( values.get( it ) )
                    }
                }
            }

        }else{

            return default
        }

        return resultArray
    }

    fun getMap( namespace: String, default: MutableMap<String, WebViewApiArgumentValue?> = mutableMapOf() ): MutableMap<String, WebViewApiArgumentValue?> {

        var resultMap: MutableMap<String, WebViewApiArgumentValue?> = mutableMapOf()

        if( hasArray( namespace ) ){

            values.keys.map {

                if( it.startsWith( namespace ) && it !== namespace ) {

                    val subspace = it.replace( namespace, "" )

                    if( """^[A-z0-9_]+$""".toRegex().matches( subspace ) ) {

                        resultMap.put( subspace, values.get( it ) )
                    }
                }
            }

        }else{

            return default
        }

        return resultMap
    }
}