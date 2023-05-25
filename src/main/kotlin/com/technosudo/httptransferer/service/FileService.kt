package com.technosudo.httptransferer.service

import com.technosudo.httptransferer.data.FileData
import com.technosudo.httptransferer.data.FileType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File

@Service
class FileService @Autowired constructor(
    val accountService: AccountService
) {

    fun getFiles(generalPath: String, specificPath: String): List<FileData> {

        val totalPath = pathMerger(generalPath, specificPath)
        val directory = File(totalPath)

        if (directory.isDirectory) {
            val files = directory.listFiles()
            val directories = mutableListOf<FileData>()
            val regularFiles = mutableListOf<FileData>()

            var i = 0

            files!!.forEach { file ->
                if (file.isDirectory) {
                    directories.add(FileData(
                        i,
                        file.name,
                        pathMerger(specificPath, file.name),
                        FileType.DIRECTORY
                    ))
                } else if (file.isFile) {
                    regularFiles.add(FileData(
                        i,
                        file.name,
                        pathMerger(specificPath, file.name),
                        if (file.name.endsWith(".png")) FileType.PNG
                        else if (file.name.endsWith(".jpg")) FileType.JPG
                        else if (file.name.endsWith(".jpeg")) FileType.JPEG
                        else FileType.FILE
                    ))
                }
                i++
            }

            directories.sortBy{ it.name }
            regularFiles.sortBy{ it.name }

            val sortedFiles = mutableListOf<FileData>()
            sortedFiles.addAll(directories)
            sortedFiles.addAll(regularFiles)

            return sortedFiles
        }
        return listOf()
    }

    public fun pathMerger(vararg paths: String): String {

        var total = ""

        for (path in paths) {
            if (path.isEmpty())
                continue

            if (total.endsWith("/") xor path.startsWith("/"))
                total += path
            else if (total.endsWith("/") and path.startsWith("/"))
                total += path.substring(1)
            else
                total += "/$path"
        }

        return total
    }
}