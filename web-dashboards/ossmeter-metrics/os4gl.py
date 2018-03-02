#!/usr/bin/env python3
# -*- coding: utf-8 -*-
#
# Make a GrimoireLab index compatible with a OSSMeter metrics one
#
# Copyright (C) 2017 Bitergia
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
import json
import logging

import requests


from grimoire_elk.elk.elastic import ElasticSearch

# Filters to be supported from OSSMeter metrics items
OSS_FILTERS = ['project', 'metric_class', 'metric_name', 'metric_es_name']
# For ES6 the headers are needed in POST and PUT requests
HEADERS_JSON = {"Content-Type": "application/json"}


def get_params():
    desc = "Convert a GrimoireLab Elasticseach index to make "
    desc += "it compatible (share filters) with a OSSMeter metrics index"
    parser = argparse.ArgumentParser(usage="usage: os4gl.py [options]",
                                     description=desc)
    parser.add_argument("-e", "--elastic-url", required=True, help="ElasticSearch URL")
    parser.add_argument("--ossmeter-index", required=True,
                        help="ElasticSearch OSSMeter metrics index")
    parser.add_argument("--grimoirelab-index", required=True,
                        help="ElasticSearch GrimoireLab index")
    parser.add_argument("-o", "--output-index", required=True,
                        help="ElasticSearch GrimoireLab new index compatible" +
                             " with OSSMeter metrics")

    parser.add_argument('-g', '--debug', dest='debug', action='store_true')
    args = parser.parse_args()

    return args


def find_ossmeter_filters(elastic_url, ossmeter_index):

    filters_data = {}

    elastic = ElasticSearch(elastic_url, ossmeter_index)

    def build_query(filter_name):
        # ES query
        query = '''
        {
            "size": 0,
            "query": {
                "bool": {
                }
            },
            "aggs": {
                "2": {
                  "terms": {
                    "field": "%s.keyword",
                    "size": 1000,
                    "order": {
                      "_count": "desc"
                    }
                  }
                }
            }
        } ''' % (filter_name)

        return query

    for filter_name in OSS_FILTERS:
        query = build_query(filter_name)
        url = elastic.index_url + "/_search"
        res = requests.post(url, data=query, headers=HEADERS_JSON)
        res.raise_for_status()
        filter_data = [f['key'] for f in res.json()['aggregations']['2']['buckets']
                       if not f['key'].find(":") > 0]
        # print(filter_data)
        filters_data[filter_name] = filter_data

    return filters_data


def get_elastic_items(elastic, elastic_scroll_id=None):
    """ Get the items from the index """

    scroll_size = 1000

    if not elastic:
        return None

    url = elastic.index_url
    max_process_items_pack_time = "5m"  # 10 minutes
    url += "/_search?scroll=%s&size=%i" % (max_process_items_pack_time,
                                           scroll_size)

    if elastic_scroll_id:
        # Just continue with the scrolling
        url = elastic.url
        url += "/_search/scroll"
        scroll_data = {
            "scroll": max_process_items_pack_time,
            "scroll_id": elastic_scroll_id
        }
        res = requests.post(url, data=json.dumps(scroll_data))
    else:
        query = """
        {
            "query": {
                "bool": {
                    "must": []
                }
            }
        }
        """

        # logging.debug("%s\n%s", url, json.dumps(json.loads(query), indent=4))
        res = requests.post(url, data=query)

    rjson = None
    try:
        rjson = res.json()
    except Exception:
        logging.error("No JSON found in %s", res.text)
        logging.error("No results found from %s", url)

    return rjson


# Items generator
def fetch(elastic, filters):
    """ Fetch the items from raw or enriched index """

    logging.debug("Creating a elastic items generator.")

    elastic_scroll_id = None

    while True:
        rjson = get_elastic_items(elastic, elastic_scroll_id)

        if rjson and "_scroll_id" in rjson:
            elastic_scroll_id = rjson["_scroll_id"]

        if rjson and "hits" in rjson:
            if not rjson["hits"]["hits"]:
                break
            for hit in rjson["hits"]["hits"]:
                item = hit['_source']
                item.update(filters)
                yield item
        else:
            logging.error("No results found from %s", elastic.index_url)
            break

    return


def add_filters_item(filters, es_url, es_in_index, es_out_index):

    elastic_in = ElasticSearch(es_url, es_in_index)
    elastic_out = ElasticSearch(es_url, es_out_index)

    # Time to just copy from in_index to our_index
    total = elastic_out.bulk_upload_sync(fetch(elastic_in, filters), "uuid")

    # logging.info("Total items copied with filters: %i", total)


if __name__ == '__main__':

    ARGS = get_params()

    if ARGS.debug:
        logging.basicConfig(level=logging.DEBUG, format='%(asctime)s %(message)s')
        logging.debug("Debug mode activated")
    else:
        logging.basicConfig(level=logging.INFO, format='%(asctime)s %(message)s')

    logging.info("Finding the filters in OSSMeter metrics index: %s", ARGS.ossmeter_index)

    filters = find_ossmeter_filters(ARGS.elastic_url, ARGS.ossmeter_index)

    logging.info("Adding to items from %s to %s with filters for OSSMeter",
                 ARGS.grimoirelab_index, ARGS.output_index)

    add_filters_item(filters, ARGS.elastic_url, ARGS.grimoirelab_index, ARGS.output_index)

    logging.info("All done")
