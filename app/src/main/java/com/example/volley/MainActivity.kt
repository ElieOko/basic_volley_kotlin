package com.example.volley

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.volley.constante.Global
import com.example.volley.databinding.ActivityHomeBinding
import com.example.volley.databinding.ActivityMainBinding
import com.example.volley.models.User
import com.example.volley.utils.Myfunction
import com.example.volley.volley.MySingleton
import com.example.volley.volley.UserSingleton
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        connexion()
       // register()
//        val queue = MySingleton.getInstance(this.applicationContext).requestQueue
//
//        val url = ""
//        val stringRequest: StringRequest = StringRequest(
//            Request.Method.GET, url,
//            { response ->
//                binding.response.text = "Response is: ${response.substring(0, 500)}"
//            },
//           {  binding.response.text = "That didn't work!" })
//
//        //JsonObjectRequest
//        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
//             { response ->
//                 binding.response.text = "Response: %s".format(response.toString())
//            },
//            { error ->
//
//            }
//        )
//
//// Access the RequestQueue through your singleton class.
//        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

        //Info brute
       // MySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun connexion(){
        binding.btnCheck.setOnClickListener {
            var msg  = "Succes"
            if (!checkingConnexion(applicationContext)){
                msg = "No connect"
            }
            Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
        }

    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkingConnexion(context: Context):Boolean {
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as (ConnectivityManager)
        val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)
        var isAvailable: Boolean = false
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                isAvailable = true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                isAvailable = true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                isAvailable = true
            }
        }
        return isAvailable
    }
   private fun register(){
        val data = JSONObject()
        data.put("usename","ElieOko100")
        data.put("password","0000")
        data.put("first_name","Oko")
        data.put("last_name","Elie")

        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, Myfunction.url_fusio(Global.url,Global.user_url), data,
            { response ->
                Toast.makeText(this,"Success",Toast.LENGTH_LONG).show()
                binding.response.text = "Response: %s".format(response.toString())
            },
            { error ->
                Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()
                binding.response.text = "Response: %s".format(error.toString())
            }
        )
        UserSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)



    }



















    fun createUser(jsonObject: JSONObject){

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