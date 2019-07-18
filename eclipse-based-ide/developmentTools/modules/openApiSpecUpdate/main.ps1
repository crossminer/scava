Write-Host "Open API Specification Updater module" -ForegroundColor Red;

# Configuration

$url = "http://10.6.13.58:8080/v2/api-docs";
$outputFile = $PSScriptRoot + "/../openApiClientBuilding/openapi.json";

# Do not modify anything under this line

try{
    Write-Host "Fetching Open API specification`n from $url`n into $outputFile";
    Invoke-WebRequest -Uri $url -OutFile $outputFile;
    Write-Host "`aOpen API specification has been updated succesfully." -ForegroundColor Green;
}catch{
    Write-Error "`aSomething went wrong: $_";
}

