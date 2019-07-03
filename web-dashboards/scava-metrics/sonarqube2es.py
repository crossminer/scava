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
import logging
import time

from perceval.backends.scava.sonarqube import Sonar

from grimoirelab_toolkit.datetime import unixtime_to_datetime

from grimoire_elk.elastic import ElasticSearch
from grimoire_elk.elastic_mapping import Mapping as BaseMapping

DEFAULT_BULK_SIZE = 100
DEFAULT_WAIT_TIME = 10


def get_params():
    parser = argparse.ArgumentParser(usage="usage: sonarqube2es [options]",
                                     description="Import Sonarqube metrics in ElasticSearch")
    parser.add_argument("--bulk-size", default=DEFAULT_BULK_SIZE, type=int,
                        help="Number of items uploaded per bulk")
    parser.add_argument("--wait-time", default=DEFAULT_WAIT_TIME, type=int,
                        help="Seconds to wait in case ES is not ready")
    parser.add_argument("-u", "--url", help="URL for Sonarqube instance")
    parser.add_argument("-c", "--components", nargs='+', help="List of components")
    parser.add_argument("-m", "--metrics", nargs='+', help="List of metrics")
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


def enrich_metrics(sonar_metrics):
    """
    Enrich metrics coming from Sonarqube to use them in Kibana

    :param sonar_metrics: metrics generator
    :return:
    """
    processed = 0
    enriched_skipped = 0

    for sonar_metric in sonar_metrics:

        processed += 1

        project = ((sonar_metric['origin'].split('?')[1]).split('&')[0]).split('=')[1]
        datetime = unixtime_to_datetime(sonar_metric['data']['fetched_on']).isoformat()

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
            'metric_id': sonar_data['id'],
            'metric_desc': 'Sonar ' + sonar_data['metric'],
            'metric_name': 'Sonar ' + sonar_data['metric'],
            'metric_es_value': metric_value,
            'metric_es_compute': 'sample',
            'metric_value': metric_value,
            'metric_es_value_weighted': metric_value,
            'datetime': datetime,
            'sonar': sonar_data
        }

        eitem['uuid'] = uuid(eitem['metric_id'], eitem['project'], eitem['datetime'])

        yield eitem

    msg = "Metric enrichment summary processed: {}, skipped: {}".format(processed, enriched_skipped)
    logging.info(msg)


def fetch_sonarqube(url, components, metrics):
    """
    Fetch the metrics from Sonarqube

    """
    # Get the metrics for all projects
    for component in components:

        sonar_backend = Sonar(url, component, metrics)

        for enriched_metric in enrich_metrics(sonar_backend.fetch()):
            yield enriched_metric

        msg = "Metrics {} from component {} fetched".format(metrics, component)
        logging.debug(msg)


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

    # OW2 specific: fetch from SonarQube and our quality model, OMM
    sonar_metrics = fetch_sonarqube(ARGS.url, ARGS.components, ARGS.metrics)

    if sonar_metrics:
        logging.info("Uploading SonarQube metrics to Elasticsearch")

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
            elastic.bulk_upload(to_upload, "uuid")
