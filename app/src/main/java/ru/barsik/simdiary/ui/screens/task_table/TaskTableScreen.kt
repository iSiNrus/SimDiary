package ru.barsik.simdiary.ui.screens.task_table

import android.util.Log
import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.navArgument
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.barsik.domain.getDateInt
import ru.barsik.domain.model.Task
import ru.barsik.simdiary.ui.screens.Screen
import ru.barsik.simdiary.ui.theme.Purple200
import ru.barsik.simdiary.ui.theme.SimTypography
import java.util.*

@Composable
@ExperimentalMaterialApi
fun TaskTableScreen(
    navController: NavController,
    viewModel: TaskTableViewModel = hiltViewModel()
) {
//    Log.d("CALENDAR", "TaskTableScreen: ${Calendar.getInstance().get(Calendar.HOUR_OF_DAY)}")
    val uiState = viewModel.uiState.collectAsState()
    val scope = (rememberCoroutineScope())
    if (viewModel.isFirstLaunch) {
        viewModel.getTasksByDate(scope)
        viewModel.isFirstLaunch = false
    }
    Log.d("TAG", "Screen compose")

    BottomDrawer(
        drawerState = uiState.value.bottomDrawerState,
        gesturesEnabled = false,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Blue)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                AndroidView(factory = { CalendarView(it) }, modifier = Modifier.fillMaxWidth(),
                    update = { views ->
                        views.date = viewModel.mDate.timeInMillis
                        views.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
                            viewModel.setSelectedDate(year, month, dayOfMonth)
                            viewModel.getTasksByDate(scope)
                            scope.launch { uiState.value.bottomDrawerState.close() }
                        }
                    }
                )
            }
        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.TopCenter
        )
        {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Blue)
                    .padding(8.dp), onClick = {
                    scope.launch {
                        uiState.value.bottomDrawerState.open()
                    }
                }) {
                    Text(uiState.value.dateText, style = SimTypography.h1)
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(state = uiState.value.scrollState)
                ) {
                    //CONTENT TASKS
                    val tasks = uiState.value.tasks
                    Log.d("TAG", "ContentTable: ${tasks.size}")
                    for (hour in 0..23) {
                        val taskForHour = mutableListOf<Task>()
                        tasks.map {
                            if (it.getDateOfStart() < viewModel.mDate.getDateInt() && it.getDateOfFinish() > viewModel.mDate.getDateInt())
                                taskForHour.add(it)
                            else {
                                if (it.getDateOfStart() < viewModel.mDate.getDateInt() && it.getDateOfFinish() == viewModel.mDate.getDateInt()) {
                                    if (it.getHourOfFinish() >= hour) taskForHour.add(it)
                                }
                                if (it.getDateOfStart() == viewModel.mDate.getDateInt() && it.getDateOfFinish() > viewModel.mDate.getDateInt())
                                    if (it.getHourOfStart() <= hour) taskForHour.add(it)

                            }
                            if (it.getDateOfStart() == viewModel.mDate.getDateInt() && it.getDateOfFinish() == viewModel.mDate.getDateInt())
                                if (it.getHourOfStart() <= hour && it.getHourOfFinish() >= hour) taskForHour.add(
                                    it
                                )
                        }
                        if (taskForHour.isNotEmpty()) {
                            val taskColors = viewModel.getTaskColors()
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .border(width = 2.dp, Color.DarkGray),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Spacer(modifier = Modifier.size(16.dp))
                                Text(text = "$hour:00 - ${hour + 1}:00")
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .scrollable(
                                            rememberScrollState(),
                                            orientation = Orientation.Horizontal
                                        ),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    taskForHour.map {
                                        Card(
                                            elevation = 10.dp,
                                            modifier = Modifier
                                                .padding(12.dp, 0.dp)
                                                .fillMaxHeight(),
                                            onClick = { navController.navigate("${Screen.TaskInfoScreen.route}/${it.id}") },
                                            backgroundColor = Purple200,
                                            shape = RoundedCornerShape(10.dp)
                                        ) {
                                            Text(
                                                text = it.name,
                                                modifier = Modifier.padding(8.dp)
                                            )
                                        }
                                    }
                                }

                            }
                        } else {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .border(width = 2.dp, Color.DarkGray),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "$hour:00 - ${hour + 1}:00")
                                Text(text = "Дел нет")
                            }
                        }


                    }

                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                FloatingActionButton(
                    content = {
                        Icon(Icons.Filled.Add, contentDescription = "Добавить")
                    },
                    onClick = { navController.navigate(Screen.NewTaskScreen.route) }
                )
            }

        }

    }
}
