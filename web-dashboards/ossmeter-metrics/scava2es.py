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

from perceval.backends.scava.scava import Scava

from grimoire_elk.elastic import ElasticSearch


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

    return uuid_sha1


def connect_to_mongo(host=None, port=None):
    """ Return a connection to the mongo server in host and port """
    if host and port:
        client = MongoClient(host, port)
    elif host:
        client = MongoClient(host)
    else:
        client = MongoClient()

    return client


def is_ossmeter_historic_collection(collection):
    # Check if a collection is an OSSMeter historic one
    # Sample: modeling-graphiti.historic.newsgroups.articles

    is_historic = False

    if len(collection.split(".")) == 4:
        if collection.split(".")[1] == 'historic':
            is_historic = True

    return is_historic


def is_ossmeter_project_collection(project, collection):
    # Check if a collection is an OSSMeter one from project
    # Sample: perceval.historic.bugs.bugs

    is_project = False

    if is_ossmeter_historic_collection(collection):
        if project == collection.split(".")[0]:
            is_project = True

    return is_project


def fetch_mongodb_all(host=None, port=None):
    logging.info("Searching for all OSSMeter metrics collections")
    client = connect_to_mongo(host, port)

    # Find all OSSMeter collections in mongo
    for db in client.database_names():
        logging.info('Loading items from database %s', db)
        for collection in client[db].collection_names():
            collection_name = db + '.' + collection
            if is_ossmeter_historic_collection(collection_name):
                logging.info('Loading items from %s', collection_name)
                for item in fetch_mongodb_collection(collection_name, client=client):
                    yield item


def fetch_mongodb_project(project, host=None, port=None):
    logging.info("Searching OSSMeter metrics collections for project %s", project)
    client = connect_to_mongo(host, port)

    # Find all OSSMeter collections in mongo
    for db in client.database_names():
        logging.info('Checking items from database %s', db)
        for collection in client[db].collection_names():
            collection_name = db + '.' + collection
            if not is_ossmeter_historic_collection(collection_name):
                continue
            if not is_ossmeter_project_collection(project, collection_name):
                continue
            logging.info('Loading items from %s', collection_name)
            for item in fetch_mongodb_collection(collection_name, client=client):
                yield item


def extract_metrics(item, item_meta):
    # Extract metric names and values from an item

    value_formatted_pattern = 'Formatted'

    def find_topic():
        """ Find the topic label for an item """
        topic = None
        if item_meta['metric_name'] == "topics" and 'label' in item:
            topic = item['label']

        return topic

    def create_item_metric(field, value):
        # The metrics could be computed as cumulative, average or single sample
        item_metric = {}
        item_metric['metric_es_name'] = field
        item_metric['metric_es_value'] = value
        item_metric['metric_es_compute'] = 'sample'
        if 'cumulative' in field.lower():
            item_metric['metric_es_compute'] = 'cumulative'
        if 'avg' in field.lower() or 'average' in field.lower():
            item_metric['metric_es_compute'] = 'average'
        # For the topic metrics we get the topic label and add it as a field
        topic = find_topic()
        if topic:
            item_metric['topic'] = find_topic()

        return item_metric

    def find_metric_prefix(item, value_fields):
        metric_prefix = None

        for field in value_fields:
            if value_formatted_pattern in field:
                # It is a formatted value, not a string with the metric name
                continue
            if find_topic():
                # Topics are a special case and will be added as an extra field
                continue
            if isinstance(item[field], str):
                # If the string is url it is just the origin
                if field == 'url':
                    continue
                # This is the name of the metric prefix: 'severityLevel': 'normal'}
                metric_prefix = item[field]

        return metric_prefix

    item_metrics = []

    no_value_fields = ['__date', '_type', '_id', '__datetime', 'bugs',
                       'bugData', 'bugTrackers', 'newsgroups', 'newsgroupName',
                       'bugTrackerId', 'percentage']
    value_fields = list(set(item.keys()) - set(no_value_fields))

    metric_prefix = find_metric_prefix(item, value_fields)

    for field in value_fields:
        if value_formatted_pattern in field:
            # We don't want string formatted values
            continue
        if isinstance(item[field], list):
            # In the list items we can have metrics
            for subitem in item[field]:
                subitem_metrics = extract_metrics(subitem, item_meta)
                item_metrics += subitem_metrics
        elif isinstance(item[field], (int, float)):
            value = item[field]
            item_metric = create_item_metric(field, value)
            if metric_prefix:
                item_metric['metric_es_name'] = metric_prefix + "_" + item_metric['metric_es_name']
            item_metrics.append(item_metric)

    # logging.debug("Metrics found: %s", item_metrics)

    return item_metrics


def enrich_ossmeter_item(item, item_meta):
    # Given a ossmeter item enrich it to be used in Kibana

    # A raw item from OSSMeter could generate several enriched items, one for
    # each metric
    eitems = []

    for metric in extract_metrics(item, item_meta):
        eitem = {}
        eitem.update(metric)
        # It is useful to have all item fields for debugging
        eitem.update(item)

        if '__datetime' in item:
            eitem['datetime'] = eitem['__datetime'].isoformat()
            eitem['__datetime'] = eitem['__datetime'].isoformat()
        if '__date' in item:
            eitem['date'] = item['__date']
        eitem['mongo_id'] = eitem.pop('_id')
        eitem['mongo_type'] = eitem.pop('_type')
        eitem['id'] = uuid(eitem['mongo_id'], eitem['metric_es_name'])
        if 'topic' in eitem:
            eitem['id'] = uuid(eitem['mongo_id'], eitem['metric_es_name'],
                               eitem['topic'])

        eitems.append(eitem)

    return eitems


def fetch_scava(url_api_rest, project=None):
    """
    Fetch the metrics from a Scava project using the Scava API REST

    :param project: name of the Scava project to get the metrics from
    :param url_api_rest: URL for the Scava API REST
    :return: a metrics generator
    """


    item_meta = {
        'project': None,
        'metric_type': None,
        'metric_class': None,
        'metric_name': None
    }

    scava = Scava(url=url_api_rest, project=project)

    if not project:
        # Get the list of projects and get the metrics for all of them
        for project_scava in scava.fetch():
            scavaProject = Scava(url=url_api_rest, project=project_scava['data']['name'])
            for metric in scavaProject.fetch():
                # TODO: enrich the metric
                yield metric
    else:
        # Get the metrics directly
        for metric in scava.fetch():
            # TODO: enrich the metric
            yield metric

    # for item in collection.find():
    #     enrich_items = enrich_ossmeter_item(item, item_meta)
    #     for eitem in enrich_items:
    #         eitem.update(item_meta)
    #         yield eitem


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
