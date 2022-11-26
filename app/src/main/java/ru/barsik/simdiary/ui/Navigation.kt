package ru.barsik.simdiary.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.barsik.simdiary.ui.screens.Screen
import ru.barsik.simdiary.ui.screens.new_task.NewTaskScreen
import ru.barsik.simdiary.ui.screens.task_info.TaskInfoScreen
import ru.barsik.simdiary.ui.screens.task_table.TaskTableScreen

@Composable
@ExperimentalMaterialApi
fun Navigation(startDist: String) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDist) {
        composable(Screen.TaskTableScreen.route) { TaskTableScreen(navController) }
        composable(route = "${Screen.TaskInfoScreen.route}/{taskId}",
            arguments = listOf( navArgument("taskId") { type = NavType.LongType })
        ) { backStackEntry ->
            if (backStackEntry.arguments?.getLong("taskId") != null)
                TaskInfoScreen(navController,
                    taskID = backStackEntry.arguments?.getLong("taskId")!!
                )
        }
        composable(Screen.NewTaskScreen.route) { NewTaskScreen(navController = navController)}
    }
}