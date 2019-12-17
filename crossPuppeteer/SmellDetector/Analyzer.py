import sys

import SmellDetector.Constants as CONSTS
from SmellDetector import SmellDectector, SizeMetrics, FileOperations, Utilities


def analyze(folder, repoName):
    outputFile = open(folder + "/" + CONSTS.PUPPETEER_OUT_FILE, 'w')

    option = sys.argv[2]

    if option == '1':
        puppetFileCount = FileOperations.countPuppetFiles(folder)
        print("1,PuppetFileCount," + str(puppetFileCount) + "\n")

        SizeMetrics.collectSizeMetrics(folder, outputFile)

    if option == '2':
        SmellDectector.detectSmells(folder, outputFile)

    outputFile.close()
    return