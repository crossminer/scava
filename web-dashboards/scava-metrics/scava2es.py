#!/usr/bin/env python3
# -*- coding: utf-8 -*-
#
# Get metrics from Scava and publish them in Elasticsearch
# If the collection is a OSSMeter one add project and other fields to items
#
# Copyright (C) 2018 Bitergia
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
#
# Authors:
#   Alvaro del Castillo San Felix <acs@bitergia.com>
#

import argparse
import hashlib
import json
import logging
import statistics
import time

from perceval.backends.scava.scava import (Scava,
                                           CATEGORY_DEV_DEPENDENCY,
                                           CATEGORY_CONF_DEPENDENCY,
                                           CATEGORY_FACTOID,
                                           CATEGORY_METRIC,
                                           CATEGORY_USER,
                                           CATEGORY_TOPIC,
                                           CATEGORY_RECOMMENDATION,
                                           CATEGORY_DEPENDENCY_OLD_NEW_VERSIONS,
                                           CATEGORY_CONF_SMELL,
                                           CATEGORY_PROJECT_RELATION,
                                           DEP_MAVEN,
                                           DEP_OSGI)
from grimoirelab_toolkit.datetime import str_to_datetime

from grimoire_elk.elastic import ElasticSearch
from grimoire_elk.elastic_mapping import Mapping as BaseMapping

KEYWORD_MAX_SIZE = 30000  # this control allows to avoid max_bytes_length_exceeded_exception

META_MARKER = '--meta'
DEFAULT_TOP_PROJECT = 'main'

DEFAULT_BULK_SIZE = 100
DEFAULT_WAIT_TIME = 10


class Mapping(BaseMapping):

    @staticmethod
    def get_elastic_mappings(es_major):
        """Get Elasticsearch mapping.

        geopoints type is not created in dynamic mapping

        :param es_major: major version of Elasticsearch, as string
        :returns:        dictionary with a key, 'items', with the mapping
        """

        mapping = """
        {
            "properties": {
               "scava": {
                    "dynamic": "false",
                    "properties": {}
               }
            }
        }
        """

        return {"items": mapping}


def get_params():
    parser = argparse.ArgumentParser(usage="usage: scava2es [options]",
                                     description="Import Scava metrics in ElasticSearch")
    parser.add_argument("--project", help="CROSSMINER Project Collection")
    parser.add_argument("--category", help="category (either metric or factoid)")
    parser.add_argument("--recommendation-url", default='http://localhost:8080', help="Recommendation API URL")
    parser.add_argument("--bulk-size", default=DEFAULT_BULK_SIZE, type=int, help="Number of items uploaded per bulk")
    parser.add_argument("--wait-time", default=DEFAULT_WAIT_TIME, type=int, help="Seconds to wait in case ES is not ready")
    parser.add_argument("-u", "--url", default='http://localhost:8182',
                        help="URL for Scava API REST (default: http://localhost:8182)")
    parser.add_argument("-e", "--elastic-url", default="http://localhost:9200",
                        help="ElasticSearch URL (default: http://localhost:9200)")
    parser.add_argument("-i", "--index", required=True, help="ElasticSearch index in which to import the metrics")
    parser.add_argument('-g', '--debug', dest='debug', action='store_true')
    args = parser.parse_args()

    return args


def uuid(*args):
    """Generate a UUID based on the given parameters.

    The UUID will be the SHA1 of the concatenation of the values
    from the list. The separator bewteedn these values is ':'.
    Each value must be a non-empty string, otherwise, the function
    will raise an exception.

    :param *args: list of arguments used to generate the UUID

    :returns: a universal unique identifier

    :raises ValueError: when anyone of the values is not a string,
        is empty or `None`.
    """
    def check_value(v):
        if not isinstance(v, str):
            raise ValueError("%s value is not a string instance" % str(v))
        elif not v:
            raise ValueError("value cannot be None or empty")
        else:
            return v

    s = ':'.join(map(check_value, args))

    sha1 = hashlib.sha1(s.encode('utf-8', errors='surrogateescape'))
    uuid_sha1 = sha1.hexdigest()

    return uuid_sha1


