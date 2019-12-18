import datetime

from dateutil import relativedelta
from pydriller import RepositoryMining, GitRepository

noOfCommits = 0
noOfModifications = 0
totalLOC = 0

visitedFile = []
committerList = []
fileLocCache = {}

firstCommitDate = datetime.datetime.now()
lastCommitDate = datetime.datetime.now()

totalAddedLOC = 0
totalDeletedLOC = 0

repoPath = '/tmp/REPO-CLONES/lawloretienne/HackerNews/5d9732690f100da4dbcdbd482cbcf2d3b6af5b28' # small repo
# repoPath = '/tmp/REPO-CLONES/facebook/osquery/68cb40bae1c38f663b3ab7b029b2d5ae97bb9928' # large repo

# iterate through repo commits
for commit in RepositoryMining(repoPath).traverse_commits():
    noOfCommits += 1
    if noOfCommits==1:
        firstCommitDate = commit.committer_date
    else:
        lastCommitDate = commit.committer_date

    if commit.committer not in committerList:
        committerList.append(commit.committer)

    for modification in commit.modifications:
        noOfModifications += 1
        if modification.new_path != None:
            visitedFile.append(modification.new_path) # add file to list of visited files
            if modification.nloc != None:
                totalLOC += modification.nloc
                fileLocCache.update( { modification.new_path : modification.nloc } )

            gr = GitRepository(repoPath)
            parsed_lines = gr.parse_diff(modification.diff)
            for item in parsed_lines['added']:
                totalAddedLOC += item[0]
            for item in parsed_lines['deleted']:
                totalDeletedLOC += item[0]

        else:
            fileLocCache.update( { modification.new_path : 0 } )

for file in visitedFile:
    if file not in fileLocCache.keys():
        print('visited file not in cache')

timeDelta = relativedelta.relativedelta(lastCommitDate, firstCommitDate)
timeDeltaInMonths = timeDelta.years * 12 + timeDelta.months + timeDelta.days / 30.4167 + timeDelta.hours / 730.001 + timeDelta.minutes / 43800

# print results
print('noOfCommits: ' + str(noOfCommits))
print('noOfModifications: ' + str(noOfModifications))
print('totalAddedLOC: ' + str(totalAddedLOC))
print('totalDeletedLOC: ' + str(totalDeletedLOC))
print('totalLOC: ' + str(totalLOC))
print('numberOfDevs: ', len(committerList))

print('firstCommitDate: ' + str(firstCommitDate))
print('lastCommitDate: ' + str(lastCommitDate))
print('projectLength: ' + str(timeDeltaInMonths))

# assert results
assert noOfCommits==12
assert noOfModifications==100
assert totalAddedLOC==136843
assert totalDeletedLOC==14847
assert totalLOC==5202
assert len(committerList)==2
assert timeDeltaInMonths==1.6351819507107201
