package com.example.nextdoorfriend.attractionCourse

import android.util.Log
import com.example.nextdoorfriend.attraction.AttractionLoader
import com.example.nextdoorfriend.attraction.AttractionLoader.Companion.sg_apim
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.start
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import java.net.HttpURLConnection
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

class AttractionCourseLoader {

    var totalCount: Int = 0
    val list = mutableListOf<AttractionCourse>()

    companion object {
        val BASE = "http://apis.data.go.kr/3450000/daegu/bukgu/tourCourse"
        val url = "$BASE/viewTourCourse"

        val service_key = "JFoswUu9XrFFq8xkhILAZps89iqUp9oi3UCo%2Bqzl8CwnUna2n%2FP%2Fi6g0YhsRW%2BSr8KH2q9FS8yFespSMaeyp6Q%3D%3D"

        val TAG = "Dirtfy"
    }

    private fun String.replaceSign(): String {
        var string = this.replace("<br>", "\n")
        string = string.replace("&lt;", "<")
        string = string.replace("&gt;", ">")
        string = string.replace("&nbsp;", " ")
        return string
    }

    private suspend fun getUrlConnection(index: Int): Deferred<HttpURLConnection> {
        return CoroutineScope(Dispatchers.IO).async {
            val urlString =
                "${url}?serviceKey=${service_key}&pageNo=${index}&numOfRows=${1}&resultType=xml"
            Log.d(AttractionLoader.TAG, urlString)
            val url = URL(urlString)
            val conn = withContext(Dispatchers.IO) {
                url.openConnection()
            } as HttpURLConnection
            conn.requestMethod = "GET"
            conn.setRequestProperty("Content-type", "text/xml")

            conn
        }
    }

    private suspend fun getUrlConnection(pageNo: Int, numOfRows: Int): Deferred<HttpURLConnection> {
        return CoroutineScope(Dispatchers.IO).async {
            val urlString =
                "${url}?serviceKey=${service_key}&pageNo=${pageNo}&numOfRows=${numOfRows}&resultType=xml"
            Log.d(AttractionLoader.TAG, urlString)
            val url = URL(urlString)
            val conn = withContext(Dispatchers.IO) {
                url.openConnection()
            } as HttpURLConnection
            conn.requestMethod = "GET"
            conn.setRequestProperty("Content-type", "text/xml")
            Log.d(AttractionLoader.TAG, "Response code: " + conn.responseCode)
            Log.d(AttractionLoader.TAG, "Response message: " + conn.responseMessage)

            conn
        }
    }

    private fun _init(): Deferred<Unit> {
        return CoroutineScope(Dispatchers.IO).async {
            val conn = getUrlConnection(1).await()

            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val doc: Document =
                withContext(Dispatchers.IO) {
                    builder.parse(conn.inputStream)
                }
            val order: Element = doc.documentElement

            val totalCountList: NodeList = order.getElementsByTagName("totalCount")

            totalCount = totalCountList.item(0).firstChild.nodeValue.toInt()
        }
    }

    fun init(): Deferred<Unit> {
        return CoroutineScope(Dispatchers.IO).async {
            _init().await()

            val conn = getUrlConnection(1, totalCount).await()

            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val doc: Document =
                withContext(Dispatchers.IO) {
                    builder.parse(conn.inputStream)
                }
            val order: Element = doc.documentElement

            if (order.getElementsByTagName("resultCode").item(0).firstChild.nodeValue != "00")
                return@async

            val itemList: NodeList = order.getElementsByTagName("tourCourseList")

            totalCount = itemList.length

            Log.d(TAG, "init end")
        }
    }

    fun getAll(): Deferred<Unit> {
        return CoroutineScope(Dispatchers.IO).async {
            init().await()

            val conn = getUrlConnection(1, totalCount).await()

            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val doc: Document =
                withContext(Dispatchers.IO) {
                    builder.parse(conn.inputStream)
                }
            val order: Element = doc.documentElement

            if (order.getElementsByTagName("resultCode").item(0).firstChild.nodeValue != "00")
                return@async

            val itemList: NodeList = order.getElementsByTagName("tourCourseList")
            val courseNmList: NodeList = order.getElementsByTagName("courseNm")
            val distanceList: NodeList = order.getElementsByTagName("distance")
            val timeList: NodeList = order.getElementsByTagName("time")
            val tourCourseList: NodeList = order.getElementsByTagName("tourCourse")

            var itemDataList: Array<AttractionCourse> = arrayOf()

            for (i in 0 until itemList.length) {
                val courseNm: String = courseNmList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val distance: String = distanceList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val time: String = timeList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val tourCourse: String = tourCourseList.item(i).firstChild?.nodeValue?:"".replaceSign()

                itemDataList += AttractionCourse(
                    courseNm,
                    distance,
                    time,
                    tourCourse.split(" > ").toTypedArray()
                )
            }

            list += itemDataList

            Log.d(TAG, "get All end")
        }
    }

