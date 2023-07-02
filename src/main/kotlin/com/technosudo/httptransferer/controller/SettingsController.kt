package com.technosudo.httptransferer.controller

import com.technosudo.httptransferer.Settings
import com.technosudo.httptransferer.SettingsData
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/settings")
class SettingsController {

        @PostMapping
        fun saveSettings(
            @RequestBody settingsData: SettingsData
        ) {
            Settings.START_DIRECTORY = settingsData.startDirectory
        }
}