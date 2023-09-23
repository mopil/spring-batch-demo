package com.example.springbatchdemo.job

import com.example.springbatchdemo.dto.CorpusJsonDto
import com.example.springbatchdemo.dto.CorpusJsonRootDto
import com.example.springbatchdemo.util.JsonFileReader
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.batch.item.json.JacksonJsonObjectReader
import org.springframework.batch.item.json.JsonItemReader
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.File
import java.io.Serializable
import javax.sql.DataSource


@Configuration
class CorpusLoadBatchJob(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val corpusItemReader: CorpusItemReader,
    private val dataSource: DataSource,
    private val corpusLoadTasklet: CorpusLoadTasklet
) {
    @Bean
    fun corpusLoadJob() = jobBuilderFactory.get("corpusLoadJob")
        .incrementer(RunIdIncrementer())
        .start(corpusLoadStep())
        .build()

    @Bean
    fun corpusLoadStep() = stepBuilderFactory.get("corpusLoadStep")
        .tasklet(corpusLoadTasklet)
        .build()

//    @Bean
//    fun corpusLoadStep() = stepBuilderFactory.get("corpusLoadStep")
//        .chunk<List<CorpusJsonDto>, List<CorpusJsonDto>>(500_000)
//        .reader(corpusItemReader)
//        .writer(corpusItemWriter())
//        .build()
//
//
//    @Bean
//    fun corpusItemWriter(): JdbcBatchItemWriter<List<CorpusJsonDto>> {
//        return JdbcBatchItemWriterBuilder<List<CorpusJsonDto>>()
//            .dataSource(dataSource)
//            .beanMapped()
//            .sql("""
//                INSERT INTO corpus (write_date, content, url, parent_url, source_site)
//                VALUES (:write_date, :content, :url, :parent_url, :source_site)
//            """.trimIndent())
//            .build()
//    }
}

