<# .SYNOPSIS #>
param (
    [Parameter(HelpMessage="If it is not set, the script wont run unless there are no uncommitted changes in the woring directory")]
    [switch]
    $ignoreUncommittedChanges = $false,
    [Parameter(HelpMessage="Ignore incorrect and different versions of the components")]
    [switch]
    $ignoreVersions = $false,
    [Parameter(HelpMessage="Ignore incorrect and different versions of the components")]
    [switch]
    $increaseVersions = $false,
    [Parameter(HelpMessage="Ignore if the number of modified files are not the expected")]
    [switch]
    $ignoreNumberOfModifications,
    [Parameter(HelpMessage="Ignore if the HEAD is diverged from the upstream")]
    [switch]
    $ignoreHeadDiverge,
    [Parameter(HelpMessage="The documentation will not be built and copied and it's version is ignored")]
    [switch]
    $noDoc = $false,
    [Parameter(HelpMessage="Set the release version by hand")]
    [string]
    $releaseVersion,
    [Parameter(HelpMessage="Update site will not be built")]
    [switch]
    $noBuild = $false,
    [Parameter(HelpMessage="After the build of a package it will launched")]
    [switch]
    $testRun = $false,
    [Parameter(HelpMessage="The built packages will not be zipped")]
    [switch]
    $noZip = $false,
    [Parameter(HelpMessage="The source of the built packages will not be removed after the build")]
    [switch]
    $keepSources = $false
)

# Config
# Path are relative to the eclipse-based-ide directory
$versionPattern = "(\d+)\.(\d+)\.(\d+)\.rev(\d+)";
$docPdfFilePath = "./doc/user-guide.pdf"
$updateSitePomPath = "./org.eclipse.scava.root/pom.xml"
$eclipsePackagesPath = "$PSScriptRoot/eclipsePackages"
$preparedTestProjects = "$PSScriptRoot/preparedTestProjects"
$preparedWorkspace = "$PSScriptRoot/preparedWorkspace"
$releasedPackages = "$PSScriptRoot/releasedPackages"
$projectImporterJar = "$PSScriptRoot/com.seeq.eclipse.importprojects_1.4.0.jar"
$builtPluginLocation = "./org.eclipse.scava.root/releng/org.eclipse.scava.update/target/repository/plugins";
$tmpPath = "$PSScriptRoot/tmp"

