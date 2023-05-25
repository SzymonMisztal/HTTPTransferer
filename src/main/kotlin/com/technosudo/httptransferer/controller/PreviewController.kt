package com.technosudo.httptransferer.controller

import com.technosudo.httptransferer.service.FileService
import jakarta.servlet.http.HttpServletRequest
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.HandlerMapping
import org.springframework.web.servlet.resource.ResourceUrlProvider
import java.io.File
import java.io.Serializable

@RestController
@RequestMapping("/preview")
class PreviewController @Autowired constructor(
    val fileService: FileService
) {

    @GetMapping("/image/**")
    fun getImagePreviewWithPath(
        request: HttpServletRequest
    ): ResponseEntity<out Serializable> {

        val urlProvider = request.getAttribute(ResourceUrlProvider::class.java.canonicalName) as ResourceUrlProvider
        val path: String = urlProvider.getPathMatcher().extractPathWithinPattern(
            request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE) as String,
            request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE) as String
        )

        val imagePath = fileService.pathMerger("/home/Johanes/Pictures", path)
        val imageFile = File(imagePath)
        if (!imageFile.exists() || !imageFile.isFile()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Image not found for path: $path")
        }
        val imageBytes = imageFile.readBytes()

        val headers = HttpHeaders()
        headers.contentType = MediaType.IMAGE_JPEG
        headers.contentLength = imageBytes.size.toLong()

        return ResponseEntity(imageBytes, headers, HttpStatus.OK)
    }

    @GetMapping("/text/**")
    fun getTextPreviewWithPath(
        request: HttpServletRequest
    ): ResponseEntity<out Serializable> {

        val urlProvider = request.getAttribute(ResourceUrlProvider::class.java.canonicalName) as ResourceUrlProvider
        val path: String = urlProvider.getPathMatcher().extractPathWithinPattern(
            request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE) as String,
            request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE) as String
        )

        val fileContents = File(fileService.pathMerger("/home/Johanes/Pictures", path)).readBytes()
        val headers = HttpHeaders()
        headers.contentType= MediaType.TEXT_PLAIN

        return ResponseEntity(fileContents, headers, HttpStatus.OK)
    }

    @GetMapping("/video/**")
    fun getVideoPreviewWithPath(
        request: HttpServletRequest
    ): ResponseEntity<out Serializable> {

        val urlProvider = request.getAttribute(ResourceUrlProvider::class.java.canonicalName) as ResourceUrlProvider
        val path: String = urlProvider.getPathMatcher().extractPathWithinPattern(
            request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE) as String,
            request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE) as String
        )

        return ResponseEntity("text", HttpStatus.OK)
    }
}