def create_item_metrics_from_barchart(mdata, mupdated):
    if not isinstance(mdata['y'], str):
        logging.debug("Barchart metric, Y axis not handled %s", mdata)
        return []

    values = [bar[mdata['y']] for bar in mdata['datatable']]

    max_values = max(values)
    min_values = min(values)
    mean_values = statistics.mean(values)
    median_values = statistics.median(values)

    metric_max = {
        'project': mdata['project'],
        'metric_class': mdata['id'].split(".")[0],
        'metric_type': mdata['type'],
        'metric_id': mdata['id'] + '_max',
        'metric_desc': mdata['description'] + '(Max)',
        'metric_name': mdata['name'] + '(Max)',
        'metric_es_value': max_values,
        'metric_es_compute': 'sample',
        'datetime': mupdated,
        'scava': mdata
    }

    metric_min = {
        'project': mdata['project'],
        'metric_class': mdata['id'].split(".")[0],
        'metric_type': mdata['type'],
        'metric_id': mdata['id'] + '_min',
        'metric_desc': mdata['description'] + '(Min)',
        'metric_name': mdata['name'] + '(Min)',
        'metric_es_value': min_values,
        'metric_es_compute': 'sample',
        'datetime': mupdated,
        'scava': mdata
    }

    metric_mean = {
        'project': mdata['project'],
        'metric_class': mdata['id'].split(".")[0],
        'metric_type': mdata['type'],
        'metric_id': mdata['id'] + '_mean',
        'metric_desc': mdata['description'] + '(Mean)',
        'metric_name': mdata['name'] + '(Mean)',
        'metric_es_value': mean_values,
        'metric_es_compute': 'sample',
        'datetime': mupdated,
        'scava': mdata
    }

    metric_median = {
        'project': mdata['project'],
        'metric_class': mdata['id'].split(".")[0],
        'metric_type': mdata['type'],
        'metric_id': mdata['id'] + '_median',
        'metric_desc': mdata['description'] + '(Median)',
        'metric_name': mdata['name'] + '(Median)',
        'metric_es_value': median_values,
        'metric_es_compute': 'sample',
        'datetime': mupdated,
        'scava': mdata
    }

    return [metric_max, metric_min, metric_mean, metric_median]


def create_item_metrics_from_linechart(mdata, mupdated):
    metrics = []
    if isinstance(mdata['y'], str):

        for sample in mdata['datatable']:
            metric = {
                'project': mdata['project'],
                'metric_class': mdata['id'].split(".")[0],
                'metric_type': mdata['type'],
                'metric_id': mdata['id'],
                'metric_desc': mdata['description'],
                'metric_name': mdata['name'],
                'metric_es_compute': 'cumulative',
                'datetime': mupdated,
                'scava': mdata,
                'metric_es_value': None
            }

            if 'y' in mdata and mdata['y'] in sample:
                metric['metric_es_value'] = sample[mdata['y']]
            elif 'Smells' in sample:
                metric['metric_es_value'] = sample['Smells']

            if metric['metric_es_value'] is None:
                logging.debug("Linechart metric not handled %s", sample)

            if 'Date' in sample:
                metric['metric_es_compute'] = 'sample'
                metric['datetime'] = sample['Date']

            metrics.append(metric)
    elif isinstance(mdata['y'], list):
        for sample in mdata['datatable']:
            for y in mdata['y']:
                metric = {
                    'project': mdata['project'],
                    'metric_class': mdata['id'].split(".")[0],
                    'metric_type': mdata['type'],
                    'metric_id': mdata['id'] + '_' + y,
                    'metric_desc': mdata['description'] + '(' + y + ')',
                    'metric_name': mdata['name'] + '(' + y + ')',
                    'metric_es_value': sample[y],
                    'metric_es_compute': 'cumulative',
                    'datetime': mupdated,
                    'scava': mdata
                }

                if 'Date' in sample:
                    metric['metric_es_compute'] = 'sample'
                    metric['datetime'] = sample['Date']

                metrics.append(metric)
    else:
        logging.debug("Linechart metric, Y axis not handled %s", mdata)

    return metrics


def create_item_metrics_from_linechart_series(mdata, mupdated):
    metrics = []
    for sample in mdata['datatable']:
        if isinstance(mdata['y'], str):
            metric = {
                'project': mdata['project'],
                'metric_class': mdata['id'].split(".")[0],
                'metric_type': mdata['type'],
                'metric_id': mdata['id'] + '_' + mdata['series'],
                'metric_desc': mdata['description'] + '(' + mdata['series'] + ')',
                'metric_name': mdata['name'] + '(' + mdata['series'] + ')',
                'metric_es_value': sample[mdata['y']],
                'metric_es_compute': 'cumulative',
                'datetime': mupdated,
                'scava': mdata
            }

            if 'Date' in sample:
                metric['metric_es_compute'] = 'sample'
                metric['datetime'] = sample['Date']

            if mdata['series'] in sample and mdata['series'] != 'Repository':
                metric['metric_id'] = mdata['id'] + '_' + sample[mdata['series']]
                metric['metric_desc'] = mdata['description'] + '(' + sample[mdata['series']] + ')',
                metric['metric_name'] = mdata['name'] + '(' + sample[mdata['series']] + ')'

            metrics.append(metric)
        else:
            logging.debug("Linechart series metric, Y axis not handled %s", mdata)

    return metrics


