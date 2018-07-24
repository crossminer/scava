# Scava metrics Dashboard

The Scava metrics dashboard shows all metrics collected by Scava platform. It is in alpha status yet so expect changes.

Metrics can be filtered with:

* Project
* Metric class
* Metric name
* Metric id

## Public instance for Scava Web Dashboards

You can access an already deployed Scava Metrics Project dashboard at:

https://crossminer.biterg.io/app/kibana#/dashboard/ScavaProject

## Install

The first step is to create a [OSSMETER mongodb database with data from projects](/web-dashboards#init-ossmeter-mongodb-with-grimoirelab-data).

[Elasticsearch and Kibana will be needed also](/web-dashboards#install-elasticsearch-and-kibana).

With mongodb populated with metrics data let's process the metrics so they
can be used in Kibana.

The data processing is done inside [GrimoireLab python platform](/web-dashboards#install-grimoirelab-python-env).

### Importing the Scava metrics using the REST API

A perceval backend has been developed to import the Scava metrics to Elasticsearch.

First you need to install it inside the `python venv` created in [GrimoireLab python platform](/web-dashboards#install-grimoirelab-python-env).

```
git clone -b dev https://github.com/crossminer/scava.git
cd scava/web-dashboards/perceval-scava
pip install .
```

And now to load all metrics you need to execute in Elasticsearch:

```
cd scava/web-dashboards/scava-metrics
./scava2es.py -g -e https://admin:admin@localhost:9200 -i scava-metrics
```

### Importing the Scava project panel

The panel is loaded with:

`kidash.py -e http://bitergia:bitergia@localhost:9200 --import panels/scava-project.json`

The panel is ready at the URL:

http://localhost:5601/app/kibana#/dashboard/ScavaProject

![](screenshot.png?raw=true)


### Accessing a specific project only

The Scava Projects panel by default shows all Scava metrics for all projects. To show the metrics for a specific project
you can use to the URL:

http://localhost:5601/app/kibana#/dashboard/ScavaProject?_a=(query:(match:(project.keyword:(query:PROJECT_NAME))))

In it you must change PROJECT_NAME by the name of the project you want to show. For example, with the default dataset
for Web Dashboards `perceval` could be used as PROJECT_NAME.

As en example, to access the `perceval` project in the public Web Dashboard site:

https://crossminer.biterg.io/app/kibana#/dashboard/ScavaProject?_a=(query:(match:(project.keyword:(query:perceval))))

