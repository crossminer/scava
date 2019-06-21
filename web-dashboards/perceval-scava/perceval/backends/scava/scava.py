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

from grimoirelab_toolkit.datetime import str_to_datetime, datetime_utcnow
from grimoirelab_toolkit.uris import urijoin

from ...backend import (Backend,
                        BackendCommand,
                        BackendCommandArgumentParser)
from ...client import HttpClient

SCAVA_API = "http://localhost:8182"
RECOMMENDATION_API = "http://localhost:8080/api"

CATEGORY_METRIC = 'metric'
CATEGORY_PROJECT = 'project'
CATEGORY_FACTOID = 'factoid'
CATEGORY_TOPIC = 'topic'
CATEGORY_DEV_DEPENDENCY = 'dev-dependency'
CATEGORY_CONF_DEPENDENCY = 'conf-dependency'
CATEGORY_DEPENDENCY_OLD_NEW_VERSIONS = 'version-dependency'
CATEGORY_USER = 'user'
CATEGORY_RECOMMENDATION = 'recommendation'
CATEGORY_CONF_SMELL = 'conf-smell'
CATEGORY_PROJECT_RELATION = 'project-relation'

DEP_MAVEN = 'maven'
DEP_MAVEN_OPT = 'opt'
DEP_OSGI = 'osgi'
DEP_OSGI_PACKAGE = 'package'
DEP_OSGI_BUNDLE = 'bundle'
DEP_PUPPET = 'puppet'
DEP_DOCKER = 'docker'

DESIGN_SMELL = 'design'
IMPLEMENTATION_SMELL = 'implementation'
ANTIPATTERN_SMELL = 'antipattern'
CONF_SMELL = 'conf'

METRIC_PROVIDER_ID_COMMENTS_TOPICS = 'bugs.topics.comments'

METRICPROVIDER_ID_MAVEN_DEP_ALL = 'trans.rascal.dependency.maven.allMavenDependencies'
METRICPROVIDER_ID_MAVEN_DEP_OPT = 'trans.rascal.dependency.maven.allOptionalMavenDependencies'
METRICPROVIDER_ID_OSGI_DEP_PKG_ALL = 'trans.rascal.dependency.osgi.allOSGiPackageDependencies'
METRICPROVIDER_ID_OSGI_DEP_BNL_ALL = 'trans.rascal.dependency.osgi.allOSGiBundleDependencies'

METRICPROVIDER_ID_DOCKER_DEPS = 'org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.DockerDependenciesTransMetricProvider'
METRICPROVIDER_ID_PUPPET_DEPS = 'org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies.PuppetDependenciesTransMetricProvider'

METRIC_PROVIDER_ID_NEWVERSION_DOCKER_DEPS = 'org.eclipse.scava.metricprovider.trans.newversion.docker.NewVersionDockerTransMetricProvider'
METRIC_PROVIDER_ID_NEWVERSION_PUPPET_DEPS = 'org.eclipse.scava.metricprovider.trans.newversion.puppet.NewVersionPuppetTransMetricProvider'
METRIC_PROVIDER_ID_NEWVERSION_OSGI_DEPS = 'org.eclipse.scava.metricprovider.trans.newversion.osgi.NewVersionOsgiTransMetricProvider'
METRIC_PROVIDER_ID_NEWVERSION_MAVEN_DEPS = 'org.eclipse.scava.metricprovider.trans.newversion.puppet.NewVersionPuppetTransMetricProvider'

METRIC_PROVIDER_ID_PUPPET_DESIGN_SMELLS = 'org.eclipse.scava.metricprovider.trans.configuration.puppet.designsmells.PuppetDesignTransMetricProvider'
METRIC_PROVIDER_ID_PUPPET_IMPL_SMELLS = 'org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.PuppetImplementationTransMetricProvider'
METRIC_PROVIDER_ID_DOCKER_SMELLS = 'org.eclipse.scava.metricprovider.trans.configuration.docker.smells.DockerTransMetricProvider'
METRIC_PROVIDER_ID_PUPPET_DESIGN_ANTIPATTERN_SMELLS = 'org.eclipse.scava.metricprovider.trans.configuration.puppet.designantipatterns.PuppetDesignAntipatternTransMetricProvider'
METRIC_PROVIDER_ID_PUPPET_IMPL_ANTIPATTERN_SMELLS = 'org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationantipatterns.PuppetImplementationAntipatternTransMetricProvider'