# functions
function ToLinuxPath ([string] $path) {
    $disk = $path.Substring(0, 1).ToLower();
    $rest = $path.Substring(2).Replace("\", "/");
    return "/mnt/$($disk)$($rest)";
}

try {
    # logging
    $logStream = [System.IO.StreamWriter] "$PSScriptRoot/testPackager.log";

    # Set working dir
    Push-Location -Path $PSScriptRoot/../..

    # Testing pre-conditions
    if( !(Test-Path -Path $eclipsePackagesPath) ) {
        throw "The eclipse packages directory was not found. Check script for details"
    }
    if( !(Test-Path -Path $preparedTestProjects) ) {
        throw "The prepared test projects directory was not found. Check script for details"
    }
    if( !(Test-Path -Path $preparedWorkspace) ) {
        throw "The prepared workspace directory was not found. Check script for details"
    }
    if( !(Test-Path -Path $releasedPackages) ) {
        throw "The released packages directory was not found. Check script for details"
    }
    if( !(Test-Path -Path $projectImporterJar) ) {
        throw "The project importer JAR was not found. Check script for details"
    }

    # Check for uncommitted changes.
    if( !$ignoreUncommittedChanges ) {
        if( git status --porcelain | Where-Object { $_ -notmatch "^\?\?" } ) {
            throw "There are uncommitted changes in the working directory."
        }
    }
    # Check if the versions are the same in every component
    if( !$ignoreVersions ) {
        "Checking component versions"

        # Get versions
        $pluginVersion = & "$PSScriptRoot/../common/version/getVersion.ps1" -print -noDoc:$noDoc

        # Increase the version of the package programmatically
        if( $increaseVersions ) {

            # check task (issue/dev/master/etc)
            $gitBranch = git rev-parse --abbrev-ref HEAD
            switch -regex ($gitBranch) {
                "^feature/(\d+)/([^/]+)/(.+)$" { $task = $Matches[1] }
                "^dev$" { $task = "dev" }
                "^master$" { $task = "master" }
                Default { throw "Could not determine the task you are currently working on from the git branch name ($gitBranch)" }
            }

            "Task currently working on:`t$task"

            # check if the task is an issue
            if( $task -notmatch "\d+" ) {
                throw "You have to work on an issue to use the auto version increaser. You are currently on the $gitBranch branch. Checkout to a feature branch."
            }

            if( !$ignoreHeadDiverge ) {
                # update repository
                git fetch

                # get short describtion about the local and the remote HEAD
                $gitHead = git rev-parse HEAD
                $gitUpstream = git rev-parse '@{u}' 2>$null

                # check if the local and the remote HEAD are pointing to the same commit, if an upstream was configured
                if( $gitUpstream -notmatch "fatal: no upstream configured for branch" -and $gitHead -ne $gitUpstream ) {
                    throw "It looks like the HEAD diverged from the remote. Please check your git repository (push, pull) and then try again"
                }
            }

            # fill the $Matches variable with the parts of the plug-in version
            ($pluginVersion -match $versionPattern) | Out-Null
            # check if this will be the first release on this branch
            if( $Matches[3] -ne $task ) {
                $releaseVersion = $Matches[1]+"."+$Matches[2]+"."+$task+".rev0"
            }else{
                $releaseVersion = $Matches[1]+"."+$Matches[2]+"."+$task+".rev"+$(1+$Matches[4])
            }
            
            # ask if the proposed version is acceptable
            while( $accept -notmatch "[yn]" ) {
                Write-host "Do you accept " -NoNewline
                Write-Host $releaseVersion -ForegroundColor Yellow -NoNewline
                Write-Host " as the proposed next version? (y/n):" -NoNewline
                $accept = Read-Host
            }
            
            # if not, then ask for an other
            if( $accept -notmatch "[y]" ) {
                do {
                    $releaseVersion = Read-Host -Prompt "Input the package version (0.0.0.rev0)"
                }while( $releaseVersion -notmatch $versionPattern )
            }

            Write-host "Package version will be: " -NoNewline
            Write-Host $releaseVersion -ForegroundColor Yellow

            # Write new versions
            try{
                & "$PSScriptRoot/../common/version/setVersion.ps1" -createBackup -version $releaseVersion -noDoc:$noDoc
                
                $createdVersionBackups = $true
                Write-host "Versions have been updated to " -NoNewline
                Write-host $releaseVersion -ForegroundColor Yellow
            }catch{
                throw "Something went wrong during updating the versions of components: $_"
            }

            # check if the number of modified files is the expected
            if( !$ignoreNumberOfModifications ) {
                $numberOfModifiedFiles = (git status --porcelain | Where-Object { $_ -match "^[ M]M" } | Measure-Object ).Count
                $expectedNumberOfModifiedFiles = if(!$noDoc) {5} else {3}
                if( $numberOfModifiedFiles -ne $expectedNumberOfModifiedFiles ) {
                    "Modified files:"
                    ( git status --porcelain ) | ForEach-Object { Write-Host $_ -ForegroundColor Yellow }
                    throw "Something went wrong during setting the versions. Unexpected number of modified files. Number of modified files: $numberOfModifiedFiles , expected: $expectedNumberOfModifiedFiles"
                }
            }

        }else{
            $releaseVersion = $pluginVersion;
        }
    }else{
        while( $releaseVersion -notmatch $versionPattern ) {
            $releaseVersion = Read-Host -Prompt "Input the package version (0.0.0.rev0)"
        }
    }


    # Build updatesite
    if( !$noBuild ) {
        "Building Update site"

        $updateSiteBuildSuccess = $false;
        & { mvn -f $updateSitePomPath clean verify 2>&1 } | ForEach-Object {
            if( $_ -eq "[INFO] BUILD SUCCESS" ) {
                $updateSiteBuildSuccess = $true;
            }
            $logStream.WriteLine($_)
        }

        if( !$updateSiteBuildSuccess ) {
            throw "Something went wrong during the building of the Update Site"
        }
    }

    # Build test pacakge
    $releasePath = "$releasedPackages/$releaseVersion"

    # cleaning previous build
    if( Test-Path -Path $releasePath ) {
        "Cleaning up after previous build with the same version"
        Remove-Item $releasePath -Recurse -Force -ErrorAction Ignore
        Remove-Item $releasePath -Recurse -Force -ErrorAction Ignore # this second action is intended
        
        if( Test-Path -Path $releasePath ) {
            throw "Cleaning up after previous build with the same version failed. Please remove the previous build manually."
        }
    }

    # scanning eclipse packages
    "Scanning for Eclipse packages"
    $eclipsePackages = @();
    Get-ChildItem $eclipsePackagesPath -Directory -Exclude "_*" | ForEach-Object { $eclipsePackages += $_ };

    foreach($eclipsePackage in $eclipsePackages) {
        try {
            Write-host "Processing package " -NoNewline
            Write-Host $($eclipsePackage.Name) -ForegroundColor Yellow

            # determine platform from the name of the package
            "Determine platform";
            $platform = @{os = ""; bit = 32; buildName = "" };
            switch -Wildcard ($eclipsePackage.Name) {
                "*win32*" {
                    $platform.os = "win";
                    $platform.buildName = "Windows"
                }
                "*linux*" {
                    $platform.os = "linux";
                    $platform.buildName = "Linux";
                }
                # it's a bit hacky, but if we found this substring too, then we expect a x64 package
                "*-x86_64*" {
                    $platform.bit = 64;
                }
                Default {
                    throw "Could not determine the platform of the package";
                    continue;
                }
            }
            "Platform is $($platform.os) $($platform.bit)bit";

            # determine package name
            $packageName = "XM_plugin_$($platform.buildName)_v$releaseVersion"
            $packagePath = "$releasePath/$packageName"

            # copy package
            "Copy Eclipse package"
            Copy-Item -Path "$($eclipsePackage.FullName)/eclipse" -Destination $packagePath -Recurse

            "Configure package"
            # configure eclipse.ini
            (Get-Content "$packagePath/eclipse.ini") | ForEach-Object { 
                if( $_ -eq "-Dosgi.instance.area.default=@user.home/eclipse-workspace" ) {
                    "-Dosgi.instance.area.default=./eclipse-workspace";
                }else{
                    $_;
                }
            } | Set-Content "$packagePath/eclipse.ini";

            # copy prepared workspace
            Copy-Item -Path "$preparedWorkspace" -Destination "$packagePath/eclipse-workspace" -Recurse

            "Import prepared test projects"
            # copy prepared test projects
            Copy-Item -Path "$preparedTestProjects/*" -Destination "$packagePath/eclipse-workspace" -Recurse

            # install importer plug-in
            Copy-Item -Path $projectImporterJar -Destination "$packagePath/plugins/com.seeq.eclipse.importprojects.jar"
            
            # first run, to finish installation
            switch ($platform.os) {
                "win" {
                    . $packagePath/eclipsec.exe -nosplash -application com.seeq.eclipse.importprojects.headlessimport -data "$packagePath/eclipse-workspace" 2>&1>$null -ErrorAction Continue;
                }
                "linux" {
                    . bash -c "$(ToLinuxPath($packagePath))/eclipse -nosplash -data `"$(ToLinuxPath($packagePath))/eclipse-workspace`"" 2>&1>$null -ErrorAction Continue;
                }
            }

            # second run, to activate importer
            switch ($platform.os) {
                "win" {
                    . $packagePath/eclipsec.exe -nosplash -application com.seeq.eclipse.importprojects.headlessimport -data "$packagePath/eclipse-workspace" -import "$packagePath/eclipse-workspace" 2>&1>$null -ErrorAction Continue;
                }
                "linux" {
                    . bash -c "$(ToLinuxPath($packagePath))/eclipse -nosplash -application com.seeq.eclipse.importprojects.headlessimport -data `"$(ToLinuxPath($packagePath))/eclipse-workspace`" -import `"$(ToLinuxPath($packagePath))eclipse-workspace`"" 2>&1>$null -ErrorAction Continue;
                }
            }

            # uninstall project importer
            Remove-Item -Path "$packagePath/plugins/com.seeq.eclipse.importprojects.jar"

            "Install plug-in"
            Copy-Item -Path "$builtPluginLocation/*" -Destination "$packagePath/plugins" -Filter "*.jar"

            if( !$noDoc ) {
                "Copy documentation"
                Copy-Item -Path $docPdfFilePath -Destination "$packagePath/user-guide.pdf"
            }

            # Test run
            if( $testRun ) {
                if( $platform.os -eq "win" ) {
                    $testPackagePath = "$($packagePath)_TEST"
                    Copy-Item -Path $packagePath -Destination $testPackagePath -Recurse
                    Push-Location $testPackagePath
                    try{
                        . ./eclipse.exe
                        :testMenuLoop do {
                            Write-Host "*** Test menu ***" -ForegroundColor Magenta
                            "y: Test finished successfully, build can continue"
                            "n: Test failed. Stop building"
                            "r: Rerun Eclipse"
                            "o: Open package in Explorer"
                            $result = Read-Host -Prompt "Enter the letter of one of the commands"
                            
                            if( $result -match "[ynr]" ) {
                                do{
                                    $testEclipsePath = Resolve-Path ./eclipse.exe
                                    $testEclipseProcess = Get-Process | Where-Object { $_.path -eq $testEclipsePath }
                                    if( $testEclipseProcess ) {
                                        Write-Host "Waiting for $testEclipsePath to close..." -ForegroundColor Magenta
                                        # $testEclipseProcess.CloseMainWindow() | Out-Null
                                        $testEclipseProcess | Stop-Process -Force
                                        Start-Sleep -Seconds 1
                                    }else{
                                        break
                                    }
                                }while( $true )
                            }

                            switch ($result) {
                                "y" { "Test finished successfully"; break testMenuLoop }
                                "n" { throw "Test failed. Building is stopped" }
                                "r" { . ./eclipse.exe }
                                "o" { Invoke-Item . }
                            }
                        } while ( $true )
                    }finally{
                        Pop-Location
                        "Removing test copy"
                        Remove-Item $testPackagePath -Recurse -Force -ErrorAction Ignore
                        Remove-Item $testPackagePath -Recurse -Force -ErrorAction Ignore
                    }
                    
                }else{
                    Write-host "Packages only for windows platfrom can be tested at the moment. Sry" -ForegroundColor Red
                }
            }

            # Packaging
            if( !$noZip ) {
                "Compressing package"
                Compress-Archive -Path "$packagePath/*" -DestinationPath "$releasePath/$packageName.zip" -CompressionLevel Optimal
            }

            Write-host "$packageName was built successfully" -ForegroundColor Green
        }catch{
            throw "Something went wrong during building the test package: $_"
        }finally{
            if( !$keepSources ) {
                "Cleaning after build"
                Remove-Item $packagePath -Recurse -Force -ErrorAction Ignore
            }

            if( (Get-ChildItem -Path $releasePath | Measure-Object).Count -eq 0 ) {
                Remove-Item $releasePath
            }
        }
    }

    Write-host "`aAll found Eclipse packages($($eclipsePackages.count)) has been processed" -ForegroundColor Green
    if( Test-Path -Path $releasePath ) {
        Invoke-Item $releasePath
    }


    # Finish auto version increase
    if( !$ignoreVersions -and $increaseVersions ) {
        do{
            "The following tracked files have changes:"
            ( git status --untracked-files=no --porcelain ) | ForEach-Object { Write-Host $_ -ForegroundColor Yellow }
            $acceptAutoCommiting = Read-Host -Prompt "Do you want to stage these changes, commit them with the message 'set versions' and tag the commit with $releaseVersion ? (y/n)"
        }while( $acceptAutoCommiting -notmatch "[yn]")

        if( $acceptAutoCommiting -match "y" ) {
            # stage changes
            git add -u

            # commit changes
            git commit -m "set versions"

            # tag the commit with the correct version
            git tag $releaseVersion

            # ask for push
            do{
                $acceptAutoTagUpload = Read-Host -Prompt "Do you want to push the tag $releaseVersion to origin and open the gitlab pages related to the new tag and to the issue $task in your default browser? A default tag description and a default comment to the issue will be shown as well. (y/n)"
            }while( $acceptAutoTagUpload -notmatch "[yn]")
    
            if( $acceptAutoTagUpload -match "y" ) {

                # push tag to origin
                git push origin $releaseVersion
    
                # open the issue's page in browser
                Start-Process "https://git.sed.hu/geryxyz/crossminer/issues/$task"
    
                # open tag's page in browser
                Start-Process "https://git.sed.hu/geryxyz/crossminer/tags/$releaseVersion"

                # default messages
                "Test package is available at: https://git.sed.hu/geryxyz/crossminer/tags/$releaseVersion" | Set-Content "$tmpPath/issueComment.txt"
                . "$tmpPath/issueComment.txt"
                
                "# Release $releaseVersion

## Description

$(if( $noDoc ) { "You can download a portable eclipse with preinstalled plugin." } else { "You can download a portable eclipse with preinstalled plugin and the user guide." })
" | Set-Content "$tmpPath/tagDescription.txt"
                . "$tmpPath/tagDescription.txt"
            }
        }
    }
}catch {
    if( $createdVersionBackups ) {
        # reset versions
        "Reset versions"
        & "$PSScriptRoot/../common/version/setVersion.ps1" -restoreBackup -noDoc:$noDoc
    }
    throw $_
}finally{
    Pop-Location
    $logStream.close();
}
