# -*- coding: utf-8 -*-
#
# Copyright (C) 2015-2019 Bitergia
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
#     Assad Montasser <assad.montasser@ow2.org>
#     Valerio Cosentino <valcos@bitergia.com>
#

import json
import logging

from grimoirelab_toolkit.datetime import (datetime_to_utc,
                                          datetime_utcnow)
from grimoirelab_toolkit.uris import urijoin

from ...backend import (Backend,
                        BackendCommand,
                        BackendCommandArgumentParser,
                        uuid)
from ...client import HttpClient
from ...utils import DEFAULT_DATETIME

CATEGORY = "metrics"

SONAR_URL = "https://sonarcloud.io/"
SONAR_API_URL = "api/measures/component?component="

# Range before sleeping until rate limit reset
MIN_RATE_LIMIT = 10
MAX_RATE_LIMIT = 500

PER_PAGE = 100

# Default sleep time and retries to deal with connection/server problems
DEFAULT_SLEEP_TIME = 1
MAX_RETRIES = 5

# For the moment static but should be either parameter, either remove
# list parameter
TARGET_METRIC_FIELDS = [
    "accessors", "new_technical_debt",
    "blocker_violations", "new_it_conditions_to_cover",
    "bugs", "burned_budget", "business_value",
    "class_complexity_distribution", "classes,code_smells",
    "cognitive_complexity", "commented_out_code_lines",
    "comment_lines", "comment_lines_data", "comment_lines_density",
    "class_complexity", "file_complexity","function_complexity",
    "complexity_in_classes", "complexity_in_functions",
    "branch_coverage", "new_it_branch_coverage", "new_branch_coverage",
    "conditions_by_line", "conditions_to_cover",
    "new_conditions_to_cover", "confirmed_issues", "coverage",
    "new_it_coverage", "coverage_line_hits_data",
    "new_coverage", "covered_conditions_by_line", "critical_violations", "complexity", "last_commit_date",
    "directories", "duplicated_blocks", "new_duplicated_blocks", "duplicated_files", "duplicated_lines",
    "duplicated_lines_density", "new_duplicated_lines", "new_duplicated_lines_density", "duplications_data",
    "effort_to_reach_maintainability_rating_a", "executable_lines_data", "false_positive_issues",
    "file_complexity_distribution", "files", "function_complexity_distribution", "functions", "generated_lines",
    "generated_ncloc", "info_violations", "violations", "it_conditions_to_cover", "it_branch_coverage",
    "it_conditions_by_line", "it_coverage", "it_coverage_line_hits_data", "it_covered_conditions_by_line",
    "it_line_coverage", "it_lines_to_cover", "it_uncovered_conditions", "it_uncovered_lines", "line_coverage",
    "new_it_line_coverage", "new_line_coverage", "lines", "ncloc", "ncloc_language_distribution", "lines_to_cover",
    "new_it_lines_to_cover", "new_lines_to_cover", "sqale_rating", "new_maintainability_rating", "major_violations",
    "minor_violations", "ncloc_data", "new_blocker_violations", "new_bugs", "new_code_smells",
    "new_critical_violations", "new_info_violations", "new_violations", "new_lines", "new_major_violations",
    "new_minor_violations", "new_vulnerabilities", "open_issues", "overall_conditions_to_cover",
    "new_overall_conditions_to_cover", "overall_branch_coverage", "new_overall_branch_coverage",
    "overall_conditions_by_line", "overall_coverage", "overall_coverage_line_hits_data", "new_overall_coverage",
    "overall_covered_conditions_by_line", "overall_line_coverage", "new_overall_line_coverage",
    "overall_lines_to_cover", "new_overall_lines_to_cover", "overall_uncovered_conditions",
    "new_overall_uncovered_conditions", "overall_uncovered_lines", "new_overall_uncovered_lines", "quality_profiles",
    "projects", "public_api", "public_documented_api_density", "public_undocumented_api", "quality_gate_details",
    "alert_status", "reliability_rating", "new_reliability_rating", "reliability_remediation_effort",
    "new_reliability_remediation_effort", "reopened_issues", "security_rating", "new_security_rating",
    "security_remediation_effort", "new_security_remediation_effort", "skipped_tests", "sonarjava_feedback",
    "development_cost", "statements", "team_size", "sqale_index", "sqale_debt_ratio", "new_sqale_debt_ratio",
    "uncovered_conditions", "new_it_uncovered_conditions", "new_uncovered_conditions", "uncovered_lines",
    "new_it_uncovered_lines", "new_uncovered_lines", "test_data", "test_execution_time", "test_errors", "test_failures",
    "tests", "test_success_density", "vulnerabilities", "wont_fix_issues"]


METRIC_KEYS = "metricKeys"

logger = logging.getLogger(__name__)


