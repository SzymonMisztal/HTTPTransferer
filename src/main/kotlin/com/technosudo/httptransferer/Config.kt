package com.technosudo.httptransferer

import java.io.File

object Config {

    var CONFIG_PATH = "/home/Johanes/MyApp/httpt.conf"

    var APP_ROOT_DIRECTORY = System.getProperty("user.home")
    var ADMIN_LOGIN = "admin"
    var ADMIN_PASSWORD = "password"

    init {
        if (File(CONFIG_PATH).exists())
            readConfig()
    }

    fun readConfig() {

        val configFile = File(CONFIG_PATH)
        if (!configFile.exists())
            throw IllegalArgumentException("Config file does not exist")

        configFile.forEachLine { line ->
            if (line.isNotBlank() && !line.startsWith("#")) {
                val keyValPair = line.split("=")
                if (keyValPair.size != 2) {
                    throw IllegalArgumentException("Invalid key value pair: $line")
                }
                val key = keyValPair[0].trim()
                val value = keyValPair[1].trim()

                when (key) {
                    "AppRootDirectory" ->
                        if (File(value).exists())
                            if (File(value).isDirectory)
                                APP_ROOT_DIRECTORY = value
                    "AdminLogin" ->
                        if (Regex("^[a-zA-Z][a-zA-Z0-9]{5,15}$").matches(value))
                            ADMIN_LOGIN = value
                    "AdminPassword" ->
                        if (Regex("^[a-zA-Z0-9@#\$%!^&+?=':;,*.\\-_]{8,255}\$").matches(value))
                            ADMIN_PASSWORD = value
                }
            }
        }
    }
}