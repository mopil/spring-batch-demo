package com.example.springbatchdemo.util

import com.example.springbatchdemo.dto.CorpusJsonDto
import com.example.springbatchdemo.dto.CorpusJsonRootDto
import com.example.springbatchdemo.util.CustomLogger.logger
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import java.io.File

@Component
class JsonFileReader(
    private val objectMapper: ObjectMapper
) {
    val log = logger()

    companion object {
        const val JSON_FILE_PATH = "C:\\dev_main\\corpus"
    }

    fun readCorpusJsonToObject(): List<CorpusJsonDto> {
        val files = try {
            File(JSON_FILE_PATH).listFiles()
        } catch (e: Exception) {
            log.warn("말뭉치 JSON 파일 읽기 실패 : ${e.message}")
            return emptyList()
        }
        return files.filter { it.name.endsWith(".json") }
            .map { it.readText() }
            .map { objectMapper.readValue(it, CorpusJsonRootDto::class.java) }
            .flatMap { it.rootShell.contents }
    }
}