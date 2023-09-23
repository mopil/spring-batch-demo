package com.example.springbatchdemo.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CorpusJsonRootDto(
    @JsonProperty("SJML")
    val rootShell: CorpusJsonMiddleShellDto
)

data class CorpusJsonMiddleShellDto(
    @JsonProperty("text")
    val contents: List<CorpusJsonDto>
)
data class CorpusJsonDto(
    @JsonProperty("content")
    val content: String,
    @JsonProperty("write_date")
    val writeDate: String,
    @JsonProperty("url")
    val url: String,
    @JsonProperty("parent_url")
    val parentUrl: String,
    @JsonProperty("source_site")
    val sourceSite: String
)