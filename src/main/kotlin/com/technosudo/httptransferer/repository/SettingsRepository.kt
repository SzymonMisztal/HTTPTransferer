package com.technosudo.httptransferer.repository

import com.technosudo.httptransferer.entity.Settings
import org.springframework.data.jpa.repository.JpaRepository

interface SettingsRepository : JpaRepository<Settings, Int> {}