package com.windrr.androidinterview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.windrr.androidinterview.model.interviewParts
import com.windrr.androidinterview.ui.InterviewListScreen
import com.windrr.androidinterview.ui.PartSelectionScreen
import com.windrr.androidinterview.ui.theme.AndroidInterviewTheme
import com.windrr.androidinterview.viewmodel.InterviewViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidInterviewTheme {
                InterviewApp()
            }
        }
    }
}

@Composable
fun InterviewApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "part_selection"
    ) {
        // 파트 선택 화면
        composable("part_selection") {
            PartSelectionScreen(
                onPartSelected = { part ->
                    navController.navigate("interview/${part.fileName}/${part.subtitle}")
                }
            )
        }

        // 질문 목록 화면 - fileName과 partTitle을 nav 인자로 전달
        composable(
            route = "interview/{fileName}/{partTitle}",
            arguments = listOf(
                navArgument("fileName") { type = NavType.StringType },
                navArgument("partTitle") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val partTitle = backStackEntry.arguments?.getString("partTitle") ?: ""
            val viewModel: InterviewViewModel = viewModel()
            InterviewListScreen(
                partTitle = partTitle,
                onBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
    }
}