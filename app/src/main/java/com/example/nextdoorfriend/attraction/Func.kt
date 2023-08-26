package com.example.nextdoorfriend.attraction

import android.content.Intent
import com.google.gson.Gson

object Func {

    fun <T: Sendable> Intent.putAny(name: String, any: T) {
        this.putExtra(name, (any as Sendable).toString())
    }
    fun <T: Sendable> Intent.getAny(name: String, anyType: Class<T>): T {
        return Gson().fromJson(this.getStringExtra(name), anyType)
    }

}