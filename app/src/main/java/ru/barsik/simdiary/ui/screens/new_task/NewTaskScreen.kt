package ru.barsik.simdiary.ui.screens.new_task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import ru.barsik.simdiary.ui.screens.Screen

@Composable
fun NewTaskScreen(navController: NavController, viewModel: NewTaskViewModel = hiltViewModel()) {

    val uiState = viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .scrollable(state = rememberScrollState(), orientation = Orientation.Vertical),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.value.tName,
            onValueChange = { viewModel.nameChanged(it) },
            singleLine = true,
            enabled = !uiState.value.isSaving,
            isError = uiState.value.isNameError,
            label = { Text(text = "Название") }
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .size(150.dp),
            value = uiState.value.tDescription,
            onValueChange = { viewModel.descriptionChanged(it) },
            label = { Text("Описание") },
            enabled = !uiState.value.isSaving,
            singleLine = false
        )
        val dateStartDialogState = rememberMaterialDialogState(initialValue = false)
        val timeStartDialogState = rememberMaterialDialogState(initialValue = false)
        val dateFinishDialogState = rememberMaterialDialogState(initialValue = false)
        val timeFinishDialogState = rememberMaterialDialogState(initialValue = false)
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text("Начало: ")
            Button(onClick = { dateStartDialogState.show() }, enabled = !uiState.value.isSaving) {
                Text(text = uiState.value.timeStartText)
            }
        }
        MaterialDialog(
            dialogState = dateStartDialogState,
            buttons = {
                positiveButton("Ok")
            }
        ) {
            datepicker { date ->
                viewModel.setStartDate(date)
                timeStartDialogState.show()
            }
        }
        MaterialDialog(
            dialogState = timeStartDialogState,
            buttons = {
                positiveButton("Ok")
            }
        ) {
            timepicker { time ->
                viewModel.setStartTime(time)
            }
        }
        if(uiState.value.endButtonEnable) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text("Окончание: ")
                Button(onClick = { dateFinishDialogState.show() }, enabled = !uiState.value.isSaving) {
                    Text(text = uiState.value.timeFinishText)
                }
            }
        }
        if(uiState.value.isTimeError){
            Text(color = Color.Red, text = "Ошибка времени")
        }
        MaterialDialog(
            dialogState = dateFinishDialogState,
            buttons = {
                positiveButton("Ok")
            }
        ) {
            datepicker { date ->
                viewModel.setFinishDate(date)
                timeFinishDialogState.show()
            }
        }
        MaterialDialog(
            dialogState = timeFinishDialogState,
            buttons = {
                positiveButton("Ok")
            }
        ) {
            timepicker { time ->
                viewModel.setFinishTime(time)
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .clickable(
                    enabled = !uiState.value.isSaving,
                    onClick = { viewModel.selectRadioButton("task") }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = uiState.value.rbTaskCheck,
                onClick = { viewModel.selectRadioButton("task") },
                        enabled = !uiState.value.isSaving,
            )
            Text(text = "Задача (от и до)")
        }
        Row(
            Modifier
                .fillMaxWidth()
                .clickable(
                    enabled = !uiState.value.isSaving,
                    onClick = { viewModel.selectRadioButton("notif") }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = uiState.value.rbNotifCheck,
                enabled = !uiState.value.isSaving,
                onClick = { viewModel.selectRadioButton("notif") })
            Text(text = "Уведомление (одмномоментно)")
        }
        Row(
            Modifier
                .fillMaxWidth()
                .clickable(
                    enabled = !uiState.value.isSaving,
                    onClick = { viewModel.selectRadioButton("all_day") }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = uiState.value.rbAllDayCheck,
                enabled = !uiState.value.isSaving,
                onClick = { viewModel.selectRadioButton("all_day") })
            Text(text = "Весь день")
        }
        if(uiState.value.isSaving){
            CircularProgressIndicator()
        } else {
            Button(onClick = { scope.launch { viewModel.checkAndSave(scope) } }) {
                Text(text = "Создать")
            }
        }
        if(viewModel.isComplete) {
            navController.navigate(Screen.TaskTableScreen.route)
            viewModel.isComplete = false
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun NewTaskScreenPreview() {
//    NewTaskScreen(navController = NavController(LocalContext.current), NewTaskViewModel())
//}