/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.sakaki

import com.deflatedpickle.sakaki.listener.KeyListener
import com.deflatedpickle.sakaki.util.Files
import javafx.application.Application
import org.jnativehook.GlobalScreen
import java.util.logging.Level
import java.util.logging.Logger

fun main() {
    Files

    Logger.getLogger(GlobalScreen::class.java.`package`.name).apply {
        this.level = Level.OFF
    }

    GlobalScreen.registerNativeHook()
    GlobalScreen.addNativeKeyListener(KeyListener)

    Application.launch(PanelController::class.java)
}
