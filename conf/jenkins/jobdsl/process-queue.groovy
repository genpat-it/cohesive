pipelineJob("process-queue") {
    throttleConcurrentBuilds {
        maxTotal(1)
    }
    definition {
        cpsScm {
            lightweight(false)
            scm {
                git {
                    remote {
                        credentials("git_repo")
                        url(JENKINS_JOB_PROJECT)
                    }
                    branch(JENKINS_JOB_REF)
                }
            }
            scriptPath('process-queue/Jenkinsfile')
        }
    }
}