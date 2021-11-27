package com.example.weatherable.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.weatherable.DetailYanActivity
import com.example.weatherable.R
import com.example.weatherable.data.view_states.InternetResponse
import com.example.weatherable.ui.cells.CardDetailYan
import com.example.weatherable.ui.cells.CellHeader
import com.example.weatherable.ui.viewmodel.DetailYanViewModel
import com.example.weatherable.utilites.isOnline


@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun DetailYan(viewModel: DetailYanViewModel) {
    val values by remember(viewModel) {
        viewModel.internetValues
    }.collectAsState()
    val listTod = remember { mutableListOf<String>() }
    val listTom = remember { mutableListOf<String>() }
    val listHour = listOf("Утром", "Днём", "Вечером", "Ночью")
    val min = "00"
    val context = LocalContext.current as DetailYanActivity
    when (values) {
        is InternetResponse.OnSuccess -> {
            Column(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    border = BorderStroke(2.dp, Color.Black),
                    shape = RoundedCornerShape(16.dp), modifier = Modifier.wrapContentSize()
                ) {
                    Column {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CellHeader(string = "Сегодня", color = Color.Black)
                            (values as InternetResponse.OnSuccess).dataValues.apply {
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(6.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    CardDetailYan(
                                        time = listHour[0],
                                        rain = getString("yan_temp_rain"),
                                        index = 0, temp = getString("yan_temp_tod")
                                    )
                                    CardDetailYan(
                                        time = listHour[1],
                                        rain = getString("yan_temp_rain1"),
                                        index = 1, temp = getString("yan_temp_tod1")
                                    )
                                    CardDetailYan(
                                        time = listHour[2],
                                        rain = getString("yan_temp_rain2"),
                                        index = 2, temp = getString("yan_temp_tod2")
                                    )
                                    CardDetailYan(
                                        time = listHour[3],
                                        rain = getString("yan_temp_rain3"),
                                        index = 3, temp = getString("yan_temp_tod3")
                                    )
                                }
                                CellHeader(string = "Завтра", color = Color.Black)
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(6.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    CardDetailYan(
                                        time = listHour[0],
                                        rain = getString("yan_temp_rain_t"),
                                        index = 0, temp = getString("yan_temp_tom")
                                    )
                                    CardDetailYan(
                                        time = listHour[1],
                                        rain = getString("yan_temp_rain_t1"),
                                        index = 1, temp = getString("yan_temp_tom1")
                                    )
                                    CardDetailYan(
                                        time = listHour[2],
                                        rain = getString("yan_temp_rain_t2"),
                                        index = 2, temp = getString("yan_temp_tom2")
                                    )
                                    CardDetailYan(
                                        time = listHour[3],
                                        rain = getString("yan_temp_rain_t3"),
                                        index = 3, temp = getString("yan_temp_tom3")
                                    )
                                }
                            }
                        }
                        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                            Image(
                                painter = rememberImagePainter(
                                    R.drawable.yan_logo
                                ),
                                contentDescription = null,
                                modifier = Modifier.size(38.dp)
                            )
                        }
                    }
                }
            }
        }
        is InternetResponse.Loading -> {
            Column(
                Modifier
                    .wrapContentSize()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Card(
                    border = BorderStroke(2.dp, Color.Black),
                    shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        Modifier.wrapContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        if (isOnline(context)) {
                            CellHeader(
                                string = "Загрузка...",
                                paddingTop = 8.dp, paddingStart = 8.dp,
                                paddingBottom = 8.dp, paddingEnd = 8.dp,
                                color = Color.Black
                            )
                        } else {
                            CellHeader(
                                string = "Отсутствует интернет",
                                color = Color.Red,
                                paddingTop = 8.dp, paddingStart = 8.dp,
                                paddingBottom = 8.dp, paddingEnd = 8.dp
                            )
                        }
                    }
                }
            }
        }
        else -> {
        }
    }
    LocalLifecycleOwner.current.lifecycle.addObserver(viewModel)
}



