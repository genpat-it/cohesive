# Disclaimer

Please note that this version of the COHESIVE Information System (CIS) is currently under development. As such, it may be unstable and is subject to changes and improvements. We recommend using it for testing and development purposes only until a stable release is available.

# Table of Contents

- [Introduction](#introduction)
- [About](#about)
- [Installation](#installation)
- [License](#license)

# Introduction

Welcome to the COHESIVE Information System (CIS) Docker setup. This repository contains the necessary configurations and instructions to deploy the CIS using Docker Compose. The CIS is a pivotal component of the COHESIVE project, which aims to develop sustainable One Health approaches for the surveillance, signalling, assessing, and controlling of zoonoses at both national and regional levels within the EU and across borders.

# About

The COHESIVE project is dedicated to reducing the burden of zoonotic diseases through collaboration between the veterinary and human health domains. This initiative is crucial for managing emerging zoonoses, antimicrobial resistance (AMR), and food-borne zoonoses effectively. The project has been instrumental in designing and implementing integrated One Health risk-analysis structures, fostering cooperation between EU member states.

## Objectives of the COHESIVE Project

- **National One Health Approaches**: Strengthening human-veterinary collaboration by designing common procedures and tools tailored to the specific needs of individual EU countries.
- **EU Zoonoses Risk-Assessment Structure**: Creating a roadmap for a cohesive EU-wide risk-analysis structure, integrating early warning systems, and harmonizing tools for tracing, whole genome sequencing (WGS), and standardized risk assessments.
- **Common Open Source Platform**: Developing and testing an open-source platform for the collection and analysis of surveillance and outbreak data on zoonoses, ensuring interoperability with major data exchange systems at the EU level.
- **Capacity Building**: Enhancing capabilities at various levels within and between EU countries to manage zoonotic diseases effectively, through pilots and partnerships.

## COHESIVE Information System (CIS)

The CIS is a web-based interface and database designed to integrate pathogen information from both medical and veterinary sectors, facilitating outbreak investigation and surveillance. It is one of the four IT tools developed by the COHESIVE project to support outbreaks and risk-based surveillance. The other tools include:

- **Decision Support Tool**: A comprehensive guide for One-Health Risk Assessment.
- **FoodChain-Lab Web Application**: A tool for tracing food products during foodborne disease incidents.
- **Shiny Rrisk**: A web-based tool for risk assessment using Monte-Carlo Simulation.

By deploying CIS through Docker Compose, you can easily set up and manage this essential tool for One Health risk analysis, benefiting pandemic disease preparedness and improving the efficiency of zoonotic disease management.

# Installation

Here are the steps to install the package.

## Prerequisites

The following tools are required:

| Tool          | Description   | Version Used for Testing |
| ------------- | ------------- | ------------------------ |
| docker        | An open platform for developing, shipping, and running applications. Docker enables you to separate your applications from your infrastructure so you can deliver software quickly. | 24.0.4 |
| docker-compose| A tool for defining and managing multi-container Docker applications. It uses YAML files to configure the application's services and performs the creation and start-up process of all the containers with a single command. | 2.26.0 |
| jdk           | The Java Development Kit (JDK) is a software development environment used for developing Java applications. It includes the Java Runtime Environment (JRE), an interpreter/loader (java), a compiler (javac), an archiver (jar), a documentation generator (javadoc), and other tools needed in Java development. | 17.0.2 |
| md5sum        | A computer program that calculates and verifies 128-bit MD5 hashes. It's commonly used to verify the integrity of files. | 8.30 |
| rsync         | A fast, versatile, remote (and local) file-copying tool. It's used for copying and synchronizing files across systems. | 3.1.3 |
| nextflow      | A reactive workflow framework and programming DSL that eases writing computational pipelines with complex data. | 22.04.5.5708 |
| free          | A command-line utility that displays the total amount of free and used physical memory and swap space in the system, as well as the buffers and caches used by the kernel. | 3.3.15 |
| lscpu         | A command-line utility that displays information about the CPU architecture. | 2.32.1 |

*The current configuration of the Docker Compose process is not compatible with Windows operating systems.*

## Clone the project

To get started, you'll need to clone the project repository to your local machine. You can do this by executing the following command in your terminal:

```bash
git clone https://github.com/genpat-it/cohesive
```

## Usage

1. Ensure Docker ([link](https://docs.docker.com/get-docker/)) and Docker Compose ([link](https://docs.docker.com/compose/install/)) are installed on your machine.
2. Clone this repository and navigate to the project directory.
3. Configure the `.env` file with the appropriate values.
4. Run the following command to create the external volumes:

```bash
$ docker volume create cohesive-db
$ docker volume create cohesive-jenkins
```

5. generate a token:

```bash
$ uuidgen
8a8cd5a5-7983-4971-9766-7a5fa1deb00c
```
and update the placeholder WEBHOOK_TOKEN in the `.env` file

example:

```bash
WEBHOOK_TOKEN=8a8cd5a5-7983-4971-9766-7a5fa1deb00c
```

6. Change permissions of all files and folders inside the `data` folder.

```bash
$ chmod -R 777 data/*
```

7. Build the docker

```bash
$ docker-compose build
```

8. Run the `db` service first:

```bash
$ docker-compose up -d db
```

9. Run the other services:

```bash
$ docker-compose up -d
```

10. To check that all the services are running correctly, run:

```bash
docker-compose ps
```

This will display the status of all services, ensuring they are up and running. You should see output similar to this:

```sh
Name                       Command                  State           Ports         
--------------------------------------------------------------------------------
cohesive-ext-apache-1      httpd-foreground         Up              0.0.0.0:9005->80/tcp, :::9005->80/tcp
cohesive-ext-db-1          docker-entrypoint.sh     Up              0.0.0.0:9002->5432/tcp, :::9002->5432/tcp
cohesive-ext-jenkins-1     /usr/bin/tini -- /u...   Up              0.0.0.0:50000->50000/tcp, :::50000->50000/tcp, 0.0.0.0:9003->8080/tcp, :::9003->8080/tcp
cohesive-ext-webserver-1   catalina.sh run          Up              0.0.0.0:9001->8080/tcp, :::9001->8080/tcp
```

11. To inspect the logs of a specific service, use the following command:

```bash
$ docker-compose logs [service] -f
```

12. Download `webapps`

```bash
$ wget --no-parent -r https://bioinfoweb.izs.it/bioinfonas/download/public/cohesive/webapps/ --no-check-certificate -nd -P data/webapps/ -R 'index.html' -q
```

13. Download databases

```bash
$ wget --no-parent -r https://bioinfoweb.izs.it/bioinfonas/download/public/cohesive/db/ --no-check-certificate -nd -P data/db/ -R 'index.html' -q
```

Decompress each `.tar` and `.tgz` file, ensuring that the contents of each file are placed in a corresponding folder that shares the same name as the file.

14. Download tools

```bash
wget --no-parent -r https://bioinfoweb.izs.it/bioinfonas/public/cohesive/tools/ --no-check-certificate -nd -P data/tools/ -R 'index.html*' -q
```

15. Configure server-url for download

In the `webapps\bitw2.yml` file, set the `server-url` property to a public hostname that is accessible to users.
This configuration enables users to access downloads.

16. Configure jenkins nodes
    
Entering on Jenkins at [http://localhost:9003] using the credentials admin/admin you can click on the two nodes on the left to get the command lines to activate the nodes.

```bash
echo c341c4cbbaa6565eaf1a844e8ee46c35780a1f22d7457af58af352458b5c987e > secret-file
curl -sO http://localhost:9003/jnlpJars/agent.jar
java -jar agent.jar -url http://localhost:9003/ -secret @secret-file -name "node-controller" -workDir "/data/jenkins_node"
```

```bash
echo c341c4cbbaa6565eaf1a844e8ee46c35780a1f22d7457af58af352458b5c987e > secret-file
curl -sO http://localhost:9003/jnlpJars/agent.jar
java -jar agent.jar -url http://localhost:9003/ -secret @secret-file -name "node-controller" -workDir "/data/jenkins_node"
```

17. Share folders

It's important to note that the folders:
`work, tools, samples,  db, job_queue`

should be shared between jenkins node.

One trick might be to create symbolic links such as:

```bash
ln -s $(pwd)/data /data
```


## Services

- The `db` service initializes a PostgreSQL database.
- The `webserver` service runs a Tomcat web server and depends on the `db` service.
- The `jenkins` service provides a Jenkins CI server with custom configuration.
- The `apache` service serves static content via an Apache HTTP server.
- The services are configured to restart unless stopped manually.
- Memory limits are set for each service to ensure resource constraints are managed effectively.

For further customization, modify the `docker-compose.yml` file and adjust the environment variables as needed.

| SERVICE  | PORT | CREDENTIALS       | URL                                 |
|----------|------|-------------------|-------------------------------------|
| TOMCAT   | 9001 | admin/admin       | [http://localhost:9001](http://localhost:9001) |
| POSTGRES | 9002 | postgres/postgres | N/A                                 |
| APACHE   | 9005 | N/A               | [http://localhost:9005](http://localhost:9005) |
| JENKINS  | 9003 | admin/admin       | [http://localhost:9003](http://localhost:9003) |

**Note**: Replace `localhost` with the appropriate hostname or IP address if you are accessing the services from a different network.

To check the status of all the services, you can use the `docker-compose ps` command in the terminal. This command will list all the running services along with their current state. If you want to check the logs of a specific service, you can use the `docker-compose logs <service_name>` command. Remember to replace `<service_name>` with the name of the service you want to check.

For example, to check the logs of the `db` service, you can use the following command:

```bash
$ docker-compose logs db
```

This will display the logs for the `db` service, which can be useful for debugging or monitoring the service's activity.

By following these steps, you ensure that all services are properly started, running as expected, and accessible via the provided URLs.

## .env File

This `.env` file is a configuration file that sets up environment variables to configure various services and their settings.

- **Postgres**: The `POSTGRES_VOLUME` variable is used to specify the volume for the Postgres database. 

- **Apache**: The Apache web server is configured with the `APACHE_PORT` variable, which sets the port number. The `APACHE_LOG_FOLDER` variable is used to specify the location of the log files, and `APACHE_MEMORY` is used to allocate memory to Apache.

- **Git**: The `GIT_USERNAME` and `GIT_PASSWORD` variables are used to configure the credentials for the Git repository.

- **Jenkins**: Jenkins is a continuous integration server. The `JENKINS_PORT` and `JENKINS_AGENT_PORT` variables are used to set the ports for Jenkins and its agent. The `JENKINS_MEMORY` variable is used to allocate memory to Jenkins. The `JENKINS_JOB_PROJECT` and `JENKINS_JOB_REF` variables are used to specify the Jenkins job project and reference. The `JENKINS_ADMIN_USERNAME` and `JENKINS_ADMIN_PASSWORD` variables are used to set the admin credentials for Jenkins. The `JENKINS_VOLUME` variable is used to specify the volume for Jenkins.

## docker-compose.yml File

The `docker-compose.yml` file is used to define and manage multi-container Docker applications and reads the .env file to set environment variables that configure the services. These variables are used to set parameters such as ports, memory allocation, and volume paths for the services.

Here's a breakdown of the services defined in this file:

1. **db**: This service uses the `postgres:12.11` image and sets memory limits based on the `POSTGRES_MEMORY` environment variable. It also sets several environment variables for the Postgres database, including the user, password, and database name. The `cohesive-db` volume is used for persistent storage of the database data.

2. **webserver**: This service builds a Docker image from the Dockerfile located in `./build/tomcat`. It sets memory limits based on the `TOMCAT_MEMORY` environment variable and mounts several volumes for various data and configuration files. The service is exposed on the port specified by the `TOMCAT_PORT` environment variable and depends on the `db` service.

3. **jenkins**: This service builds a Docker image from the Dockerfile located in `./build/jenkins`. It sets memory limits based on the `JENKINS_MEMORY` environment variable and uses the `.env` file for additional environment variables. The service is exposed on two ports specified by the `JENKINS_PORT` and `JENKINS_AGENT_PORT` environment variables. The `cohesive-jenkins` volume is used for persistent storage of Jenkins data.

4. **apache**: This service uses the `httpd:2.4` image and sets memory limits based on the `APACHE_MEMORY` environment variable. It mounts several volumes for various data and configuration files, including a custom `httpd.conf` file. The service is exposed on the port specified by the `APACHE_PORT` environment variable.

The `volumes` section at the end of the file defines two named volumes, `cohesive-db` and `cohesive-jenkins`, which are used by the `db` and `jenkins` services respectively. The `external: true` option indicates that these volumes are managed outside of Docker Compose.

### Volumes in Docker Compose

In Docker, volumes are used to persist data generated by and used by Docker containers. Volumes are managed by Docker and are stored in a part of the host filesystem managed by Docker (typically `/var/lib/docker/volumes/...` on Linux). They are essential for persisting data across container restarts and for sharing files between the host and container.

Here’s a detailed breakdown of the volumes specified in the `docker-compose.yml` file, with a refactored explanation:

- `${SAMPLES_FOLDER}:/data/samples`: This volume mounts the host directory specified by the `SAMPLES_FOLDER` environment variable to `/data/samples` in the container. This allows any changes made to `/data/samples` in the container to be reflected in `${SAMPLES_FOLDER}` on the host, and vice versa. **SAMPLES_FOLDER** contains the sample data.

- `${JOB_QUEUE_FOLDER}:/data/job_queue`: This mounts the directory specified by `JOB_QUEUE_FOLDER` to `/data/job_queue` in the container. **JOB_QUEUE_FOLDER** is used by Jenkins.

- `${WORK_FOLDER}:/data/work`: This mounts the directory specified by `WORK_FOLDER` to `/data/work` in the container. **WORK_FOLDER** is used during jobs launched by Jenkins and serves as the input/output directory for Jenkins and the COHESIVE information system.

- `${TOOLS_FOLDER}/:/data/tools`: This mounts the directory specified by `TOOLS_FOLDER` to `/data/tools` in the container. **TOOLS_FOLDER** contains the bioinformatics tools.

- `${DOWNLOAD_FOLDER}:/data/downloads`: This mounts the directory specified by `DOWNLOAD_FOLDER` to `/data/downloads` in the container. **DOWNLOAD_FOLDER** contains the results of the jobs.

- `${WEBAPPS_FOLDER}:/usr/local/tomcat/webapps`: This mounts the directory specified by `WEBAPPS_FOLDER` to `/usr/local/tomcat/webapps` in the container. **WEBAPPS_FOLDER** contains the WAR files of the web applications.

- `${TOMCAT_LOG_FOLDER}:/opt/cmdbuild/logs`: This mounts the directory specified by `TOMCAT_LOG_FOLDER` to `/opt/cmdbuild/logs` in the container. **TOMCAT_LOG_FOLDER** contains the Tomcat logs.

- `${CONF_FOLDER}/webapps:/conf`: This mounts the directory specified by `CONF_FOLDER` to `/conf` in the container. **CONF_FOLDER** contains the configuration files.

### Understanding Docker Volumes and `external: true`

When we use `external: true` in the `docker-compose.yml` file, it means that the volumes are created and managed outside the scope of the Docker Compose file. These volumes need to exist before you run `docker-compose up`, and they are not automatically created by Docker Compose.

## Configure Jenkins

### Start the process_queue job

On the first startup of Jenkins, you need to activate the `process_queue` job.

The `process_queue` job periodically scans the `job_queue` folder. Its role is to identify and initiate processing for any samples that are described by JSON files within this folder.

To do this, go to `http://localhost:9003` (assuming the port is left as default). Log in using the credentials `admin/admin` and start the `process_queue` job.

### Configure nodes

Configure at least two nodes. The first node is the `worker` node and the second one the `controller` node.


## Cohesive

### How to upload samples

[Link](https://cohesive.izs.it/wiki/user/Upload/Introduction.html)


### How to add analyses






## Configure Localizations


TODO: spiegare come importare i paesi (T_WHERE)

Al primo avvio su jenkins bisogna attivare il job process_queue
