package com.example.vk_cup_2021.modules.second_task

import android.view.View
import android.widget.ImageView
import android.widget.SimpleAdapter
import android.widget.TextView
import com.example.vk_cup_2021.R
import com.example.vk_cup_2021.modules.FontWorker
import com.example.vk_cup_2021.modules.ImageWorker

class NewsViewBinder: SimpleAdapter.ViewBinder{

    override fun setViewValue(view: View?, data: Any?, text: String?): Boolean {
        when(view?.id){
            R.id.avatar -> {
                if (data != null){
                    ImageWorker.setImageToView(view.context, data as String, view as ImageView)
                }
                return true
            }
            R.id.photo -> {
                if (data != null){
                    ImageWorker.setImageToView(view.context, data as String, view as ImageView)
                }
                else {
                    (view as ImageView).visibility = View.GONE
                }
                return true
            }
            R.id.name -> {
                if (data != null && !(data as String).isBlank()) {
                    var c = view.context
                    FontWorker.setDemiBoldVKFont(view as TextView, c.assets)
                    (view).setText(data)
                }
                else {
                    view.visibility = View.GONE
                }
                return true
            }
            R.id.content_text -> {
                if (data != null && !(data as String).isBlank()) {
                    (view as TextView).setText(data)
                }
                else {
                    view.visibility = View.GONE
                }
                return true
            }
        }
        return false
    }

}