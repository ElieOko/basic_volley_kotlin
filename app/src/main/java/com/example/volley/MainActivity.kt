package com.example.volley

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.volley.volley.MySingleton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.response)
// Get a RequestQueue
        val queue = MySingleton.getInstance(this.applicationContext).requestQueue
// Add a request (in this example, called stringRequest) to your RequestQueue.
        val url = "https://www.google.com"
        val stringRequest: StringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                // Display the first 500 characters of the response string.
                textView.text = "Response is: ${response.substring(0, 500)}"
            },
           { textView.text = "That didn't work!" })


//JsonObjectRequest

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
             { response ->
                textView.text = "Response: %s".format(response.toString())
            },
            { error ->
                // TODO: Handle error
            }
        )

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

        //Info brute
       // MySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }


    fun apiVolley(){
        val textView = findViewById<TextView>(R.id.response)
        val queue = Volley.newRequestQueue(this)
        val url = "https://www.google.com"
        val stringRequest: StringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                // Display the first 500 characters of the response string.
                textView.text = "Response is: ${response.substring(0, 500)}"
            },
           { textView.text = "That didn't work!" })

// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    fun customisation(){
        val textView = findViewById<TextView>(R.id.response)
        // Instantiate the cache
        val cache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
        val network = BasicNetwork(HurlStack())

// Instantiate the RequestQueue with the cache and network. Start the queue.
        val requestQueue = RequestQueue(cache, network).apply {
            start()
        }

        val url = "http://www.example.com"

// Formulate the request and handle the response.
        val stringRequest = StringRequest(Request.Method.GET, url,
            { rep ->
                // Do something with the response
                textView.text =   rep.toString()
            },
           { error ->
                // Handle error
                textView.text = "ERROR: %s".format(error.toString())
            })

// Add the request to the RequestQueue.
        requestQueue.add(stringRequest)

    }
}