package com.technosudo.httptransferer.controller

import com.technosudo.httptransferer.Config
import com.technosudo.httptransferer.Settings
import com.technosudo.httptransferer.data.FileData
import com.technosudo.httptransferer.service.FileService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.HandlerMapping
import org.springframework.web.servlet.resource.ResourceUrlProvider
import java.io.FileInputStream
import java.io.FileNotFoundException

@RestController
@RequestMapping("/download")
class DownloadController @Autowired constructor(
    val fileService: FileService
) {
    @GetMapping("/**")
    fun downloadFile(
        request: HttpServletRequest
    ): ResponseEntity<InputStreamResource> {
        val urlProvider = request.getAttribute(ResourceUrlProvider::class.java.canonicalName) as ResourceUrlProvider
        val path: String = urlProvider.getPathMatcher().extractPathWithinPattern(
            request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE) as String,
            request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE) as String
        )

        val fileName = "example.pdf"
        val filePath = fileService.pathMerger(Config.APP_ROOT_DIRECTORY, Settings.START_DIRECTORY, path) // Replace with the actual file path

        try {
            val file = FileInputStream(filePath)
            val resource = InputStreamResource(file)

            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"$fileName\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource)
        } catch (e: FileNotFoundException) {
            return ResponseEntity.notFound().build()
        }
    }
}