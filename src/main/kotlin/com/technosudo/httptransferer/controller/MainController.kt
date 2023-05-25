package com.technosudo.httptransferer.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainController {

    @GetMapping("/")
    fun getHomeView(): String {
        return "redirect:/files"
    }
}