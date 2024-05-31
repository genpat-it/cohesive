FROM jenkins/jenkins:2.440.3-jdk11

COPY --chown=jenkins:jenkins plugins.txt /usr/share/jenkins/ref/plugins.txt

RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt && \
     echo 2.0 > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state

ENV JAVA_OPTS=-Djenkins.install.runSetupWizard=false
    
# COPY --chown=jenkins:jenkins executors.groovy /usr/share/jenkins/ref/init.groovy.d/executors.groovy




