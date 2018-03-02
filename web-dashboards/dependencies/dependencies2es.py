#!/usr/bin/env python3
# -*- coding: utf-8 -*-
#
# Copy JSON items with dependencies information to Elasticsearch
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
import hashlib
import json
import logging

from grimoire_elk.elk.elastic import ElasticSearch


def get_params():
    desc_help = 'Import JSON items with CROSSMINER dependencies in ElasticSearch'
    parser = argparse.ArgumentParser(usage="usage:dependencies2es [options]",
                                     description=desc_help)
    parser.add_argument("-f", "--file", required=True, help="JSON file with the dependencies")
    parser.add_argument("-e", "--elastic-url", required=True, help="ElasticSearch URL")
    parser.add_argument("-i", "--index", required=True,
                        help="ElasticSearch index in which to import the mongodb items")
    parser.add_argument('-g', '--debug', dest='debug', action='store_true')
    parser.add_argument('-p', '--project', help="Name of the project for the dependencies")
    args = parser.parse_args()

    if not args.file:
        parser.error("--file needed")

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


def find_version(dependency):

    version = None

    if len(dependency) == 3:
        if 'lower-version' in dependency[2]:
            version = dependency[2]['lower-version'] + ":" + dependency[2]['upper-version']
        elif 'version' in dependency[2]:
            version = dependency[2]['version']

    return version


def get_unique_id(dependency):
    # Sample dependency
    # [
    #     "bundle://eclipse/org.eclipse.ant.tests.ui/3.9.100.v20160418-1631",
    #     "bundle://eclipse/org.apache.ant/1.9.6.v201510161327",
    #     {
    #         "lower-version": "[1.9.2",
    #         "resolution": "mandatory",
    #         "resolved": "true",
    #         "upper-version": "(-1",
    #         "visibility": "private"
    #     }
    # ]
    version = find_version(dependency)
    if version:
        uuid_val = uuid(dependency[0], dependency[1], version)
    else:
        uuid_val = uuid(dependency[0], dependency[1])

    return uuid_val


def enrich_item(item):
    eitem = {}

    eitem['origin'] = item[0]
    eitem['dependency'] = item[1]
    version = find_version(item)
    eitem['dependency_ver'] = None
    if version:
        eitem['dependency_ver'] = item[1] + "_" + version
    eitem['uuid'] = get_unique_id(item)

    return eitem


def fetch_dependencies(dependencies_file, project=None):
    logging.info("Import all JSON items with dependencies from: %s", dependencies_file)

    with open(dependencies_file) as dfile:
        dependencies = json.load(dfile)

        for dependency in dependencies:
            eitem = enrich_item(dependency)
            eitem['project'] = project
            yield eitem


if __name__ == '__main__':

    args = get_params()

    if args.debug:
        logging.basicConfig(level=logging.DEBUG, format='%(asctime)s %(message)s')
        logging.debug("Debug mode activated")
    else:
        logging.basicConfig(level=logging.INFO, format='%(asctime)s %(message)s')

    logging.info("Importing items from %s to %s/%s", args.file, args.elastic_url, args.index)

    elastic = ElasticSearch(args.elastic_url, args.index)

    items = fetch_dependencies(args.file, args.project)

    if items:
        logging.info("Loading dependencies in Elasticsearch ...")
        elastic.bulk_upload_sync(items, "uuid")
        logging.info("Import completed.")
