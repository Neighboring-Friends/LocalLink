package com.example.nextdoorfriend.attraction

import android.content.Intent
import com.google.gson.Gson

object Func {

    fun <T> Intent.putAny(name: String, any: T) {
        this.putExtra(name, Gson().toJson(any))
    }
    fun <T> Intent.getAny(name: String, anyType: Class<T>): T {
        return Gson().fromJson(this.getStringExtra(name), anyType)
    }

}