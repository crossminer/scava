# OSSMeter metrics Dashboard

The OSSMeter metrics dashboard shows all metrics collected by OSSMeter platform of the type `history`. Metrics can be filtered with:

* Project
* Data source
* Metric class
* Metric name
* Metric compute type (sample, cumulative, average)
* Topic


## Install

The first step is to create a [OSSMETER mongodb database with data from projects](/web-dashboards#init-ossmeter-mongodb-with-grimoirelab-data).

[Elasticsearch and Kibana will be needed also](/web-dashboards#install-elasticsearch-and-kibana).

With mongodb populated with metrics data let's process the metrics so they
can be used in Kibana.

The data processing is done with [GrimoireLab python platform](/web-dashboards#install-grimoirelab-python-env). Inside the python env execute.


Now the tool can be executed with:

```
./mongo2es.py -g -e http://bitergia:bitergia@localhost:9200 -i ossmeter --project perceval
./mongo2es.py -g -e http://bitergia:bitergia@localhost:9200 -i ossmeter --project GrimoireELK
```

And now the Kibana panel can be imported to show the data:

`kidash.py -e http://bitergia:bitergia@localhost:9200 --import panels/ossmeter-metrics.json`

The panel is ready at the URL:

http://localhost:5601/app/kibana#/dashboard/AV7RLRT1TD5aYQ-eX8nd

![](screenshot.png?raw=true)

There is a [draft reference manual](https://docs.google.com/document/d/1OJj6WNgAsR9UvWGOThIoyjucSMNH55B88qQ8e52NPVY/edit?usp=sharing) available for this dashboard.
