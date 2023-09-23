package com.example.springbatchdemo.model

import org.springframework.data.jpa.repository.JpaRepository

interface CorpusRepository : JpaRepository<Corpus, Long> {
}