/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.sakaki.util

import com.electronwill.nightconfig.core.file.FileConfig
import java.io.File
import net.harawata.appdirs.AppDirsFactory

object Files {
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
        appDir.mkdirs()
        println(appDir)
        configFile.createNewFile()

        config.load()
        println(config)
    }
}
