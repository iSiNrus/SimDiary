package ru.barsik.simdiary.ui.screens.task_info

import ru.barsik.domain.model.Task

data class TaskInfoUIState(
    val tName : String = "",
    val tDescription: String = "",
    val timeStartText : String = "Выбрать",
    val timeFinishText : String = "Выбрать",

    val endButtonEnable: Boolean = true,

    val isSaving : Boolean = false,
    val isNameError: Boolean = false,
    val isTimeError: Boolean = false,
    val needSaving : Boolean = false
)
