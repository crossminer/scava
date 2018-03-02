#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# Check the total number of items in Elasticsearch uploaded by mongo2es
# using the OSSMeter mongodb restored from data/mongo-ossmeter-dump.tgz

import sys

import requests

ITEMS_EXPECTED = 105737
ES_URL_SEARCH = "http://localhost:9200/ossmeter/_search?size=0"


if __name__ == '__main__':

    res = requests.get(ES_URL_SEARCH)
    items_found = res.json()['hits']['total']

    if items_found != ITEMS_EXPECTED:
        print("FAIL - Items found: %i. Items expected: %i." % (items_found, ITEMS_EXPECTED))
        sys.exit(1)
    else:
        print("Number of items correct", ITEMS_EXPECTED)
