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
import random
import statistics
import time

from perceval.backends.scava.scava import (Scava,
                                           CATEGORY_DEV_DEPENDENCY,
                                           CATEGORY_CONF_DEPENDENCY,
                                           CATEGORY_FACTOID,
                                           CATEGORY_METRIC,
                                           CATEGORY_USER,
                                           DEP_MAVEN,
                                           DEP_OSGI)
from grimoirelab_toolkit.datetime import str_to_datetime

from grimoire_elk.elastic import ElasticSearch
from grimoire_elk.elastic_mapping import Mapping as BaseMapping


UUIDS = {}
DUPLICATED_UUIDS = 0
META_MARKER = '--meta'
DEFAULT_TOP_PROJECT = 'main'

DEFAULT_BULK_SIZE = 100
DEFAULT_WAIT_TIME = 10

# FAKE DATA: create fake unique UUIDs in order to maximize the amount of data to create dashboards
# This is related to https://github.com/crossminer/scava/issues/139 and https://github.com/crossminer/scava/issues/138
GLOBAL_METRIC_COUNTER = 0
GLOBAL_FACTOID_COUNTER = 0
MIN_VALUE = 1
MAX_VALUE = 100


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

    if uuid_sha1 not in UUIDS:
        UUIDS[uuid_sha1] = args
    else:
        logging.debug("Detected scava item value %s" % str(args))
        global DUPLICATED_UUIDS
        DUPLICATED_UUIDS += 1

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

    # FAKE DATA: replace empty data with fake one.
    # This is related to https://github.com/crossminer/scava/issues/139
    # and https://github.com/crossminer/scava/issues/138
    if not mdata['datatable']:
        item_metric = {
            'project': mdata['project'],
            'metric_class': mdata['id'].split(".")[0],
            'metric_type': mdata['type'],
            'metric_id': mdata['id'],
            'metric_desc': mdata['description'],
            'metric_name': mdata['name'],
            'metric_es_value': random.randint(MIN_VALUE, MAX_VALUE),
            'metric_es_compute': 'FAKE',
            'datetime': mupdated,
            'scava': mdata
        }
        item_metrics.append(item_metric)

    elif mdata['type'] == 'BarChart':
        for item_metric in create_item_metrics_from_barchart(mdata, mupdated):
            item_metrics.append(item_metric)
    elif mdata['type'] == 'LineChart' and 'series' not in mdata:
        for item_metric in create_item_metrics_from_linechart(mdata, mupdated):
            item_metrics.append(item_metric)
    elif mdata['type'] == 'LineChart':
        for item_metric in create_item_metrics_from_linechart_series(mdata, mupdated):
            item_metrics.append(item_metric)
    else:
        logging.debug("Metric type %s not handled, skipping item %s", mdata['type'], scava_metric)

    logging.debug("Metrics found: %s", item_metrics)
    return item_metrics


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

    # FAKE DATA: avoid duplicates maximize the amount of data to create dashboards.
    # This is related to https://github.com/crossminer/scava/issues/139
    # and https://github.com/crossminer/scava/issues/138
    global GLOBAL_METRIC_COUNTER

    for scava_metric in scava_metrics:

        processed += 1

        # FAKE DATA: replace empty data maximize the amount of data to create dashboards.
        # This is related to https://github.com/crossminer/scava/issues/139
        # and https://github.com/crossminer/scava/issues/138
        if not scava_metric['data']['datatable']:
            empty += 1
            logging.debug("Faking datable for item %s", scava_metric)
            # logging.debug("Skipping item due to missing datable for item %s", scava_metric)
            # continue

        enriched_items = extract_metrics(scava_metric)
        for eitem in enriched_items:
            enriched += 1

            eitem['datetime'] = str_to_datetime(eitem['datetime']).isoformat()

            # FAKE DATA: avoid duplicates maximize the amount of data to create dashboards.
            # This is related to https://github.com/crossminer/scava/issues/139
            # and https://github.com/crossminer/scava/issues/138
            eitem['uuid'] = uuid(eitem['metric_id'], eitem['project'], eitem['datetime'], str(GLOBAL_METRIC_COUNTER))
            GLOBAL_METRIC_COUNTER += 1

            if isinstance(eitem['metric_es_value'], str):
                enriched_error += 1
                logging.debug("Skipping metric since 'metric_es_value' is not numeric, %s", eitem)
                continue

            eitem['metric_es_value'] = float(eitem['metric_es_value'])
            if eitem['metric_es_value'] == 0:
                enriched_zero += 1
                # FAKE DATA: replace empty data maximize the amount of data to create dashboards.
                # This is related to https://github.com/crossminer/scava/issues/139
                # and https://github.com/crossminer/scava/issues/138
                eitem['metric_es_value'] = float(random.randint(MIN_VALUE, MAX_VALUE))
                logging.debug("Faking Metric_es_value is 0 for %s", eitem)
                # logging.debug("Metric_es_value is 0 for %s", eitem)

            if meta_info:
                eitem['meta'] = meta_info

            yield eitem

    logging.debug("Metric enrichment summary (metrics in input) - processed: %s, empty: %s, duplicated: %s",
                  processed, empty, DUPLICATED_UUIDS)

    logging.debug("Metric enrichment summary (enriched metrics in output) - "
                  "total: %s, enriched: %s, failed: %s, zero: %s",
                  enriched + enriched_error, enriched, enriched_error, enriched_zero)

    # FAKE DATA: avoid duplicates maximize the amount of data to create dashboards.
    # This is related to https://github.com/crossminer/scava/issues/139
    # and https://github.com/crossminer/scava/issues/138
    GLOBAL_METRIC_COUNTER = 0


