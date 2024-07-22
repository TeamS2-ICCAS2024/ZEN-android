package com.iccas.zen.utils

import android.content.Context
import android.media.MediaPlayer
import com.iccas.zen.R

object MusicManager {
    private var mainMusicPlayer: MediaPlayer? = null
    private var tetrisMusicPlayer: MediaPlayer? = null
    private var yogaMusicPlayer: MediaPlayer? = null

    fun initializeMainMusic(context: Context) {
        if (mainMusicPlayer == null) {
            mainMusicPlayer = MediaPlayer.create(context, R.raw.background_music).apply {
                isLooping = true
            }
        }
    }

    fun initializeTetrisMusic(context: Context) {
        if (tetrisMusicPlayer == null) {
            tetrisMusicPlayer = MediaPlayer.create(context, R.raw.tetris_music).apply {
                isLooping = true
            }
        }
    }

    fun initializeYogaMusic(context: Context) {
        if (yogaMusicPlayer == null) {
            yogaMusicPlayer = MediaPlayer.create(context, R.raw.yoga_music).apply {
                isLooping = true
            }
        }
    }

    fun playMainMusic() {
        mainMusicPlayer?.start()
    }

    fun playTetrisMusic() {
        tetrisMusicPlayer?.start()
    }

    fun playYogaMusic() {
        yogaMusicPlayer?.start()
    }
    fun stopMainMusic() {
        mainMusicPlayer?.let {
            if (it.isPlaying) {
                it.stop()
                it.prepare()
            }
        }
    }
    fun resumeMainMusic() {
        mainMusicPlayer?.let {
            if (!it.isPlaying) {
                it.start()
            }
        }
    }
    fun stopTetrisMusic() {
        tetrisMusicPlayer?.let {
            if (it.isPlaying) {
                it.stop()
                it.prepare()
            }
        }
    }
    fun stopMusic() {
        mainMusicPlayer?.let {
            if (it.isPlaying) {
                it.stop()
                it.prepare()
            }
        }
        tetrisMusicPlayer?.let {
            if (it.isPlaying) {
                it.stop()
                it.prepare()
            }
        }
        yogaMusicPlayer?.let {
            if (it.isPlaying) {
                it.stop()
                it.prepare()
            }
        }
    }

    fun releaseAllPlayers() {
        mainMusicPlayer?.release()
        tetrisMusicPlayer?.release()
        yogaMusicPlayer?.release()
        mainMusicPlayer = null
        tetrisMusicPlayer = null
        yogaMusicPlayer = null
    }
}
