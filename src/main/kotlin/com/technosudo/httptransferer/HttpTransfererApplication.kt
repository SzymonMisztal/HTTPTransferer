package com.technosudo.httptransferer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HttpTransfererApplication

fun main(args: Array<String>) {
    runApplication<HttpTransfererApplication>(*args)
}
