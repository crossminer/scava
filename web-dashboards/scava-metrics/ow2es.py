#!/usr/bin/env python3
# -*- coding: utf-8 -*-
#
# Get metrics from OW2 backends and publish them in Elasticsearch
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
import json

from dateutil import parser

from grimoire_elk.elastic import ElasticSearch

from perceval.backends.core.sonarqube import Sonar

SONARQUBE_ARGS= ["{component: 'com.docdoku:docdoku-plm', base_url: 'http://sonar.docdoku.net'}",
                "{component: 'ASM:asm', base_url: 'https://sonarqube.ow2.org'}",
                "{component: 'clif-legacy', base_url: 'https://sonarqube.ow2.org'}",
                "{component: 'org.ow2.sat4j:org.ow2.sat4j.pom', base_url: 'https://sonarqube.ow2.org'}",
                "{component: 'bonita-studio', base_url: 'https://sonarqube.ow2.org'}",
                "{component: 'org.xwiki.contrib:application-antispam', base_url:'None'}",
                "{component: 'fr.paris.lutece:site-ppsa', base_url: 'http://http://dev.lutece.paris.fr'}",
                "{component: 'it.eng.knowage:knowage', base_url:'None'}",
                "{component: 'fr.inria.gforge.spoon:spoon-core', base_url:'https://sonarqube.ow2.org'}"
            ]


def fetch_sonarqube():
    """
    Fetch the metrics from Sonarqube

    """
    # Get the metrics for all projects
    for project_sonar in SONARQUBE_ARGS:

            project = json.load(project_sonar)

            sonar_metrics = Sonar(component=project["component"], base_url=project["base_url"])

            logging.info("Getting metrics for %s" % sonar_metrics['data']['shortName'])
            for enriched_metric in enrich_metrics(sonar_metrics.fetch()):
                yield enriched_metric



