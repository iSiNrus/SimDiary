package ru.barsik.simdiary.ui.screens

sealed class Screen(val route : String){
    object TaskTableScreen : Screen("task_table_screen")
    object TaskInfoScreen : Screen("task_info_screen")
    object NewTaskScreen : Screen("new_task_screen")
}