def extract_metrics(scava_metric):
    """
    Extract metric names and values from an scava_metric. It can be
    a cumulative value or a time series value metric. The time series will
    generate one metric per each sample.

    :param scava_metric: metric collected using Scava API REST
    :return: the list of metrics values from a scava_metric
    """
    item_metrics = []

    mdata = scava_metric['data']
    mupdated = mdata['updated']

    if mdata['type'] == 'BarChart':
        for item_metric in create_item_metrics_from_barchart(mdata, mupdated):
            item_metrics.append(item_metric)
    elif mdata['type'] == 'LineChart' and 'series' not in mdata:
        for item_metric in create_item_metrics_from_linechart(mdata, mupdated):
            item_metrics.append(item_metric)
    elif mdata['type'] == 'LineChart':
        for item_metric in create_item_metrics_from_linechart_series(mdata, mupdated):
            item_metrics.append(item_metric)
    else:
        logging.warning("Metric type %s not handled, skipping item %s", mdata['type'], scava_metric)

    logging.debug("Metrics found: %s", item_metrics)

    return item_metrics


def add_weighted_value(eitem):
    """Add a weighted value to each metric, by default the weight is set to 1,
    thus `metric_es_value` is equal to `metric_es_value_weighted`

    :param eitem: the enriched item containing the metric
    """

    if eitem['metric_id'] in ['bugs.emotions.comments___label__anger',
                              'bugs.emotions.cumulativeComments___label__anger']:
        metric_es_value_weighted = -3 * eitem['metric_es_value']
    elif eitem['metric_id'] in ['bugs.emotions.comments___label__fear',
                                'bugs.emotions.cumulativeComments___label__fear']:
        metric_es_value_weighted = -2 * eitem['metric_es_value']
    elif eitem['metric_id'] in ['bugs.emotions.comments___label__sadness',
                                'bugs.emotions.cumulativeComments___label__sadness']:
        metric_es_value_weighted = -1 * eitem['metric_es_value']
    elif eitem['metric_id'] in ['bugs.emotions.comments___label__surprise',
                                'bugs.emotions.cumulativeComments___label__surprise']:
        metric_es_value_weighted = 1 * eitem['metric_es_value']
    elif eitem['metric_id'] in ['bugs.emotions.comments___label__joy',
                                'bugs.emotions.cumulativeComments___label__joy']:
        metric_es_value_weighted = 2 * eitem['metric_es_value']
    elif eitem['metric_id'] in ['bugs.emotions.comments___label__love',
                                'bugs.emotions.cumulativeComments___label__love']:
        metric_es_value_weighted = 3 * eitem['metric_es_value']
    else:
        metric_es_value_weighted = eitem['metric_es_value']

    return metric_es_value_weighted


def enrich_metrics(scava_metrics, meta_info=None):
    """
    Enrich metrics coming from Scava to use them in Kibana

    :param scava_metrics: metrics generator
    :param meta_info: meta project information retrieved from the project description
    """
    processed = 0
    empty = 0
    enriched = 0
    enriched_error = 0
    enriched_zero = 0

    for scava_metric in scava_metrics:

        processed += 1

        if not scava_metric['data']['datatable']:
            empty += 1
            logging.debug("Skipping item due to missing datable for item %s", scava_metric)
            continue

        enriched_items = extract_metrics(scava_metric)
        for eitem in enriched_items:
            enriched += 1

            eitem['datetime'] = str_to_datetime(eitem['datetime']).isoformat()
            eitem['uuid'] = uuid(eitem['metric_id'], eitem['project'], eitem['datetime'])

            # add weighted metric value to the enrich item
            eitem['metric_es_value_weighted'] = add_weighted_value(eitem)

            if isinstance(eitem['metric_es_value'], str):
                enriched_error += 1
                logging.debug("Skipping metric since 'metric_es_value' is not numeric, %s", eitem)
                continue

            eitem['metric_es_value'] = float(eitem['metric_es_value'])
            if eitem['metric_es_value'] == 0.0:
                enriched_zero += 1
                logging.debug("Metric_es_value is 0 for %s", eitem)

            if meta_info:
                eitem['meta'] = meta_info

            yield eitem

    logging.info("Metric enrichment summary (metrics in input) - processed: %s, empty: %s", processed, empty)
    logging.info("Metric enrichment summary (enriched metrics in output) - "
                 "total: %s, enriched: %s, failed: %s, zero: %s",
                 enriched + enriched_error, enriched, enriched_error, enriched_zero)


