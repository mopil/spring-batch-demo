package com.example.springbatchdemo.model


import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Nickname(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val nickname: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)