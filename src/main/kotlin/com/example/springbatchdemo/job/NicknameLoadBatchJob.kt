package com.example.springbatchdemo.job

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class NicknameLoadBatchJob(
    private val nicknameLoadTasklet: NicknameLoadTasklet,
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {

    @Bean
    fun nicknameLoadJob(): Job = jobBuilderFactory.get("nicknameLoadJob")
        .incrementer(RunIdIncrementer())
        .start(nicknameLoadStep())
        .build()

    @Bean
    fun nicknameLoadStep(): Step = stepBuilderFactory.get("nicknameLoadStep")
        .tasklet(nicknameLoadTasklet)
        .build()
}