package com.technosudo.httptransferer.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne

@Entity
class Settings(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @OneToOne
    val account: Account,

    fontSize: Int,
    preferredDirectory: String,

    lastDirectoryEnabled: Boolean,
    previewEnabled: Boolean,
    uploadEnabled: Boolean,
    keepLogged: Boolean
)