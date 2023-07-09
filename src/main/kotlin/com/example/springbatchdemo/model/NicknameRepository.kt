package com.example.springbatchdemo.model

import org.springframework.data.jpa.repository.JpaRepository

interface NicknameRepository : JpaRepository<Nickname, Long> {
}