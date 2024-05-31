pipelineJob("post-processing") {
    parameters {
        stringParam('WATCHED_DIR', '', '')
        stringParam('JENKINS_BUILD_RESULT', '', '')
        stringParam('JENKINS_BUILD_URL', '', '')
        stringParam('NXF_SCRIPT', '', '')
        stringParam('NXF_ENTRYPOINT', '', '')
        stringParam('NXF_TRACEDIR', '', '')
        stringParam('NXF_OUTDIR', '', '')
        stringParam('TEMPLATE_DEFINITION', '', '')
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
            scriptPath('post-processing/Jenkinsfile')
        }
    }
}