def enrich_factoids(scava_factoids, meta_info=None):
    """
    Enrich factoids coming from Scava to use them in Kibana

    :param scava_factoids: factoid generator
    :param meta_info: meta project information retrieved from the project description
    """
    processed = 0
    for scava_factoid in scava_factoids:
        processed += 1

        factoid_data = scava_factoid['data']
        eitem = factoid_data
        eitem['datetime'] = str_to_datetime(factoid_data['updated']).isoformat()
        eitem['uuid'] = uuid(factoid_data['id'], factoid_data['project'], factoid_data['updated'])

        if 'stars' in factoid_data:
            stars_id = 'stars_' + factoid_data['stars']
            eitem[stars_id] = 1

            if factoid_data['stars'] == 'ONE':
                eitem['stars_num'] = 1
            elif factoid_data['stars'] == 'TWO':
                eitem['stars_num'] = 2
            elif factoid_data['stars'] == 'THREE':
                eitem['stars_num'] = 3
            elif factoid_data['stars'] == 'FOUR':
                eitem['stars_num'] = 4

        if meta_info:
            eitem['meta'] = meta_info

        yield eitem

    logging.info("Factoid enrichment summary - processed/enriched: %s", processed)


def enrich_version_dependencies(scava_version_dependencies, meta_info=None):
    """
    Enrich version dependencies coming from Scava to use them in Kibana

    :param scava_version_dependencies: dependency generator
    :param meta_info: meta project information retrieved from the project description
    :return:
    """
    processed = 0

    for scava_dep in scava_version_dependencies:
        processed += 1

        dependency_data = scava_dep['data']
        eitem = dependency_data

        # common fields
        eitem['datetime'] = str_to_datetime(dependency_data['updated']).isoformat()
        eitem['uuid'] = uuid(dependency_data['id'], dependency_data['project'], dependency_data['updated'])

        if meta_info:
            eitem['meta'] = meta_info

        yield eitem

    logging.info("Dependency version enrichment summary - processed/enriched: %s", processed)


def enrich_dependencies(scava_dependencies, meta_info=None):
    """
    Enrich dependencies coming from Scava to use them in Kibana

    :param scava_dependencies: dependency generator
    :param meta_info: meta project information retrieved from the project description
    """
    processed = 0

    for scava_dep in scava_dependencies:
        processed += 1

        dependency_data = scava_dep['data']
        eitem = dependency_data
        eitem['dependency_count'] = 1

        # common fields
        eitem['datetime'] = str_to_datetime(dependency_data['updated']).isoformat()
        eitem['uuid'] = uuid(dependency_data['id'], dependency_data['project'], dependency_data['updated'])

        # maven and osgi dependencies require specific processing
        if eitem['type'] in [DEP_MAVEN, DEP_OSGI]:
            dependency_raw = dependency_data['dependency']
            eitem['dependency_name'] = '/'.join(dependency_raw.split('/')[:-1])
            eitem['dependency_version'] = dependency_raw.split('/')[-1]
            eitem['dependency_has_version'] = 1 if eitem['dependency_version'] else 0

        if meta_info:
            eitem['meta'] = meta_info

        yield eitem

    logging.info("Dependency enrichment summary - processed/enriched: %s", processed)


