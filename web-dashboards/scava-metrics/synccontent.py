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
import json
import logging
import requests

from grimoirelab_toolkit.uris import urijoin


ES6_HEADER = {"Content-Type": "application/json", "kbn-xsrf": "true"}


def fetch_projects_from_scava(base_url):
    """Fetch the list of projects from SCAVA API"""

    api_url = urijoin(base_url, "projects")
    res = requests.get(api_url)

    try:
        res.raise_for_status()
    except requests.exceptions.HTTPError:
        msg = "Impossible to fetch projects from {}: {}".format(api_url, res.json())
        logging.error(msg)
        return None

    res_json = res.json()

    projects = [r['shortName'] for r in res_json]
    return projects


def fetch_projects_from_es_index(elastic_url, index):
    """Fetch the list of projects from Elasticsearch index"""

    api_url = urijoin(elastic_url, index, '_search')
    es_query = """
    {
        "size": 0,
        "aggs": {
            "projects": {
                "terms": {
                    "field": "project",
                    "size": 5000
                }
            }
        }
    }"""
    res = requests.get(api_url, data=es_query, headers=ES6_HEADER, verify=False)

    try:
        res.raise_for_status()
    except requests.exceptions.HTTPError:
        msg = "Impossible to fetch projects from {}: {}".format(api_url, res.json())
        logging.error(msg)
        return None

    res_json = res.json()

    try:
        buckets = res_json['aggregations']['projects']['buckets']
        projects = [bucket['key'] for bucket in buckets]
    except:
        projects = []

    return projects


def delete_data_from_es_index(elastic_url, index, project):
    """Delete the metrics of a target project from the index"""

    api_url = urijoin(elastic_url, index, '_delete_by_query?refresh')
    es_query = """
        {
            "query": {
                "bool": {
                    "filter": {
                        "terms": {
                            "project": ["%s"]
                        
                        }
                    }
                }
            }
        }""" % project
    res = requests.post(api_url, data=es_query, headers=ES6_HEADER, verify=False)
    try:
        res.raise_for_status()
        msg = "Data deleted from index {} for project {}".format(index, project)
        logging.info(msg)
    except requests.exceptions.HTTPError as ex:
        msg = "Error updating deleted data for {}".format(api_url, res.json())
        logging.error(msg)
        logging.error(ex)
        return


def sync_content(base_url, elastic_url, indexes):
    """Sync the ES content with the data stored in the SCAVA API.

    The project names are retrieved from the ES indexes and their names are checked
    against the ones returned from the SCAVA API. In case a project is not found in
    the SCAVA API, its metrics are deleted from the ES index."""

    scava_projects = fetch_projects_from_scava(base_url)

    if not scava_projects:
        logging.error("No projects found")
        return

    for index in indexes:
        projects_in_index = fetch_projects_from_es_index(elastic_url, index)

        for prj in projects_in_index:
            if prj in scava_projects:
                continue

            delete_data_from_es_index(elastic_url, index, prj)


def get_params():
    parser = argparse.ArgumentParser(usage="usage: synccontent [options]",
                                     description="Sync the ES and SCAVA content")
    parser.add_argument("-u", "--url", default='http://localhost:8182',
                        help="URL for Scava API REST (default: http://localhost:8182)")
    parser.add_argument("-e", "--elastic-url", default="http://localhost:9200",
                        help="ElasticSearch URL (default: http://localhost:9200)")
    parser.add_argument("-i", "--indexes", nargs='+', help="ElasticSearch indexes to sync")
    parser.add_argument('-g', '--debug', dest='debug', action='store_true')
    args = parser.parse_args()

    return args


if __name__ == '__main__':

    ARGS = get_params()
    if ARGS.debug:
        logging.basicConfig(level=logging.DEBUG, format='%(asctime)s %(message)s')
        logging.debug("Debug mode activated")
    else:
        logging.basicConfig(level=logging.INFO, format='%(asctime)s %(message)s')

    sync_content(ARGS.url, ARGS.elastic_url, ARGS.indexes)
