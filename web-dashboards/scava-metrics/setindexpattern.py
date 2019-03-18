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
#   Valerio Cosentino <valcos@bitergia.com>
#

import argparse
import copy
import json
import logging
import requests


ES6_HEADER = {"Content-Type": "application/json", "kbn-xsrf": "true"}
KIBANA_SETTINGS_URL = '/api/kibana/settings'


def get_params():
    parser = argparse.ArgumentParser(usage="usage: setindexpattern [options]",
                                     description="Set Kibiter index pattern")
    parser.add_argument("-k", "--kibana-url", default="http://localhost:5601",
                        help="Kibana URL (default: http://localhost:5601)")
    parser.add_argument("-ip", "--index-pattern-id", required=True, help="Default index pattern id to set in Kibiter")
    parser.add_argument('-g', '--debug', dest='debug', action='store_true')
    args = parser.parse_args()

    return args


def configure_kibiter_default_index(kibana_url, index_pattern_id=None):
    kibana_headers = copy.deepcopy(ES6_HEADER)

    kibana_url = kibana_url + KIBANA_SETTINGS_URL
    endpoint_url = kibana_url + '/defaultIndex'

    conn = requests.Session()

    data_value = {"value": index_pattern_id}
    res = conn.post(endpoint_url, headers=kibana_headers, data=json.dumps(data_value))
    try:
        res.raise_for_status()
    except requests.exceptions.HTTPError:
        logging.error("Impossible to set defaultIndex: %s", str(res.json()))
        return

    logging.info("DefaultIndex set to: %s", str(data_value))
    return


if __name__ == '__main__':

    ARGS = get_params()
    if ARGS.debug:
        logging.basicConfig(level=logging.DEBUG, format='%(asctime)s %(message)s')
        logging.debug("Debug mode activated")
    else:
        logging.basicConfig(level=logging.INFO, format='%(asctime)s %(message)s')

    logging.info("Setting index pattern %s on %s", ARGS.index_pattern_id, ARGS.kibana_url)
    configure_kibiter_default_index(ARGS.kibana_url, ARGS.index_pattern_id)