
# scava-scripts

Various scripts for Eclipse Scava

## Introduction

This is the reference implementation for the Scava APIs.

All scripts rely on the `SCAVA_HOST` environment variable, or use the default url: `http://ci4.castalia.camp`.

## Get the list of projects

Synopsis:
`scava_get_projects.pl`

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
`scava_get_metrics_def_raw.pl`

Example:
```
boris@kadath:gh_scava-scripts$ perl scava_get_metrics_def_raw.pl

* Fetching http://ci4.castalia.camp:8182/raw/metrics/.

# Found 317 metrics:

* Bug Tracker Usage data
  - org.eclipse.scava.factoid.bugs.channelusage.BugsChannelUsageFactoid
  - This plugin generates the factoid regarding emotions for bug trackers. For example, the percentage of positive, negative or surprise emotions expressed
```

## Get a single metric for a project

Synopsis:
`scava_get_metric.pl project_id metric_id`

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

## Get all metrics for a project

Synopsis:
`scava_get_metrics.pl project_id`

Example:
```
```
