package ru.barsik.simdiary.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import ru.barsik.simdiary.ui.screens.Screen
import ru.barsik.simdiary.ui.screens.task_table.TaskTableScreen
import ru.barsik.simdiary.ui.theme.SimDiaryTheme

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimDiaryTheme(darkTheme = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Navigation(Screen.TaskTableScreen.route)
                }
            }
        }
    }
}