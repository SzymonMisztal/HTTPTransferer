package com.technosudo.httptransferer.repository

import com.technosudo.httptransferer.entity.Logs
import org.springframework.data.jpa.repository.JpaRepository

interface LogsRepository : JpaRepository<Logs, Int> {}