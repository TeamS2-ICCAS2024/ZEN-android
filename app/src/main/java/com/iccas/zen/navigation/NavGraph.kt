package com.iccas.zen.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.iccas.zen.R
import com.iccas.zen.presentation.character.CharScreen
import com.iccas.zen.presentation.character.CollectionScreen
import com.iccas.zen.presentation.chatBot.ChatScreen
import com.iccas.zen.presentation.heart.*
import com.iccas.zen.presentation.heart.viewmodel.MeasureHeartViewModel
import com.iccas.zen.presentation.home.*
import com.iccas.zen.presentation.onBoarding.*
import com.iccas.zen.presentation.report.*
import com.iccas.zen.presentation.auth.*
import com.iccas.zen.presentation.chatBot.DiaryCharSelectScreen
import com.iccas.zen.presentation.chatBot.SelectEmotionScreen
import com.iccas.zen.presentation.tetris.TetrisGameScreen
import com.iccas.zen.presentation.tetris.logic.GameViewModel
import com.iccas.zen.presentation.diagnosis.DiagnosisScreen
import com.iccas.zen.presentation.diagnosis.SurveyScreen
import com.iccas.zen.presentation.diagnosis.ResultScreen
import com.iccas.zen.presentation.report.GameReportScreen
import com.iccas.zen.presentation.yoga.*
import com.iccas.zen.utils.MusicManager


@Composable
fun NavGraph(
    measureHeartViewModel: MeasureHeartViewModel,
    gameViewModel: GameViewModel
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    // MusicManager 초기화
    LaunchedEffect(context) {
        MusicManager.initializeMainMusic(context)
        MusicManager.initializeTetrisMusic(context)
        MusicManager.initializeYogaMusic(context)
    }

    NavHost(navController, startDestination = "onboarding1") {
        composable("onboarding1") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            OnboardingPage1(navController)
        }
        composable("onboarding2") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            OnboardingPage2(navController)
        }
        composable("onboarding3") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            OnboardingPage3(navController)
        }
        composable("onboarding4") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            OnboardingPage4(navController)
        }
        composable("welcome") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            WelcomeScreen(navController)
        }
        composable("login") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            LoginScreen(navController)
        }
        composable("signup") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            SignupScreen(navController)
        }
        composable("game_select") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            GameSelectScreen(navController = navController)
        }
        composable("guide_measure_base") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            GuideMeasureBaseScreen(navController = navController)
        }
        composable("recommend_measure_base") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            RecommendMeasureBaseScreen(navController = navController)
        }
        composable("measure_base") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            MeasureBaseScreen(
                measureHeartViewModel = measureHeartViewModel,
                navController = navController
            )
        }
        composable("base_result") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            BaseResultScreen(
                measureHeartViewModel = measureHeartViewModel,
                navController = navController
            )
        }
        composable(
            "countdown/{route}",
            arguments = listOf(navArgument("route") { type = NavType.StringType })
        ) { backStackEntry ->
            val route = backStackEntry.arguments?.getString("route") ?: "tetris_game"
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            CountDownScreen(navController = navController, route = route)
        }
        composable("tetris_game") {
            LaunchedEffect(Unit) {
                MusicManager.initializeTetrisMusic(context)
            }
            TetrisGameScreen(
                measureHeartViewModel = measureHeartViewModel,
                gameViewModel = gameViewModel,
                navController = navController
            )
        }
        composable("diagnosis") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            DiagnosisScreen(navController)
        }
        composable("survey") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            SurveyScreen(navController)
        }
        composable(
            "result/{score}",
            arguments = listOf(navArgument("score") { type = NavType.IntType })
        ) { backStackEntry ->
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            ResultScreen(score = score, navController = navController)
        }
        composable("report/game") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            GameReportListScreen(navController)
        }
        composable(
            route = "report/game/{gameId}",
            arguments = listOf(navArgument("gameId") { type = NavType.LongType })
        ) {
            backStackEntry ->
            val gameId = backStackEntry.arguments?.getLong("gameId") ?: 0L
            GameReportScreen(navController = navController, gameId = gameId)
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
        }
        composable("high_heart_rate") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            HighHeartRateScreen()
        }
        composable("select_emotion/{characterImageRes}/{characterDescription}",
            arguments = listOf(
                navArgument("characterImageRes") { type = NavType.IntType },
                navArgument("characterDescription") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val characterImageRes = backStackEntry.arguments?.getInt("characterImageRes") ?: R.drawable.char_mozzi1
            val characterDescription = backStackEntry.arguments?.getString("characterDescription") ?: "Mozzi"
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            SelectEmotionScreen(navController = navController, characterImageRes = characterImageRes, characterDescription = characterDescription)
        }
        composable(
            "chat_screen/{emojiResId}?prompt={prompt}&characterDescription={characterDescription}",
            arguments = listOf(
                navArgument("emojiResId") { type = NavType.IntType },
                navArgument("prompt") { type = NavType.StringType; defaultValue = "" },
                navArgument("characterDescription") { type = NavType.StringType; defaultValue = "Mozzi" }
            )
        ) { backStackEntry ->
            val emojiResId = backStackEntry.arguments?.getInt("emojiResId") ?: 0
            val prompt = backStackEntry.arguments?.getString("prompt") ?: ""
            val characterDescription = backStackEntry.arguments?.getString("characterDescription") ?: "Mozzi"
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            ChatScreen(navController, emojiResId, prompt, characterDescription)
        }
        composable("start_yoga") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            StartYogaGameScreen(navController = navController)
        }
        composable(route = "yoga_game") {
            LaunchedEffect(Unit) {
                MusicManager.playYogaMusic()
            }
            YogaGameScreen(initialPoseIndex = 0, navController = navController)
        }
        composable("char_main") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            CharScreen(navController = navController)
        }
        composable("collection") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            CollectionScreen(navController = navController)
        }
        composable("report") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            ReportScreen(navController = navController)
        }
        composable("report/emotion_diary") {
            EmotionDiarySelectScreen(navController = navController)

            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
        }
        composable("report/self_diagnosis") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            SelfDiagnosisSelect(navController = navController)
        }
        composable("report/self_diagnosis/{test_id}",
            arguments = listOf(navArgument("test_id") { type = NavType.IntType })
        ) {
            val testId = it.arguments?.getInt("test_id") ?: 0
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            SelfDiagnosisReport(navController = navController, testId = testId)
        }
        composable(
            route = "emotion_detail/{emotionDiaryId}",
            arguments = listOf(navArgument("emotionDiaryId") { type = NavType.LongType })
        )  { backStackEntry ->
            val emotionDiaryId = backStackEntry.arguments?.getLong("emotionDiaryId") ?: 0L
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            DiaryDetailScreen(navController = navController, emotionDiaryId = emotionDiaryId)
    }
        composable("setting") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            SettingScreen(navController = navController)
        }
        composable("personal_setting") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            PersonalSettingScreen(navController = navController)
        }
        composable("emotion_diary") {
            LaunchedEffect(Unit) {
                MusicManager.playMainMusic()
            }
            DiaryCharSelectScreen(navController = navController)
        }
    }
}