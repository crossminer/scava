$menu = @();

$menu += @{caption="Fetch OpenAPI specification from http://10.6.13.58:8080 to openapi.json, and fix it"; command="./openApi/specificationFetcher.ps1 -address http://10.6.13.58:8080 -file openapi.json; ./openApi/specificationFixer.ps1 -file openapi.json"}
$menu += @{caption="Fetch OpenAPI specification from http://ci4.castalia.camp:8080 to openapi.json, and fix it"; command="./openApi/specificationFetcher.ps1 -address http://ci4.castalia.camp:8080 -file openapi.json; ./openApi/specificationFixer.ps1 -file openapi.json"}
$menu += @{caption="Build OpenAPI client from openapi.json and install to local Maven repo and delete generated source project"; command="./openApi/clientBuilder.ps1 -file openapi.json -outputDirectory client"}
$menu += @{caption="Build OpenAPI client from openapi.json and install to local Maven repo and keep generated source project"; command="./openApi/clientBuilder.ps1 -file openapi.json -outputDirectory client -keepSources"}
$menu += @{caption="Build Dependency project"; command="./mavenBuilder/dependencyBuilder.ps1"}
$menu += @{caption="Build Update Site (dependencies are built automatically)"; command="./mavenBuilder/updatesiteBuilder.ps1"}
$menu += @{caption="Create a Test package with auto increased version number"; command="./testPackager/testPackager.ps1 -increaseVersions"}
$menu += @{caption="Create a Test package with auto increased version number and with a test run"; command="./testPackager/testPackager.ps1 -increaseVersions -testRun"}
$menu += @{caption="Create a Test package without auto increased version"; command="./testPackager/testPackager.ps1"}
$menu += @{caption="Create a Test package without auto increased version and with a test run"; command="./testPackager/testPackager.ps1 -testRun"}
$menu += @{caption="Launch a test run with ignoring uncommitted changes and the versions of the components and ignoring documentation"; command="./testPackager/testPackager.ps1 -ignoreUncommittedChanges -ignoreVersions -ignoreNumberOfModifications -releaseVersion 999.999.999.rev999 -testRun -noZip -noDoc -ignoreHeadDiverge"}

while( $true ) {
    Clear-Host

    Write-Host "Development Tools v2.0" -ForegroundColor Yellow
    $counter = 1;
    foreach($menuItem in $menu) {
        if($menuItem.command -eq ""  ) {
            Write-Host ""
        }else {
            Write-Host $counter -ForegroundColor Yellow -NoNewline
            Write-Host "."$menuItem.caption
            $counter++;
        }
    }
    Write-Host $counter -ForegroundColor Yellow -NoNewline
    Write-Host ". Exit"
    
    $action = Read-Host -Prompt "Tool to run: "
    if( $action -match "\d+" ) {
        $action = [int]$action
        if( $action -ge 1 -and $action -lt $counter ) {
            $action = -1 + $action
            Write-Host "Run:"$menu[$action].command -ForegroundColor Blue
            try{
                Invoke-Expression $PSScriptRoot"/"$($menu[$action].command)
            }catch {
                Write-Error $_
            }
        }elseif ( $action -eq $counter ) {
            exit
        }else{
            Write-Error "Only numbers from 1 to $counter are accepted"
        }
    }else{
        Write-Error "Only numbers are accepted"
    }
    pause;
}
