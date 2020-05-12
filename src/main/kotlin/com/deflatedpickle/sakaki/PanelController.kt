/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.sakaki

import com.deflatedpickle.sakaki.util.Files
import com.deflatedpickle.sakaki.util.ScreenPoint
import com.deflatedpickle.sakaki.util.Side
import com.electronwill.nightconfig.core.CommentedConfig
import javafx.application.Application
import javafx.scene.Node
import javafx.stage.Screen
import javafx.stage.Stage

class PanelController : Application() {
    companion object {
        val panelList = mutableListOf<PanelStage>()
    }

    override fun start(primaryStage: Stage) {
        val screenBounds = Screen.getPrimary().bounds

        for ((key, value) in Files.config.valueMap()) {
            // Create a window for each of the keys
            if (value is CommentedConfig) {
                val x: Double = try {
                    value.get("x")
                } catch (e: ClassCastException) {
                    when (value.getEnum("x", ScreenPoint::class.java)) {
                        ScreenPoint.LEFT -> screenBounds.maxX
                        ScreenPoint.CENTRE -> (screenBounds.maxX / 2) - (value.get<Double>("width") / 2)
                        else -> 0.0
                    }
                }

                val y: Double = try {
                    value.get("y")
                } catch (e: ClassCastException) {
                    when (value.getEnum("y", ScreenPoint::class.java)) {
                        ScreenPoint.BOTTOM -> screenBounds.maxY
                        ScreenPoint.CENTRE -> (screenBounds.maxY / 2) - (value.get<Double>("height") / 2)
                        else -> 0.0
                    }
                }

                PanelStage(
                    // Uses the block key as the name
                    key.capitalize(),
                    value.get("style"),
                    value.getEnumOrElse("slide-from", Side.NORTH),
                    value.getOrElse("slide-time", 1.0),
                    value.getOrElse("toggle-modifier", "CTRL"),
                    value.get("toggle-key"),
                    x,
                    y,
                    value.get("width"),
                    value.get("height")
                ).apply {
                    panelList.add(this)

                    // Loop the widgets
                    val widgets = value.get<ArrayList<Any>>("widgets")
                    if (widgets is ArrayList<Any>) {
                        for (i in widgets) {
                            if (i is CommentedConfig) {
                                group.children.add(
                                    // Get the class by it's package and name
                                    Class.forName(i.get("type"))
                                        // Get a constructor matching the argument types, dropping the type
                                        .getConstructor(*i.valueMap().filter { it.key != "type" }
                                            .map { it.value::class.java }.toTypedArray())
                                        // Create a new instance and pass in the argument values
                                        .newInstance(*i.valueMap().filter { it.key != "type" }.map { it.value }
                                            .toTypedArray()) as Node
                                )
                            }
                        }
                    }
                }.show()
            }
        }
    }
}
