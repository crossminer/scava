#!/usr/bin/env python3
# -*- coding: utf-8 -*-
#
# Copyright (C) 2015-2017 Bitergia
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

import re
import sys
import unittest
from datetime import datetime

import httpretty

# Make sure we use our code and not any other could we have installed
sys.path.insert(0, '..')
from perceval.backend import BackendError, BackendCommandArgumentParser
from perceval.backends.crossminer.crossminer import (Crossminer, CrossminerClient, CrossminerCommand)
from base import TestCaseBackendArchive

CROSSMINER_API_REST_URL = 'http://localhost'

CROSSMINER_CATEGORIES = ['project', 'metric']


def read_file(filename, mode='r'):
    with open(filename, mode) as f:
        content = f.read()
    return content


class HTTPServer():

    requests_http = []  # requests done to the server

    @classmethod
    def routes(cls, empty=False):
        """Configure in http the routes to be served"""

        crossminer_bodies = {}  # dict with all the bodies to be returned by category

        for category in CROSSMINER_CATEGORIES:
            crossminer_bodies[category] = {}
            # First page for each category
            crossminer_bodies[category] = read_file('data/crossminer/crossminer_' + category + 's.json')
        if empty:
            for category in CROSSMINER_CATEGORIES:
                crossminer_bodies[category] = read_file('data/crossminer/crossminer_' + category + 's_empty.json')

        metric_body = read_file('data/crossminer/crossminer_metric.json')  # single metric

        def request_callback(method, uri, headers):
            body = ''
            # Specific item. Always return the same for each category.
            for category in CROSSMINER_CATEGORIES:
                if '/m/' in uri:
                    # Getting a metric for a project
                    body = metric_body
                elif category in uri:
                    body = crossminer_bodies[category]
                    break

            HTTPServer.requests_http.append(httpretty.last_request())

            return (200, headers, body)

        httpretty.register_uri(httpretty.GET,
                               re.compile(CROSSMINER_API_REST_URL + ".*"),
                               responses=[
                                   httpretty.Response(body=request_callback)
                               ])


