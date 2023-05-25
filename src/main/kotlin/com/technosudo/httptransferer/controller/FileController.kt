package com.technosudo.httptransferer.controller

import com.technosudo.httptransferer.data.FileData
import com.technosudo.httptransferer.service.FileService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.HandlerMapping
import org.springframework.web.servlet.resource.ResourceUrlProvider





@RestController
@RequestMapping("/files")
class FileController @Autowired constructor(
    val fileService: FileService
) {
    @GetMapping("/**")
    fun getFilesViewWithPath(
        request: HttpServletRequest
    ): ResponseEntity<List<FileData>> {
        val urlProvider = request.getAttribute(ResourceUrlProvider::class.java.canonicalName) as ResourceUrlProvider
        val path: String = urlProvider.getPathMatcher().extractPathWithinPattern(
            request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE) as String,
            request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE) as String
        )
        val files = fileService.getFiles("/home/Johanes/Pictures", path)
        return ResponseEntity(files, HttpStatus.OK)
    }
}