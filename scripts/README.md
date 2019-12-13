
# scava-scripts

Various scripts for Eclipse Scava

## Introduction

This is the reference implementation for the Scava APIs.

All scripts rely on the `SCAVA_HOST` environment variable, or use the default url: `http://ci4.castalia.camp`.

## Create all projects on a given instance

The scripts rely on the environment variable `SCAVA_HOST`. It should point to an URL in the form of: `http://scava.myhost.com`. The list of projects to create is set in `list_projects.csv`.

In the simplest case, just issue the two following commands to start the mass creation of projects and their associated tasks:

```
boris@kadath:gh_scava-scripts$ export SCAVA_HOST=http://ci4.castalia.camp
boris@kadath:gh_scava-scripts$ sh create_all.sh 

# Authenticating against http://ci4.castalia.camp.

Authorisation is Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iLCJST0xFX1BST0pFQ1RfTUFOQUdFUiIsIlJPTEVfVVNFUiJdLCJpYXQiOjE1NzA5ODAyNTIsImV4cCI6MTU3MTA2NjY1Mn0.iZFm7EyqyY0OS5RvWvQHm5ZKOfAZg5YlMu0pKs_qOm4.

# Fetching http://ci4.castalia.camp:8086/administration/projects/import.

[SNIP]
```

## Get the list of projects

Synopsis:
`perl scava_get_projects.pl`

Example:
```
boris@kadath:gh_scava-scripts$ perl scava_get_projects.pl

# Authenticating against http://ci4.castalia.camp.

  Authorisation is Bearer eyJhbGciOiJIUzI1Ni...

* Fetching http://ci4.castalia.camp:8086/administration/projects.

# List of projects:

* emf [Full name: eclipse/emf] [name: emf]
  - Home page: Unknown
  - Size: 57434
  - SCM url: https://github.com/eclipse/emf.git
  - ITS url: https://api.github.com/repos/eclipse/emf/issues
  - COM url: Unknown
```

## Get metrics definition

Synopsis:
`perl scava_get_metrics_def.pl`

Example:
```
boris@kadath:gh_scava-scripts$ perl scava_get_metrics_def.pl

* Fetching http://ci4.castalia.camp:8182/metrics/.

# Found 228 metrics:

* bugs.cumulativeNewUsers
  - Bug Tracker New Users (Cumulative)
  - The number of new users up to and including the current date per bug tracker.

* coreCommittersChurnBar
  - Core committers
  - This metric shows the total accumulated churn per committer.
```

Synopsis:
`perl scava_get_metrics_def_raw.pl`

Example:
```
boris@kadath:gh_scava-scripts$ perl scava_get_metrics_def_raw.pl

* Fetching http://ci4.castalia.camp:8182/raw/metrics/.

# Found 317 metrics:

* Bug Tracker Usage data
  - org.eclipse.scava.factoid.bugs.channelusage.BugsChannelUsageFactoid
  - This plugin generates the factoid regarding emotions for bug trackers. For example, the percentage of positive, negative or surprise emotions expressed
```

Synopsis:
`perl scava_get_metrics_def_csv.pl`

Example:
```
```

## Get a single metric for a project

Synopsis:
`scava_get_metric.pl project_id metric_id`

Example:
```
boris@kadath:gh_scava-scripts$ perl scava_get_metrics_def_csv.pl
id,name,description
bugs.cumulativeNewUsers,"Bug Tracker New Users (Cumulative)","The number of new users up to and including the current date per bug tracker."
coreCommittersChurnBar,"Core committers","This metric shows the total accumulated churn per committer."
CeJavaQuartilesHistoric,"Efferent coupling (Java)","This metric shows the quartiles of Efferent coupling for Java over time."
```

Output would typically be saved directly in a csv file:
```
boris@kadath:gh_scava-scripts$ perl scava_get_metrics_def_csv.pl > all_metrics.csv
```

## Get all metrics for a project

Synopsis:
`scava_get_metrics.pl project_id`

Example:
```
```

## Get all metrics for a project

Synopsis:
`scava_get_metrics.pl project_id`

Example:
```
```

## Get all metrics for a project

Synopsis:
`scava_get_metrics.pl project_id`

Example:
```
```
