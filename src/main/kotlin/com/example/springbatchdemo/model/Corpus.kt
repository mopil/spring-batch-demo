package com.example.springbatchdemo.model

import javax.persistence.*


@Entity
class Corpus(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(length = 1000)
    val content: String,
    val writeDate: String,
    val url: String,
    val parentUrl: String,
    val sourceSite: String
)