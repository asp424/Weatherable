package com.example.weatherable.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.weatherable.DetailGisActivity
import com.example.weatherable.data.view_states.InternetResponse
import com.example.weatherable.ui.cells.CellHeader
import com.example.weatherable.ui.viewmodel.DetailViewModel
import com.example.weatherable.utilites.*


@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun DetailGis(viewModel: DetailViewModel) {
    val values by remember(viewModel) { viewModel.internetValues }.collectAsState()
    val listTod = remember { mutableListOf<String>() }
    val listTom = remember { mutableListOf<String>() }
    val listHour = listOf("2", "5", "8", "11", "14", "17", "20", "23")
    val min = "00"
    val context = LocalContext.current as DetailGisActivity
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
                    var itemTod = ""
                    var itemTom = ""
                    val listSkyTod = mutableListOf<String>()
                    val listSkyTom = mutableListOf<String>()
                    (values as InternetResponse.OnSuccess).dataValues.apply {
                        getString("gis_icon_tod").apply {
                            listSkyTod.apply {
                                add(sA.sB)
                                add(sA.sA.sB)
                                add(sA.sA.sA.sB)
                                add(sA.sA.sA.sA.sB)
                                add(sA.sA.sA.sA.sA.sB)
                                add(sA.sA.sA.sA.sA.sA.sB)
                                add(sA.sA.sA.sA.sA.sA.sA.sB)
                                add(sA.sA.sA.sA.sA.sA.sA.sA.sB)
                            }
                        }
                        getString("gis_icon_tom").apply {
                            listSkyTom.apply {
                                add(sA.sB)
                                add(sA.sA.sB)
                                add(sA.sA.sA.sB)
                                add(sA.sA.sA.sA.sB)
                                add(sA.sA.sA.sA.sA.sB)
                                add(sA.sA.sA.sA.sA.sA.sB)
                                add(sA.sA.sA.sA.sA.sA.sA.sB)
                                add(sA.sA.sA.sA.sA.sA.sA.sA.sB)
                            }
                        }
                        getString("gis_temp_tod").forEach {
                            if (it.toString() != " ")
                                itemTod += it.toString()
                            else {
                                listTod.add(itemTod)
                                itemTod = ""
                            }
                        }
                        Thread.sleep(400L)
                        listTod.add(itemTod)
                        getString("gis_temp_tom").forEach {
                            if (it.toString() != " ")
                                itemTom += it.toString()
                            else {
                                listTom.add(itemTom)
                                itemTom = ""
                            }
                        }
                        Thread.sleep(400L)
                        listTom.add(itemTom)
                        Column {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CellHeader(string = "Сегодня", color = Color.Black)
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(6.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    listTod.takeLast(8).take(4).forEachIndexed { i, item ->
                                        Card(
                                            modifier = Modifier.padding(3.dp),
                                            border = BorderStroke(1.dp, Color.Black)
                                        ) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center,
                                                modifier = Modifier.width(64.dp)

                                            ) {
                                                Row(
                                                    horizontalArrangement = Arrangement.Center,
                                                    verticalAlignment = Alignment.Top
                                                ) {
                                                    Text(
                                                        text = listHour[i],
                                                        textAlign = TextAlign.Center,
                                                        fontSize = 12.sp, modifier = Modifier.padding(top = 2.dp)
                                                    )
                                                    Text(text = min, fontSize = 8.sp, modifier = Modifier.padding(top = 2.dp))
                                                }
                                                Image(
                                                    painter = rememberImagePainter(
                                                        if (i in 0..2) getIconNightGis(listSkyTod[i])
                                                        else getIconDayGis(listSkyTod[i])
                                                    ),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(38.dp)
                                                )
                                                Row(horizontalArrangement = Arrangement.Center) {
                                                    Text(
                                                        text = item.replace(",", ".").replace("+", ""),
                                                        textAlign = TextAlign.Center,
                                                        fontStyle = FontStyle.Italic
                                                    )
                                                    Text(text = " °C")
                                                }
                                            }
                                        }
                                    }
                                }
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(6.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    listTod.takeLast(4).forEachIndexed { i, item ->
                                        Card(
                                            modifier = Modifier.padding(3.dp),
                                            border = BorderStroke(1.dp, Color.Black)
                                        ) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center,
                                                modifier = Modifier.width(64.dp)

                                            ) {
                                                Row(
                                                    horizontalArrangement = Arrangement.Center,
                                                    verticalAlignment = Alignment.Top
                                                ) {
                                                    Text(
                                                        text = listHour[4 + i],
                                                        textAlign = TextAlign.Center,
                                                        fontSize = 12.sp, modifier = Modifier.padding(top = 2.dp)
                                                    )
                                                    Text(text = min, fontSize = 8.sp, modifier = Modifier.padding(top = 2.dp))
                                                }
                                                Image(
                                                    painter = rememberImagePainter(
                                                        if (i in 2..3) getIconNightGis(listSkyTod[4 + i])
                                                        else getIconDayGis(listSkyTod[4 + i])
                                                    ),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(38.dp)
                                                )
                                                Row(horizontalArrangement = Arrangement.Center) {
                                                    Text(
                                                        text = item,
                                                        textAlign = TextAlign.Center,
                                                        fontStyle = FontStyle.Italic
                                                    )
                                                    Text(text = " °C")
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CellHeader(string = "Завтра", color = Color.Black)
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(6.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    listTom.takeLast(8).take(4).forEachIndexed { i, item ->
                                        Card(
                                            modifier = Modifier.padding(3.dp),
                                            border = BorderStroke(1.dp, Color.Black)
                                        ) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center,
                                                modifier = Modifier.width(64.dp)

                                            ) {
                                                Row(
                                                    horizontalArrangement = Arrangement.Center,
                                                    verticalAlignment = Alignment.Top
                                                ) {
                                                    Text(
                                                        text = listHour[i],
                                                        textAlign = TextAlign.Center,
                                                        fontSize = 12.sp, modifier = Modifier.padding(top = 2.dp)
                                                    )
                                                    Text(text = min, fontSize = 8.sp, modifier = Modifier.padding(top = 2.dp))
                                                }
                                                Image(
                                                    painter = rememberImagePainter(
                                                        if (i in 0..2) getIconNightGis(listSkyTom[i])
                                                        else getIconDayGis(listSkyTom[i])
                                                    ),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(38.dp)
                                                )
                                                Row(horizontalArrangement = Arrangement.Center) {
                                                    Text(
                                                        text = item.replace(",", ".").replace("+", ""),
                                                        textAlign = TextAlign.Center,
                                                        fontStyle = FontStyle.Italic
                                                    )
                                                    Text(text = " °C")
                                                }
                                            }
                                        }
                                    }
                                }
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(6.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    listTom.takeLast(4).forEachIndexed { i, item ->
                                        Card(
                                            modifier = Modifier.padding(3.dp),
                                            border = BorderStroke(1.dp, Color.Black)
                                        ) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center,
                                                modifier = Modifier.width(64.dp)

                                            ) {
                                                Row(
                                                    horizontalArrangement = Arrangement.Center,
                                                    verticalAlignment = Alignment.Top
                                                ) {
                                                    Text(
                                                        text = listHour[4 + i],
                                                        textAlign = TextAlign.Center,
                                                        fontSize = 12.sp, modifier = Modifier.padding(top = 2.dp)
                                                    )
                                                    Text(text = min, fontSize = 8.sp, modifier = Modifier.padding(top = 2.dp))
                                                }
                                                Image(
                                                    painter = rememberImagePainter(
                                                        if (i in 2..3) getIconNightGis(listSkyTom[4 + i])
                                                        else getIconDayGis(listSkyTom[4 + i])
                                                    ),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(38.dp)
                                                )
                                                Row(horizontalArrangement = Arrangement.Center) {
                                                    Text(
                                                        text = item,
                                                        textAlign = TextAlign.Center,
                                                        fontStyle = FontStyle.Italic
                                                    )
                                                    Text(text = " °C")
                                                }
                                            }
                                        }
                                    }
                                }
                            }
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


