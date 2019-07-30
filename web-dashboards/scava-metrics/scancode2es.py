#!/usr/bin/env python3
# -*- coding: utf-8 -*-
#
# Get metrics from Scava and publish them in Elasticsearch
# If the collection is a OSSMeter one add project and other fields to items
#
# Copyright (C) 2018-2019 Bitergia
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
#   Valerio Cosentino <valcos@bitergia.com>
#   Assad Montasser <assad.montasser@ow2.org>
#   Martin Hamant <martin.hamant@ow2.org>
#

import argparse
import hashlib
import json
import logging
import time

from grimoirelab_toolkit.datetime import datetime_utcnow

from collections import Counter

from grimoire_elk.elastic import ElasticSearch
from grimoire_elk.elastic_mapping import Mapping as BaseMapping

DEFAULT_BULK_SIZE = 100
DEFAULT_WAIT_TIME = 10

def get_params():
    parser = argparse.ArgumentParser(usage="usage: scancode2es [options]",
                                     description="Import Scancode aggregated metrics in ElasticSearch")
    parser.add_argument("--bulk-size", default=DEFAULT_BULK_SIZE, type=int,
                        help="Number of items uploaded per bulk")
    parser.add_argument("--wait-time", default=DEFAULT_WAIT_TIME, type=int,
                        help="Seconds to wait in case ES is not ready")
    parser.add_argument("-u", "--uri", help="URI for Scancode result")
    parser.add_argument("-p", "--project", help="Project name")
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

def load_report(uri):
    try:

        with open(uri) as json_file:
            data = json.load(json_file)

    except FileNotFoundError:
            pass

    return data

def fetch_scancode(uri, project):
    """
    Fetch the metrics from Scancode

    """

    licensesCount = 0
    noLicenseCount = 0

    metrics = load_report(uri)

    licensesNames = []

    for scannedFile in metrics['files']:

        if 'licenses' in scannedFile:  # licenses could not appear because of scancode error
            if scannedFile['type'] == 'file':
                if scannedFile['licenses']:

                    for license in scannedFile['licenses']:
                        licensesNames.append(license['short_name'])
                else:
                    noLicenseCount += 1


    fileCount = metrics['files_count'] # total files

    licensesCount = fileCount - noLicenseCount # Number of files with licenses (somes files may have several licenceNames)

    uniqueLicenses = len(Counter(licensesNames)) # Number of unique licenses (some licenses may be for several files)

    timestamp = datetime_utcnow().isoformat()

    # enrich fields for ES: Unique licenses
    eitem = {
        'project': project,
        'metric_class': 'scancode',
        'metric_type': 'scancode',
        'metric_id': 'scancode_unique_lic',
        'metric_desc': 'Number of unique licenses',
        'metric_name': 'scancode_unique_lic',
        'metric_es_value': uniqueLicenses,
        'metric_es_compute': 'sample',
        'metric_value': uniqueLicenses,
        'metric_es_value_weighted': uniqueLicenses,
        'datetime': timestamp,
        'scancode': uniqueLicenses
    }

    eitem['uuid'] = uuid(eitem['metric_id'], eitem['project'], eitem['datetime'])

    yield eitem

    msg = "Metrics {} from component {} fetched".format(eitem, project)
    logging.debug(msg)

    # enrich fields for ES: Number of files with licenses
    eitem = {
        'project': project,
        'metric_class': 'scancode',
        'metric_type': 'scancode',
        'metric_id': 'scancode_number_lic',
        'metric_desc': 'Number of files with licenses',
        'metric_name': 'scancode_number_lic',
        'metric_es_value': licensesCount,
        'metric_es_compute': 'sample',
        'metric_value': licensesCount,
        'metric_es_value_weighted': licensesCount,
        'datetime': timestamp,
        'scancode': licensesCount
    }

    eitem['uuid'] = uuid(eitem['metric_id'], eitem['project'], eitem['datetime'])

    yield eitem

    msg = "Metrics {} from component {} fetched".format(eitem, project)
    logging.debug(msg)

    # enrich fields for ES: Number of files without licenses
    eitem = {
        'project': project,
        'metric_class': 'scancode',
        'metric_type': 'scancode',
        'metric_id': 'scancode_files_no_lic',
        'metric_desc': 'Number of files without licenses',
        'metric_name': 'scancode_files_no_lic',
        'metric_es_value': noLicenseCount,
        'metric_es_compute': 'sample',
        'metric_value': noLicenseCount,
        'metric_es_value_weighted': noLicenseCount,
        'datetime': timestamp,
        'scancode': noLicenseCount
    }

    eitem['uuid'] = uuid(eitem['metric_id'], eitem['project'], eitem['datetime'])

    yield eitem

    msg = "Metrics {} from component {} fetched".format(eitem, project)
    logging.debug(msg)


    # enrich fields for ES: Number of files
    eitem = {
        'project': project,
        'metric_class': 'scancode',
        'metric_type': 'scancode',
        'metric_id': 'scancode_number_files',
        'metric_desc': 'Number of files',
        'metric_name': 'scancode_number_files',
        'metric_es_value': fileCount,
        'metric_es_compute': 'sample',
        'metric_value': fileCount,
        'metric_es_value_weighted': fileCount,
        'datetime': timestamp,
        'scancode': fileCount
    }

    eitem['uuid'] = uuid(eitem['metric_id'], eitem['project'], eitem['datetime'])

    yield eitem

    msg = "Metrics {} from component {} fetched".format(eitem, project)
    logging.debug(msg)

    # enrich fields for ES: Ratio of files with licenses compared to total files
    ratio = licensesCount / fileCount

    eitem = {
        'project': project,
        'metric_class': 'scancode',
        'metric_type': 'scancode',
        'metric_id': 'scancode_ratio_no_lic',
        'metric_desc': 'Ratio of files with licenses compared to total files',
        'metric_name': 'scancode_number_files',
        'metric_es_value': ratio,
        'metric_es_compute': 'sample',
        'metric_value':ratio ,
        'metric_es_value_weighted': ratio,
        'datetime': timestamp,
        'scancode': ratio
    }

    eitem['uuid'] = uuid(eitem['metric_id'], eitem['project'], eitem['datetime'])

    yield eitem

    msg = "Metrics {} from component {} fetched".format(eitem, project)
    logging.debug(msg)


if __name__ == '__main__':

    ARGS = get_params()
    if ARGS.debug:
        logging.basicConfig(level=logging.DEBUG, format='%(asctime)s %(message)s')
        logging.debug("Debug mode activated")
    else:
        logging.basicConfig(level=logging.INFO, format='%(asctime)s %(message)s')

    logging.info("Importing items from Scancode to %s/%s", ARGS.elastic_url, ARGS.index)

    elastic = __init_index(ARGS.elastic_url, ARGS.index, ARGS.wait_time)
    elastic.max_items_bulk = min(ARGS.bulk_size, elastic.max_items_bulk)

    # OW2 specific: fetch from Scancode
    scancode_metrics = fetch_scancode(ARGS.uri, ARGS.project)

    if scancode_metrics:
        logging.info("Uploading Scancode metrics to Elasticsearch")

        counter = 0
        to_upload = []
        for item in scancode_metrics:

            to_upload.append(item)

            if len(to_upload) == ARGS.bulk_size:
                elastic.bulk_upload(to_upload, "uuid")
                counter += len(to_upload)
                to_upload = []

        if len(to_upload) > 0:
            counter += len(to_upload)
            elastic.bulk_upload(to_upload, "uuid")