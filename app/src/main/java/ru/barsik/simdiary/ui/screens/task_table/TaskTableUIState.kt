package ru.barsik.simdiary.ui.screens.task_table

import androidx.compose.foundation.ScrollState
import androidx.compose.material.BottomDrawerState
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import ru.barsik.domain.model.Task

@ExperimentalMaterialApi
data class TaskTableUIState (
    val bottomDrawerState: BottomDrawerState = BottomDrawerState(BottomDrawerValue.Closed),
    val scrollState: ScrollState = ScrollState(0),
    val dateText: String = "",
    val tasks: List<Task> = ArrayList(),
    val isNewData: Boolean = false

)