class TestCrossminerBackend(unittest.TestCase):
    """Crossminer backend tests"""

    def test_initialization(self):
        """Test whether attributes are initializated"""

        crossminer = Crossminer(CROSSMINER_API_REST_URL, tag='test')

        self.assertEqual(crossminer.url, CROSSMINER_API_REST_URL)
        self.assertEqual(crossminer.origin, CROSSMINER_API_REST_URL)
        self.assertEqual(crossminer.tag, 'test')
        self.assertIsNone(crossminer.client)

        # When tag is empty or None it will be set to
        # the value in url
        crossminer = Crossminer(CROSSMINER_API_REST_URL)
        self.assertEqual(crossminer.url, CROSSMINER_API_REST_URL)
        self.assertEqual(crossminer.origin, CROSSMINER_API_REST_URL)
        self.assertEqual(crossminer.tag, CROSSMINER_API_REST_URL)

        crossminer = Crossminer(CROSSMINER_API_REST_URL, tag='')
        self.assertEqual(crossminer.url, CROSSMINER_API_REST_URL)
        self.assertEqual(crossminer.origin, CROSSMINER_API_REST_URL)
        self.assertEqual(crossminer.tag, CROSSMINER_API_REST_URL)

    def test_has_archiving(self):
        """Test if it returns True when has_archiving is called"""

        self.assertEqual(Crossminer.has_archiving(), True)

    def test_has_resuming(self):
        """Test if it returns True when has_resuming is called"""

        self.assertEqual(Crossminer.has_resuming(), True)

    def __check_metric_contents(self, items):
        self.assertEqual(items[0]['data']['id'], 'coreCommittersChurnBar')
        self.assertEqual(items[0]['origin'], CROSSMINER_API_REST_URL)
        self.assertEqual(items[0]['uuid'], 'c03ab0aaaac9ba96a6e498f97136706ab1a49e65')
        self.assertEqual(items[0]['updated_on'], 1526601600.0)
        self.assertEqual(items[0]['category'], 'metric')
        self.assertEqual(items[0]['tag'], CROSSMINER_API_REST_URL)
        self.assertEqual(items[0]['backend_name'], 'Crossminer')
        self.assertEqual(items[1]['data']['id'], 'coreCommittersChurnBar')

    def __check_project_contents(self, items):
        self.assertEqual(items[0]['data']['name'], 'perceval')
        self.assertEqual(items[0]['origin'], CROSSMINER_API_REST_URL)
        self.assertEqual(items[0]['uuid'], 'c4fdca17a5ba60e869bb56415cef2a3b211b98c6')
        self.assertEqual(items[0]['updated_on'], 1526601600.0)
        self.assertEqual(items[0]['category'], 'project')
        self.assertEqual(items[0]['tag'], CROSSMINER_API_REST_URL)
        self.assertEqual(items[1]['data']['name'], 'GrimoireELK')

    @httpretty.activate
    def __test_fetch(self, category='project'):
        """Test whether the metrics are returned"""

        project_items = 2
        metrics_items = 2

        HTTPServer.routes()
        prev_requests_http = len(HTTPServer.requests_http)

        project = None

        if category == 'metric':
            project = 'perceval'

        # Test fetch events with their reviews
        crossminer = Crossminer(CROSSMINER_API_REST_URL, project=project)

        items = [item for item in crossminer.fetch(category=category)]

        if category == 'metric':
            self.assertEqual(len(items), metrics_items)
            self.__check_metric_contents(items)
        elif category == 'project':
            self.assertEqual(len(items), project_items)
            self.__check_project_contents(items)

        # Check requests
        if category == 'metric':
            expected = [{}, {}, {}, {}]  # for getting a metric we need 4 calls
        else:
            expected = [{}]  # for getting the projects we need 1 call

        self.assertEqual(len(HTTPServer.requests_http) - prev_requests_http, len(expected))

        for i in range(len(expected)):
            self.assertDictEqual(HTTPServer.requests_http[i].querystring, expected[i])

    def test_fetch_metrics(self):
        self.__test_fetch(category='metric')

    def test_fetch_projects(self):
        self.__test_fetch(category='project')

    @httpretty.activate
    def tests_wrong_metadata_updated_on(self):

        HTTPServer.routes()
        prev_requests_http = len(HTTPServer.requests_http)

        # Test fetch events with their reviews
        crossminer = Crossminer(CROSSMINER_API_REST_URL)

        items = [page for page in crossminer.fetch(category="project")]
        item = items[0]

        item.pop('executionInformation', None)

        with self.assertRaises(TypeError):
            crossminer.metadata_updated_on(item)

    @httpretty.activate
    def tests_wrong_metadata_category(self):

        HTTPServer.routes()
        prev_requests_http = len(HTTPServer.requests_http)

        # Test fetch events with their reviews
        crossminer = Crossminer(CROSSMINER_API_REST_URL, project='perceval')

        items = [page for page in crossminer.fetch(category="metric")]
        item = items[0]['data']

        item.pop('datatable', None)

        with self.assertRaises(TypeError):
            crossminer.metadata_category(item)

    def off_test_fetch_wrong_category(self):
        with self.assertRaises(BackendError):
            self.__test_fetch(category='wrong')

    @httpretty.activate
    def test_fetch_empty(self):
        """Test whether it works when no items are fetched"""

        HTTPServer.routes(empty=True)

        crossminer = Crossminer(CROSSMINER_API_REST_URL)
        events = [event for event in crossminer.fetch()]

        self.assertEqual(len(events), 0)


