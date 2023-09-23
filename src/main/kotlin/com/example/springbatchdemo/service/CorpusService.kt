package com.example.springbatchdemo.service

import com.example.springbatchdemo.model.CorpusRepository
import com.example.springbatchdemo.util.CustomLogger.logger
import com.example.springbatchdemo.util.JsonFileReader
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CorpusService(
    private val jdbcTemplate: JdbcTemplate,
    private val jsonFileReader: JsonFileReader,
    private val corpusRepository: CorpusRepository
) {
    val log = logger()

    companion object {
        const val FINISH_COUNT = 10_000_000
    }
    fun loadAll() {
        var repeatCount = 0
        while (corpusRepository.count() < FINISH_COUNT) {
            val currentCount = corpusRepository.count()
            log.info("현재 말뭉치 수 : $currentCount")
            load()
            log.info("말뭉치 로드 완료 :: 현재 반복 횟수 : ${++repeatCount}")
        }
    }

    @Transactional
    fun load() {
        val targets = jsonFileReader.readCorpusJsonToObject()
        log.info("말뭉치 JSON 객체 수 : ${targets.size}")
        val sql = """
                INSERT INTO corpus (write_date, content, url, parent_url, source_site)
                VALUES (?, ?, ?, ?, ?)
            """.trimIndent()
        jdbcTemplate.batchUpdate(
            sql, targets, targets.size
        ) { ps, target ->
            ps.setString(1, target.writeDate)
            ps.setString(2, target.content)
            ps.setString(3, target.url)
            ps.setString(4, target.parentUrl)
            ps.setString(5, target.sourceSite)
        }
    }
}