/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.sakaki

import javafx.scene.Group
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.stage.StageStyle

class PanelStage(text: String, x: Double, y: Double, width: Double, height: Double) : Stage(StageStyle.UNDECORATED) {
    val group = Group()

    init {
        this.title = text
        this.x = x
        this.y = y
        this.scene = Scene(group, width, height)
        this.isAlwaysOnTop = true
    }
}
