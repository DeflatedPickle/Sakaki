/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.sakaki

import com.deflatedpickle.sakaki.util.Side
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage
import javafx.stage.StageStyle
import javafx.util.Duration

class PanelStage(
    text: String,
    style: String,
    slideFrom: Side, slideTime: Double,
    val toggleModifier: String, val toggleKey: String,
    x: Double, y: Double,
    width: Double, height: Double
) : Stage(StageStyle.UNDECORATED) {
    val pane = Pane()

    val outProperty = SimpleBooleanProperty(false)

    private val positionProperty = SimpleDoubleProperty(
        when (slideFrom) {
            Side.NORTH -> y - height
            Side.EAST -> x - width
            Side.SOUTH -> y + height
            Side.WEST -> x + width
        }
    ).apply {
        addListener { _, oldValue, newValue ->
            when (slideFrom) {
                Side.NORTH, Side.SOUTH -> this@PanelStage.y = newValue.toDouble()
                Side.EAST, Side.WEST -> this@PanelStage.x = newValue.toDouble()
            }

            outProperty.value = oldValue.toDouble() < newValue.toDouble()
        }
    }

    val showTimeline = Timeline(
        KeyFrame(
            Duration.seconds(slideTime),
            KeyValue(
                positionProperty, when (slideFrom) {
                    Side.NORTH, Side.SOUTH -> y
                    Side.EAST, Side.WEST -> x
                }
            )
        )
    )

    val hideTimeline = Timeline(
        KeyFrame(
            Duration.seconds(slideTime),
            KeyValue(
                positionProperty, when (slideFrom) {
                    Side.NORTH -> y - height
                    Side.EAST -> x - width
                    Side.SOUTH -> y + height
                    Side.WEST -> x + width
                }
            )
        )
    )

    init {
        this.pane.styleClass.clear()
        this.pane.styleClass.add("panel")

        this.title = text

        this.x = x
        this.y = y

        when (slideFrom) {
            Side.NORTH -> this.y -= height
            Side.EAST -> this.x -= width
            Side.SOUTH -> this.y += height
            Side.WEST -> this.x += width
        }

        this.scene = Scene(pane, width, height).apply {
            if (style.startsWith("file:")) {
                stylesheets.add(style)
                this@PanelStage.pane.stylesheets.add(style)
            } else {
                with(this::class.java.getResource(style).toExternalForm()) {
                    stylesheets.add(this)
                    this@PanelStage.pane.stylesheets.add(this)
                }
            }
        }
        this.isAlwaysOnTop = true
    }
}
