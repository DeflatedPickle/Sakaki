/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.sakaki.handler

import java.net.URL
import java.net.URLConnection
import java.net.URLStreamHandler

class ResourceHandler : URLStreamHandler() {
    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun openConnection(u: URL): URLConnection =
        this::class.java.classLoader.getResource(u.path).openConnection()
}
