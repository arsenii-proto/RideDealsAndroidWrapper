package com.proto.arsenii.ridedeals.webviewapi

import android.widget.Toast

class WebViewApiArgumentValue {

    private val parent: WebViewApiArgument
    val namespace: String
    val type: String
    val value: String

    constructor( row: String, parent: WebViewApiArgument ) {

        this.parent = parent
        val result = """^([A-z0-9\_\.]+)\:([A-z]+)\=(.+)$""".toRegex().find( row, 0 )

        if( result != null && result.groups.size == 4) {

            this.namespace  = result.groups[1]!!.value
            this.type       = result.groups[2]!!.value
            this.value      = result.groups[3]!!.value

        } else {

            this.namespace  = ""
            this.type       = ""
            this.value      = ""
        }
    }

    fun isName( namespace: String ): Boolean {

        return namespace.toRegex().matches( this.namespace )
    }

    fun isNumber()  = this.type == "number"
    fun isString()  = this.type == "string"
    fun isArray()   = this.type == "array"
    fun isMap()     = this.type == "object"
    fun isBool()    = this.type == "boolean"
    fun isInt()     = isNumber()
    fun isLong()    = isNumber()
    fun isShort()   = isNumber()
    fun isFloat()   = isNumber()
    fun isDouble()  = isNumber()

    override
    fun toString()  = this.value
    fun toInt()     = this.value.toInt()
    fun toLong()    = this.value.toLong()
    fun toShort()   = this.value.toShort()
    fun toFloat()   = this.value.toFloat()
    fun toDouble()  = this.value.toDouble()
    fun toBool()    = this.value === "true"

    fun getArrayChilds(): MutableList<WebViewApiArgumentValue?> {

        if( isArray() ){

            return parent.getArray( namespace )
        }

        return mutableListOf()
    }


    fun getMapChilds(): MutableMap<String, WebViewApiArgumentValue?> {

        if( isArray() ){

            return parent.getMap( namespace )
        }

        return mutableMapOf()
    }
}