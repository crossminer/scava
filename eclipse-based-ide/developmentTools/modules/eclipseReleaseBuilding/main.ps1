param ( # do not modify this section
    [string]$Version = $(Read-Host -Prompt "Build version number"),
    [switch]$CopyDocument = $((Read-Host -Prompt "Do you want to copy the documentation? (empty=y / n)" ) -eq "n" ),
    [switch]$ToZip = $((Read-Host -Prompt "Do you want to zip the releases? (empty=y / n)" ) -eq "n" )
)

$CopyDocument = !$CopyDocument;
$ToZip = !$ToZip;

Write-Host "Eclipse Release Builder module" -ForegroundColor Red;

# Configuration

$pathToConfig = $PSScriptRoot + "/config/";
$pathToEclipses = $PSScriptRoot + "/eclipses/";
$pathToReleases = $PSScriptRoot + "/releases/";
$pathToReleaseDestination = $pathToReleases + "/" + $Version + "/";
$pathToProjects = $PSScriptRoot + "/projects/";
$pathToUpdatesite = $PSScriptRoot + "/../../../org.eclipse.scava.root/releng/org.eclipse.scava.update/target/repository/";
$pathToUpdatesitePlugins = $pathToUpdatesite + "/plugins/";
$pathToDocument = $PSScriptRoot + "/../../../doc/user-guide.pdf";
$pathToTmp = $PSScriptRoot + "/tmp/";

# Do not modify anything under this line

function BuildStep ([String] $message) {
    #Write-Host "* " -ForegroundColor Yellow -noNewLine;
    Write-Host $message;
}

