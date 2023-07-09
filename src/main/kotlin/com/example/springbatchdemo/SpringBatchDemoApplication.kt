package com.example.springbatchdemo

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
class SpringBatchDemoApplication

fun main(args: Array<String>) {
	runApplication<SpringBatchDemoApplication>(*args)
}
