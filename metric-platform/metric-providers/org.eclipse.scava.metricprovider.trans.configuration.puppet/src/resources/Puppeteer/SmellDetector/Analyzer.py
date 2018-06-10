import SmellDetector.Constants as CONSTS
import sys
from SmellDetector import SmellDectector, SizeMetrics, FileOperations, Utilities


def analyze(folder, repoName):
    fold = sys.argv[2]
    outputFile = open(fold + "/" + CONSTS.PUPPETEER_OUT_FILE, 'a')

    outputFile.write(folder + "\n\n")

    puppetFileCount = FileOperations.countPuppetFiles(folder)
    outputFile.write(CONSTS.PUPPET_FILE_COUNT + str(puppetFileCount) + "\n")
    Utilities.myPrint(CONSTS.PUPPET_FILE_COUNT + str(puppetFileCount))

    SizeMetrics.collectSizeMetrics(folder, outputFile)

    SmellDectector.detectSmells(folder, outputFile)

    outputFile.write("\n\n")

    outputFile.close()
    return