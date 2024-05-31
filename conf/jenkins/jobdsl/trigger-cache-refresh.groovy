pipelineJob("trigger-cache-refresh") {
    parameters {
        stringParam('ENDPOINT_PATH', '/views/refresh', '')
    }
    throttleConcurrentBuilds {
        maxTotal(1)
    }    
    triggers {
        cron("@midnight")
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
            scriptPath('trigger-webhook/Jenkinsfile')
        }
    }
}