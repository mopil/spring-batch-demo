package com.example.springbatchdemo.job

import com.example.springbatchdemo.model.Nickname
import com.example.springbatchdemo.model.NicknameRepository
import com.example.springbatchdemo.util.CustomLogger.logger
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@Component
@StepScope
class NicknameLoadTasklet(
    private val nicknameRepository: NicknameRepository,
) : Tasklet {
    val log = logger()

    @Transactional
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        val randomTimes = Random.nextInt(100, 500)
        val list = mutableListOf<Int>()
        repeat(randomTimes) {
            list.add(Random.nextInt(1000, 5000))
        }
        val results = list.map { Nickname(nickname = it.toString()) }
        log.info(" >>> [nickname auto load] SIZE : ${results.size} ########")
        nicknameRepository.saveAll(results)
        return RepeatStatus.FINISHED
    }
}