class Sonar(Backend):
    """Sonarqube backend for Perceval.

    This class allows to fetch data from Sonarqube.

    :param component: Sonar component (ie project)
    :param base_url: Sonar URL in enterprise edition case;
        when no value is set the backend will be fetch the data
        from the Sonar public site.
    :param tag: label used to mark the data
    :param archive: archive to store/retrieve items
    """
    version = '0.0.1'

    CATEGORIES = [CATEGORY]

    def __init__(self, component=None, base_url=None, tag=None, archive=None):
        origin = base_url if base_url else SONAR_URL
        origin = urijoin(origin, SONAR_API_URL) + component

        super().__init__(origin, tag=tag, archive=archive)
        self.base_url = base_url
        self.component = component
        self.client = None

    def fetch(self, category=CATEGORY, from_date=DEFAULT_DATETIME):
        """Fetch the metrics from the component.

        The method retrieves, from a Sonarqube instance, the metrics
        updated since the given date.

        :param category: the category of items to fetch
        :param from_date: obtain issues updated since this date

        :returns: a generator of metrics
        """
        if not from_date:
            from_date = DEFAULT_DATETIME

        from_date = datetime_to_utc(from_date)

        kwargs = {'from_date': from_date}
        items = super().fetch(category, **kwargs)

        return items

    def fetch_items(self, category, **kwargs):
        """Fetch the metrics

        :param category: the category of items to fetch
        :param kwargs: backend arguments

        :returns: a generator of items
        """
        from_date = kwargs['from_date']

        nmetrics = 0
        component_metrics_raw = self.client.component_metrics(from_date=from_date)

        component = json.loads(component_metrics_raw)['component']
        for metric in component['measures']:

            fetched_on = datetime_utcnow().timestamp()
            id_args = [component['key'], metric['metric'], str(fetched_on)]
            metric['id'] = uuid(*id_args)
            metric['fetched_on'] = fetched_on

            yield metric
            nmetrics += 1

        logger.info("Fetch process completed: %s metrics fetched", nmetrics)

    @classmethod
    def has_archiving(cls):
        """Returns whether it supports archiving items on the fetch process.

        :returns: this backend supports items archive
        """
        return True

    @classmethod
    def has_resuming(cls):
        """Returns whether it supports to resume the fetch process.

        :returns: this backend does not support items resuming
        """
        return True

    @staticmethod
    def metadata_id(item):
        """Extracts the identifier from a Sonarqube item."""

        return str(item['id'])

    @staticmethod
    def metadata_updated_on(item):
        """Extracts the update time from a Sonarqube item.

        The timestamp is based on the current time when the metric was extracted.
        This field is not part of the data provided by Sonarqube API. It is added
        by this backend.

        :param item: item generated by the backend

        :returns: a UNIX timestamp
        """
        return item['fetched_on']

    @staticmethod
    def metadata_category(item):
        """Extracts the category from a Sonarqube item.

        This backend only generates one type of item which is
        'metric'.
        """
        return CATEGORY

    def _init_client(self, from_archive=False):
        """Init client"""

        return SonarClient(self.component, self.base_url, self.archive, from_archive)


class SonarClient(HttpClient):
    """Client for retrieving information from Sonarqube API

    :param component: Sonar component (ie project)
    :param base_url: Sonar URL in enterprise edition case;
        when no value is set the backend will be fetch the data
        from the Sonar public site.
    :param archive: archive to store/retrieve items
    """

    RATE_LIMIT_HEADER = "RateLimit-Remaining"
    RATE_LIMIT_RESET_HEADER = "RateLimit-Reset"

    _users = {}       # users cache

    def __init__(self, component, base_url=None, archive=None, from_archive=False):
        self.component = component

        if base_url:
            base_url = urijoin(base_url, SONAR_API_URL) + component
        else:
            base_url = urijoin(SONAR_URL, SONAR_API_URL) + component

        super().__init__(base_url, sleep_time=DEFAULT_DATETIME, max_retries=MAX_RETRIES,
                         archive=archive, from_archive=from_archive)

    def component_metrics(self, from_date=None):
        """Get metrics for a given component.

        :param from_date: obtain metrics updated since this date

        :returns: a generator of metrics
        """
        payload = {
            'metricKeys': ','.join(TARGET_METRIC_FIELDS)
        }

        response = super().fetch(self.base_url, payload=payload)

        return response.text


class SonarCommand(BackendCommand):
    """Class to run Sonaqube backend from the command line."""

    BACKEND = Sonar

    @classmethod
    def setup_cmd_parser(cls):
        """Returns the Sonarqube argument parser."""

        parser = BackendCommandArgumentParser(cls.BACKEND.CATEGORIES,
                                              from_date=True,
                                              archive=True)

        # Sonarqube options
        group = parser.parser.add_argument_group('Sonarqube arguments')
        group.add_argument('--base-url', dest='base_url',
                           help="Base URL for Sonarqube instance")

        # Positional arguments
        parser.parser.add_argument('component',
                                   help="Sonarqube component/project")

        return parser