class TestCrossminerBackendArchive(TestCaseBackendArchive):
    """Crossminer backend tests using an archive"""

    def setUp(self):
        super().setUp()
        self.backend_write_archive = Crossminer(CROSSMINER_API_REST_URL, archive=self.archive)
        self.backend_read_archive = Crossminer(CROSSMINER_API_REST_URL, archive=self.archive)

    @httpretty.activate
    def __test_fetch_from_archive(self, category='event'):
        """Test whether the events are returned from archive"""

        HTTPServer.routes()
        self._test_fetch_from_archive(category=category)

    def test_fetch_events(self):
        self.__test_fetch_from_archive(category='event')

    def test_fetch_metrics(self):
        self.__test_fetch_from_archive(category='metric')

    def test_fetch_projects(self):
        self.__test_fetch_from_archive(category='project')

    @httpretty.activate
    def test_fetch_empty_from_archive(self):
        """Test whether it works when no items are fetched from archive"""

        HTTPServer.routes(empty=True)
        self._test_fetch_from_archive()


class TestCrossminerCommand(unittest.TestCase):
    """Tests for CrossminerCommand class"""

    def test_backend_class(self):
        """Test if the backend class is Crossminer"""

        self.assertIs(CrossminerCommand.BACKEND, Crossminer)

    def test_setup_cmd_parser(self):
        """Test if it parser object is correctly initialized"""

        parser = CrossminerCommand.setup_cmd_parser()
        self.assertIsInstance(parser, BackendCommandArgumentParser)

        args = [CROSSMINER_API_REST_URL,
                '--category', 'metric',
                '--tag', 'test',
                '--no-archive',
                '--project', 'perceval']

        parsed_args = parser.parse(*args)
        self.assertEqual(parsed_args.url, CROSSMINER_API_REST_URL)
        self.assertEqual(parsed_args.category, 'metric')
        self.assertEqual(parsed_args.tag, 'test')
        self.assertEqual(parsed_args.no_archive, True)
        self.assertEqual(parsed_args.project, "perceval")


class TestCrossminerClient(unittest.TestCase):
    """Crossminer API client tests

    These tests not check the body of the response, only if the call
    was well formed and if a response was obtained. Due to this, take
    into account that the body returned on each request might not
    match with the parameters from the request.
    """
    @httpretty.activate
    def test_init(self):
        """Test initialization"""
        client = CrossminerClient(CROSSMINER_API_REST_URL)

    @httpretty.activate
    def test_get_items(self):
        """Test get_events API call"""

        HTTPServer.routes()

        # Set up a mock HTTP server
        body = read_file('data/crossminer/crossminer_metric.json')
        client = CrossminerClient(CROSSMINER_API_REST_URL)
        response = next(client.get_items())
        req = HTTPServer.requests_http[-1]
        self.assertEqual(response, body)
        self.assertEqual(req.method, 'GET')
        self.assertRegex(req.path, '/projects/p/None/m/bugs.cumulativeNewUsers')
        self.assertDictEqual(req.querystring, {})

    @httpretty.activate
    def test_get_wrong_items(self):
        """Test get_events API call"""

        HTTPServer.routes()

        # Set up a mock HTTP server
        body = read_file('data/crossminer/crossminer_metric.json')
        client = CrossminerClient(CROSSMINER_API_REST_URL)

        with self.assertRaises(ValueError):
            _ = next(client.get_items(category='wrong'))

    @httpretty.activate
    def test_call(self):
        """Test get projects API call"""

        HTTPServer.routes()

        # Set up a mock HTTP server
        body = read_file('data/crossminer/crossminer_metric.json')
        client = CrossminerClient(CROSSMINER_API_REST_URL)
        response = client.fetch(CROSSMINER_API_REST_URL + '/projects/p/perceval/m/bugs.cumulativeNewUsers')
        req = HTTPServer.requests_http[-1]
        self.assertEqual(response, body)
        self.assertEqual(req.method, 'GET')
        self.assertEqual(req.path, '/projects/p/perceval/m/bugs.cumulativeNewUsers')
        # Check request params
        self.assertDictEqual(req.querystring, {})


if __name__ == "__main__":
    unittest.main(warnings='ignore')
