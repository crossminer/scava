# -*- coding: utf-8 -*-
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
# Foundation, 51 Franklin Street, Fifth Floor, Boston, MA 02110-1335, USA.
#
# Authors:
#     Alvaro del Castillo <acs@bitergia.com>
#

import json
import logging
import urllib.parse

from grimoirelab_toolkit.datetime import str_to_datetime
from grimoirelab_toolkit.uris import urijoin

from ...backend import (Backend,
                        BackendCommand,
                        BackendCommandArgumentParser)
from ...client import HttpClient


CATEGORY_METRIC = 'metric'
CATEGORY_PROJECT = 'project'

logger = logging.getLogger(__name__)



class Scava(Backend):
    """Scava backend for Perceval.

    This class retrieves the projects and metrics from a Scava URL. To initialize
    this class an URL must be provided with the Scava sever.
    The origin of the data will be set to this URL.

    It uses v1 API to get projects and metrics.

    :param url: Scava URL

    :param tag: label used to mark the data
    :param archive: archive to store/retrieve items
    """
    version = '0.0.1'

    CATEGORIES = [CATEGORY_METRIC, CATEGORY_PROJECT]

    def __init__(self, url, project=None, tag=None, archive=None):
        origin = url

        super().__init__(origin, tag=tag, archive=archive)
        self.project = project
        self.url = url
        self.client = None

    def fetch(self, category=CATEGORY_METRIC):
        """Fetch items from the Scava url.

        The method retrieves, from a Scava URL, the set of items
        of the given `category`.

        :param category: the category of items to fetch
        :returns: a generator of items
        """

        if not self.project:
            # If a project has not been provided, return the list of all projects
            category = CATEGORY_PROJECT

        kwargs = {"project": self.project}  # backend args
        items = super().fetch(category, **kwargs)

        return items

    def fetch_items(self, category, **kwargs):
        """Fetch items

        :param category: the category of items to fetch
        :param kwargs: backend arguments

        :returns: a generator of items
        """
        project = kwargs['project']

        if category == CATEGORY_PROJECT:
            logger.info("Looking for projects at url '%s'", self.url)
        else:
            logger.info("Looking for '%s' project metrics at url '%s'", project, self.url)

        nitems = 0  # number of items processed

        for raw_items in self.client.get_items(category, project):
            items = json.loads(raw_items)
            items = [items] if isinstance(items, dict) else items
            for item in items:
                if category == CATEGORY_METRIC:
                    item['updated'] = self.project_updated
                    item['project'] = self.project
                yield item
                nitems += 1

        logger.info("Total number of items: %i (%s)", nitems, category)

    @staticmethod
    def metadata_category(item):
        """Extracts the category from a Mattermost item.

        This backend only generates one type of item which is
        'post'.
        """
        category = None

        if 'name' in item:
            category = CATEGORY_PROJECT
        elif '_id' in item:
            mid = CATEGORY_METRIC
        else:
            raise RuntimeError("Can not detect category for", item)

        return category

    @classmethod
    def has_archiving(cls):
        """Returns whether it supports archiving items on the fetch process.

        :returns: this backend supports items archive
        """
        return True

    @classmethod
    def has_resuming(cls):
        """Returns whether it supports to resume the fetch process.

        :returns: this backend supports items resuming
        """
        return False

    @staticmethod
    def metadata_id(item):
        """Extracts the identifier from a Scava item."""

        if 'id' in item:
            # the item is a metric
            mid = item['id'] + item['updated'] + item['project']
        elif 'name' in item:
            # the item is a project
            mid = item['name']
        else:
            raise TypeError("Can not extract metadata_id from", item)

        return mid

    @staticmethod
    def metadata_updated_on(item):
        """Extracts the update time from a Scava item.

        The timestamp is extracted from 'end' field.
        This date is converted to a perceval format using a float value.

        :param item: item generated by the backend

        :returns: a UNIX timestamp
        """

        if 'executionInformation' in item:
            updated = item['executionInformation']['lastExecuted']
        elif 'id' in item:
            updated = item['updated']
        else:
            raise TypeError("Can not extract metadata_updated_on from", item)

        return float(str_to_datetime(updated).timestamp())

    @staticmethod
    def metadata_category(item):
        """Extracts the category from a Scava item.

        This backend generates items types 'project', 'metric'.
        To guess the type of item, the code will look
        for unique fields.
        """
        if 'datatable' in item:
            category = CATEGORY_METRIC
        elif 'parent' in item:
            category = CATEGORY_PROJECT
        else:
            raise TypeError("Could not define the category of item " + str(item))

        return category

    def _init_client(self, from_archive=False):
        """Init client"""

        client = ScavaClient(self.url, self.project, self.archive, from_archive)
        if self.project:
            self.project_updated = client.get_project_update(self.project)
        return client


class ScavaClient(HttpClient):
    """Scava API client.

    This class implements a simple client to retrieve metrics from
    projects in a Scava site.

    :param url: URL of Scava
    :param project: Scava project from which to get the data
    :param archive: an archive to store/read fetched data
    :param from_archive: it tells whether to write/read the archive

    :raises HTTPError: when an error occurs doing the request
    """

    FIRST_PAGE = 1  # Initial page in Scava API
    ITEMS_PER_PAGE = 20  # Items per page in Scava API
    API_PATH = '/'

    def __init__(self, url, project=None, archive=None, from_archive=False):
        super().__init__(url, archive=archive, from_archive=from_archive)
        self.project = project
        self.api_metrics_url = urijoin(self.base_url, "metrics")
        self.api_projects_url = urijoin(self.base_url, "projects")

    def get_project_update(self, project_name=None):

        updated = None

        if project_name is None:
            return updated

        api = self.api_projects_url
        projects = self.fetch(api)
        for project in json.loads(projects):
            if project_name in [project['name'], project['shortName']]:
                # project['shortName'] is used for building the API URLs so it is the one used in general
                updated = project['executionInformation']['lastExecuted']

        return updated

    def get_items(self, category=CATEGORY_METRIC, project=None):
        """Retrieve all items for category """

        metrics = None  # Metrics available in CROSSMINER platform

        if category == CATEGORY_PROJECT:
            # Return the the list of projects
            api = self.api_projects_url
        elif category == CATEGORY_METRIC:
            # Get all metrics definitions and then find the values for the current project
            api_metrics = self.api_metrics_url
            metrics = json.loads(self.fetch(api_metrics))
        else:
            raise ValueError(category + ' not supported in Scava')

        if category == CATEGORY_PROJECT:
            logger.debug("Scava client calls APIv1: %s", api)
            projects = self.fetch(api)
            yield projects
        else:
            for metric in metrics:
                metric_id = metric['id']
                api = urijoin(self.api_projects_url, "/p/%s/m/%s" % (project, metric_id))
                logger.debug("Scava client calls API: %s", api)
                project_metric = self.fetch(api)
                yield project_metric

    def fetch(self, url, payload=None):
        """Return the textual content associated to the Response object"""

        response = super().fetch(url, payload)

        return response.text


class ScavaCommand(BackendCommand):
    """Class to run Scava backend from the command line."""

    BACKEND = Scava

    @staticmethod
    def setup_cmd_parser():
        """Returns the Scava argument parser."""

        parser = BackendCommandArgumentParser(archive=True)
        # Required arguments
        parser.parser.add_argument('url', nargs='?',
                                   default="http://localhost:8182",
                                   help="Scava REST API URL (default: http://localhost:8182")
        # Optional arguments
        group = parser.parser.add_argument_group('Scava arguments')
        group.add_argument('--project', dest='project',
                           required=False,
                           help="Name of the Scava project")

        return parser
