import argparse
import sys
import time

from crossflow.projectanalysis.projects_analysis import ProjectsAnalysis
from crossflow.runtime import Mode


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('-name', default='ProjectsAnalysisPython', help='The name of the workflow')
    parser.add_argument('-brokerHost', default='localhost', help='Host of the JMX broker')
    parser.add_argument('-stompPort', default=61613, help='Port to use for stomp based messages')
    parser.add_argument('-instance', default='ProjectsAnalysis', help='The instance of the master (to contribute to)')
    parser.add_argument('-mode', default='WORKER', help='must be one of WORKER or API')

    parsedArgs = parser.parse_args(sys.argv[1:len(sys.argv)])

    app = ProjectsAnalysis(name=parsedArgs.name,
                      instance=parsedArgs.instance,
                      broker_host=parsedArgs.brokerHost,
                      stomp_port=parsedArgs.stompPort,
                      mode=Mode.enum_from_name(parsedArgs.mode))

    app.run()

    while (not app.terminated):
        time.sleep(0.1)
    print("terminated")
