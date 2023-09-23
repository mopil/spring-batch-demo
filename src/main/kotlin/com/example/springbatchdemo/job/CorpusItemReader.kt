package com.example.springbatchdemo.job

import com.example.springbatchdemo.dto.CorpusJsonDto
import com.example.springbatchdemo.dto.CorpusJsonRootDto
import com.example.springbatchdemo.util.CustomLogger.logger
import com.example.springbatchdemo.util.JsonFileReader
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.batch.item.ItemReader
import org.springframework.stereotype.Component
import java.io.File

@Component
class CorpusItemReader(
    private val objectMapper: ObjectMapper
) : ItemReader<List<CorpusJsonDto>> {
    val log = logger()
    override fun read(): List<CorpusJsonDto> {
        val files = try {
            File(JsonFileReader.JSON_FILE_PATH).listFiles()
        } catch (e: Exception) {
            log.warn("말뭉치 JSON 파일 읽기 실패 : ${e.message}")
            return emptyList()
        }
        log.info("말뭉치 SIZE : ${files.size} ########")
        return files.filter { it.name.endsWith(".json") }
            .map { it.readText() }
            .map { objectMapper.readValue(it, CorpusJsonRootDto::class.java) }
            .flatMap { it.rootShell.contents }
    }

}