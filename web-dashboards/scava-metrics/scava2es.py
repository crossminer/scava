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

from dateutil import parser
from perceval.backends.scava.scava import Scava

from grimoire_elk.elastic import ElasticSearch


UUIDS = {}

DEBUG_METRIC = "bugs.cumulativeNewUsers"
DEBUG_METRIC = "newsgroups.severity.sentimentAtThreadEnd"
DEBUG_METRIC = "bugs.newbugs"
DEBUG_METRIC = "commitsovertimeline"
DEBUG_METRIC = None

DEBUG_PROJECT = "puppetrocketchat"
DEBUG_PROJECT = None


def get_params():
    parser = argparse.ArgumentParser(usage="usage: scava2es [options]",
                                     description="Import Scava metrics in ElasticSearch")
    parser.add_argument("--project", help="CROSSMINER Project Collection")
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


def extract_metrics(scava_metric):
    """
    Extract metric names and values from an scava_metric. It can be
    a cumulative value or a time series value metric. The time series will
    generate one metric per each sample.

    :param scava_metric: metric collected using Scava API REST
    :return: the list of metrics values from a scava_metric
    """

    def create_item_metrics(metric_name, value, updated=None):
        """
        The main goal of this method is to find the value and datetime for a metric
        :param metric_name: name of the scava metric
        :param value: value for the scava metric
        :param updated: the datetime string when the metric was updated
        :return: a dict with a metric ready to be consumed from ES
        """

        # The metrics could be computed as cumulative, average or single sample
        item_metric = {}
        item_metric['metric_es_name'] = metric_name

        no_value_fields = ['Committer', 'Date', 'Language', 'Repository']

        if isinstance(value, dict):
            value_fields = list(set(value.keys()) - set(no_value_fields))
            if len(value_fields) > 1:
                # Multivalue metric: we need to generate different items
                # {'Requests': 0.0, 'Comments': 0.0, 'Date': '20150818', 'Replies': 0.0}
                # raise RuntimeError("More than of value detected %s: %s" % (metric_name, value))
                if 'Date' in value.keys():
                    # {'Requests': 0.0, 'Comments': 0.0, 'Date': '20150818', 'Replies': 0.0}
                    for field in value_fields:
                        submetric_name = metric_name + "_" + field
                        subvalue = {'Date': value['Date'], submetric_name: value[field]}
                        for submetric in create_item_metrics(submetric_name, subvalue, updated=None):
                            yield submetric
                else:
                    # 'Average Response Time per Thread (ms)' {'Threads': 0, 'Response Time': 0}
                    for field in value_fields:
                        submetric_name = metric_name + "_" + field
                        subvalue = {submetric_name: value[field]}
                        for submetric in create_item_metrics(submetric_name, subvalue, updated=updated):
                            yield submetric

                    # logging.debug("Metric not supported %s" % value)

            item_metric.update(value)
            item_metric['metric_es_value'] = value[value_fields[0]]

            if 'Date' in value.keys():
                # {'Users': 0, 'Date': '20150818'}
                # {'Language': 'Python', 'Date': '20150818', 'LOC': 31}
                # Always add the full details of the metric
                item_metric['datetime'] = parser.parse(value['Date']).isoformat()
                item_metric['metric_es_compute'] = 'sample'
            else:
                logging.debug("No ts metric %s: %s" % (metric_name, value))
                # {'Churn': 28, 'Committer': 'Prabhat'}
                # {'Replies': 0.0, 'Date': '20150818', 'Requests': 0.0, 'Comments': 0.0}
                item_metric['metric_es_compute'] = 'cumulative'
                item_metric['datetime'] = parser.parse(updated).isoformat()
        else:
            item_metric['metric_es_value'] = value
            item_metric['metric_es_compute'] = 'cumulative'
            item_metric['datetime'] = updated.isoformat()

        # if 'cumulative' in field.lower():
        #     item_metric['metric_es_compute'] = 'cumulative'
        # if 'avg' in field.lower() or 'average' in field.lower():
        #     item_metric['metric_es_compute'] = 'average'
        # For the topic metrics we get the topic label and add it as a field
        # topic = find_topic()
        # if topic:
        #     item_metric['topic'] = find_topic()

        yield item_metric

    item_metrics = []

    field = 'datatable'

    mupdated = scava_metric['data']['updated']

    if isinstance(scava_metric['data'][field], list):
        # time series metric: a new ES metric is created per each sample
        for sample in scava_metric['data'][field]:
            value = sample
            for item_metric in create_item_metrics(scava_metric['data']['name'], value, mupdated):
                item_metrics.append(item_metric)
            if len(item_metrics) % 100 == 0:
                logging.debug("Processed %i" % len(item_metrics))
    elif isinstance(scava_metric[field], (int, float)):
        # global metric
        value = scava_metric[field]
        for item_metric in create_item_metrics(scava_metric['data']['name'], value, mupdated):
            item_metrics.append(item_metric)
        item_metrics.append(item_metric)

    logging.debug("Metrics found: %s", item_metrics)

    return item_metrics


