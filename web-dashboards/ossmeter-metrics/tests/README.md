# OSSMeter metrics Dashboard Testing

There will be manual testing and automatic testing for the OSSMeter metrics Dashboard.

## Automatic testing

## Manual testing

### Comparison against grimoirelab

In this testing, the metrics from OSSMeter will be compared with the ones collected by [GrimoireLab](https://grimoirelab.github.io/).

In order to understand howto use GrimoireLab the best reference is the [GrimoireLab GitBook](https://www.gitbook.com/book/grimoirelab/training/details).

Let's reuse the (python env already created)[https://github.com/crossminer/crossminer/tree/dev/web-dashboards/ossmeter-metrics#install]

`source ~/venvs/crossminer/bin/activate`


#### Commits

To generate the metrics with GrimoireLab for perceval and GrimoireELK:

```
p2o.py --enrich -e http://bitergia:bitergia@localhost:9200 --index git-raw --index-enrich git git https://github.com/grimoirelab/perceval
...
2017-11-28 22:32:51,485 Total items enriched 807

p2o.py --enrich -e http://bitergia:bitergia@localhost:9200 --index git-raw --index-enrich git git https://github.com/grimoirelab/GrimoireELK
...
2017-11-28 22:33:30,144 Total items enriched 1678
```

And to create the dashboard for analyzing the data:

`kidash.py -e http://bitergia:bitergia@localhost:9200 --import panels/git.json`

The dashboard is available at:

http://localhost:5601/app/kibana#/dashboard/AWAElMdqWJ8CNnVirCtn

![](grimoirelab.png?raw=true)

Let's check the big numbers: number of commits in repositories.

|| Perceval Commits  | GrimoireELK commits |
|--| ------------- | ------------- |
|OSSMeter|  801 | 1,510  |
|GrimoireLab|  807 | 1,678  |

The number are a bit different but this is could be related to the branches covered. In GrimoireLab all branches are analyzed.

Getting the total number of commits using the OSSMeter metrics dashboard is a bit tricky:

![](ossmeter-metrics-commits.png?raw=true)

In OSSMeter the metric class with commits is `CommitsOverTimeHistoricMetricProvider` and for the perceval repository is the metric name `https://github.com/grimoirelab/perceval.git_numberOfCommits`. It is a sample metric and it has a total of `731` samples collected. In each sample the total number of commits in this time is collected. So the max value for this field is the total number of commits.

#### Issues

The same anlysis can be done for issues. In GitHub using the issues API the real issues and the pull requests are returned together.

OSSMeter as `numberOfBugs`for perceval has found: **218**. But this is the sume of issues and pull requests. Not real bugs.

Doing the same for GrimoireLab:

```
p2o.py --enrich -e http://bitergia:bitergia@localhost:9200 --index github-raw --index-enrich github github grimoirelab perceval -t <github_api_token>
....
2017-11-28 23:22:07,441 Total items enriched 219
```
|| Perceval GitHub Issues  |
|--| ------------- |
|OSSMeter|  218 |
|GrimoireLab|  219 |

Ok, so this manual testing that the metrics collected from OSSMeter and published in Elasticsearch and shown in Kibana have sense.
