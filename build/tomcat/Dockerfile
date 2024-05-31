FROM tomcat:9.0.64-jre17-temurin-focal
RUN ln -s /usr/local/tomcat /opt/cmdbuild && \
    ln -s /usr/local/tomcat/webapps.dist/manager /usr/local/tomcat/webapps/manager && \ 
    mkdir -p /usr/local/tomcat/logs && \
    mkdir -p /usr/local/tomcat/conf/cmdbuild && \
    chown -R 1000:1000 /usr/local/tomcat
COPY --chown=1000:1000 database.conf /usr/local/tomcat/conf/cmdbuild/
COPY --chown=1000:1000 context.xml /usr/local/tomcat/webapps/manager/META-INF/
COPY --chown=1000:1000 tomcat-users.xml /usr/local/tomcat/conf/
COPY --chown=1000:1000 server.xml /usr/local/tomcat/conf/
RUN addgroup --gid 1000 cmdbuild && useradd -m -d /home/cmdbuild -s /bin/bash -u 1000 -g cmdbuild cmdbuild
USER cmdbuild