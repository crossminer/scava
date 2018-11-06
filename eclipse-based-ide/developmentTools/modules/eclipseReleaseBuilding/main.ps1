param (
    [string]$Version = $(Read-Host -Prompt "Build version number"),
    [string]$DocumentName = $(Read-Host -Prompt "Document Name (leave empty to avoid copying a doc)"),
    [switch]$NoZip = $false
)

$ErrorActionPreference = "Suspend";

# Check input parameter
if ( $Version -notmatch "^\d+\.\d+\.\d+\.rev\d+$" ) {
    Write-Error "Version is not matching the given pattern. It should be something like 1.0.0.rev0";
    exit;
}

# Start 
Write-Host "Eclipse release builder module" -ForegroundColor Red;

# used paths
$pathToConfig = $PSScriptRoot + "/config/";
$pathToEclipses = $PSScriptRoot + "/eclipses/";
$pathToReleases = $PSScriptRoot + "/releases/";
$pathToReleaseDestination = $pathToReleases + "/" + $Version + "/";
$pathToProjects = $PSScriptRoot + "/projects/";
$pathToUpdatesite = $PSScriptRoot + "/../../../org.eclipse.scava.root/releng/org.eclipse.scava.update/target/repository/";
$pathToUpdatesitePlugins = $pathToUpdatesite + "/plugins/";
$pathToDocument = $PSScriptRoot + "/../../../doc/main.pdf";
$pathToTmp = $PSScriptRoot + "/tmp/";

function BuildStep ([String] $message) {
    Write-Host "* " -ForegroundColor Yellow -noNewLine;
    Write-Host $message;
}

function ToLinuxPath ([string] $path) {
    $disk = $path.Substring(0, 1).ToLower();
    $rest = $path.Substring(2).Replace("\", "/");
    return "/mnt/$($disk)$($rest)";
}

# Cleaning if tmp dir exists
if ( Test-Path -Path $pathToTmp ) {
    BuildStep "Claning after previous build";
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
BuildStep "Scanning for Eclipses sources";
$eclipseSources = @();
Get-ChildItem $pathToEclipses -Directory -Exclude "_*" | ForEach-Object { $eclipseSources += $_ };

foreach ($eclipse in $eclipseSources) {
    # information
    BuildStep "Package $($eclipse.Name)";

    # get platform
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
            Write-Error "Could not determine the platform of package";
            continue;
        }
    }

    # copy it
    BuildStep "Copy package";
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
    if ( $DocumentName -ne "" ) {
        BuildStep "Copy document";
        Copy-Item -Path "$($pathToDocument)" -Destination "$($pathToEclipse)$($DocumentName).pdf";
    }
    
    # RELEASE!
    BuildStep "Release Portable"
    $releaseName = "XM_plugin_$($platform.buildName)_v$($Version)";
    if ( $NoZip ) {
        Copy-Item -Path "$($pathToEclipse)" -Destination "$($pathToReleaseDestination)$($releaseName)" -Recurse;
    }
    else {
        mkdir "$($pathToReleaseDestination)" -ErrorAction Ignore | Out-Null;
        Compress-Archive -Path "$($pathToEclipse)*" -CompressionLevel Optimal -DestinationPath "$($pathToReleaseDestination)$($releaseName).zip";
    }
    
    # clean after build
    BuildStep "Clean after build";
    Remove-Item $pathToTmp -Recurse -Force -ErrorAction Ignore;
    
    Write-Host "$($eclipse.Name) is DONE" -ForegroundColor Green;
}
