package com.technosudo.httptransferer.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.ModelAndView

//@ControllerAdvice
//class GlobalExceptionHandler {
//
//    @ExceptionHandler(Exception::class)
//    fun handleException(ex: Exception, response: HttpServletResponse) {
//        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.message)
//    }
//}