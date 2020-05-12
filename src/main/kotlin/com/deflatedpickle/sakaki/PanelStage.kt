/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.sakaki

import com.deflatedpickle.sakaki.util.Side
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.Group
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.stage.StageStyle
import javafx.util.Duration

class PanelStage(text: String, style: String, sideFrom: Side, slideTime: Double, x: Double, y: Double, width: Double, height: Double) :
    Stage(StageStyle.UNDECORATED) {
    val group = Group()

    init {
        this.title = text
        this.x = x
        this.y = y
        this.scene = Scene(group, width, height).apply {
            if (style.startsWith("file:")) {
                stylesheets.add(style)
            } else {
                stylesheets.add(this::class.java.getResource(style).toExternalForm())
            }
        }
        this.isAlwaysOnTop = true

        val initialPosition = when (sideFrom) {
            Side.NORTH -> y - height
            Side.EAST -> x - width
            Side.SOUTH -> y + height
            Side.WEST -> x + width
        }

        val positionProperty = SimpleDoubleProperty(initialPosition).apply {
            addListener { _, _, newValue ->
                when (sideFrom) {
                    Side.NORTH, Side.SOUTH -> this@PanelStage.y = newValue.toDouble()
                    Side.EAST, Side.WEST -> this@PanelStage.x = newValue.toDouble()
                }
            }
        }

        Timeline(
            KeyFrame(
                Duration.seconds(slideTime),
                KeyValue(positionProperty, when (sideFrom) {
                    Side.NORTH, Side.SOUTH -> y
                    Side.EAST, Side.WEST -> x
                })
            )
        ).play()
    }
}
