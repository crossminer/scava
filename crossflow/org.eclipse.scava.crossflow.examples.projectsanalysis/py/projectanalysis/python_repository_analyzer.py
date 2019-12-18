import datetime
from os import path

from crossflow.projectanalysis.python_repository_analyzer import PythonRepositoryAnalyzerBase

from crossflow.projectanalysis.java_repository_analysis_result import JavaRepositoryAnalysisResult
from crossflow.projectanalysis.python_repository_analysis_result import PythonRepositoryAnalysisResult
from dateutil import relativedelta

from pydriller import RepositoryMining, GitRepository


class PythonRepositoryAnalyzer(PythonRepositoryAnalyzerBase):

    def __init__(self):
        super().__init__()

    def consumeInitialRepositoryAnalyses(self, java_repository_analysis_result: JavaRepositoryAnalysisResult):

        noOfCommits = 0
        noOfModifications = 0
        totalLOC = 0

        visitedFile = []
        committerList = []
        fileLocCache = {} # may be redundant (remove anything related for performance)

        firstCommitDate = datetime.datetime.now()
        lastCommitDate = datetime.datetime.now()

        totalAddedLOC = 0
        totalDeletedLOC = 0

        # check if repo clone exists on disk
        if path.exists(java_repository_analysis_result.path):
            # run pydriller analysis and submit result to next queue
            print('path exists: ' + java_repository_analysis_result.path)

            # iterate through repo commits
            for commit in RepositoryMining(java_repository_analysis_result.path).traverse_commits():
                noOfCommits += 1
                if noOfCommits == 1:
                    firstCommitDate = commit.committer_date
                else:
                    lastCommitDate = commit.committer_date

                if commit.committer not in committerList:
                    committerList.append(commit.committer)

                for modification in commit.modifications:
                    noOfModifications += 1
                    if modification.new_path != None:
                        visitedFile.append(modification.new_path)  # add file to list of visited files
                        if modification.nloc != None:
                            totalLOC += modification.nloc
                            fileLocCache.update({modification.new_path: modification.nloc})

                        gr = GitRepository(java_repository_analysis_result.path)
                        parsed_lines = gr.parse_diff(modification.diff)
                        for item in parsed_lines['added']:
                            totalAddedLOC += item[0]
                        for item in parsed_lines['deleted']:
                            totalDeletedLOC += item[0]

                    else:
                        fileLocCache.update({modification.new_path: 0})

            repository_analysis_results_python_repository_analysis_result = PythonRepositoryAnalysisResult()
            repository_analysis_results_python_repository_analysis_result.linesAdded = totalAddedLOC
            repository_analysis_results_python_repository_analysis_result.linesDeleted = totalDeletedLOC
            repository_analysis_results_python_repository_analysis_result.projectLOC = totalLOC
            repository_analysis_results_python_repository_analysis_result.numberOfCommits = noOfCommits
            repository_analysis_results_python_repository_analysis_result.numOfDevs = len(committerList)

            timeDelta = relativedelta.relativedelta(lastCommitDate, firstCommitDate)
            repository_analysis_results_python_repository_analysis_result.projectDuration = timeDelta.years * 12 + timeDelta.months + timeDelta.days / 30.4167 + timeDelta.hours / 730.001 + timeDelta.minutes / 43800

            self.sendToRepositoryAnalysisResults(repository_analysis_results_python_repository_analysis_result)
        else:
            print('path does NOT exist: ' + java_repository_analysis_result.path)

            # not found on local machine, sending it back to origin queue
            self.workflow.getInitialRepositoryAnalyses().send(java_repository_analysis_result,"JavaRepositoryAnalyzer")

