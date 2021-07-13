package com.example.vk_cup_2021.tasks.second

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vk_cup_2021.R
import com.example.vk_cup_2021.modules.FontWorker
import com.example.vk_cup_2021.retrofit.api.ApiWorker
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import com.lorentzos.flingswipe.SwipeFlingAdapterView.onFlingListener
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.auth.VKScope
import kotlinx.android.synthetic.main.activity_second.*


class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        FontWorker.setFont(toolbar_title, assets, "VK_Sans_DemiBold.otf")

        val flingContainer =
            findViewById<View>(R.id.fling) as SwipeFlingAdapterView

        var worker = ApiWorker(this)
        val rec = worker.getRecommend()

        var al = ArrayList<String>()
        al.add("php")
        al.add("c")
        al.add("python")
        al.add("java")
        al.add("c++")
        al.add("javascript")
        al.add("ruby")
        al.add("assembly")

        back.setOnClickListener() {
            finish()
        }

        //choose your favorite adapter
        var arrayAdapter = ArrayAdapter<String>(this, R.layout.news_card, R.id.testView, al)

        //set the listener and the adapter
        flingContainer.adapter = arrayAdapter
        flingContainer.setFlingListener(object : onFlingListener {
            override fun removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                al.removeAt(0)
                arrayAdapter.notifyDataSetChanged()
            }

            override fun onLeftCardExit(dataObject: Any) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(this@SecondActivity, "Left!", Toast.LENGTH_SHORT).show()
            }

            override fun onRightCardExit(dataObject: Any) {
                Toast.makeText(this@SecondActivity, "Right!", Toast.LENGTH_SHORT).show()
            }

            override fun onAdapterAboutToEmpty(itemsInAdapter: Int) {
                // Ask for more data here
                /*al.add("XML " + java.lang.String.valueOf(i))
                arrayAdapter.notifyDataSetChanged()
                Log.d("LIST", "notified")
                i++*/
                Log.d("SECOND", "adapter count = ${al.count()}")
            }

            override fun onScroll(p0: Float) {

            }
        })

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener { itemPosition, dataObject ->
            Toast.makeText(this@SecondActivity, "Clicked!", Toast.LENGTH_SHORT).show()
        }
    }

}