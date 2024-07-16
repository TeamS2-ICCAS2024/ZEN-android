    package com.iccas.zen

    import com.iccas.zen.presentation.heart.viewmodel.MeasureHeartViewModel
    import android.os.Bundle
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.activity.viewModels
    import androidx.compose.material3.MaterialTheme
    import com.iccas.zen.presentation.tetris.logic.SoundUtil
    import com.iccas.zen.presentation.tetris.logic.StatusBarUtil
    import com.iccas.zen.navigation.NavGraph
    import com.iccas.zen.presentation.tetris.logic.GameViewModel


    class MainActivity : ComponentActivity() {
        private val measureHeartViewModel: MeasureHeartViewModel by viewModels()
        private val gameViewModel: GameViewModel by viewModels()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            StatusBarUtil.transparentStatusBar(this)
            SoundUtil.init(this)
            setContent {
                MaterialTheme {
                    NavGraph(measureHeartViewModel, gameViewModel)
                }
            }
        }
    }

