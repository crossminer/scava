import os

import SourceModel.SM_File
from SmellDetector import Constants as CONSTS, Utilities


def collectSizeMetrics(folder, outputFile):
    totalClasses = 0
    totalDefines = 0
    totalFiles = 0
    totalPackages = 0
    totalServices = 0
    totalExecs = 0
    totalLOC = 0
    for root, dirs, files in os.walk(folder):
        for file in files:
            if file.endswith(".pp") and not os.path.islink(os.path.join(root, file)):
                Utilities.myPrint("Reading file: " + os.path.join(root, file))
                fileObj = SourceModel.SM_File.SM_File(os.path.join(root, file))

                totalClasses += fileObj.getNoOfClassDeclarations()
                totalDefines += fileObj.getNoOfDefineDeclarations()
                totalFiles += fileObj.getNoOfFileDeclarations()
                totalPackages += fileObj.getNoOfPackageDeclarations()
                totalServices += fileObj.getNoOfServiceDeclarations()
                totalExecs += fileObj.getNoOfExecDeclarations()
                totalLOC += fileObj.getLinesOfCode()

    print("1,TotalClasses," + str(totalClasses) + "\n")

    print("1,TotalDefines," + str(totalDefines) + "\n")

    print("1,TotalFiles," + str(totalFiles) + "\n")

    print("1,TotalPackages," + str(totalPackages) + "\n")

    print("1,TotalServices," + str(totalServices) + "\n")

    print("1,TotalExecs," + str(totalExecs) + "\n")

    print("1,TotalLOC," + str(totalLOC) + "\n")

