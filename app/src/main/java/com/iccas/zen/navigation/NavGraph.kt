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
import com.iccas.zen.presentation.onBoarding.OnboardingPage1
import com.iccas.zen.presentation.onBoarding.OnboardingPage2
import com.iccas.zen.presentation.onBoarding.OnboardingPage3
import com.iccas.zen.presentation.onBoarding.OnboardingPage4
import com.iccas.zen.presentation.signup.SignupScreen
import com.iccas.zen.presentation.signup.LoginScreen
import com.iccas.zen.presentation.signup.WelcomeScreen


@Composable
fun NavGraph(
    measureHeartViewModel: MeasureHeartViewModel,
    gameViewModel: GameViewModel
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "onboarding1") {
        composable("onboarding1") { OnboardingPage1(navController) }
        composable("onboarding2") { OnboardingPage2(navController) }
        composable("onboarding3") { OnboardingPage3(navController) }
        composable("onboarding4") { OnboardingPage4(navController) }
        composable("welcome") { WelcomeScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignupScreen(navController) }
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
       /* composable("game_select") {
            GameSelectScreen(navController = navController)
        }*/
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
            route = "chat_screen/{emojiResId}",
            arguments = listOf(navArgument("emojiResId") { type = NavType.IntType })
        ) { backStackEntry ->
            val emojiResId = backStackEntry.arguments?.getInt("emojiResId") ?: R.drawable.happy
            ChatScreen(navController = navController, emojiResId = emojiResId)
        }
    }
}
