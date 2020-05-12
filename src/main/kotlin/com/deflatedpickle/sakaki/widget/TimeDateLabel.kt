/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.sakaki.widget

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javafx.animation.Animation
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Label
import javafx.util.Duration

class TimeDateLabel(format: String = "HH:mm:ss") : Label() {
    val formatter = DateTimeFormatter.ofPattern(format)

    init {
        this.styleClass.clear()
        this.styleClass.add("time-date-label")

        this.text = formatter.format(LocalDateTime.now())

        Timeline(
            KeyFrame(
                Duration.millis(1000.0),
                EventHandler { event: ActionEvent ->
                    this@TimeDateLabel.text = formatter.format(LocalDateTime.now())
                }
            )
        ).apply { cycleCount = Animation.INDEFINITE }.play()
    }
}