def enrich_recommendations(scava_recommendations, meta_info=None):
    """
    Enrich recommendation data from the Knowledge-Based (KB) database.
    The current enriched items contains the ID of the recommended project in the KB, its name,
    full name, description, url, readme, dependencies, whether the project is active or not,
    the type of recommendation, the project in SCAVA and when it was updated.
    An example of enriched item is shown below:

          "id" : "5b155b04065f2d726d6db241",
          "name" : "kotlinRestAssured",
          "full_name" : "rmarinsky/kotlinRestAssured",
          "description" : "SImple project for demonstartion compatibility of Kotlin with rest-assuredd",
          "url" : "https://github.com/rmarinsky/kotlinRestAssured",
          "readme" : "...",
          "dependencies" : [
            "junit:junit",
            "io.rest-assured:rest-assured",
            "com.google.cloud:google-cloud-translate",
            "org.assertj:assertj-core",
            "com.fasterxml.jackson.core:jackson-databind"
          ],
          "active" : true,
          "recommendation_type" : "Compound",
          "project" : "microprofile",
          "updated" : "20180430",
          "datetime" : "2018-04-30T00:00:00+00:00",
          "uuid" : "ba595239b468c7242ccfd0f8203386d8d82a049c",
          "meta" : {
            "top_projects" : [
              "main"
            ]
          }

    :param scava_recommendations: recommendation generator
    :param meta_info: meta project information retrieved from the project description
    """
    processed = 0

    for scava_recommendation in scava_recommendations:
        processed += 1

        recommendation_data = scava_recommendation['data']
        eitem = recommendation_data

        eitem['readme'] = eitem['readme'][:KEYWORD_MAX_SIZE]
        # common fields
        eitem['datetime'] = str_to_datetime(eitem['updated']).isoformat()
        eitem['uuid'] = uuid(eitem['recommendation_type'], eitem['id'], eitem['url'], eitem['updated'])

        if meta_info:
            eitem['meta'] = meta_info

        yield eitem

    logging.info("Recommendation enrichment summary - processed/enriched: %s", processed)


def enrich_conf_smells(conf_smells, meta_info=None):
    """Enrich the configuration smells data extracted from the SCAVA API raw endpoints.
    The current enriched items contains the smell name, the line and file name where the smell was detected,
    the reason, the type of configuration (puppet or docker), the smell type (implementation or design) and
    the sub type (e.g., antipattern), some optional attributes such as the commit and date when the smell
    was found (`date`), plus information about the project (`project` and `meta`), when the project was updated,
    the id of the smell and the uuid of the enriched item. It is worth noting that the field `datetime` is derived
    using the `date` attribute (if not None) or the project `updated` attribute.
    An example of enriched item is shown below:

          "smell_name" : "Inconsistent naming convention",
          "line" : " 24",
          "reason" : " python::pyvenv not in autoload module layout ",
          "file_name" : "/root/scava/puppetpython/.../manifests/pyvenv.pp ",
          "commit" : "c3bb6b37e7711f816faba3ea8fb98b72286830e1",
          "date" : "2019-05-21T18:40:17.000Z",
          "conf_type" : "puppet",
          "smell_type" : "implementation",
          "smell_sub_type" : "antipattern",
          "id" : "puppet_implementation_Inconsistent...",
          "updated" : "20190402",
          "project" : "puppetpython",
          "datetime" : "2019-05-21T18:40:17+00:00",
          "uuid" : "11eb644184b9a752ccda2e00af5a76d9bfbcc32b",
          "meta" : {
            "top_projects" : [
              "main"
            ]
          }

    :param conf_smells: configuration smells generator
    :param meta_info: meta project information retrieved from the project description
    """
    processed = 0

    for conf_smell in conf_smells:
        processed += 1

        smell_data = conf_smell['data']
        eitem = smell_data

        eitem['reason'] = eitem['reason'][:KEYWORD_MAX_SIZE]
        # common fields

        if 'date' in eitem and eitem['date']:
            eitem['datetime'] = str_to_datetime(eitem['date']).isoformat()
        else:
            eitem['datetime'] = str_to_datetime(eitem['updated']).isoformat()

        eitem['uuid'] = uuid(eitem['id'], eitem['project'], eitem['datetime'])

        if meta_info:
            eitem['meta'] = meta_info

        yield eitem

    logging.info("Configuration smell enrichment summary - processed/enriched: %s", processed)


def enrich_project_relations(project_relations, meta_info=None):
    """
    Enrich project relations data coming from Scava to use them in Kibana.
    The current enriched items contain the type of the relation (e.g., docker), the name of
    the related project, when the relation was calculated (`datetime`), plus project
    and meta project information. An example of enriched item is shown below:

          "relation_type" : "docker",
          "related_to" : "im",
          "id" : "vim_im",
          "updated" : "20190616",
          "project" : "vim",
          "datetime" : "2019-06-16T00:00:00+00:00",
          "uuid" : "1fe3957fa267d89c86c3b1f5ca86fd28a40f7548",
          "meta" : {
            "top_projects" : [
              "main"
            ]
          }

    :param project_relations: project relations data generator
    :param meta_info: meta project information retrieved from the project description
    """
    processed = 0

    for project_relation in project_relations:
        processed += 1

        relation_data = project_relation['data']
        eitem = relation_data

        # common fields
        eitem['datetime'] = str_to_datetime(eitem['updated']).isoformat()
        eitem['uuid'] = uuid(eitem['id'], eitem['project'], eitem['updated'])

        if meta_info:
            eitem['meta'] = meta_info

        yield eitem

    logging.info("Project relation summary - processed/enriched: %s", processed)


