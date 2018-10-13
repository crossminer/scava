# Sentiment Analysis Dashboard

The sentiment analysis dashboard shows the sentiment related to issues from the CROSSMINER metrics
and at the same time, in another widget, the data coming from these issues from GrimoireLab.

So it shows how CROSSMINER metrics provide additional valuable information that can be used
to improve the GrimoireLab metrics panels.

## Using the Scava Deployment project

Using the project [Scava Deployment Project](https://github.com/crossminer/scava-deployment) the
Sentiment dashboard will be automatically created and deployed.

But until this is implemented, in the next sections the use of the toolchain is described
in order to do manually the creation and deploying of the sentiment dashboard.


## Install

The first step is to follow [OSSMETER metrics dashboard](/web-dashboards/ossmeter-metrics) to upload to Elasticsearch all OSSMeter metrics data.

[Elasticsearch and Kibana will be needed also](/web-dashboards#install-elasticsearch-and-kibana).

The data processing is done with [GrimoireLab python platform](/web-dashboards#install-grimoirelab-python-env). Inside the python env execute.

```
p2o.py --enrich -e http://bitergia:bitergia@localhost:9200 --index grimoirelab_github-raw --index-enrich grimoirelab_github github grimoirelab perceval -t <github_api_token>
```

In order to share the filters between CROSSMINER item metrics and GrimoireLab items, the filters must be added to GrimoireLab items. This is done with:

`web-dashboards/ossmeter-metrics/os4gl.py -g -e http://bitergia:bitergia@localhost:9200 --ossmeter-index ossmeter --grimoirelab-index grimoirelab_github -o grimoirelab_github_oss`

And now the Kibana panel can be imported to show the data:

`kidash.py -e http://bitergia:bitergia@localhost:9200 --import web-dashboards/sentiment/panels/perceval_bugs_emotion_fear.json `

The panel is ready at the URL:

http://localhost:5601/app/kibana#/dashboard/AWAMwfGdxcy7SGCnCD5U

![](screenshot.png?raw=true)
