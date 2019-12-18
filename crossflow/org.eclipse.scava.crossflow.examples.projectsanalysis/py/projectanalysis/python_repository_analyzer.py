from crossflow.projectanalysis.python_repository_analyzer import PythonRepositoryAnalyzerBase

from crossflow.projectanalysis.python_repository_analysis_result import PythonRepositoryAnalysisResult


class PythonRepositoryAnalyzer(PythonRepositoryAnalyzerBase):

    def __init__(self):
        super().__init__()

    def consumeInitialRepositoryAnalyses(self, java_repository_analysis_result: JavaRepositoryAnalysisResult):

		# check if folder exists on this machine with this path
		repoCloneAvailable = java_repository_analysis_result.getPath()
# 		if(repoCloneAvailable is not empty or null)
# 			{
# 			 # run pydriller analysis and submit result to next queue 
# 			 repository_analysis_results_python_repository_analysis_result = PythonRepositoryAnalysisResult()
# 
# 			 self.sendToRepositoryAnalysisResults(repository_analysis_results_python_repository_analysis_result)
# 			}
# 		else
# 			# not found on local machine, sending it back to origin queue
# 			workflow.getInitialRepositoryAnalyses().send(java_repository_analysis_result,"JavaRepositoryAnalyzer")

