package com.example.springbatchdemo.util

import com.example.springbatchdemo.job.NicknameLoadBatchJob
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@Component
class JobScheduler(
    private val jobLauncher: JobLauncher,
    private val job: NicknameLoadBatchJob,
) {
//    @Scheduled(initialDelay = 10000, fixedDelay = 30000)
    fun runJob() {
        val confMap: MutableMap<String, JobParameter> = HashMap()
        confMap["time"] = JobParameter(System.currentTimeMillis())
        val jobParameters = JobParameters(confMap)
        jobLauncher.run(job.nicknameLoadJob(), jobParameters)
    }
}