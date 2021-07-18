package com.example.vk_cup_2021.tasks.second

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.vk_cup_2021.R
import com.example.vk_cup_2021.modules.FontWorker
import com.example.vk_cup_2021.modules.second_task.NewsWizard
import com.example.vk_cup_2021.modules.Notifier
import com.example.vk_cup_2021.retrofit.second_task.api.RecommendApiWorker
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import com.lorentzos.flingswipe.SwipeFlingAdapterView.onFlingListener
import kotlinx.android.synthetic.main.activity_second.*


class SecondActivity : AppCompatActivity() {
    private lateinit var flingContainer: SwipeFlingAdapterView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        FontWorker.setDemiBoldVKFont(toolbar_title, assets)
        flingContainer =
            findViewById<View>(R.id.fling) as SwipeFlingAdapterView

        val wizard = NewsWizard()
        var news: ArrayList<Map<String, Any?>>? = null
        var simpleAdapter: SimpleAdapter? = null



        var worker = RecommendApiWorker(this)
        worker.getRecommend() {
            news = wizard.buildData(worker.recommendation?.response!!)
            simpleAdapter = wizard.getSimpleAdapter(this, news!!)
            flingContainer.adapter = simpleAdapter
            loading.visibility = View.GONE
            simpleAdapter?.notifyDataSetChanged()
        }

        var liked = ArrayList<Map<String, Any?>>()
        var disliked = ArrayList<Map<String, Any?>>()

        back.setOnClickListener() {
            finish()
        }

        flingContainer.setFlingListener(object : onFlingListener {
            override fun removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                // al.removeAt(0)
                news?.removeAt(0)
                simpleAdapter?.notifyDataSetChanged()
            }

            override fun onLeftCardExit(dataObject: Any) {
                disliked.add(dataObject as Map<String, Any?>)
            }

            override fun onRightCardExit(dataObject: Any) {
                liked.add(dataObject as Map<String, Any?>)
            }

            override fun onAdapterAboutToEmpty(itemsInAdapter: Int) {
                if (itemsInAdapter == 0){
                    Notifier.showToast(this@SecondActivity, "Понравилось: ${liked.size}\nНе понравилось: ${disliked.size}")
                }
            }

            override fun onScroll(f: Float) {
                Log.d("SECOND_SCROLL", "Scroll: $f")
                

                if (f > 0.0){

                } else if (f < 0.0) {

                } else {

                }
            }
        })

        /*flingContainer.setOnItemClickListener { itemPosition, dataObject ->
            Toast.makeText(this@SecondActivity, "Clicked!", Toast.LENGTH_SHORT).show()
        }*/
    }

    fun dislike(v: View){
        flingContainer.getTopCardListener().selectLeft();
    }

    fun like(v: View){
        flingContainer.getTopCardListener().selectRight();
    }

}