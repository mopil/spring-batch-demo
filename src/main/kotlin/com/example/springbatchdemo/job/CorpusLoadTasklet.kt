package com.example.springbatchdemo.job

import com.example.springbatchdemo.model.CorpusRepository
import com.example.springbatchdemo.model.Nickname
import com.example.springbatchdemo.util.CustomLogger.logger
import com.example.springbatchdemo.util.JsonFileReader
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@Component
@StepScope
class CorpusLoadTasklet(
    private val jsonFileReader: JsonFileReader,
    private val jdbcTemplate: JdbcTemplate
) : Tasklet {
    val log = logger()

    @Transactional
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
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
        return RepeatStatus.FINISHED
    }
}