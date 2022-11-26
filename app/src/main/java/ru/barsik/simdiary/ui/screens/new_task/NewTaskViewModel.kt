package ru.barsik.simdiary.ui.screens.new_task

import android.icu.util.Calendar
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.barsik.domain.model.Task
import ru.barsik.domain.usecase.SaveNewTaskUseCase
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class NewTaskViewModel @Inject constructor(
    private val saveNewTaskUseCase: SaveNewTaskUseCase
) : ViewModel() {
    private val TAG = "NewTaskViewModel"
    var isComplete: Boolean = false
    private val _uiState = MutableStateFlow(NewTaskUIState())
    val uiState = _uiState.asStateFlow()
    private var dateStart = LocalDate.now()
    private var timeStart = LocalTime.now()
    private var dateFinish = LocalDate.now()
    private var timeFinish = LocalTime.now().plusHours(1)

    init {
        setStartDate(dateStart)
        setStartTime(timeStart)

        setFinishDate(dateFinish)
        setFinishTime(timeFinish)

    }

    fun nameChanged(pName: String) {
        _uiState.value = _uiState.value.copy(tName = pName, isNameError = false)
    }

    fun descriptionChanged(pDescription: String) {
        _uiState.value = _uiState.value.copy(tDescription = pDescription)
    }

    fun setStartDate(date: LocalDate) {
        dateStart = date
        _uiState.value =
            _uiState.value.copy(timeStartText = "${date.dayOfMonth}.${date.monthValue}.${date.year}")
    }

    fun setStartTime(time: LocalTime) {
        timeStart = time
        val res = _uiState.value.timeStartText + " ${time.hour}:${time.minute}"
        _uiState.value = _uiState.value.copy(timeStartText = res)
    }

    fun setFinishDate(date: LocalDate) {
        dateFinish = date
        _uiState.value =
            _uiState.value.copy(timeFinishText = "${date.dayOfMonth}.${date.monthValue}.${date.year}")
    }

    fun setFinishTime(time: LocalTime) {
        timeFinish = time
        val res = _uiState.value.timeFinishText + " ${time.hour}:${time.minute}"
        _uiState.value = _uiState.value.copy(timeFinishText = res)
    }

    fun selectRadioButton(rbTag: String) {
        when (rbTag) {
            "task" -> _uiState.value = _uiState.value.copy(
                rbTaskCheck = true,
                rbNotifCheck = false,
                rbAllDayCheck = false,
                endButtonEnable = true
            )
            "notif" -> _uiState.value = _uiState.value.copy(
                rbTaskCheck = false,
                rbNotifCheck = true,
                rbAllDayCheck = false,
                endButtonEnable = false
            )
            "all_day" -> _uiState.value = _uiState.value.copy(
                rbTaskCheck = false,
                rbNotifCheck = false,
                rbAllDayCheck = true,
                endButtonEnable = false
            )
        }
    }

    fun checkAndSave(scope:CoroutineScope) {
        _uiState.value = _uiState.value.copy(
            isNameError = false,
            isTimeError = false,
            isSaving = true
        )

        val name = _uiState.value.tName
        val descr = _uiState.value.tDescription
        val startCalendar = Calendar.getInstance()
        val finishCalendar = Calendar.getInstance()

        startCalendar.set(Calendar.YEAR, dateStart.year)
        startCalendar.set(Calendar.MONTH, dateStart.monthValue-1)
        startCalendar.set(Calendar.DAY_OF_MONTH, dateStart.dayOfMonth)
        startCalendar.set(Calendar.HOUR_OF_DAY, timeStart.hour)
        startCalendar.set(Calendar.MINUTE, timeStart.minute)

        finishCalendar.set(Calendar.YEAR, dateFinish.year)
        finishCalendar.set(Calendar.MONTH, dateFinish.monthValue-1)
        finishCalendar.set(Calendar.DAY_OF_MONTH, dateFinish.dayOfMonth)
        finishCalendar.set(Calendar.HOUR_OF_DAY, timeFinish.hour)
        finishCalendar.set(Calendar.MINUTE, timeFinish.minute)

        if (_uiState.value.rbNotifCheck) finishCalendar.timeInMillis = startCalendar.timeInMillis
        if (_uiState.value.rbAllDayCheck) {
            finishCalendar.set(Calendar.YEAR, startCalendar.get(Calendar.YEAR))
            finishCalendar.set(Calendar.MONTH, startCalendar.get(Calendar.MONTH))
            finishCalendar.set(Calendar.DAY_OF_MONTH, startCalendar.get(Calendar.DAY_OF_MONTH))
            startCalendar.set(Calendar.HOUR_OF_DAY, 0)
            startCalendar.set(Calendar.MINUTE, 0)
            finishCalendar.set(Calendar.HOUR_OF_DAY, 23)
            finishCalendar.set(Calendar.MINUTE, 59)
        }
        if (_uiState.value.rbTaskCheck) {
            if (startCalendar.timeInMillis > finishCalendar.timeInMillis)
                _uiState.value =
                    _uiState.value.copy(isTimeError = true, isSaving = false)
        }
        if (name.trim().isEmpty()) _uiState.value =
            _uiState.value.copy(isNameError = true, isSaving = false)


        if (!_uiState.value.isNameError && !_uiState.value.isTimeError) {

            scope.launch {

                if(saveNewTaskUseCase.execute(Task(
                    name = name,
                    description = descr,
                    date_start = startCalendar.timeInMillis,
                    date_finish = finishCalendar.timeInMillis
                ))){
                    Log.d(TAG, "checkAndSave: Save Success")
                } else {
                    Log.d(TAG, "checkAndSave: Save Error")
                }
                _uiState.value = _uiState.value.copy(isSaving = false)
                isComplete = true
            }


        }
    }


}