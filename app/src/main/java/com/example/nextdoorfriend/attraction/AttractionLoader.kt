package com.example.nextdoorfriend.attraction

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import java.net.HttpURLConnection
import java.net.URL
import java.util.Locale
import java.util.Locale.filter
import javax.xml.parsers.DocumentBuilderFactory

class AttractionLoader(val context: Context) {

    var totalCount: Int = 0
    val list = mutableListOf<Attraction>()

    companion object {
        val BASE = "https://tour.daegu.go.kr/openapi-data/service/rest/getTourKorAttract"
        val url = "$BASE/svTourKorAttract.do"

        val service_key = "JFoswUu9XrFFq8xkhILAZps89iqUp9oi3UCo%252Bqzl8CwnUna2n%252FP%252Fi6g0YhsRW%252BSr8KH2q9FS8yFespSMaeyp6Q%253D%253D"
        val sg_apim = "2ug8Dm9qNBfD32JLZGPN64f3EoTlkpD8kSOHWfXpyrY"

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
                "${url}?pageNo=${index}&numOfRows=${1}&serviceKey=${service_key}&SG_APIM=${sg_apim}"
            Log.d(TAG, urlString)
            val url = URL(urlString)
            val conn = withContext(Dispatchers.IO) {
                url.openConnection()
            } as HttpURLConnection
            conn.requestMethod = "GET"
            conn.setRequestProperty("Content-type", "text/xml")
            Log.d(TAG, "Response code: " + conn.responseCode)
            Log.d(TAG, "Response message: " + conn.responseMessage)

            conn
        }
    }
    private suspend fun getUrlConnection(pageNo: Int, numOfRows: Int): Deferred<HttpURLConnection> {
        return CoroutineScope(Dispatchers.IO).async {
            val urlString =
                "${url}?pageNo=${pageNo}&numOfRows=${numOfRows}&serviceKey=${service_key}&SG_APIM=${sg_apim}"
            Log.d(TAG, urlString)
            val url = URL(urlString)
            val conn = withContext(Dispatchers.IO) {
                url.openConnection()
            } as HttpURLConnection
            conn.requestMethod = "GET"
            conn.setRequestProperty("Content-type", "text/xml")
            Log.d(TAG, "Response code: " + conn.responseCode)
            Log.d(TAG, "Response message: " + conn.responseMessage)

            conn
        }
    }

    private fun init(): Deferred<Unit> {
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

            val itemList: NodeList = order.getElementsByTagName("item")
            val addressList: NodeList = order.getElementsByTagName("address")
            val attractNameList: NodeList = order.getElementsByTagName("attractname")
            val attractContentsList: NodeList = order.getElementsByTagName("attractcontents")
            val telList: NodeList = order.getElementsByTagName("tel")
            val homepageList: NodeList = order.getElementsByTagName("homepage")
            val emailList: NodeList = order.getElementsByTagName("email")
            val attr1List: NodeList = order.getElementsByTagName("attr01")
            val attr2List: NodeList = order.getElementsByTagName("attr02")
            val attr3List: NodeList = order.getElementsByTagName("attr03")
            val attr4List: NodeList = order.getElementsByTagName("attr04")
            val attr5List: NodeList = order.getElementsByTagName("attr05")

            var attractList: Array<Attraction> = arrayOf()

            for (i in 0 until itemList.length) {
                val address: String = addressList.item(i).firstChild.nodeValue.replaceSign()
                val attractName: String = attractNameList.item(i).firstChild.nodeValue.replaceSign()
                val attractContents: String = attractContentsList.item(i).firstChild.nodeValue.replaceSign()
                val tel: String = telList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val homepage: String = homepageList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val email: String = emailList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr01: String = attr1List.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr02: String = attr2List.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr03: String = attr3List.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr04: String = attr4List.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr05: String = attr5List.item(i).firstChild?.nodeValue?:"".replaceSign()

                attractList += Attraction(
                    null, address, attractName, attractContents,
                    tel, homepage, email,
                    attr01, attr02, attr03, attr04, attr05
                )
            }

            list += attractList

            for (attraction in list) {
                geoCoding(attraction)
            }

            Log.d(TAG, "get All end")
        }
    }

    fun getAllWithFilter(filter: (Attraction) -> Boolean,
                         eachJobBuilder: (Attraction) -> Job,
                         afterGeocodingJobBuilder: (Attraction) -> Job
    ): Deferred<Unit> {
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

            val itemList: NodeList = order.getElementsByTagName("item")
            val addressList: NodeList = order.getElementsByTagName("address")
            val attractNameList: NodeList = order.getElementsByTagName("attractname")
            val attractContentsList: NodeList = order.getElementsByTagName("attractcontents")
            val telList: NodeList = order.getElementsByTagName("tel")
            val homepageList: NodeList = order.getElementsByTagName("homepage")
            val emailList: NodeList = order.getElementsByTagName("email")
            val attr1List: NodeList = order.getElementsByTagName("attr01")
            val attr2List: NodeList = order.getElementsByTagName("attr02")
            val attr3List: NodeList = order.getElementsByTagName("attr03")
            val attr4List: NodeList = order.getElementsByTagName("attr04")
            val attr5List: NodeList = order.getElementsByTagName("attr05")

            val attractList: MutableList<Attraction> = mutableListOf()

            for (i in 0 until itemList.length) {
                val address: String = addressList.item(i).firstChild.nodeValue.replaceSign()
                val attractName: String = attractNameList.item(i).firstChild.nodeValue.replaceSign()
                val attractContents: String = attractContentsList.item(i).firstChild.nodeValue.replaceSign()
                val tel: String = telList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val homepage: String = homepageList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val email: String = emailList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr01: String = attr1List.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr02: String = attr2List.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr03: String = attr3List.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr04: String = attr4List.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr05: String = attr5List.item(i).firstChild?.nodeValue?:"".replaceSign()

                val item = Attraction(
                    null, address, attractName, attractContents,
                    tel, homepage, email,
                    attr01, attr02, attr03, attr04, attr05)

                if (filter(item)) {
                    attractList += item
                    eachJobBuilder(item).start()
                }
            }

            for (attraction in attractList) {
                geoCoding(attraction)
                afterGeocodingJobBuilder(attraction).start()
            }

            Log.d(TAG, "getAllWithFilter end")
        }
    }

    fun getSome(count: Int,
                eachJobBuilder: (Attraction) -> Job,
                afterGeocodingJobBuilder: (Attraction) -> Job
    ): Deferred<Unit> {
        return CoroutineScope(Dispatchers.IO).async {
            val conn = getUrlConnection(1, count).await()

            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val doc: Document =
                withContext(Dispatchers.IO) {
                    builder.parse(conn.inputStream)
                }
            val order: Element = doc.documentElement

            val itemList: NodeList = order.getElementsByTagName("item")
            val addressList: NodeList = order.getElementsByTagName("address")
            val attractNameList: NodeList = order.getElementsByTagName("attractname")
            val attractContentsList: NodeList = order.getElementsByTagName("attractcontents")
            val telList: NodeList = order.getElementsByTagName("tel")
            val homepageList: NodeList = order.getElementsByTagName("homepage")
            val emailList: NodeList = order.getElementsByTagName("email")
            val attr1List: NodeList = order.getElementsByTagName("attr01")
            val attr2List: NodeList = order.getElementsByTagName("attr02")
            val attr3List: NodeList = order.getElementsByTagName("attr03")
            val attr4List: NodeList = order.getElementsByTagName("attr04")
            val attr5List: NodeList = order.getElementsByTagName("attr05")

            val attractList: MutableList<Attraction> = mutableListOf()

            for (i in 0 until itemList.length) {
                val address: String = addressList.item(i).firstChild.nodeValue.replaceSign()
                val attractName: String = attractNameList.item(i).firstChild.nodeValue.replaceSign()
                val attractContents: String = attractContentsList.item(i).firstChild.nodeValue.replaceSign()
                val tel: String = telList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val homepage: String = homepageList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val email: String = emailList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr01: String = attr1List.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr02: String = attr2List.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr03: String = attr3List.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr04: String = attr4List.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr05: String = attr5List.item(i).firstChild?.nodeValue?:"".replaceSign()

                val item = Attraction(
                    null, address, attractName, attractContents,
                    tel, homepage, email,
                    attr01, attr02, attr03, attr04, attr05)

                attractList += item
                eachJobBuilder(item).start()
            }

            for (attraction in attractList) {
                geoCoding(attraction)
                afterGeocodingJobBuilder(attraction).start()
            }

            Log.d(TAG, "getSome end")
        }
    }

    fun getSomeWithFilter(count: Int,
                          filter: (Attraction) -> Boolean,
                          eachJobBuilder: (Attraction) -> Job,
                          afterGeocodingJobBuilder: (Attraction) -> Job
    ): Deferred<Unit> {
        return CoroutineScope(Dispatchers.IO).async {
            val conn = getUrlConnection(1, count).await()

            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val doc: Document =
                withContext(Dispatchers.IO) {
                    builder.parse(conn.inputStream)
                }
            val order: Element = doc.documentElement

            val itemList: NodeList = order.getElementsByTagName("item")
            val addressList: NodeList = order.getElementsByTagName("address")
            val attractNameList: NodeList = order.getElementsByTagName("attractname")
            val attractContentsList: NodeList = order.getElementsByTagName("attractcontents")
            val telList: NodeList = order.getElementsByTagName("tel")
            val homepageList: NodeList = order.getElementsByTagName("homepage")
            val emailList: NodeList = order.getElementsByTagName("email")
            val attr1List: NodeList = order.getElementsByTagName("attr01")
            val attr2List: NodeList = order.getElementsByTagName("attr02")
            val attr3List: NodeList = order.getElementsByTagName("attr03")
            val attr4List: NodeList = order.getElementsByTagName("attr04")
            val attr5List: NodeList = order.getElementsByTagName("attr05")

            val attractList: MutableList<Attraction> = mutableListOf()

            for (i in 0 until itemList.length) {
                val address: String = addressList.item(i).firstChild.nodeValue.replaceSign()
                val attractName: String = attractNameList.item(i).firstChild.nodeValue.replaceSign()
                val attractContents: String = attractContentsList.item(i).firstChild.nodeValue.replaceSign()
                val tel: String = telList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val homepage: String = homepageList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val email: String = emailList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr01: String = attr1List.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr02: String = attr2List.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr03: String = attr3List.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr04: String = attr4List.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr05: String = attr5List.item(i).firstChild?.nodeValue?:"".replaceSign()

                val item = Attraction(
                    null, address, attractName, attractContents,
                    tel, homepage, email,
                    attr01, attr02, attr03, attr04, attr05)

                if (!filter(item)) continue

                attractList += item
                eachJobBuilder(item).start()
            }

            for (attraction in attractList) {
                geoCoding(attraction)
                afterGeocodingJobBuilder(attraction).start()
            }

            Log.d(TAG, "getSomeWithFilter end")
        }
    }

    fun load(index: Int): Deferred<Attraction> {
        return CoroutineScope(Dispatchers.IO).async {
            init().await()

            val conn = getUrlConnection(index).await()

            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val doc: Document =
                withContext(Dispatchers.IO) {
                    builder.parse(conn.inputStream)
                }
            val order: Element = doc.documentElement

            val addressList: NodeList = order.getElementsByTagName("address")
            val attractNameList: NodeList = order.getElementsByTagName("attractname")
            val attractContentsList: NodeList = order.getElementsByTagName("attractcontents")
            val telList: NodeList = order.getElementsByTagName("tel")
            val homepageList: NodeList = order.getElementsByTagName("homepage")
            val emailList: NodeList = order.getElementsByTagName("email")
            val attr1List: NodeList = order.getElementsByTagName("attr01")
            val attr2List: NodeList = order.getElementsByTagName("attr02")
            val attr3List: NodeList = order.getElementsByTagName("attr03")
            val attr4List: NodeList = order.getElementsByTagName("attr04")
            val attr5List: NodeList = order.getElementsByTagName("attr05")

            val address: String = addressList.item(0).firstChild?.nodeValue?:""
            val attractName: String = attractNameList.item(0).firstChild?.nodeValue?:""
            val attractContents: String = attractContentsList.item(0).firstChild?.nodeValue?:""
            val tel: String = telList.item(0).firstChild?.nodeValue ?: ""
            val homepage: String = homepageList.item(0).firstChild?.nodeValue ?: ""
            val email: String = emailList.item(0).firstChild?.nodeValue ?: ""
            val attr01: String = attr1List.item(0).firstChild?.nodeValue ?: ""
            val attr02: String = attr2List.item(0).firstChild?.nodeValue ?: ""
            val attr03: String = attr3List.item(0).firstChild?.nodeValue ?: ""
            val attr04: String = attr4List.item(0).firstChild?.nodeValue ?: ""
            val attr05: String = attr5List.item(0).firstChild?.nodeValue ?: ""

            val attract = Attraction(
                null, address, attractName, attractContents,
                tel, homepage, email,
                attr01, attr02, attr03, attr04, attr05
            )
            geoCoding(attract)
            Log.d(TAG, "$attract")
            attract
        }
    }
    fun load(index: Int, count: Int): Deferred<Array<Attraction>> {
        return CoroutineScope(Dispatchers.IO).async {
            init().await()

            val conn = getUrlConnection(index, count).await()

            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val doc: Document =
                withContext(Dispatchers.IO) {
                    builder.parse(conn.inputStream)
                }
            val order: Element = doc.documentElement

            val itemList: NodeList = order.getElementsByTagName("item")
            val addressList: NodeList = order.getElementsByTagName("address")
            val attractNameList: NodeList = order.getElementsByTagName("attractname")
            val attractContentsList: NodeList = order.getElementsByTagName("attractcontents")
            val telList: NodeList = order.getElementsByTagName("tel")
            val homepageList: NodeList = order.getElementsByTagName("homepage")
            val emailList: NodeList = order.getElementsByTagName("email")
            val attr1List: NodeList = order.getElementsByTagName("attr01")
            val attr2List: NodeList = order.getElementsByTagName("attr02")
            val attr3List: NodeList = order.getElementsByTagName("attr03")
            val attr4List: NodeList = order.getElementsByTagName("attr04")
            val attr5List: NodeList = order.getElementsByTagName("attr05")

            var attractList: Array<Attraction> = arrayOf()

            for (i in 0 until itemList.length) {
                val address: String = addressList.item(i).firstChild.nodeValue.replaceSign()
                val attractName: String = attractNameList.item(i).firstChild.nodeValue.replaceSign()
                val attractContents: String = attractContentsList.item(i).firstChild.nodeValue.replaceSign()
                val tel: String = telList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val homepage: String = homepageList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val email: String = emailList.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr01: String = attr1List.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr02: String = attr2List.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr03: String = attr3List.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr04: String = attr4List.item(i).firstChild?.nodeValue?:"".replaceSign()
                val attr05: String = attr5List.item(i).firstChild?.nodeValue?:"".replaceSign()

                attractList += Attraction(
                    null, address, attractName, attractContents,
                    tel, homepage, email,
                    attr01, attr02, attr03, attr04, attr05
                )
            }

            for (attract in attractList) {
                geoCoding(attract)
            }

            attractList
        }
    }

    fun geoCoding(attract: Attraction) {
        try {
            if (Build.VERSION.SDK_INT >= 33) {
                Geocoder(context, Locale.KOREA).getFromLocationName(attract.address, 5, object : Geocoder.GeocodeListener {
                    override fun onGeocode(p0: MutableList<Address>) {
                        val lat = p0[0].latitude
                        val lon = p0[0].longitude

                        attract.location = LatLng(lat, lon)
                    }

                    override fun onError(errorMessage: String?) {
                        super.onError(errorMessage)
                        Log.d(TAG, errorMessage?:"??")
                    }
                })
            }
            else {
                val location = Geocoder(context, Locale.KOREA).getFromLocationName(attract.address, 5)?.let{
                    Log.d(TAG, "geocoder ${attract.address}")
                    Location("").apply {
                        latitude =  it[0].latitude
                        longitude = it[0].longitude
                    }
                }?: Location("").apply {
                    latitude = 0.0
                    longitude = 0.0
                }

                attract.location = LatLng(location.latitude, location.longitude)
            }

        }catch (e:Exception) {
            e.printStackTrace()
//            geoCoding(address) //재시도
        }
    }

}