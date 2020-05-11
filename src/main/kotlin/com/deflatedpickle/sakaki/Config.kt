/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.sakaki

import com.electronwill.nightconfig.core.file.FileConfig
import java.io.File
import net.harawata.appdirs.AppDirsFactory

object Config {
    val appDir = File(
        AppDirsFactory.getInstance().getUserConfigDir(
        "Sakaki", null, "DeflatedPickle", true
    ))

    val configFile = File("${appDir.path}/default.toml")

    val config = FileConfig
        .builder(configFile)
        .defaultResource("default.toml")
        .autoreload()
        .build()

    init {
        this.appDir.mkdirs()
        println(appDir)
        this.configFile.createNewFile()

        config.load()
        println(config)
    }
}