    fun getAll(eachJobBuilder: (AttractionCourse) -> Job): Deferred<Unit> {
        return CoroutineScope(Dispatchers.IO).async {
            init().await()

            val conn = getUrlConnection(1, totalCount).await()

            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val doc: Document =
                withContext(Dispatchers.IO) {
                    builder.parse(conn.inputStream)
                }
            val order: Element = doc.documentElement

            if (order.getElementsByTagName("resultCode").item(0).firstChild.nodeValue != "00")
                return@async

            val itemList: NodeList = order.getElementsByTagName("tourCourseList")
            val courseNmList: NodeList = order.getElementsByTagName("courseNm")
            val distanceList: NodeList = order.getElementsByTagName("distance")
            val timeList: NodeList = order.getElementsByTagName("time")
            val tourCourseList: NodeList = order.getElementsByTagName("tourCourse")

            var itemDataList: Array<AttractionCourse> = arrayOf()

            for (i in 0 until itemList.length) {
                val courseNm: String = courseNmList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val distance: String = distanceList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val time: String = timeList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val tourCourse: String = tourCourseList.item(i).firstChild?.nodeValue?:"".replaceSign()

                val item = AttractionCourse(
                    courseNm,
                    distance,
                    time,
                    tourCourse.split(" > ").toTypedArray()
                )

                itemDataList += item

                eachJobBuilder(item).start()
            }

            list += itemDataList

            Log.d(TAG, "get All end")
        }
    }

    fun getAllWithFilter(
        filter: (AttractionCourse) -> Boolean,
        eachJobBuilder: (AttractionCourse) -> Job): Deferred<Unit> {
        return CoroutineScope(Dispatchers.IO).async {
            init().await()

            val conn = getUrlConnection(1, totalCount).await()

            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val doc: Document =
                withContext(Dispatchers.IO) {
                    builder.parse(conn.inputStream)
                }
            val order: Element = doc.documentElement

            if (order.getElementsByTagName("resultCode").item(0).firstChild.nodeValue != "00")
                return@async

            val itemList: NodeList = order.getElementsByTagName("tourCourseList")
            val courseNmList: NodeList = order.getElementsByTagName("courseNm")
            val distanceList: NodeList = order.getElementsByTagName("distance")
            val timeList: NodeList = order.getElementsByTagName("time")
            val tourCourseList: NodeList = order.getElementsByTagName("tourCourse")

            var itemDataList: Array<AttractionCourse> = arrayOf()

            for (i in 0 until itemList.length) {
                val courseNm: String = courseNmList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val distance: String = distanceList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val time: String = timeList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val tourCourse: String = tourCourseList.item(i).firstChild?.nodeValue?:"".replaceSign()

                val item = AttractionCourse(
                    courseNm,
                    distance,
                    time,
                    tourCourse.split(" > ").toTypedArray()
                )


                if (!filter(item)) continue

                itemDataList += item

                eachJobBuilder(item).start()
            }

            Log.d(TAG, "getAllWithFilter end")
        }
    }

    fun getSome(count: Int, eachJobBuilder: (AttractionCourse) -> Job): Deferred<Unit> {
        return CoroutineScope(Dispatchers.IO).async {
            val conn = getUrlConnection(1, count).await()

            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val doc: Document =
                withContext(Dispatchers.IO) {
                    builder.parse(conn.inputStream)
                }
            val order: Element = doc.documentElement

            if (order.getElementsByTagName("resultCode").item(0).firstChild.nodeValue != "00"){
                Log.d(TAG, "resultCode is not 00!")
                return@async
            }

            val itemList: NodeList = order.getElementsByTagName("tourCourseList")
            val courseNmList: NodeList = order.getElementsByTagName("courseNm")
            val distanceList: NodeList = order.getElementsByTagName("distance")
            val timeList: NodeList = order.getElementsByTagName("time")
            val tourCourseList: NodeList = order.getElementsByTagName("tourCourse")

            for (i in 0 until itemList.length) {
                val courseNm: String = courseNmList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val distance: String = distanceList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val time: String = timeList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val tourCourse: String = tourCourseList.item(i).firstChild?.nodeValue?:"".replaceSign()

                val item = AttractionCourse(
                    courseNm,
                    distance,
                    time,
                    tourCourse.split(" > ").toTypedArray()
                )

                Log.d(TAG, "$item")

                eachJobBuilder(item).start()
            }

            Log.d(TAG, "getSome end")
        }
    }

    fun load(index: Int): Deferred<AttractionCourse> {
        return CoroutineScope(Dispatchers.IO).async {
            val conn = getUrlConnection(index).await()

            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val doc: Document =
                withContext(Dispatchers.IO) {
                    builder.parse(conn.inputStream)
                }
            val order: Element = doc.documentElement

            if (order.getElementsByTagName("resultCode").item(0).firstChild.nodeValue != "00")
                return@async AttractionCourse("", "", "", arrayOf())

            val courseNmList: NodeList = order.getElementsByTagName("courseNm")
            val distanceList: NodeList = order.getElementsByTagName("distance")
            val timeList: NodeList = order.getElementsByTagName("time")
            val tourCourseList: NodeList = order.getElementsByTagName("tourCourse")

            val courseNm: String = courseNmList.item(0)?.firstChild?.nodeValue?:"".replaceSign()
            val distance: String = distanceList.item(0)?.firstChild?.nodeValue?:"".replaceSign()
            val time: String = timeList.item(0)?.firstChild?.nodeValue?:"".replaceSign()
            val tourCourse: List<String> = (tourCourseList.item(0)?.firstChild?.nodeValue?:"".replaceSign()).split(" > ")

            val attractionCourse = AttractionCourse(
                courseNm, distance, time, tourCourse.toTypedArray()
            )

            Log.d(AttractionLoader.TAG, "$attractionCourse")

            attractionCourse
        }
    }
}