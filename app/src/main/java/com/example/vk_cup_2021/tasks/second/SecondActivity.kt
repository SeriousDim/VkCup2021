package com.example.vk_cup_2021.tasks.second

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vk_cup_2021.R
import com.example.vk_cup_2021.modules.FontWorker
import com.example.vk_cup_2021.modules.NewsWizard
import com.example.vk_cup_2021.retrofit.api.ApiWorker
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import com.lorentzos.flingswipe.SwipeFlingAdapterView.onFlingListener
import kotlinx.android.synthetic.main.activity_second.*


class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        FontWorker.setDemiBoldVKFont(toolbar_title, assets)
        val flingContainer =
            findViewById<View>(R.id.fling) as SwipeFlingAdapterView

        val wizard = NewsWizard()
        var news: ArrayList<Map<String, Any?>>? = null
        var simpleAdapter: SimpleAdapter? = null

        var worker = ApiWorker(this)
        worker.getRecommend() {
            news = wizard.buildData(worker.recommendation?.response!!)
            simpleAdapter = wizard.getSimpleAdapter(this, news!!)
            flingContainer.adapter = simpleAdapter
            loading.visibility = View.GONE
            simpleAdapter?.notifyDataSetChanged()
        }

        /*var al = ArrayList<String>()
        al.add("php")
        al.add("c")
        al.add("python")
        al.add("java")
        al.add("c++")
        al.add("javascript")
        al.add("ruby")
        al.add("assembly")*/

        back.setOnClickListener() {
            finish()
        }

        //var arrayAdapter = ArrayAdapter<String>(this, R.layout.test_news_card, R.id.testView, al)

        //set the listener and the adapter

        flingContainer.setFlingListener(object : onFlingListener {
            override fun removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                // al.removeAt(0)
                news?.removeAt(0)
                simpleAdapter?.notifyDataSetChanged()
            }

            override fun onLeftCardExit(dataObject: Any) {

            }

            override fun onRightCardExit(dataObject: Any) {

            }

            override fun onAdapterAboutToEmpty(itemsInAdapter: Int) {
                Log.d("SECOND", "adapter count = ${simpleAdapter?.count}")
            }

            override fun onScroll(p0: Float) {

            }
        })

        /*flingContainer.setOnItemClickListener { itemPosition, dataObject ->
            Toast.makeText(this@SecondActivity, "Clicked!", Toast.LENGTH_SHORT).show()
        }*/
    }

}