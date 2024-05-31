pipelineJob("process-nextflow") {
    parameters {
        stringParam('OUTPUT_FOLDER', '', '')
        stringParam('STEP', '', '')
        stringParam('MODULE', '', '')
        stringParam('METHOD', '', '')
        stringParam('WATCHED_DIR', '', '')
        stringParam('TEMPLATE_DEFINITION', '', '')
        stringParam('PRIORITY', '', '')        
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
            scriptPath('process-nextflow/Jenkinsfile')
        }
    }
}