def enrich_users(scava_users, meta_info=None):
    """
    Enrich user data coming from Scava to use them in Kibana.
    The current enriched items contain the user name, the date (i.e., `date` and `datetime`) when
    the metric was calculated, the churn value (i.e., `churn` and `value`), the scava metric from
    where the data was fetched, project and meta project information.

          "user" : "Winnie the pooh",
          "date" : "20100523",
          "churn" : 22218,
          "scava_metric" : "churnPerCommitterTimeLine",
          "project" : "Honey tree",
          "datetime" : "2010-05-23T00:00:00+00:00",
          "uuid" : "1b7cea97ba3e6a9e1dd98385dce4abe7943f7b6e",
          "value" : 22218,
          "meta" : {
            "top_projects" : [
              "main"
            ]
          }

    :param scava_users: user data generator
    :param meta_info: meta project information retrieved from the project description
    """
    processed = 0

    for scava_user in scava_users:
        processed += 1

        user_data = scava_user['data']
        eitem = user_data

        # common fields
        eitem['datetime'] = str_to_datetime(eitem['date']).isoformat()
        eitem['uuid'] = uuid(eitem['user'], eitem['project'], eitem['date'])

        # move the churn value to a different attribute to ease aggregations
        eitem['value'] = eitem['churn']

        # remove updated field (which is the time when the project analysis was executed)
        eitem.pop('updated', None)

        if meta_info:
            eitem['meta'] = meta_info

        yield eitem

    logging.info("User enrichment summary - processed/enriched: %s", processed)


def enrich_comments_topics(scava_comments_topics, meta_info=None):
    """
    Enrich comments topics data coming from Scava to use them in Kibana.
    The current enriched items contain the topic name, the date (i.e., `date` and `datetime`) when
    the metric was calculated, the number of comments for that topic, the scava metric from
    where the data was fetched, project and meta project information.

          "topic" : "Wider Community",
          "date" : "20190307",
          "comments" : 2,
          "scava_metric" : "bugs.topics.comments",
          "updated" : "20180430",
          "project" : "microprofile",
          "datetime" : "2019-03-07T00:00:00+00:00",
          "uuid" : "6e3b802219e41792d7c3a0744b3305c42f1e249c",
          "meta" : {
            "top_projects" : [
              "main"
            ]
          }

    :param scava_comments_topics: user data generator
    :param meta_info: meta project information retrieved from the project description
    """
    processed = 0

    for comment_topic in scava_comments_topics:
        processed += 1

        topic_data = comment_topic['data']
        eitem = topic_data

        # common fields
        eitem['datetime'] = str_to_datetime(eitem['date']).isoformat()
        eitem['uuid'] = uuid(eitem['topic'], eitem['project'], eitem['date'])

        if meta_info:
            eitem['meta'] = meta_info

        yield eitem

    logging.info("Comments topics enrichment summary - processed/enriched: %s", processed)


def extract_meta(project_name, description=None):
    """Extract the meta information defined in the project description. Meta information
    should appear at the very end of the description after the marker META_MARKER, for instance:

    EMF project repository (emf)

    --meta
    {
        "top_projects": ["eclipse"]
    }

    :param project_name: short name of the project
    :param description: text representing the project description

    :return: a JSON with meta information
    """
    meta = {"top_projects": [DEFAULT_TOP_PROJECT]}

    if not description or META_MARKER not in description:
        return meta

    meta_raw = description.split(META_MARKER)[1]

    try:
        meta = json.loads(meta_raw)
    except:
        logging.debug("Failed to load meta info from %s for project %s."
                      "Default meta is applied" % (description, project_name))

    return meta


