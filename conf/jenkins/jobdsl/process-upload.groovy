pipelineJob("process-upload") {
    parameters {
        stringParam('TYPE', 'upload', '')
        stringParam('TEMPLATE_DEFINITION', '', '')
        stringParam('OUTPUT_FOLDER', '', '')
        stringParam('WATCHED_DIR', '', '')
    }
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
            scriptPath('process-import/Jenkinsfile')
        }
    }
}