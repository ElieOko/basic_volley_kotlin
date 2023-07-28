package com.example.volley.volley

import com.android.volley.Request
import com.android.volley.RequestQueue
import android.content.Context
import com.android.volley.toolbox.Volley

class UserSingleton constructor(context: Context){

    companion object {
        @Volatile
        private var INSTANCE: UserSingleton? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserSingleton(context).also {
                    INSTANCE = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        //
        Volley.newRequestQueue(context.applicationContext)
    }
    fun <T> addToRequestQueue(req: Request<T>) {
        //

        requestQueue.add(req)
    }
}