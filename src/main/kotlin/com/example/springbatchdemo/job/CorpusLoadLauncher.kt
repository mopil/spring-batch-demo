package com.example.springbatchdemo.job

import com.example.springbatchdemo.service.CorpusService
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class CorpusLoadLauncher(
    private val corpusService: CorpusService
) {

    @PostConstruct
    fun init() {
        corpusService.loadAll()
    }
}