function ToLinuxPath ([string] $path) {
    $disk = $path.Substring(0, 1).ToLower();
    $rest = $path.Substring(2).Replace("\", "/");
    return "/mnt/$($disk)$($rest)";
}

try{
    # check input parameter
    if ( $Version -notmatch "^\d+\.\d+\.\d+\.rev\d+$" ) {
        throw "The give version is not matching the pattern. It should be something like 1.0.0.rev0";
    }

    # check
    if( !(Test-Path -Path $pathToEclipses) ) {
        throw "eclipsePackages directory was not found.";
    }
    if( !(Test-Path -Path "$($pathToConfig)com.seeq.eclipse.importprojects_1.4.0.jar") ) {
        throw "Project importer plugin was not found.";
    }
    if( !(Test-Path -Path $pathToProjects) ) {
        throw "Projects directory was not found.";
    }
    if( !(Test-Path -Path $pathToUpdatesitePlugins) ) {
        throw "Plugins directory in built updatesite was not found. Did you build the updatesite?";
    }
    if( !(Test-Path -Path $pathToReleases) ) {
        throw "Releases directory was not found.";
    }

    Invoke-Expression "$($PSScriptRoot)/versionCheckPlugin.ps1 -Version $($Version)";
    if( $CopyDocument ) {
        Invoke-Expression "$($PSScriptRoot)/versionCheckDoc.ps1 -Version $($Version)";
    }
    
    # cleaning up after previous build
    if ( Test-Path -Path $pathToTmp ) {
        BuildStep "Claning up after previous build";
        Remove-Item $pathToTmp -Recurse -Force -ErrorAction Ignore;
    }

    # Cleaning previous build
    if ( Test-Path -Path $pathToReleaseDestination ) {
        BuildStep "Delete previous build with the same version number";
        Remove-Item "$($pathToReleaseDestination)" -Recurse -Force -ErrorAction Ignore;
        # needs twice to ensure the whole directory is deleted. The first erases only the content
        Remove-Item "$($pathToReleaseDestination)" -Recurse -Force -ErrorAction Ignore;
    }

    # scan for eclipse sources. Skip those that starts with _
    BuildStep "Scanning for Eclipse packages";
    $eclipseSources = @();
    Get-ChildItem $pathToEclipses -Directory -Exclude "_*" | ForEach-Object { $eclipseSources += $_ };

    $allPackagesBuiltSuccessfully = $true;

    # process found packages
    foreach ($eclipse in $eclipseSources) {
        # inner try-catch to prevent interrupt of the whole building process
        try{

            # info
            BuildStep "Processing package $($eclipse.Name)";

            # read platform from name
            BuildStep "Determine platform";
            $platform = @{os = ""; bit = 0; buildName = "" };
            switch -Wildcard ($eclipse.Name) {
                "*win32*" {
                    $platform.os = "win";
                    $platform.bit = 32;
                    $platform.buildName = "Windows"
                }
                "*linux*" {
                    $platform.os = "linux";
                    $platform.bit = 32;
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

            # print platform
            BuildStep "Operating system: $($platform.os) $($platform.bit) bit";
            
            # copy it
            BuildStep "Copy package into temp dir";
            Copy-Item -Path $eclipse.FullName -Destination $pathToTmp -Recurse;
            $pathToEclipse = $pathToTmp + "eclipse/";

            # edit eclipse.ini
            BuildStep "Edit eclipse.ini";
            Invoke-Expression "$($PSScriptRoot)/editEclipseIni.ps1 -Path `"$($pathToEclipse)eclipse.ini`"" | Out-Null;
            
            # install project importer
            BuildStep "Install project importer Plug-in";
            Copy-Item -Path "$($pathToConfig)com.seeq.eclipse.importprojects_1.4.0.jar" -Destination "$($pathToEclipse)plugins/com.seeq.eclipse.importprojects_1.4.0.jar";

            # copy config/eclipse-workspace
            if ( Test-Path -Path "$($pathToConfig)eclipse-workspace" ) {
                BuildStep "Copy prepared workspace";
                Copy-Item -Path "$($pathToConfig)eclipse-workspace" -Destination "$($pathToEclipse)eclipse-workspace" -Recurse;
            }

            # copy projects
            BuildStep "Copy projects into workspace";
            if( !(Test-Path -Path "$($pathToEclipse)eclipse-workspace") ) {
                New-Item -Path "$($pathToEclipse)eclipse-workspace" -ItemType Directory;
            }
            Copy-Item -Path "$($pathToProjects)*" -Destination "$($pathToEclipse)eclipse-workspace" -Recurse;
            
            # first run
            BuildStep "Headless run to finish installation";
            switch ($platform.os) {
                "win" {
                    Invoke-Expression "$($pathToEclipse)eclipsec.exe -nosplash -application com.seeq.eclipse.importprojects.headlessimport -data `"$($pathToEclipse)eclipse-workspace`"" 2>&1>$null;
                }
                "linux" {
                    Invoke-Expression "bash -c '$(ToLinuxPath($pathToEclipse))eclipse -nosplash -application com.seeq.eclipse.importprojects.headlessimport -data `"$(ToLinuxPath($pathToEclipse))eclipse-workspace`"'" 2>&1>$null;
                }
            }
            
            # import run
            BuildStep "Headless run to import projects";
            switch ($platform.os) {
                "win" {
                    Invoke-Expression "$($pathToEclipse)eclipsec.exe -nosplash -application com.seeq.eclipse.importprojects.headlessimport -data `"$($pathToEclipse)eclipse-workspace`" -import `"$($pathToEclipse)eclipse-workspace`"" 2>&1>$null -ErrorAction Continue;
                }
                "linux" {
                    Invoke-Expression "bash -c '$(ToLinuxPath($pathToEclipse))eclipse -nosplash -application com.seeq.eclipse.importprojects.headlessimport -data `"$(ToLinuxPath($pathToEclipse))eclipse-workspace`" -import `"$(ToLinuxPath($pathToEclipse))eclipse-workspace`"'" 2>&1>$null -ErrorAction Continue;
                }
            }

            # uninstall project improrter
            BuildStep "Uninstall project importer plug-in";
            Remove-Item -Path "$($pathToEclipse)plugins/com.seeq.eclipse.importprojects_1.4.0.jar";

            # install updatesite
            BuildStep "Install updatesite";
            Copy-Item -Path "$($pathToUpdatesitePlugins)*" -Destination "$($pathToEclipse)plugins" -Filter "*jar";

            # copy document
            if ( $CopyDocument ) {
                BuildStep "Copy document";
                Copy-Item -Path "$($pathToDocument)" -Destination "$($pathToEclipse)user-guide.pdf";
            }
            
            # RELEASE!
            BuildStep "Release Portable"
            $releaseName = "XM_plugin_$($platform.buildName)_v$($Version)";
            if ( $ToZip ) {
                New-Item -Path "$($pathToReleaseDestination)" -ItemType Directory -ErrorAction Ignore | Out-Null;
                Compress-Archive -Path "$($pathToEclipse)*" -CompressionLevel Optimal -DestinationPath "$($pathToReleaseDestination)$($releaseName).zip";
            }
            else {
                Copy-Item -Path "$($pathToEclipse)" -Destination "$($pathToReleaseDestination)$($releaseName)" -Recurse;
            }
            
            Write-Host "Package $($eclipse.Name) has been built successfully" -ForegroundColor Green;
        }catch{
            $allPackagesBuiltSuccessfully = $false;
            Write-Error "`aSomething went wrong during the processing of package $($eclipse.name): $_";
        }finally{
            # clean after build
            BuildStep "Clean after build";
            Remove-Item $pathToTmp -Recurse -Force -ErrorAction Ignore;
        }
    }

    if( $allPackagesBuiltSuccessfully ) {
        Write-Host "All found packages have been built successfully" -ForegroundColor Green;
    }else{
        Write-Error "Some errors happened during the build of a package";
    }
}catch{
    Write-Error "`aSomething went wrong: $_";
}finally{
    
}