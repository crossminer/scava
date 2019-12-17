import os
import sys
from subprocess import call

root = sys.argv[1]
puppetLint = sys.argv[2]
print("Initiating Puppet-Lint Analyzer...")
totalRepos = len(os.listdir(root))
currentItem = 0
for item in os.listdir(root):
    currentFolder = os.path.join(root, item)
    if not os.path.isfile(currentFolder):
        outToFile = " > " + currentFolder + "/puppet-lint.log"
        #print("Analyzing: " + currentFolder)
        cmd = sys.argv[2] + ' ' + currentFolder
        os.system(cmd)
    currentItem += 1
    print (str("{:.2f}".format(float(currentItem * 100)/float(totalRepos))) + "% analysis done.")
print("Puppet-lint Analyzer - Done.")
