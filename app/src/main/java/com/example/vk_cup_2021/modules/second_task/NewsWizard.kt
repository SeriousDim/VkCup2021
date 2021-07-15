package com.example.vk_cup_2021.modules.second_task

import android.content.Context
import android.widget.SimpleAdapter
import com.example.example.*
import com.example.vk_cup_2021.R
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class NewsWizard{

    private val ATTR_LOGO = "ATTR_LOGO"
    private val ATTR_NAME = "ATTR_NAME"
    private val ATTR_TIMESTAMP = "ATTR_TIMESTAMP"
    private val ATTR_PHOTO = "ATTR_PHOTO"
    private val ATTR_TEXT = "ATTR_TEXT"
    private val ATTR_LIKES = "ATTR_LIKES"
    private val ATTR_COMMENTS = "ATTR_COMMENTS"
    private val ATTR_REPOSTS = "ATTR_REPOSTS"
    private val ATTR_SCROLLED_LEFT = "ATTR_SCROLLED_LEFT"
    private val ATTR_SCROLLED_RIGHT = "ATTR_SCROLLED_RIGHT"
    private val ATTR_POST_ID = "ATTR_POST_ID"

    fun getFirstPhoto(item: Items): Photo?{
        if (item.attachments.size == 0)
            return null
        val attach = item.attachments.get(0)
        if (attach.type.equals("photo"))
            return attach.photo
        return null
    }

    fun getDate(ms: Long): String{
        val date = Date(ms)
        val symbols = object: DateFormatSymbols(){
            override fun getMonths(): Array<String> {
                return arrayOf("января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря")
            }
        }
        val formatter = SimpleDateFormat("d MMMM 'в' H:mm", symbols)
        return formatter.format(date)
    }

    fun getViewBinder(): NewsViewBinder {
        return NewsViewBinder()
    }

    fun getOptimalPhotoLink(photo: Photo): String{
        return photo.photo807
    }

    fun buildData(response: Response): ArrayList<Map<String, Any?>>{
        val data = ArrayList<Map<String, Any?>>()
        val length = response.items.size

        for (i in 0..length-1){
            val map = HashMap<String, Any?>()
            val item = response.items.get(i)

            val photo = getFirstPhoto(item)
            map.put(ATTR_PHOTO, photo?.photo807)

            map.put(ATTR_POST_ID, item.postId)
            map.put(ATTR_TEXT, item.text)
            map.put(ATTR_LIKES, item.likes.count)
            map.put(ATTR_COMMENTS, item.comments.count)
            map.put(ATTR_REPOSTS, item.reposts.count)

            //map.put(ATTR_SCROLLED_LEFT, false)
            //map.put(ATTR_SCROLLED_RIGHT, false)

            map.put(ATTR_TIMESTAMP, getDate(item.date))

            val sourceId = item.sourceId
            if (sourceId < 0){
                val groups = response.groups
                val group = groups.find { it.id == -sourceId }
                map.put(ATTR_NAME, group?.name)
                map.put(ATTR_LOGO, group?.photo50)
            } else {
                val profiles = response.profiles
                val profile = profiles.find { it.id == sourceId }
                map.put(ATTR_NAME, "${profile?.firstName} ${profile?.lastName}")
                map.put(ATTR_LOGO, profile?.photo50)
            }

            data.add(map)
        }

        return data
    }

    fun getSimpleAdapter(c: Context, data: ArrayList<Map<String, Any?>>): SimpleAdapter{
        val from = arrayOf(ATTR_LOGO, ATTR_NAME, ATTR_TIMESTAMP, ATTR_PHOTO, ATTR_TEXT,
            ATTR_LIKES, ATTR_COMMENTS, ATTR_REPOSTS/*, ATTR_SCROLLED_LEFT, ATTR_SCROLLED_RIGHT*/, ATTR_POST_ID)

        val to = intArrayOf(R.id.avatar, R.id.name, R.id.timestamp, R.id.photo, R.id.content_text,
            R.id.likes, R.id.comments, R.id.reposts/*, R.id.lessNews, R.id.moreNews*/)

        val result = SimpleAdapter(c, data, R.layout.news_card, from, to)
        result.setViewBinder(getViewBinder())

        return result
    }

    /*fun getFlingListener(): SwipeFlingAdapterView.onFlingListener{

    }*/

}