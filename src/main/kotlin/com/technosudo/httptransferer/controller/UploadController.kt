package com.technosudo.httptransferer.controller

import com.technosudo.httptransferer.Config
import com.technosudo.httptransferer.Settings
import com.technosudo.httptransferer.service.FileService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.HandlerMapping
import org.springframework.web.servlet.resource.ResourceUrlProvider
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.StandardCopyOption


@RestController
@RequestMapping("/upload")
class UploadController @Autowired constructor(
    val fileService: FileService
) {

    @PostMapping("/**")
    fun uploadFile(
        @RequestParam("file") data: MultipartFile,
        request: HttpServletRequest
    ): ResponseEntity<String> {

        val urlProvider = request.getAttribute(ResourceUrlProvider::class.java.canonicalName) as ResourceUrlProvider
        val path: String = urlProvider.getPathMatcher().extractPathWithinPattern(
            request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE) as String,
            request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE) as String
        )

        return if (!data.isEmpty) {
            return try {
                val directory = File(path)
                if (!directory.exists()) {
                    directory.mkdirs()
                }

                val path = fileService.pathMerger(Config.APP_ROOT_DIRECTORY, Settings.START_DIRECTORY, path)
                val file = File(path, data.originalFilename)
                data.transferTo(file)
                ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully")

            } catch (e: IOException) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving the file")
            }
        }
        else { ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file found") }
    }

}