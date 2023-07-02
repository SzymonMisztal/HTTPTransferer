package com.technosudo.httptransferer.controller

import com.technosudo.httptransferer.Config
import com.technosudo.httptransferer.Settings
import com.technosudo.httptransferer.service.FileService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.HandlerMapping
import org.springframework.web.servlet.resource.ResourceUrlProvider
import java.io.File
import java.util.regex.Pattern

@RestController
@RequestMapping("/video")
class VideoController @Autowired constructor(
    val fileService: FileService
)  {

    @GetMapping("/**")
    fun streamVideo(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {

        val urlProvider = request.getAttribute(ResourceUrlProvider::class.java.canonicalName) as ResourceUrlProvider
        val path: String = urlProvider.getPathMatcher().extractPathWithinPattern(
            request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE) as String,
            request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE) as String
        )

        val file = File(fileService.pathMerger(Config.APP_ROOT_DIRECTORY, Settings.START_DIRECTORY, path))
        val length = file.length()
        val rangeHeader = request.getHeader("Range")

        if (rangeHeader == null) {
            // No range header, so send the entire video
            response.contentType = "video/mp4"
            response.setHeader("Content-Length", length.toString())
            val inputStream = file.inputStream()
            inputStream.use { input ->
                val outputStream = response.outputStream
                outputStream.use { output ->
                    val buffer = ByteArray(1024)
                    var bytesRead = input.read(buffer)
                    while (bytesRead != -1) {
                        output.write(buffer, 0, bytesRead)
                        bytesRead = input.read(buffer)
                    }
                    output.flush()
                }
            }
        } else {
            // Parse the range header to determine the range of bytes to send
            val matcher = Pattern.compile("bytes=(\\d+)-(\\d*)").matcher(rangeHeader)
            matcher.matches()
            val start = matcher.group(1).toLong()
            var end = if (matcher.group(2) == "") length - 1 else matcher.group(2).toLong()

            if (end >= length) {
                end = length - 1
            }

            // Calculate the content length and range for the response
            val contentLength = end - start + 1
            val contentRange = "bytes $start-$end/$length"

            // Send the requested range of bytes
            response.contentType = "video/mp4"
            response.setHeader("Content-Range", contentRange)
            response.setHeader("Content-Length", contentLength.toString())
            response.status = HttpServletResponse.SC_PARTIAL_CONTENT

            val inputStream = file.inputStream()
            inputStream.use { input ->
                val outputStream = response.outputStream
                outputStream.use { output ->
                    val buffer = ByteArray(1024)
                    input.skip(start)
                    var bytesRead = input.read(buffer)
                    var bytesSent = 0L
                    while (bytesRead != -1 && bytesSent <= contentLength) {
                        output.write(buffer, 0, bytesRead)
                        bytesRead = input.read(buffer)
                        bytesSent += bytesRead
                    }
                    output.flush()
                }
            }
        }
    }

}