package com.example.network

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit: Retrofit =  Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val Api: Api = retrofit.create(Api::class.java)

        Api.getUser("MaryRybalka").enqueue(object: Callback<Api.User> {
            @SuppressLint("SetTextI18n")
            override fun onFailure(call: Call<Api.User>, t: Throwable) {
                val text: TextView = findViewById<View>(R.id.textView) as TextView
                text.text = "Something Went Wrong"
            }

            override fun onResponse(call: Call<Api.User>, response: Response<Api.User>) {
                val user: Api.User? = response.body()

                Log.d("AvatarURL", "${user?.avatarURL}")
                Log.d("Name", "${user?.name}")
                Log.d("Link", "${user?.htmlURL}")

                val textView: TextView = findViewById<View>(R.id.textView) as TextView
                textView.text = user?.name
                val linkView: TextView = findViewById<View>(R.id.linkView) as TextView
                linkView.text = user?.htmlURL

                val imageView: ImageView = findViewById<View>(R.id.imageView) as ImageView

                Picasso.get()
                    .load(user?.avatarURL)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.me)
                    .into(imageView);
            }
        })
    }
}