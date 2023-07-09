package com.example.springbatchdemo.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object CustomLogger {
    inline fun <reified T> T.logger(): Logger = LoggerFactory.getLogger(T::class.java)
}