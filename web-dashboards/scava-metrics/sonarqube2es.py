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
#   Assad Montasser <assad.montasser@ow2.org>
#

import argparse
import hashlib
<<<<<<< HEAD
<<<<<<< HEAD
import json
import logging
import re
import requests
import time

from perceval.backends.scava.sonarqube import Sonar
=======
=======
>>>>>>> f9b291d96304ab041f0b5dc559a23efaebf360fa
import logging
import json
import time

from perceval.backends.sonarqube import Sonar

from grimoirelab_toolkit.datetime import str_to_datetime
<<<<<<< HEAD
>>>>>>> OW2 use case sonarqube metrics integration
=======
>>>>>>> f9b291d96304ab041f0b5dc559a23efaebf360fa

from grimoire_elk.elastic import ElasticSearch
from grimoire_elk.elastic_mapping import Mapping as BaseMapping

DEFAULT_BULK_SIZE = 100
DEFAULT_WAIT_TIME = 10

<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> OW2 use case sonarqube metrics integration
=======

>>>>>>> f9b291d96304ab041f0b5dc559a23efaebf360fa
def get_params():
    parser = argparse.ArgumentParser(usage="usage: sonarqube2es [options]",
                                     description="Import Sonarqube metrics in ElasticSearch")
    parser.add_argument("--bulk-size", default=DEFAULT_BULK_SIZE, type=int,
                        help="Number of items uploaded per bulk")
    parser.add_argument("--wait-time", default=DEFAULT_WAIT_TIME, type=int,
                        help="Seconds to wait in case ES is not ready")
    parser.add_argument("-u", "--url", help="URL for Sonarqube instance")
<<<<<<< HEAD
<<<<<<< HEAD
    parser.add_argument("-t", "--time", help="Timestamp used to enrich item")
    parser.add_argument("-c", "--components", help="URL containing the list of components with corresponding mappings")
    parser.add_argument("-m", "--metrics", nargs='+', help="List of metrics")
=======
    parser.add_argument("-c", "--components", nargs='+', help="List of components")
>>>>>>> OW2 use case sonarqube metrics integration
=======
    parser.add_argument("-c", "--components", nargs='+', help="List of components")
>>>>>>> f9b291d96304ab041f0b5dc559a23efaebf360fa
    parser.add_argument("-e", "--elastic-url", default="http://localhost:9200",
                        help="ElasticSearch URL (default: http://localhost:9200)")
    parser.add_argument("-i", "--index", required=True, help="ElasticSearch index in which to import the metrics")
    parser.add_argument('-g', '--debug', dest='debug', action='store_true')
    args = parser.parse_args()

    return args


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


<<<<<<< HEAD
<<<<<<< HEAD
def enrich_metrics(sonar_metrics, timestamp):
=======
def enrich_metrics(sonar_metrics):
>>>>>>> OW2 use case sonarqube metrics integration
=======
def enrich_metrics(sonar_metrics):
>>>>>>> f9b291d96304ab041f0b5dc559a23efaebf360fa
    """
    Enrich metrics coming from Sonarqube to use them in Kibana

    :param sonar_metrics: metrics generator
    :return:
    """
<<<<<<< HEAD
<<<<<<< HEAD
    processed = 0
    enriched_skipped = 0

    for sonar_metric in sonar_metrics:

        processed += 1

        project = ((sonar_metric['origin'].split('?')[1]).split('&')[0]).split('=')[1]
        sonar_data = sonar_metric['data']

        try:
            metric_value = sonar_data.get('value', None)
            if metric_value:
                float(metric_value)
        except:
            metric_value = None

        if not metric_value:
            periods = sonar_data.get('periods', [])
            if periods:
                metric_value = periods[0]['value']

        if not metric_value:
            msg = "Metric value not processed for {} and value {}".format(sonar_data['metric'], sonar_data['value'])
            logging.warning(msg)
            enriched_skipped += 1
        else:
            metric_value = float(metric_value)

        eitem = {
            # origin : https://sonarc..component?component=org.xwiki.contrib:application-antispam&metricKeys=accessors
            # we get only component name, in this example: org.xwiki.contrib:application-antispam
            'project': project,
            'metric_class': 'sonarqube',
            'metric_type': sonar_metric['backend_name'],
            'metric_id': 'sonar_{}'.format(re.sub('\W+', '_', sonar_data['metric']).lower()),
            'metric_desc': 'Sonar ' + sonar_data['metric'],
            'metric_name': 'Sonar ' + sonar_data['metric'],
            'metric_es_value': metric_value,
            'metric_es_compute': 'sample',
            'metric_value': metric_value,
            'metric_es_value_weighted': metric_value,
            'datetime': timestamp,
            'sonar': sonar_data
        }

        eitem['uuid'] = uuid(eitem['metric_id'], eitem['project'], eitem['datetime'])

        yield eitem

    msg = "Metric enrichment summary processed: {}, skipped: {}".format(processed, enriched_skipped)
    logging.info(msg)


