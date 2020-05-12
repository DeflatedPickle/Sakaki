/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.sakaki

import com.deflatedpickle.sakaki.util.Side
import com.electronwill.nightconfig.core.CommentedConfig
import javafx.application.Application
import javafx.scene.Node
import javafx.stage.Stage

class PanelController : Application() {
    override fun start(primaryStage: Stage) {
        for ((key, value) in Files.config.valueMap()) {
            // Create a window for each of the keys
            if (value is CommentedConfig) {
                    PanelStage(
                        // Uses the block key as the name
                        key.capitalize(),
                        value.get("style"),
                        Side.valueOf(value.getOrElse("slide-from", "NORTH")),
                        value.getOrElse("slide-time", 1.0),
                        value.get("x"),
                        value.get("y"),
                        value.get("width"),
                        value.get("height")
                    ).apply {
                        // Loop the widgets
                        for (i in value.get("widgets") as ArrayList<Any>) {
                            if (i is CommentedConfig) {
                                group.children.add(
                                    // Get the class by it's package and name
                                    Class.forName(i.get("type"))
                                        // Get a constructor matching the argument types, dropping the type
                                        .getConstructor(*i.valueMap().filter { it.key != "type" }.map { it.value::class.java }.toTypedArray())
                                        // Create a new instance and pass in the argument values
                                        .newInstance(*i.valueMap().filter { it.key != "type" }.map { it.value }.toTypedArray()) as Node
                                )
                            }
                        }
                    }.show()
            }
        }
    }
}
