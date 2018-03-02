# CROSSMINER Web-based Dashboard

The goal of this directory inside CROSSMINER project is to store all the activities related to **Task 7.4: Web-based dashboards** (`Web dashboards` from here).

The `Web dashboards` will present views of analysed projects that are:

* up to date
* high level
* quantitative panoramic

They will include specific metrics for tracking key performance aspects and will show details like who and how is contributing to a given project.

Dashboard panels will show summary, aggregated and evolutionary data, statistical analysis, faceted views, etc. for each data source.

The interface will allow to drill down into the data combining different filters (time ranges, projects, repositories, contributors).

The use cases that will be covered are:

* [Dependencies Dashboard](dependencies)
* API Changes Dashboard
* [GrimoireLab Dashboard with Sentiment Analysis included](sentiment)
* Messages Dashboard with Top Threads and Top Topics
* System Configuration Dashboard()
* [Data Extraction from Knowledge Base](ossmeter-metrics)
* Cross-project relationship dashboard
* Web Dashboard access from IDE plugin

## Install GrimoireLab Python Env

The data processing is done with GrimoireLab python platform.

A virtual env in Python is used to install the tools needed.

In Debian/Ubuntu you need to execute:

`sudo apt-get install python3-venv`

To create the python virtualenv and activate it:

```
mkdir ~/venvs
python3 -m venv ~/venvs/crossminer
source ~/venvs/crossminer/bin/activate
pip3 install grimoire-elk
```

## Install Elasticsearch and Kibana

An Elasticsearch and Kibana services are needed. docker-compose can be used to start them.

Elasticsearch needs this config:

`sudo sh -c "echo 262144 > /proc/sys/vm/max_map_count"`

The contents of the `docker-compose.yml` file to be used is:

```
elasticsearch:
  image: bitergia/elasticsearch:5.6.0
  command: /elasticsearch/bin/elasticsearch -E network.bind_host=0.0.0.0
  ports:
    - "9200:9200"
  environment:
    - ES_JAVA_OPTS=-Xms2g -Xmx2g

kibiter:
  image: bitergia/kibiter:5.6.0
  links:
    - elasticsearch
  ports:
   - "5601:5601"
  environment:
    - ELASTICSEARCH_USER=bitergia
    - ELASTICSEARCH_PASSWORD=bitergia
    - NODE_OPTIONS=--max-old-space-size=1000
```

To start both services:

`docker-compose -f docker-compose.yml up kibiter`

## Install OSSMeter services

In order to collect the metrics with OSSMeter the best approach is to
use the docker compose which starts the full platform:

`docker-compose -f web-dashboards/docker/ossmeter.yml up`

[Create a initial user](https://github.com/ossmeter/ossmeter/wiki/FAQ#adding-the-first-user-in-the-local-web-application) to access OSSMeter.

Add two projects using the web interface with the GitHub urls:

* https://github.com/grimoirelab/perceval
* https://github.com/grimoirelab/grimoire_elk

You will need around 4h to collect the initial version of all metrics.

### Init OSSMeter MongoDB with GrimoireLab data

To avoid the above process (4h), a dump of an all ready populated MongoDB database is included in:

`data/mongo-ossmeter-dump.tgz`

To use it:

```
docker-compose -f web-dashboards/docker/ossmeter.yml stop
sudo rm -rf ~/oss-data/*
tar xfz web-dashboards/data/mongo-ossmeter-dump.tgz
docker-compose -f web-dashboards/docker/ossmeter.yml up -d oss-db
mongorestore dump
rm -rf dump
```