def load_components(url):

    logging.info("Fetching components from %s", url)
    raw_mappings = requests.get(url)
    mappings = json.loads(raw_mappings.text)

    return mappings['component-mapping']


def fetch_sonarqube(url, components_url, metrics, timestamp):
=======
=======
>>>>>>> f9b291d96304ab041f0b5dc559a23efaebf360fa

    for sonar_metric in sonar_metrics:

        metric = {
            # origin : https://sonarcloud.io/api/measures/component?component=org.xwiki.contrib:application-antispam&metricKeys=accessors
            # we get only component name, in this example: org.xwiki.contrib:application-antispam
            'project': ((sonar_metrics['origin'].split('?')[1]).split('&')[0]).split('=')[1],
            'metric_class': sonar_metrics['data']['id'],
            'metric_type': sonar_metrics['backend_name'],
            'metric_id': sonar_metrics['data']['id'],
            'metric_name': sonar_metrics['data']['metric'],
            'metric_value': sonar_metrics['data']['value'],
            'datetime': sonar_metrics['data']['fetched_on']
        }

        yield metric


def fetch_sonarqube(url, components):
<<<<<<< HEAD
>>>>>>> OW2 use case sonarqube metrics integration
=======
>>>>>>> f9b291d96304ab041f0b5dc559a23efaebf360fa
    """
    Fetch the metrics from Sonarqube

    """
<<<<<<< HEAD
<<<<<<< HEAD
    components = load_components(components_url)

    for component in components:
        sonar_backend = Sonar(url, component, metrics)

        for enriched_metric in enrich_metrics(sonar_backend.fetch(),timestamp):

            if components[component]:
                enriched_metric['project'] = components[component]

            yield enriched_metric

        msg = "Metrics {} from component {} fetched".format(metrics, component)
        logging.debug(msg)

=======
=======
>>>>>>> f9b291d96304ab041f0b5dc559a23efaebf360fa
    # Get the metrics for all projects
    for component in components:

        sonar_backend = Sonar(component=component, base_url=url)

        for enriched_metric in enrich_metrics(sonar_backend.fetch()):
            yield enriched_metric

<<<<<<< HEAD
>>>>>>> OW2 use case sonarqube metrics integration
=======
>>>>>>> f9b291d96304ab041f0b5dc559a23efaebf360fa

if __name__ == '__main__':

    ARGS = get_params()
    if ARGS.debug:
        logging.basicConfig(level=logging.DEBUG, format='%(asctime)s %(message)s')
        logging.debug("Debug mode activated")
    else:
        logging.basicConfig(level=logging.INFO, format='%(asctime)s %(message)s')

    logging.info("Importing items from Sonarqube to %s/%s", ARGS.elastic_url, ARGS.index)

    elastic = __init_index(ARGS.elastic_url, ARGS.index, ARGS.wait_time)
    elastic.max_items_bulk = min(ARGS.bulk_size, elastic.max_items_bulk)

<<<<<<< HEAD
<<<<<<< HEAD

    # OW2 specific: fetch from SonarQube and our quality model, OMM
    sonar_metrics = fetch_sonarqube(ARGS.url, ARGS.components, ARGS.metrics,ARGS.time)

    if sonar_metrics:
        logging.info("Uploading SonarQube metrics to Elasticsearch")
=======
=======
>>>>>>> f9b291d96304ab041f0b5dc559a23efaebf360fa
    # OW2 specific: fetch from SonarQube and our quality model, OMM
    sonar_metrics = fetch_sonarqube(ARGS.url, ARGS.components)

    if sonar_metrics:
        logging.info("Loading SonarQube metrics in Elasticsearch")
<<<<<<< HEAD
>>>>>>> OW2 use case sonarqube metrics integration
=======
>>>>>>> f9b291d96304ab041f0b5dc559a23efaebf360fa

        counter = 0
        to_upload = []
        for item in sonar_metrics:

            to_upload.append(item)

            if len(to_upload) == ARGS.bulk_size:
                elastic.bulk_upload(to_upload, "uuid")
                counter += len(to_upload)
                to_upload = []

        if len(to_upload) > 0:
            counter += len(to_upload)
<<<<<<< HEAD
<<<<<<< HEAD
            elastic.bulk_upload(to_upload, "uuid")
=======
            elastic.bulk_upload(to_upload, "uuid")
>>>>>>> OW2 use case sonarqube metrics integration
=======
            elastic.bulk_upload(to_upload, "uuid")
>>>>>>> f9b291d96304ab041f0b5dc559a23efaebf360fa