def enrich_factoids(scava_factoids, meta_info=None):
    """
    Enrich factoids coming from Scava to use them in Kibana

    :param scava_factoids: factoid generator
    :param meta_info: meta project information retrieved from the project description
    """
    processed = 0
    # FAKE DATA: avoid duplicates maximize the amount of data to create dashboards.
    # This is related to https://github.com/crossminer/scava/issues/139
    # and https://github.com/crossminer/scava/issues/138
    global GLOBAL_FACTOID_COUNTER

    for scava_factoid in scava_factoids:
        processed += 1

        factoid_data = scava_factoid['data']
        eitem = factoid_data
        eitem['datetime'] = str_to_datetime(factoid_data['updated']).isoformat()
        # FAKE DATA: avoid duplicates maximize the amount of data to create dashboards.
        # This is related to https://github.com/crossminer/scava/issues/139
        # and https://github.com/crossminer/scava/issues/138
        eitem['uuid'] = uuid(factoid_data['id'], factoid_data['project'], factoid_data['updated'],
                             str(GLOBAL_FACTOID_COUNTER))

        GLOBAL_FACTOID_COUNTER += 1

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

    logging.debug("Factoid enrichment summary - processed/enriched: %s, duplicated: %s", processed, DUPLICATED_UUIDS)
    # FAKE DATA: avoid duplicates maximize the amount of data to create dashboards.
    # This is related to https://github.com/crossminer/scava/issues/139
    # and https://github.com/crossminer/scava/issues/138
    GLOBAL_FACTOID_COUNTER = 0


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

        # common fields
        eitem['datetime'] = str_to_datetime(dependency_data['updated']).isoformat()
        eitem['uuid'] = uuid(dependency_data['id'], dependency_data['project'], dependency_data['updated'])

        # maven and osgi dependencies require specific processing
        if eitem['type'] in [DEP_MAVEN, DEP_OSGI]:
            dependency_raw = dependency_data['dependency']
            eitem['dependency_name'] = '/'.join(dependency_raw.split('/')[:-1])
            eitem['dependency_version'] = dependency_raw.split('/')[-1]

        if meta_info:
            eitem['meta'] = meta_info

        yield eitem

    logging.debug("Dependency enrichment summary - processed/enriched: %s, duplicated: %s", processed, DUPLICATED_UUIDS)


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

    logging.debug("User enrichment summary - processed/enriched: %s, duplicated: %s", processed, DUPLICATED_UUIDS)


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


def fetch_scava(url_api_rest, project=None, category=CATEGORY_METRIC):
    """
    Fetch the metrics from a Scava project using the Scava API REST

    :param project: name of the Scava project to get the metrics from
    :param url_api_rest: URL for the Scava API REST
    :param category: category of the items to fetch
    :return: a metrics generator
    """
    scava = Scava(url=url_api_rest, project=project)

    if not project:
        # Get the list of projects and get the metrics for all of them
        for project_scava in scava.fetch():
            global DUPLICATED_UUIDS
            DUPLICATED_UUIDS = 0

            project_shortname = project_scava['data']['shortName']
            scavaProject = Scava(url=url_api_rest, project=project_shortname)

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
            elif category == CATEGORY_USER:
                logging.debug("Start fetch user data for %s" % project)

                for enriched_user in enrich_users(scavaProject.fetch(CATEGORY_USER), meta):
                    yield enriched_user

                logging.debug("End fetch user data for %s" % project)

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

        elif category == CATEGORY_USER:
            logging.debug("Start fetch user data for %s" % project)

            for enriched_user in enrich_users(scava.fetch(CATEGORY_USER)):
                yield enriched_user

            logging.debug("End fetch user data for %s" % project)

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

    scava_data = fetch_scava(ARGS.url, ARGS.project, ARGS.category)

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
