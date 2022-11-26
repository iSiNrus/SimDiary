package ru.barsik.simdiary.ui.screens.new_task

data class NewTaskUIState(
    val tName : String = "",
    val tDescription: String = "",
    val timeStartText : String = "",
    val timeFinishText : String = "",

    val rbTaskCheck : Boolean = true,
    val rbNotifCheck : Boolean = false,
    val rbAllDayCheck: Boolean = false,

    val endButtonEnable: Boolean = true,

    val isSaving : Boolean = false,
    val isNameError: Boolean = false,
    val isTimeError: Boolean = false
)