def enrich_scava_metric(scava_metric):
    """
    Given a ossmeter item enrich it to be used in Kibana

    :param scava_metric: Scava metric to enrich
    :return:
    """

    # A Scava metric could generate several enriched items, one for
    # each metric value
    eitems = []

    for metric in extract_metrics(scava_metric):
        eitem = metric
        # It is useful to have all item fields for debugging
        # eitem['scava_metric'] = scava_metric
        eitems.append(eitem)

    return eitems

def enrich_metrics(scava_metrics):
    """
    Enrich metrics coming from Scava to use them in Kibana

    :param scava_metrics: metrics generator
    :return:
    """

    for scava_metric in scava_metrics:

        if DEBUG_METRIC and scava_metric['data']['id'] != DEBUG_METRIC:
            continue

        # id : 'bugs.cumulativeNewUsers'
        metric_meta = {
            'project': scava_metric['data']['project'],
            'metric_class': scava_metric['data']['id'].split(".")[0],
            'metric_type': scava_metric['data']['type'],
            'metric_id': scava_metric['data']['id'],
            'metric_desc': scava_metric['data']['description'],
            'metric_name': scava_metric['data']['name']
        }

        enriched_items = enrich_scava_metric(scava_metric)
        for eitem in enriched_items:
            eitem.update(metric_meta)
            if 'datetime' not in eitem:
                logging.error("Can not find datetime field in %s" % eitem)
            eitem['uuid'] = uuid(eitem['metric_id'], eitem['project'], eitem['datetime'])
            yield eitem


def fetch_scava(url_api_rest, project=None):
    """
    Fetch the metrics from a Scava project using the Scava API REST

    :param project: name of the Scava project to get the metrics from
    :param url_api_rest: URL for the Scava API REST
    :return: a metrics generator
    """

    scava = Scava(url=url_api_rest, project=project)

    if not project:
        # Get the list of projects and get the metrics for all of them
        for project_scava in scava.fetch():

            if DEBUG_PROJECT and project_scava['data']['shortName'] != DEBUG_PROJECT:
                continue

            scavaProject = Scava(url=url_api_rest, project=project_scava['data']['shortName'])
            logging.info("Getting metrics for %s" % project_scava['data']['shortName'])
            for enriched_metric in enrich_metrics(scavaProject.fetch()):
                yield enriched_metric
    else:
        # Get the metrics directly
        for enriched_metric in enrich_metrics(scava.fetch()):
            yield enriched_metric


if __name__ == '__main__':

    ARGS = get_params()
    if ARGS.debug:
        logging.basicConfig(level=logging.DEBUG, format='%(asctime)s %(message)s')
        logging.debug("Debug mode activated")
    else:
        logging.basicConfig(level=logging.INFO, format='%(asctime)s %(message)s')

    logging.info("Importing items from %s to %s/%s", ARGS.url, ARGS.elastic_url, ARGS.index)

    elastic = ElasticSearch(ARGS.elastic_url, ARGS.index)

    scava_metrics = fetch_scava(ARGS.url, ARGS.project)

    if scava_metrics:
        logging.info("Loading Scava metrics in Elasticsearch")
        elastic.bulk_upload(scava_metrics, "uuid")
