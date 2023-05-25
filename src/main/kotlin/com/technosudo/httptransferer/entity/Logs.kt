package com.technosudo.httptransferer.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.springframework.format.annotation.DateTimeFormat
import java.sql.Timestamp

@Entity
class Logs(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @ManyToOne
    val account: Account,

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    val logTimestamp: Timestamp,
    val operation: Int,
    val message: String
)