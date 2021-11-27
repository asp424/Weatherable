package com.example.weatherable.data.internet.jsoup

import android.util.Log
import com.example.weatherable.data.view_states.InternetResponse
import com.example.weatherable.utilites.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.jsoup.Jsoup
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class JsoupSource {
    suspend fun getCityValues(): InternetResponse =
        suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.Default).launch {
                continuation.resume(
                    InternetResponse.OnSuccess(
                        JSONObject()
                            .put("nov_value", getCityValue(NOV_URL))
                            .put("ana_value", getCityValue(ANA_URL))
                            .put("gel_value", getCityValue(GEL_URL))
                            .put("yan_temp", getOnSitesTemps(YAN_URL, CLASS_TEMP, 1))
                            .put("hydro_temp", getOnSitesTemps(GID_URL, TAG, 10, 1))
                            .put("gis_temp", getOnSitesTemps(KRM_URL, CLASS_CITY))
                            .put("krm_wind", getOnSitesTemps(KRM_URL, CLASS_WIND))
                            .put("chel_temp", getOnSitesTemps(CHL_URL, CLASS_CITY))
                    )
                )
            }
        }

    private suspend fun getOnSitesTemps(
        url: String,
        classOrTag: String,
        index: Int = 0,
        flag: Int = 0
    ): String? = suspendCoroutine { continuation ->
        runCatching {
            try {
                continuation.resume(
                    if (flag == 0) Jsoup.connect(url).get()
                        .getElementsByClass(classOrTag)[index]?.text()
                    else Jsoup.connect(url).get()
                        .getElementsByTag(classOrTag)[index]?.text()
                )
            }
            catch (e: Exception){
                continuation.resume("Err")
            }
        }
    }

    private suspend fun getCityValue(city: String): JSONObject =
        suspendCoroutine { continuation ->
            runCatching {
                try {
                    Jsoup.connect(city).get().apply {
                        continuation.resume(
                            JSONObject()
                                .put("temp", getElementsByClass(CLASS_CITY)[0]?.text())
                                .put("water", getElementsByClass(CLASS_INFO)[8]?.text())
                        )
                    }
                } catch (e: Exception) {
                    continuation.resume(
                        JSONObject()
                            .put("temp", "Err")
                            .put("water", "Err")
                    )
                }
            }
        }

    suspend fun getGisData(): InternetResponse =
        suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.Default).launch {
                continuation.resume(
                    InternetResponse.OnSuccess(
                        JSONObject()
                            //Hydromet

                            //Gismeteo
                            .put("gis_temp_tod",
                                com.example.weatherable.utilites.getOnSitesTemps(
                                    GIS_URL_TOD,
                                    GIS_TEMP_TOD,
                                    0,
                                    4
                                )
                            )
                            .put("gis_icon_tod",
                                com.example.weatherable.utilites.getOnSitesTemps(
                                    GIS_URL_TOD,
                                    GIS_ICON_LIST,
                                    0,
                                    5
                                )
                            )
                            .put("gis_temp_tom",
                                com.example.weatherable.utilites.getOnSitesTemps(
                                    GIS_URL_TOM,
                                    GIS_TEMP_TOD,
                                    0,
                                    4
                                )
                            )
                            .put("gis_icon_tom",
                                com.example.weatherable.utilites.getOnSitesTemps(
                                    GIS_URL_TOM,
                                    GIS_ICON_LIST,
                                    0,
                                    5
                                )
                            )
                            .put("gis_sun_up",
                                com.example.weatherable.utilites.getOnSitesTemps(
                                    KRM_URL,
                                    GIS_SUN_UP,
                                    0
                                )
                            )
                            .put("gis_sun_down",
                                com.example.weatherable.utilites.getOnSitesTemps(
                                    KRM_URL,
                                    GIS_SUN_UP,
                                    1
                                )
                            )

                        //Yandex

                    )
                )
            }
        }

    suspend fun getYanData(): InternetResponse =
        suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.Default).launch {
                Log.d("My", com.example.weatherable.utilites.getOnSitesTemps(
                    YAN_URL_DETAILS,
                    "temp__value temp__value_with-unit",
                    0,
                    4
                ).toString())
                continuation.resume(
                    InternetResponse.OnSuccess(
                        JSONObject()
                            .put("yan_temp_tod",
                                com.example.weatherable.utilites.getOnSitesTemps(
                                    YAN_URL_DETAILS,
                                    "weather-table__temp",
                                    0,
                                    0
                                )
                            )
                            .put("yan_temp_tod1",
                                com.example.weatherable.utilites.getOnSitesTemps(
                                    YAN_URL_DETAILS,
                                    "weather-table__temp",
                                    1,
                                    0
                                )
                            )
                            .put("yan_temp_tod2",
                                com.example.weatherable.utilites.getOnSitesTemps(
                                    YAN_URL_DETAILS,
                                    "weather-table__temp",
                                    2,
                                    0
                                )
                            )
                            .put("yan_temp_tod3",
                                com.example.weatherable.utilites.getOnSitesTemps(
                                    YAN_URL_DETAILS,
                                    "weather-table__temp",
                                    3,
                                    0
                                )
                            )
                            .put("yan_temp_rain",
                                com.example.weatherable.utilites.getOnSitesTemps(
                                    YAN_URL_DETAILS,
                                    "weather-table__body-cell weather-table__body-cell_type_condition",
                                    0
                                )
                            )
                            .put("yan_temp_rain1",
                                com.example.weatherable.utilites.getOnSitesTemps(
                                    YAN_URL_DETAILS,
                                    "weather-table__body-cell weather-table__body-cell_type_condition",
                                    1
                                )
                            )
                            .put("yan_temp_rain2",
                                com.example.weatherable.utilites.getOnSitesTemps(
                                    YAN_URL_DETAILS,
                                    "weather-table__body-cell weather-table__body-cell_type_condition",
                                    2
                                )
                            )
                            .put("yan_temp_rain3",
                                com.example.weatherable.utilites.getOnSitesTemps(
                                    YAN_URL_DETAILS,
                                    "weather-table__body-cell weather-table__body-cell_type_condition",
                                    3
                                )
                            )

                            .put("yan_temp_tom",
                                com.example.weatherable.utilites.getOnSitesTemps(
                                    YAN_URL_DETAILS,
                                    "weather-table__temp",
                                    4,
                                    0
                                )
                            )
                            .put("yan_temp_tom1",
                                com.example.weatherable.utilites.getOnSitesTemps(
                                    YAN_URL_DETAILS,
                                    "weather-table__temp",
                                    5,
                                    0
                                )
                            )
                            .put("yan_temp_tom2",
                                com.example.weatherable.utilites.getOnSitesTemps(
                                    YAN_URL_DETAILS,
                                    "weather-table__temp",
                                    6,
                                    0
                                )
                            )
                            .put("yan_temp_tom3",
                                com.example.weatherable.utilites.getOnSitesTemps(
                                    YAN_URL_DETAILS,
                                    "weather-table__temp",
                                    7,
                                    0
                                )
                            )
                            .put("yan_temp_rain_t",
                                com.example.weatherable.utilites.getOnSitesTemps(
                                    YAN_URL_DETAILS,
                                    "weather-table__body-cell weather-table__body-cell_type_condition",
                                    4
                                )
                            )
                            .put("yan_temp_rain_t1",
                                com.example.weatherable.utilites.getOnSitesTemps(
                                    YAN_URL_DETAILS,
                                    "weather-table__body-cell weather-table__body-cell_type_condition",
                                    5
                                )
                            )
                            .put("yan_temp_rain_t2",
                                com.example.weatherable.utilites.getOnSitesTemps(
                                    YAN_URL_DETAILS,
                                    "weather-table__body-cell weather-table__body-cell_type_condition",
                                    6
                                )
                            )
                            .put("yan_temp_rain_t3",
                                com.example.weatherable.utilites.getOnSitesTemps(
                                    YAN_URL_DETAILS,
                                    "weather-table__body-cell weather-table__body-cell_type_condition",
                                    7
                                )
                            )
                    )
                )
            }
        }
}

