package com.iccas.zen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.iccas.zen.R
import com.iccas.zen.SelectEmotionScreen
import com.iccas.zen.presentation.chatBot.ChatScreen
import com.iccas.zen.presentation.heart.BaseResultScreen
import com.iccas.zen.presentation.heart.GuideMeasureBaseScreen
import com.iccas.zen.presentation.heart.MeasureBaseScreen
import com.iccas.zen.presentation.heart.viewmodel.MeasureHeartViewModel
import com.iccas.zen.presentation.tetris.logic.GameViewModel
import com.iccas.zen.presentation.tetris.TetrisGameScreen
import com.iccas.zen.presentation.heart.CountDownScreen
import com.iccas.zen.presentation.heart.HighHeartRateScreen
import com.iccas.zen.presentation.heart.RecommendMeasureBaseScreen
import com.iccas.zen.presentation.home.GameSelectScreen
import com.iccas.zen.presentation.tetris.TetrisGameOverScreen

@Composable
fun NavGraph(
    measureHeartViewModel: MeasureHeartViewModel,
    gameViewModel: GameViewModel
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "game_select") {
        composable("game_select") {
            GameSelectScreen(navController = navController)
        }
        composable("guide_measure_base") {
            GuideMeasureBaseScreen(navController = navController)
        }
        composable("recommend_measure_base") {
            RecommendMeasureBaseScreen(navController = navController)
        }
        composable("measure_base") {
            MeasureBaseScreen(measureHeartViewModel = measureHeartViewModel, navController = navController)
        }
        composable("base_result") {
            BaseResultScreen(measureHeartViewModel = measureHeartViewModel, navController = navController)
        }
        composable("countdown") {
            CountDownScreen(navController = navController)
        }
        composable("game_select") {
            GameSelectScreen(navController = navController)
        }
        composable("tetris_game") {
            TetrisGameScreen(measureHeartViewModel = measureHeartViewModel, gameViewModel = gameViewModel, navController = navController)
        }
        composable("high_heart_rate") {
            HighHeartRateScreen()
        }
        composable("select_emotion") {
            SelectEmotionScreen(navController = navController)
        }
        composable(
            route = "tetris_game_over/level={level}/score={score}/lives={lives}/dateTime={dateTime}",
            arguments = listOf(
                navArgument("level") { type = NavType.IntType },
                navArgument("score") { type = NavType.IntType },
                navArgument("lives") { type = NavType.IntType },
                navArgument("dateTime") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val level = backStackEntry.arguments?.getInt("level")
            val score = backStackEntry.arguments?.getInt("score")
            val lives = backStackEntry.arguments?.getInt("lives")
            val dateTime = backStackEntry.arguments?.getString("dateTime")
            TetrisGameOverScreen(navController, level, score, lives, dateTime)
        }
        composable(
            route = "chat_screen/{emojiResId}",
            arguments = listOf(navArgument("emojiResId") { type = NavType.IntType })
        ) { backStackEntry ->
            val emojiResId = backStackEntry.arguments?.getInt("emojiResId") ?: R.drawable.happy
            ChatScreen(navController = navController, emojiResId = emojiResId)
        }
    }
}
