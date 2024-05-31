# Disclaimer

Please note that this version of the COHESIVE Information System (CIS) is currently under development. As such, it may be unstable and is subject to changes and improvements. We recommend using it for testing and development purposes only until a stable release is available.

# Table of Contents
- [Disclaimer](#disclaimer)
- [Table of Contents](#table-of-contents)
- [Introduction](#introduction)
  - [Objectives of the COHESIVE Project](#objectives-of-the-cohesive-project)
  - [COHESIVE Information System (CIS)](#cohesive-information-system-cis)
- [Installation](#installation)
  - [Prerequisites](#prerequisites)
  - [Clone the project](#clone-the-project)
  - [Configuration](#configuration)
  - [Services](#services)
- [Security Considerations](#security-considerations)
  - [Cohesive Usage](#cohesive-usage)
    - [How to upload samples](#how-to-upload-samples)
    - [How to add analyses](#how-to-add-analyses)
    - [How to add sampling points](#how-to-add-sampling-points)
- [How to contribute](#how-to-contribute)
- [Credits](#credits)
- [Contact](#contact)

# Introduction

Welcome to the COHESIVE Information System (CIS) Docker setup. This repository contains the necessary configurations and instructions to deploy the CIS using Docker Compose. The CIS is a pivotal component of the COHESIVE project, which aims to develop sustainable One Health approaches for the surveillance, signalling, assessing, and controlling of zoonoses at both national and regional levels within the EU and across borders.

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

A complete installation requires 400 Gigabytes of storage space.

The following tools are required:

| Tool          | Description   | Version Used for Testing |
| ------------- | ------------- | ------------------------ |
| docker        | An open platform for developing, shipping, and running applications. Docker enables you to separate your applications from your infrastructure so you can deliver software quickly (Note: Newer versions come bundled with the Docker Compose tool.). | 20.10.18, build b40c2f6 |
| jdk           | The Java Development Kit (JDK) is a software development environment used for developing Java applications. It includes the Java Runtime Environment (JRE), an interpreter/loader (java), a compiler (javac), an archiver (jar), a documentation generator (javadoc), and other tools needed in Java development. | 1-0.23 2024-04-16 |
| md5sum        | A computer program that calculates and verifies 128-bit MD5 hashes. It's commonly used to verify the integrity of files. | 8.30 |
| rsync         | A fast, versatile, remote (and local) file-copying tool. It's used for copying and synchronizing files across systems. | 3.-3 |
| nextflow      | A reactive workflow framework and programming DSL that eases writing computational pipelines with complex data. | 20.10.0.5430 |
| free          | A command-line utility that displays the total amount of free and used physical memory and swap space in the system, as well as the buffers and caches used by the kernel. | 3.3.15 |
| lscpu         | A command-line utility that displays information about the CPU architecture. | 2.32.1 |

*The current configuration of the Docker Compose process is not compatible with Windows operating systems.*

## Clone the project

To get started, you'll need to clone the project repository to your local machine. You can do this by executing the following command in your terminal:

```bash
$ git clone https://github.com/genpat-it/cohesive
$ cd cohesive
$ export COHESIVE_FOLDER=$(pwd)
```

## Configuration

- **Configure `.env` file**

One of the values you need to set is `WEBHOOK_TOKEN`. This should be a unique and robust value. 

You can generate a robust token using the `uuidgen` tool. Here's how to do it:

```bash
$ uuidgen
```

This command will output a UUID, like this:

```bash
56e0740a-ea76-4440-8329-b1fb8daefe17
```

You can then use this UUID as your `WEBHOOK_TOKEN`.
Open `.env` file and update the placeholder.

```bash
WEBHOOK_TOKEN=56e0740a-ea76-4440-8329-b1fb8daefe17
```

Configure the appropriate timezone modifying the `TIMEZONE` placeholder.

Example:

```bash
TIMEZONE=Europe/Lisbon
```

**While it's possible to modify other variables, it's recommended not to do so on your first attempt.**

- **Set up `jdk11` environment**

```bash
wget https://builds.openlogic.com/downloadJDK/openlogic-openjdk/1-0.23+9/openlogic-openjdk-1-0.23+9-linux-x64.tar.gz
sudo tar -xvzf openlogic-openjdk-1-0.23+9-linux-x64.tar.gz -C /opt
sudo mv /opt/openlogic-openjdk-1-0.23+9-linux-x64/ /opt/jdk11
echo "export JAVA_HOME=/opt/jdk11" >> ~/.bashrc
echo "export PATH=\$JAVA_HOME/bin:\$PATH" >> ~/.bashrc
source ~/.bashrc
```

- **Create external Docker volumes**

These volumes are defined by the `POSTGRES_VOLUME` and `JENKINS_VOLUME` variables in the `.env` file. 

To create these volumes, run the following commands:

```bash
docker volume create cohesive-db
docker volume create cohesive-jenkins
```

You can then verify that the volumes were created successfully by listing all Docker volumes and filtering for the ones you just created:

```bash
docker volume ls | grep cohesive
```

This command should output the names of the two volumes you just created.

- **Modify the permissions for all files and directories within the `data` directory.**

This step ensures that all files and directories within `data` are readable, writable, and executable by all users. 

Execute the following command in your terminal:

```bash
$ chmod -R 777 data/*
```

- **Build the Docker images**

This step involves using Docker Compose to build your Docker images. Docker Compose reads the `docker-compose.yml` file in the current directory and uses it to build the images.

Run the following command in your terminal:

```bash
$ docker compose build
```

- **Start the `db` service**

Run the following command in your terminal:

```bash
$ docker compose up -d db && docker compose logs -f db
```

- **Start the remaining services**

```bash
$ docker compose up -d
```

To check that all the services are running correctly, run:

```bash
docker-compose ps
```

This will display the status of all services, ensuring they are up and running. You should see output similar to this:

```bash
CONTAINER ID   IMAGE                       COMMAND                  CREATED          STATUS         PORTS                                                                                      NAMES
7bb443289c48   docker-services-webserver   "catalina.sh run"        10 seconds ago   Up 8 seconds   0.0.0.0:9001->8080/tcp, :::9001->8080/tcp                                                  docker-services-webserver-1
2476c8594e4a   postgres:12.11              "docker-entrypoint.s…"   10 seconds ago   Up 9 seconds   0.0.0.0:9002->5432/tcp, :::9002->5432/tcp                                                  docker-services-db-1
97ad1ac9cda3   docker-services-jenkins     "/usr/bin/tini -- /u…"   10 seconds ago   Up 9 seconds   0.0.0.0:50000->50000/tcp, :::50000->50000/tcp, 0.0.0.0:9003->8080/tcp, :::9003->8080/tcp   docker-services-jenkins-1
255fb29a759d   httpd:2.4                   "httpd-foreground"       10 seconds ago   Up 9 seconds   0.0.0.0:9005->80/tcp, :::9005->80/tcp                                                      docker-services-apache-1
```

- **Download `webapps`, `db` and `tools`**

Please, require a temporary account (`TEMP_USERNAME` and `TEMP_USERNAME`) to bioinformatica@izs.it  
Then you can launch:

```bash
$ wget --no-parent -r https://TEMP_USERNAME:TEMP_USERNAME@bioinfoweb.izs.it/bioinfonas/public/cohesive/webapps/ --no-check-certificate -nd -P data/webapps/ -R 'index.html*' -q
$ wget --no-parent -r https://TEMP_USERNAME:TEMP_USERNAME@bioinfoweb.izs.it/bioinfonas/public/cohesive/db/ --no-check-certificate -nd -P data/db/ -R 'index.html*' -q
$ wget --no-parent -r https://TEMP_USERNAME:TEMP_USERNAME@bioinfoweb.izs.it/bioinfonas/public/cohesive/tools/ --no-check-certificate -nd -P data/tools/ -R 'index.html*' -q
```

- **Extract `db` packages**
The script `extract_and_cleanup.sh` extracts the packages and then deletes the compressed files.

```bash
$ chmod +x /data/db/extract_and_cleanup.sh 
$ ./data/db/extract_and_cleanup.sh 
```

- **Configure server-url for download**

In the `webapps\bitw2.yml` file, modify the `server-url` property to a public hostname/ip that users can access. 
This adjustment allows users to access downloads.

Initially, we set it to localhost for testing purposes.

- **Configure jenkins nodes**

Access Jenkins by navigating to `http://your_machine_ip:9003`. Log in using the credentials `admin/admin`. Once logged in, click on the two nodes on the left to retrieve the command lines needed to activate the nodes.

In the final section of the left column, you'll find two nodes that need to be activated:
* `node-controller`
* `node-worker`

Select each node individually, and copy the provided command lines to activate them.
Replace the correct secret.

```bash
$ cd /tmp
$ curl -sO http://localhost:9003/jnlpJars/agent.jar
$ screen -S jworker -d -m java -jar agent.jar -url http://localhost:9003/ -secret {secret} -name "node-worker" -workDir "/tmp"
$ screen -S jcontroller -d -m java -jar agent.jar -url http://localhost:9003/ -secret {secret} -name "node-controller" -workDir "/tmp"
```

After running these commands, make sure to navigate back to the cloned project directory.

```bash
$ cd $COHESIVE_FOLDER
```

If everything has been set up correctly, you should now see the node connected in Jenkins.

- **Sharing Subfolders within `data`**

Please ensure that the following subfolders within the `data` directory:
`work`, `tools`, `samples`, `db`, `job_queue`
are shared across the Jenkins node.

A useful technique could be to create symbolic links. For example:

```bash
sudo ln -s $(pwd)/data /
```

- **Start the `process-queue` Job in Jenkins**

On the Jenkins main dashboard, find the `process-queue` job. Click the play icon in its row to initiate the job.

## Services

- The `db` service initializes a PostgreSQL database.
- The `webserver` service runs a Tomcat web server and depends on the `db` service.
- The `jenkins` service provides a Jenkins CI server with custom configuration.
- The `apache` service serves static content via an Apache HTTP server.
- The services are configured to restart unless stopped manually.
- Memory limits are set for each service to ensure resource constraints are managed effectively.

For further customization, modify the `docker-compose.yml` file and adjust the environment variables as needed.

| SERVICE  | PORT | CREDENTIALS       | URL (Replace `your_machine_ip` with the IP of the machine where the services are running) |
|----------|------|-------------------|----------------------------------------------------|
| TOMCAT   | 9001 | admin/admin       | [http://your_machine_ip:9001](http://your_machine_ip:9001) |
| POSTGRES | 9002 | postgres/postgres | N/A                                                |
| APACHE   | 9005 | N/A               | [http://your_machine_ip:9005](http://your_machine_ip:9005) |
| JENKINS  | 9003 | admin/admin       | [http://your_machine_ip:9003](http://your_machine_ip:9003) |

**Note**: Replace `localhost` with the appropriate hostname or IP address if you are accessing the services from a different network.

To check the status of all the services, you can use the `docker-compose ps` command in the terminal. This command will list all the running services along with their current state. If you want to check the logs of a specific service, you can use the `docker-compose logs <service_name>` command. Remember to replace `<service_name>` with the name of the service you want to check.

For example, to check the logs of the `db` service, you can use the following command:

```bash
$ docker-compose logs db
```

This will display the logs for the `db` service, which can be useful for debugging or monitoring the service's activity.

By following these steps, you ensure that all services are properly started, running as expected, and accessible via the provided URLs.

# Security Considerations

For production use, it's crucial to replace all passwords found in the [.env](.env), [docker-compose.yml](docker-compose.yml), [bitw2.yml](conf/webapps/bitw2.yml) , and [config.yaml](conf/jenkins/config.yaml) files.

Ensure that you use a strong `WEBHOOK_TOKEN` in the [.env](.env) file.

Also, replace the `server-url` placeholder in the [bitw2.yml](conf/webapps/bitw2.yml) file with an `https` URL.

## Cohesive Usage

You can find additional information in our user wiki, accessible at the following URL:
[https://cohesive.izs.it/wiki/user/](https://cohesive.izs.it/wiki/user/)

### How to upload samples

[Link](https://cohesive.izs.it/wiki/user/Upload/Introduction.html)

### How to add analyses

(todo)

### How to add sampling points

(todo)


# How to contribute

We welcome and appreciate all contributions. You can help by testing the software, providing feedback, or suggesting improvements to the documentation.

# Credits

This package is developed by GENPAT (the Italian National Reference Centre for Whole Genome Sequencing of Microbial Pathogens) and incorporates open-source software from third parties.

# Contact

For any inquiries or support, feel free to reach out to us at [genpat@izs.it](mailto:genpat@izs.it).