def fetch_scava(url_api_rest, project=None, category=CATEGORY_METRIC, recommendation_url=None):
    """
    Fetch the metrics from a Scava project using the Scava API REST

    :param project: name of the Scava project to get the metrics from
    :param url_api_rest: URL for the Scava API REST
    :param category: category of the items to fetch
    :param recommendation_url: URL for the Recommendation API REST

    :return: a metrics generator
    """
    scava = Scava(url=url_api_rest, project=project, recommendation_url=recommendation_url)

    if not project:
        # Get the list of projects and get the metrics for all of them
        for project_scava in scava.fetch():

            project_shortname = project_scava['data']['shortName']
            scavaProject = Scava(url=url_api_rest, project=project_shortname, recommendation_url=recommendation_url)

            prj_descr = project_scava['data']['description'] if 'description' in project_scava['data'] else None
            meta = extract_meta(prj_descr, project_shortname)

            if category == CATEGORY_METRIC:
                logging.debug("Start fetch metrics for %s" % project_scava['data']['shortName'])

                for enriched_metric in enrich_metrics(scavaProject.fetch(CATEGORY_METRIC), meta):
                    yield enriched_metric

                logging.debug("End fetch metrics for %s" % project_scava['data']['shortName'])
            elif category == CATEGORY_FACTOID:
                logging.debug("Start fetch factoids for %s" % project_scava['data']['shortName'])

                for enriched_factoid in enrich_factoids(scavaProject.fetch(CATEGORY_FACTOID), meta):
                    yield enriched_factoid

                logging.debug("End fetch factoids for %s" % project_scava['data']['shortName'])
            elif category == CATEGORY_DEV_DEPENDENCY:
                logging.debug("Start fetch dev dependencies for %s" % project_scava['data']['shortName'])

                for enriched_dep in enrich_dependencies(scavaProject.fetch(CATEGORY_DEV_DEPENDENCY), meta):
                    yield enriched_dep

                logging.debug("End fetch dev dependencies for %s" % project_scava['data']['shortName'])
            elif category == CATEGORY_CONF_DEPENDENCY:
                logging.debug("Start fetch conf dependencies for %s" % project_scava['data']['shortName'])

                for enriched_dep in enrich_dependencies(scavaProject.fetch(CATEGORY_CONF_DEPENDENCY), meta):
                    yield enriched_dep

                logging.debug("End fetch conf dependencies for %s" % project_scava['data']['shortName'])
            elif category == CATEGORY_DEPENDENCY_OLD_NEW_VERSIONS:
                logging.debug("Start fetch version dependencies for %s" % project_scava['data']['shortName'])

                for enriched_dep in enrich_version_dependencies(
                        scavaProject.fetch(CATEGORY_DEPENDENCY_OLD_NEW_VERSIONS), meta):
                    yield enriched_dep

                logging.debug("End fetch version dependencies for %s" % project_scava['data']['shortName'])
            elif category == CATEGORY_USER:
                logging.debug("Start fetch user data for %s" % project)

                for enriched_user in enrich_users(scavaProject.fetch(CATEGORY_USER), meta):
                    yield enriched_user

                logging.debug("End fetch user data for %s" % project)
            elif category == CATEGORY_RECOMMENDATION:
                logging.debug("Start fetch recommendation data for %s" % project)

                for enriched_recommendation in enrich_recommendations(scavaProject.fetch(CATEGORY_RECOMMENDATION),
                                                                      meta):
                    yield enriched_recommendation

                logging.debug("End fetch recommendation data for %s" % project)
            elif category == CATEGORY_CONF_SMELL:
                logging.debug("Start fetch configuration smells data for %s" % project)

                for enriched_conf_smell in enrich_conf_smells(scavaProject.fetch(CATEGORY_CONF_SMELL),
                                                              meta):
                    yield enriched_conf_smell

                logging.debug("End fetch configuration smells data for %s" % project)

            elif category == CATEGORY_PROJECT_RELATION:
                logging.debug("Start fetch project relations for %s" % project)

                for enriched_project_relation in enrich_project_relations(scavaProject.fetch(CATEGORY_PROJECT_RELATION),
                                                                          meta):
                    yield enriched_project_relation

                logging.debug("End fetch project relations for %s" % project)

            elif category == CATEGORY_TOPIC:
                logging.debug("Start fetch comments topics for %s" % project)

                for enriched_comment_topic in enrich_comments_topics(scavaProject.fetch(CATEGORY_TOPIC),
                                                                     meta):
                    yield enriched_comment_topic

                logging.debug("End fetch comments topics for %s" % project)

            else:
                msg = "category %s not handled" % category
                raise Exception(msg)
    else:
        if category == CATEGORY_METRIC:
            logging.debug("Start fetch metrics for %s" % project)

            for enriched_metric in enrich_metrics(scava.fetch(CATEGORY_METRIC)):
                yield enriched_metric

            logging.debug("End fetch metrics for %s" % project)
        elif category == CATEGORY_FACTOID:
            logging.debug("Start fetch factoids for %s" % project)

            for enriched_factoid in enrich_factoids(scava.fetch(CATEGORY_FACTOID)):
                yield enriched_factoid

            logging.debug("End fetch factoids for %s" % project)
        elif category == CATEGORY_DEV_DEPENDENCY:
            logging.debug("Start fetch dev dependencies for %s" % project)

            for enriched_dep in enrich_dependencies(scava.fetch(CATEGORY_DEV_DEPENDENCY)):
                yield enriched_dep

            logging.debug("End fetch dev dependencies for %s" % project)
        elif category == CATEGORY_CONF_DEPENDENCY:
            logging.debug("Start fetch conf dependencies for %s" % project)

            for enriched_dep in enrich_dependencies(scava.fetch(CATEGORY_CONF_DEPENDENCY)):
                yield enriched_dep

            logging.debug("End fetch conf dependencies for %s" % project)
        elif category == CATEGORY_DEPENDENCY_OLD_NEW_VERSIONS:
            logging.debug("Start fetch version dependencies for %s" % project)

            for enriched_dep in enrich_version_dependencies(scava.fetch(CATEGORY_DEV_DEPENDENCY)):
                yield enriched_dep

            logging.debug("End fetch version dependencies for %s" % project)
        elif category == CATEGORY_USER:
            logging.debug("Start fetch user data for %s" % project)

            for enriched_user in enrich_users(scava.fetch(CATEGORY_USER)):
                yield enriched_user

            logging.debug("End fetch user data for %s" % project)
        elif category == CATEGORY_RECOMMENDATION:
            logging.debug("Start fetch recommendation data for %s" % project)

            for enriched_recommendation in enrich_recommendations(scava.fetch(CATEGORY_RECOMMENDATION)):
                yield enriched_recommendation

            logging.debug("End fetch recommendation data for %s" % project)
        elif category == CATEGORY_CONF_SMELL:
            logging.debug("Start fetch configuration smells data for %s" % project)

            for enriched_conf_smell in enrich_conf_smells(scava.fetch(CATEGORY_CONF_SMELL)):
                yield enriched_conf_smell

            logging.debug("End fetch configuration smells data for %s" % project)

        elif category == CATEGORY_PROJECT_RELATION:
            logging.debug("Start fetch project relations for %s" % project)

            for enriched_project_relation in enrich_project_relations(scava.fetch(CATEGORY_PROJECT_RELATION)):
                yield enriched_project_relation

            logging.debug("End fetch project relations for %s" % project)

        elif category == CATEGORY_TOPIC:
            logging.debug("Start fetch comments topics for %s" % project)

            for enriched_comment_topic in enrich_comments_topics(scava.fetch(CATEGORY_TOPIC)):
                yield enriched_comment_topic

            logging.debug("End fetch comments topics for %s" % project)
        else:
            msg = "category %s not handled" % category
            raise Exception(msg)


