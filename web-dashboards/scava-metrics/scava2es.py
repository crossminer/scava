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
import logging
import statistics

from dateutil import parser
from perceval.backends.scava.scava import (Scava,
                                           CATEGORY_FACTOID,
                                           CATEGORY_METRIC)
from grimoirelab_toolkit.datetime import str_to_datetime

from grimoire_elk.elastic import ElasticSearch
from grimoire_elk.elastic_mapping import Mapping as BaseMapping


UUIDS = {}


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
    parser.add_argument("-u", "--url", default='http://localhost:8182',
                        help="URL for Scava API REST (default: http://localhost:8182)")
    parser.add_argument("-e", "--elastic-url", default="http://localhost:9200",
                        help="ElasticSearch URL (default: http://localhost:9200)")
    parser.add_argument("-i", "--index", required=True, help="ElasticSearch index in which to import the metrics")
    parser.add_argument('-g', '--debug', dest='debug', action='store_true')
    args = parser.parse_args()

    return args


# From perceval
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
        logging.warning("Detected duplicated scava metric value %s" % str(args))

    return uuid_sha1


def create_item_metrics_from_barchart(mdata, mupdated):
    if not isinstance(mdata['y'], str):
        logging.warning("Barchart metric, Y axis not handled %s", mdata)
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
                'metric_es_value': sample[mdata['y']],
                'metric_es_compute': 'cumulative',
                'datetime': mupdated,
                'scava': mdata
            }

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
        logging.warning("Linechart metric, Y axis not handled %s", mdata)

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
            if mdata['series'] in sample:
                metric['metric_id'] = mdata['id'] + '_' + sample[mdata['series']]
                metric['metric_desc'] = mdata['description'] + '(' + sample[mdata['series']] + ')',
                metric['metric_name'] = mdata['name'] + '(' + sample[mdata['series']] + ')'

            metrics.append(metric)
        else:
            logging.warning("Linechart series metric, Y axis not handled %s", mdata)
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


def enrich_metrics(scava_metrics):
    """
    Enrich metrics coming from Scava to use them in Kibana

    :param scava_metrics: metrics generator
    :return:
    """
    for scava_metric in scava_metrics:

        if not scava_metric['data']['datatable']:
            logging.warning("Missing datable for item %s, skipping it", scava_metric)
            continue

        enriched_items = extract_metrics(scava_metric)
        for eitem in enriched_items:
            eitem['datetime'] = str_to_datetime(eitem['datetime']).isoformat()
            eitem['uuid'] = uuid(eitem['metric_id'], eitem['project'], eitem['datetime'])
            yield eitem


def enrich_factoids(scava_factoids):
    """
    Enrich factoids coming from Scava to use them in Kibana

    :param scava_factoids: factoid generator
    :return:
    """
    for scava_factoid in scava_factoids:
        yield scava_factoid


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

            scavaProject = Scava(url=url_api_rest, project=project_scava['data']['shortName'])
            logging.info("Getting metrics for %s" % project_scava['data']['shortName'])

            if category == CATEGORY_METRIC:
                for enriched_metric in enrich_metrics(scavaProject.fetch(CATEGORY_METRIC)):
                    if isinstance(enriched_metric['metric_es_value'], str):
                        logging.warning("Skipping metric since 'metric_es_value' is not numeric, %s", enriched_metric)
                        continue

                    yield enriched_metric

            else:
                for enriched_factoid in enrich_factoids(scavaProject.fetch(CATEGORY_FACTOID)):
                    yield enriched_factoid
    else:
        if category == CATEGORY_METRIC:
            # Get the metrics directly
            for enriched_metric in enrich_metrics(scava.fetch(CATEGORY_METRIC)):
                if isinstance(enriched_metric['metric_es_value'], str):
                    logging.warning("Skipping metric since 'metric_es_value' is not numeric, %s", enriched_metric)
                    continue

                yield enriched_metric
        else:
            for enriched_factoid in enrich_factoids(scava.fetch(CATEGORY_FACTOID)):
                yield enriched_factoid


if __name__ == '__main__':

    ARGS = get_params()
    if ARGS.debug:
        logging.basicConfig(level=logging.DEBUG, format='%(asctime)s %(message)s')
        logging.debug("Debug mode activated")
    else:
        logging.basicConfig(level=logging.INFO, format='%(asctime)s %(message)s')

    logging.info("Importing items from %s to %s/%s", ARGS.url, ARGS.elastic_url, ARGS.index)

    mapping = Mapping
    elastic = ElasticSearch(ARGS.elastic_url, ARGS.index, mappings=mapping)

    scava_data = fetch_scava(ARGS.url, ARGS.project, ARGS.category)

    if scava_data:
        logging.info("Loading Scava metrics/factoids in Elasticsearch")
        elastic.bulk_upload(scava_data, "uuid")