METRIC_PROVIDER_ID_PROJECT_RELATIONS = 'org.eclipse.scava.metricprovider.trans.configuration.projects.relations.ProjectsRelationsTransMetricProvider'

METRIC_CHURN_PER_COMMITTER = 'churnPerCommitterTimeLine'

SIM_METHODS = ['Compound', 'CrossSim', 'Dependency', 'CrossRec', 'Readme', 'RepoPalCompound', 'RepoPalCompoundV2']
RECOMMENDED_PROJECTS = 10

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
    version = '0.1.0'

    CATEGORIES = [CATEGORY_METRIC, CATEGORY_PROJECT, CATEGORY_FACTOID,
                  CATEGORY_USER, CATEGORY_DEV_DEPENDENCY, CATEGORY_TOPIC,
                  CATEGORY_CONF_DEPENDENCY, CATEGORY_RECOMMENDATION,
                  CATEGORY_DEPENDENCY_OLD_NEW_VERSIONS,
                  CATEGORY_CONF_SMELL, CATEGORY_PROJECT_RELATION]

    def __init__(self, url, project=None, recommendation_url=RECOMMENDATION_API, tag=None, archive=None):
        origin = url

        super().__init__(origin, tag=tag, archive=archive)
        self.project = project
        self.url = url
        self.recommendation_url = recommendation_url
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
            logger.info("Looking for '%s' project %ss at url '%s'", project, category, self.url)

        nitems = 0  # number of items processed

        for raw_items in self.client.get_items(category, project):
            items = json.loads(raw_items)
            items = [items] if isinstance(items, dict) else items
            for item in items:
                # projects without last execution information get the lastExecuted value to the day
                # of the import to the dashboard. Issue: https://github.com/crossminer/scava/issues/134
                if 'executionInformation' in item and 'lastExecuted' not in item['executionInformation']:
                    logger.warning("%s is missing lastExecuted info, the current time will be set: %s",
                                   category, str(item))
                    item['executionInformation']['lastExecuted'] = datetime_utcnow().strftime("%Y%m%d")

                if category in [CATEGORY_FACTOID,
                                CATEGORY_TOPIC,
                                CATEGORY_METRIC,
                                CATEGORY_USER,
                                CATEGORY_DEV_DEPENDENCY,
                                CATEGORY_CONF_DEPENDENCY,
                                CATEGORY_RECOMMENDATION,
                                CATEGORY_DEPENDENCY_OLD_NEW_VERSIONS,
                                CATEGORY_CONF_SMELL,
                                CATEGORY_PROJECT_RELATION]:
                    item['updated'] = self.project_updated
                    item['project'] = self.project
                yield item
                nitems += 1

        logger.info("Total number of items: %i (%s)", nitems, category)

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
        elif 'user' in item:
            mid = item['user'] + item['updated'] + item['project']
        elif 'topic' in item:
            mid = item['topic'] + item['updated'] + item['project']
        else:
            raise TypeError("Cannot extract metadata_id from", item)

        return mid

    @staticmethod
    def metadata_updated_on(item):
        """Extracts the update time from a Scava item.

        The timestamp is extracted from 'end' field.
        This date is converted to a perceval format using a float value.

        :param item: item generated by the backend

        :returns: a UNIX timestamp
        """

        if 'updated' in item:
            updated = item['updated']
        elif 'executionInformation' in item:
            updated = item['executionInformation']['lastExecuted']
        else:
            raise TypeError("Cannot extract metadata_updated_on from", item)

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
        elif 'factoid' in item:
            category = CATEGORY_FACTOID
        elif 'dependency' in item:
            category = CATEGORY_DEV_DEPENDENCY
        elif 'dependencyName' in item:
            category = CATEGORY_CONF_DEPENDENCY
        elif 'user' in item:
            category = CATEGORY_USER
        elif 'recommendation_type' in item:
            category = CATEGORY_RECOMMENDATION
        elif 'packageName' in item:
            category = CATEGORY_DEPENDENCY_OLD_NEW_VERSIONS
        elif 'reason' in item:
            category = CATEGORY_CONF_SMELL
        elif 'related_to' in item:
            category = CATEGORY_PROJECT_RELATION
        elif 'topic' in item:
            category = CATEGORY_TOPIC
        else:
            raise TypeError("Could not define the category of item " + str(item))

        return category

    def _init_client(self, from_archive=False):
        """Init client"""

        client = ScavaClient(self.url, self.project, self.recommendation_url, self.archive, from_archive)
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

    def __init__(self, url, project=None, recommendation_url=None, archive=None, from_archive=False):
        super().__init__(url, archive=archive, from_archive=from_archive)
        self.project = project
        self.recommendation_url = recommendation_url
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
                if 'lastExecuted' in project['executionInformation']:
                    updated = project['executionInformation']['lastExecuted']
                else:
                    updated = datetime_utcnow().strftime("%Y%m%d")

        return updated

    def get_items(self, category=CATEGORY_METRIC, project=None):
        """Retrieve all items for category """

        if category == CATEGORY_PROJECT:
            # Return the the list of projects
            api = self.api_projects_url
            logger.debug("Scava client calls APIv1: %s", api)
            projects = self.fetch(api)
            yield projects

        elif category == CATEGORY_METRIC:
            api_metrics = urijoin(self.base_url, "metrics/p/%s" % project)
            raw_metrics = self.fetch(api_metrics)
            metrics = json.loads(raw_metrics)

            for metric in metrics:
                metric_id = metric['id']

                api = urijoin(self.base_url, "projects/p/%s/m/%s" % (project, metric_id))
                logger.debug("Scava client calls API: %s", api)

                project_metric = self.fetch(api)
                yield project_metric

        elif category == CATEGORY_USER:
            api = urijoin(self.base_url, "projects/p/%s/m/%s" % (project, METRIC_CHURN_PER_COMMITTER))
            logger.debug("Scava client calls API: %s", api)
            project_metric = self.fetch(api)

            json_metric = json.loads(project_metric)

            if 'datatable' not in json_metric or not json_metric['datatable']:
                return '[]'

            for u in json_metric['datatable']:
                project_user = {
                    "user": u['Committer'],
                    "date": u['Date'],
                    "churn": u['Churn'],
                    "scava_metric": METRIC_CHURN_PER_COMMITTER
                }

                yield json.dumps(project_user)

        elif category == CATEGORY_FACTOID:
            api_factoids = urijoin(self.base_url, "/projects/p/%s/f" % project)
            factoids = json.loads(self.fetch(api_factoids))

            for factoid in factoids:
                factoid_id = factoid['id']
                api = urijoin(self.api_projects_url, "/p/%s/f/%s" % (project, factoid_id))
                logger.debug("Scava client calls API: %s", api)
                project_factoid = self.fetch(api)

                project_factoid_json = json.loads(project_factoid)
                if 'status' in project_factoid_json and project_factoid_json['status'] == 'error':
                    logger.debug("Something went wrong with '%s' for project %s: %s",
                                 api, project, project_factoid_json['msg'])
                    continue

                yield project_factoid

        elif category == CATEGORY_DEV_DEPENDENCY:
            maven_all_deps = self.__fetch_dev_dependencies(project, METRICPROVIDER_ID_MAVEN_DEP_ALL,
                                                           dep_type=DEP_MAVEN)

            maven_opt_deps = self.__fetch_dev_dependencies(project, METRICPROVIDER_ID_MAVEN_DEP_OPT,
                                                           dep_type=DEP_MAVEN, dep_sub_type=DEP_MAVEN_OPT)

            osgi_all_pkg_deps = self.__fetch_dev_dependencies(project, METRICPROVIDER_ID_OSGI_DEP_PKG_ALL,
                                                              dep_type=DEP_OSGI, dep_sub_type=DEP_OSGI_PACKAGE)
            osgi_all_bnl_deps = self.__fetch_dev_dependencies(project, METRICPROVIDER_ID_OSGI_DEP_BNL_ALL,
                                                              dep_type=DEP_OSGI, dep_sub_type=DEP_OSGI_BUNDLE)

            group_deps = [maven_all_deps, maven_opt_deps, osgi_all_bnl_deps, osgi_all_pkg_deps]

            for deps in group_deps:
                for dep in deps:
                    yield dep

        elif category == CATEGORY_CONF_DEPENDENCY:
            docker_deps = self.__fetch_conf_dependencies(project, METRICPROVIDER_ID_DOCKER_DEPS)
            puppet_deps = self.__fetch_conf_dependencies(project, METRICPROVIDER_ID_PUPPET_DEPS)

            group_deps = [docker_deps, puppet_deps]

            for deps in group_deps:
                for dep in deps:
                    yield dep

        elif category == CATEGORY_CONF_SMELL:
            puppet_design_smells = self.__fetch_conf_smells(project, METRIC_PROVIDER_ID_PUPPET_DESIGN_SMELLS,
                                                            conf_type=DEP_PUPPET,
                                                            smell_type=DESIGN_SMELL)
            puppet_impl_smells = self.__fetch_conf_smells(project, METRIC_PROVIDER_ID_PUPPET_IMPL_SMELLS,
                                                          conf_type=DEP_PUPPET,
                                                          smell_type=IMPLEMENTATION_SMELL)
            docker_conf_smells = self.__fetch_conf_smells(project, METRIC_PROVIDER_ID_DOCKER_SMELLS,
                                                          smell_type=CONF_SMELL, conf_type=DEP_DOCKER)
            puppet_design_ap_smells = self.__fetch_conf_smells(project,
                                                               METRIC_PROVIDER_ID_PUPPET_DESIGN_ANTIPATTERN_SMELLS,
                                                               conf_type=DEP_PUPPET,
                                                               smell_type=DESIGN_SMELL,
                                                               smell_sub_type=ANTIPATTERN_SMELL)
            puppet_impl_ap_smells = self.__fetch_conf_smells(project,
                                                             METRIC_PROVIDER_ID_PUPPET_IMPL_ANTIPATTERN_SMELLS,
                                                             conf_type=DEP_PUPPET,
                                                             smell_type=IMPLEMENTATION_SMELL,
                                                             smell_sub_type=ANTIPATTERN_SMELL)

            group_smells = [puppet_design_smells, puppet_impl_smells,
                            docker_conf_smells, puppet_design_ap_smells,
                            puppet_impl_ap_smells]

            for smells in group_smells:
                for smell in smells:
                    yield smell

        elif category == CATEGORY_RECOMMENDATION:
            api = urijoin(self.api_projects_url, "/p/%s" % project)
            logger.debug("Scava client calls API: %s", api)
            project_info_raw = self.fetch(api)
            project_info = json.loads(project_info_raw)

            project_name = project_info['shortName']
            recommendation_project_api = urijoin(self.recommendation_url,
                                                 "artifacts", "artifact", "mpp", project_name)
            recommendation_project_raw = self.fetch(recommendation_project_api)

            # Fake data
            recommendation_project_raw = """
            {
              "id": "5b155b04065f2d726d6db241",
              "tags": null,
              "name": "json-simple",
              "shortName": null,
              "description": "A simple Java toolkit for JSON. You can use json-simple to encode or decode JSON text.",
              "fullName": "fangyidong/json-simple",
              "methodDeclarations": [],
              "year": 0,
              "active": true,
              "homePage": "",
              "type": null,
              "private_": null,
              "fork": null,
              "html_url": "https://github.com/fangyidong/json-simple",
              "clone_url": "https://github.com/fangyidong/json-simple.git",
              "git_url": "git://github.com/fangyidong/json-simple.git",
              "ssh_url": null,
              "svn_url": null,
              "mirror_url": null,
              "size": 0,
              "master_branch": "master",
              "webDashboardId": "app/kibana#/dashboard/ScavaProject?_a=(query:(match:(project.keyword:(query:jsonsimple))))",
              "metricPlatformId": "jsonsimple",
              "readmeText": "Please visit: http://code.google.com/p/json-simple/",
              "dependencies": [
                "junit:junit"
              ],
              "starred": [
                {
                  "login": "veita",
                  "datestamp": "2015-04-13T08:55:08Z"
                }
              ],
              "parent": null
            }
            """

            try:
                recommendation_artifacts = json.loads(recommendation_project_raw)
            except:
                return '[]'

            kb_project_id = recommendation_artifacts['id']

            for sim_method in SIM_METHODS:
                recommended_projects = self.__fetch_recommendations(kb_project_id, sim_method)

                for prj in recommended_projects:
                    yield json.dumps(prj)
        elif category == CATEGORY_DEPENDENCY_OLD_NEW_VERSIONS:
            docker_version_deps = self.__fetch_version_dependencies(project,
                                                                    METRIC_PROVIDER_ID_NEWVERSION_DOCKER_DEPS,
                                                                    DEP_DOCKER)
            puppet_version_deps = self.__fetch_version_dependencies(project,
                                                                    METRIC_PROVIDER_ID_NEWVERSION_PUPPET_DEPS,
                                                                    DEP_PUPPET)
            maven_version_deps = self.__fetch_version_dependencies(project,
                                                                   METRIC_PROVIDER_ID_NEWVERSION_MAVEN_DEPS,
                                                                   DEP_MAVEN)
            osgi_version_deps = self.__fetch_version_dependencies(project,
                                                                  METRIC_PROVIDER_ID_NEWVERSION_OSGI_DEPS,
                                                                  DEP_OSGI)

            group_deps = [docker_version_deps, puppet_version_deps, maven_version_deps, osgi_version_deps]

            for deps in group_deps:
                for dep in deps:
                    yield dep

        elif category == CATEGORY_PROJECT_RELATION:
            api_project_relations = urijoin(self.base_url, 'raw/projects/p/%s/m/%s' %
                                            (project, METRIC_PROVIDER_ID_PROJECT_RELATIONS))
            logger.debug("Scava client calls API: %s", api_project_relations)

            project_relations = json.loads(self.fetch(api_project_relations))

            if not project_relations:
                return '[]'

            for project_relation in project_relations:

                relation = {
                    "relation_type": project_relation['dependencyType'],
                    "related_to": project_relation['relationName']
                }

                relation['id'] = "{}_{}_{}".format(project, relation['related_to'], relation['relation_type'])

                yield json.dumps(relation)

        elif category == CATEGORY_TOPIC:
            api = urijoin(self.base_url, "projects/p/%s/m/%s" % (project, METRIC_PROVIDER_ID_COMMENTS_TOPICS))
            logger.debug("Scava comments topics calls API: %s", api)
            project_metric = self.fetch(api)

            json_metric = json.loads(project_metric)

            if 'datatable' not in json_metric or not json_metric['datatable']:
                return '[]'

            for t in json_metric['datatable']:
                project_topic = {
                    "topic": t['Topic'],
                    "date": t['Date'],
                    "comments": t['Comments'],
                    "scava_metric": METRIC_PROVIDER_ID_COMMENTS_TOPICS
                }

                yield json.dumps(project_topic)
        else:
            raise ValueError(category + ' not supported in Scava')

    def fetch(self, url, payload=None):
        """Return the textual content associated to the Response object"""

        response = super().fetch(url, payload)

        return response.text

    def __fetch_recommendations(self, project_id, sim_method):
        recommendation_similar_api = urijoin(self.recommendation_url, "recommendation", "similar",
                                             "p", project_id, "m", sim_method, "n", RECOMMENDED_PROJECTS)
        recommendation_similar_raw = self.fetch(recommendation_similar_api)
        recommendation_similar = json.loads(recommendation_similar_raw)

        recommendations = []
        for recommendation in recommendation_similar:
            recommended_project = {
                "id": project_id,
                "name": recommendation['name'],
                "full_name": recommendation['fullName'],
                "description": recommendation['description'],
                "url": recommendation['html_url'],
                "readme": recommendation['readmeText'],
                "dependencies": recommendation['dependencies'],
                "active": recommendation['active'],
                "recommendation_type": sim_method,
                "project": recommendation['metricPlatformId']
            }

            recommendations.append(recommended_project)

        return recommendations

    def __fetch_conf_smells(self, project, smell_id, conf_type, smell_type, smell_sub_type=None):
        api_conf_smells = urijoin(self.base_url, 'raw/projects/p/%s/m/%s' % (project, smell_id))

        logger.debug("Scava client calls API: %s", api_conf_smells)
        conf_smells = json.loads(self.fetch(api_conf_smells))

        if not conf_smells:
            return '[]'

        for conf_smell in conf_smells:
            smell = {
                "smell_name": conf_smell.get('smellName', None),
                "line": conf_smell.get('line', None),
                "reason": conf_smell.get('reason', None),
                "file_name": conf_smell.get('fileName', None),
                "commit": conf_smell.get('commit', None),
                "date": conf_smell['date']['$date'] if 'date' in conf_smell else None,
                "conf_type": conf_type,
                "smell_type": smell_type,
                "smell_sub_type": smell_sub_type
            }

            smell['id'] = "{}_{}_{}_{}_{}".format(conf_type, smell_type,
                                                  smell['smell_name'], smell['line'], smell['file_name'])

            yield json.dumps(smell)

    def __fetch_version_dependencies(self, project, dep_metric, dep_type):
        api_version_dependencies = urijoin(self.base_url, 'raw/projects/p/%s/m/%s' % (project, dep_metric))
        logger.debug("Scava client calls API: %s", api_version_dependencies)
        version_dependencies = json.loads(self.fetch(api_version_dependencies))

        if not version_dependencies:
            return '[]'

        for version in version_dependencies:

            version_dep = {
                "dependency": version['packageName'],
                "scava_metric_provider": dep_metric,
                "type": dep_type,
                "new_version": version['newVersion'],
                "old_version": version['oldVersion'],
                "id": version['packageName']
            }

            yield json.dumps(version_dep)

    def __fetch_dev_dependencies(self, project, dep_metric, dep_type=None, dep_sub_type=None):
        api_dependencies = urijoin(self.base_url, 'raw/projects/p/%s/m/%s' % (project, dep_metric))
        logger.debug("Scava client calls API: %s", api_dependencies)
        dependencies = json.loads(self.fetch(api_dependencies))

        if not dependencies:
            return '[]'

        for dep in dependencies:
            if 'value' not in dep or not dep['value']:
                logger.debug("No value for dependency %s for project %s", str(dep), project)
                continue

            project_dep = {
                "dependency": dep['value'],
                "scava_metric_provider": dep_metric,
                "type": dep_type,
                "sub_type": dep_sub_type,
                "id": dep['value']
            }

            yield json.dumps(project_dep)

    def __fetch_conf_dependencies(self, project, dep_metric):
        api_dependencies = urijoin(self.base_url, 'raw/projects/p/%s/m/%s' % (project, dep_metric))
        logger.debug("Scava client calls API: %s", api_dependencies)
        dependencies = json.loads(self.fetch(api_dependencies))

        if not dependencies:
            return '[]'

        for dep in dependencies:
            if 'dependencyName' not in dep or not dep['dependencyName']:
                logger.debug("No dependency name %s for project %s", str(dep), project)
                continue

            project_dep = {
                "dependency": dep['dependencyName'],
                "dependency_version": dep['dependencyVersion'],
                "scava_metric_provider": dep_metric,
                "type": dep['type'],
                "sub_type": dep['subType'] if 'subType' in dep else None,
                "id": "{}_{}".format(dep['dependencyName'], dep['dependencyVersion'])
            }

            yield json.dumps(project_dep)


class ScavaCommand(BackendCommand):
    """Class to run Scava backend from the command line."""

    BACKEND = Scava

    @staticmethod
    def setup_cmd_parser():
        """Returns the Scava argument parser."""

        parser = BackendCommandArgumentParser(archive=True)
        # Required arguments
        parser.parser.add_argument('url', default=SCAVA_API,
                                   help="Scava REST API URL (default: http://localhost:8182")
        # Optional arguments
        group = parser.parser.add_argument_group('Scava arguments')
        group.add_argument('--project', dest='project',
                           required=False,
                           help="Name of the Scava project")
        group.add_argument('--recommendation-api', default=RECOMMENDATION_API, dest='recommendation_url',
                           required=False, help="Recommendation API URL (default: http://localhost:8080/api")

        return parser
