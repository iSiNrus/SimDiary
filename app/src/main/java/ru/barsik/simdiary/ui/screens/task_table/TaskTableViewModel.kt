package ru.barsik.simdiary.ui.screens.task_table

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.barsik.domain.model.Task
import ru.barsik.domain.usecase.DeleteTaskUseCase
import ru.barsik.domain.usecase.GetTasksByDateUseCase
import ru.barsik.domain.usecase.SaveNewTaskUseCase
import ru.barsik.domain.usecase.UpdateTaskUseCase
import ru.barsik.domain.printDate
import java.lang.Math.random
import java.util.*
import javax.inject.Inject

@ExperimentalMaterialApi
@HiltViewModel
class TaskTableViewModel @Inject constructor(
    private val getTasksByDateUseCase: GetTasksByDateUseCase,
    private val saveNewTaskUseCase: SaveNewTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
): ViewModel() {

    private val TAG = "TaskTableViewModel"

    private val _uiState = MutableStateFlow(TaskTableUIState())
    val uiState: StateFlow<TaskTableUIState> = _uiState.asStateFlow()
    val mDate = Calendar.getInstance()
    var isFirstLaunch = true

    private val taskColor = mutableMapOf<Task, Color>()

    init {
        _uiState.value = uiState.value.copy(dateText = mDate.printDate())
    }
    fun setSelectedDate(year: Int, month: Int, dayOfMonth: Int) {
        mDate.set(year, month, dayOfMonth)
        _uiState.value = uiState.value.copy(dateText = mDate.printDate())
    }

    fun getTasksByDate(scope: CoroutineScope) {
        scope.launch {
            val list = getTasksByDateUseCase.execute(
                mDate.get(Calendar.DAY_OF_MONTH),
                mDate.get(Calendar.MONTH),
                mDate.get(Calendar.YEAR)
            )
            Log.d(TAG, "getTasksByDate: принял данные ${list.size}")
            (list as MutableList).sortBy{ it.getHourOfStart() }

            list.map { taskColor.put(it, Color(random().toFloat(), random().toFloat(), random().toFloat())) }

            _uiState.value = _uiState.value.copy(tasks = list, isNewData = true)
        }
    }

    fun getTaskColors(): Map<Task, Color> = taskColor

}