def __init_index(elastic_url, index, wait_time):
    mapping = Mapping

    while True:
        try:
            elastic = ElasticSearch(elastic_url, index, mappings=mapping)
            break
        except Exception as e:
            logging.info("Index %s not ready: %s", ARGS.index, str(e))
            time.sleep(wait_time)

    return elastic


if __name__ == '__main__':

    ARGS = get_params()

    if ARGS.debug:
        logging.basicConfig(level=logging.DEBUG, format='%(asctime)s %(message)s')
        logging.debug("Debug mode activated")
    else:
        logging.basicConfig(level=logging.INFO, format='%(asctime)s %(message)s')

    logging.info("Importing items from %s to %s/%s", ARGS.url, ARGS.elastic_url, ARGS.index)

    elastic = __init_index(ARGS.elastic_url, ARGS.index, ARGS.wait_time)
    elastic.max_items_bulk = min(ARGS.bulk_size, elastic.max_items_bulk)

    scava_data = fetch_scava(ARGS.url, ARGS.project, ARGS.category, ARGS.recommendation_url)

    if scava_data:
        logging.info("Uploading Scava data to Elasticsearch")

        counter = 0
        to_upload = []
        for item in scava_data:

            to_upload.append(item)

            if len(to_upload) == ARGS.bulk_size:
                elastic.bulk_upload(to_upload, "uuid")
                counter += len(to_upload)
                # logging.info("Added %s items to %s", counter, ARGS.index)
                to_upload = []

        if len(to_upload) > 0:
            counter += len(to_upload)
            elastic.bulk_upload(to_upload, "uuid")
            # logging.info("Added %s items to %s", counter, ARGS.index)
