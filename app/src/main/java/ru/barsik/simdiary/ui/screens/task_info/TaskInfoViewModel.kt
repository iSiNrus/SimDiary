package ru.barsik.simdiary.ui.screens.task_info

import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.barsik.domain.model.Task
import ru.barsik.domain.usecase.DeleteTaskUseCase
import ru.barsik.domain.usecase.GetTaskByIdUseCase
import ru.barsik.domain.usecase.UpdateTaskUseCase
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class TaskInfoViewModel @Inject constructor(
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {
    private val TAG = "TaskInfoViewModel"
    var isComplete: Boolean = false
    lateinit var mTask: Task

    private val _uiState = MutableStateFlow(TaskInfoUIState())
    val uiState: StateFlow<TaskInfoUIState> = _uiState.asStateFlow()
    var isFirstLaunch = true

    private var dateStart: LocalDate? = null
    private var timeStart: LocalTime? = null
    private var dateFinish: LocalDate? = null
    private var timeFinish: LocalTime? = null


    fun getTaskById(taskID: Long, scope: CoroutineScope) {
        scope.launch {
            mTask = getTaskByIdUseCase.execute(taskID)

            _uiState.value = _uiState.value.copy(
                tName = mTask.name,
                tDescription = mTask.description,
                timeStartText = mTask.dateTimeStartText(),
                timeFinishText = mTask.dateTimeFinishText()
            )
        }
    }

    fun nameChanged(pName: String) {
        _uiState.value = _uiState.value.copy(tName = pName, isNameError = false, needSaving = true)
    }

    fun descriptionChanged(pDescription: String) {
        _uiState.value = _uiState.value.copy(tDescription = pDescription, needSaving = true)
    }

    fun setStartDate(date: LocalDate) {
        dateStart = date
        _uiState.value =
            _uiState.value.copy(
                timeStartText = "${date.dayOfMonth}.${date.monthValue}.${date.year}",
                needSaving = true
            )
    }

    fun setStartTime(time: LocalTime) {
        timeStart = time
        val res = _uiState.value.timeStartText + " ${time.hour}:${time.minute}"
        _uiState.value = _uiState.value.copy(timeStartText = res, needSaving = true)
    }

    fun setFinishDate(date: LocalDate) {
        dateFinish = date
        _uiState.value =
            _uiState.value.copy(
                timeFinishText = "${date.dayOfMonth}.${date.monthValue}.${date.year}",
                needSaving = true
            )
    }

    fun setFinishTime(time: LocalTime) {
        timeFinish = time
        val res = _uiState.value.timeFinishText + " ${time.hour}:${time.minute}"
        _uiState.value = _uiState.value.copy(timeFinishText = res, needSaving = true)
    }

    fun checkAndSave(scope: CoroutineScope) {
        _uiState.value = _uiState.value.copy(
            isNameError = false,
            isTimeError = false,
            isSaving = true
        )

        mTask.name = _uiState.value.tName
        mTask.description = _uiState.value.tDescription
        if (dateStart != null && timeStart != null) {
            val startCalendar = Calendar.getInstance()

            startCalendar.set(Calendar.YEAR, dateStart?.year ?: throw Exception("BadDate"))
            startCalendar.set(Calendar.MONTH, (dateStart?.monthValue!! - 1))
            startCalendar.set(
                Calendar.DAY_OF_MONTH,
                dateStart?.dayOfMonth ?: throw Exception("BadDate")
            )
            startCalendar.set(Calendar.HOUR_OF_DAY, timeStart?.hour ?: throw Exception("BadDate"))
            startCalendar.set(Calendar.MINUTE, timeStart?.minute ?: throw Exception("BadDate"))
            mTask.date_start = startCalendar.timeInMillis
        }
        if (dateFinish != null && timeFinish != null) {
            val finishCalendar = Calendar.getInstance()

            finishCalendar.set(Calendar.YEAR, dateFinish?.year ?: throw Exception("BadDate"))
            finishCalendar.set(Calendar.MONTH, dateFinish?.monthValue!!-1)
            finishCalendar.set(
                Calendar.DAY_OF_MONTH,
                dateFinish?.dayOfMonth ?: throw Exception("BadDate")
            )
            finishCalendar.set(Calendar.HOUR_OF_DAY, timeFinish?.hour ?: throw Exception("BadDate"))
            finishCalendar.set(Calendar.MINUTE, timeFinish?.minute ?: throw Exception("BadDate"))
            mTask.date_start = finishCalendar.timeInMillis
        }

        if (mTask.name.trim().isEmpty()) _uiState.value =
            _uiState.value.copy(isNameError = true, isSaving = false)
        if (mTask.date_start > mTask.date_finish)
            _uiState.value =
                _uiState.value.copy(isTimeError = true, isSaving = false)

        scope.launch {
            if (updateTaskUseCase.execute(task = mTask)) {
                Log.d(TAG, "checkAndSave: Save Success")
            } else {
                Log.d(TAG, "checkAndSave: Save Error")
            }
            _uiState.value = _uiState.value.copy(isSaving = false)
            isComplete = true
        }

    }

    fun deleteTask(scope: CoroutineScope){
        scope.launch {
            if(deleteTaskUseCase.execute(mTask)){
                Log.d(TAG, "checkAndSave: Delete Success")
            } else {
                Log.d(TAG, "checkAndSave: Delete Error")
            }
            _uiState.value = _uiState.value.copy(isSaving = false)
            isComplete = true
        }
    }
}