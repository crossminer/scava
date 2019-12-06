param(
    [Parameter(HelpMessage="The input specification file")]
    [string]
    [ValidateNotNullOrEmpty()]
    $file = "openapi.json",
    [Parameter(HelpMessage="The output directory where the generated project will be placed")]
    [string]
    $outputDirectory,
    [Parameter(HelpMessage="Do not remove the generated client's project after building")]
    [switch]
    $keepSources,
    [Parameter(HelpMessage="Do not generate new client")]
    [switch]
    $noGeneration,
    [Parameter(HelpMessage="Do not install the built project to the Maven local repository")]
    [switch]
    $noInstall
)

$generatorJar = "$PSScriptRoot/swagger-codegen-cli-3.0.7.jar"
$generatorConfig = "$PSScriptRoot/codegen-java-config.json"

try{
    # Log
    $logStream = [System.IO.StreamWriter] "$PSScriptRoot/clientBuilder.log";

    # Test previous build
    if( Test-Path -Path "$PSScriptRoot/projects/$outputDirectory" ) {
        "Cleaning up after previous build"
        Remove-Item -Path "$PSScriptRoot/projects/$outputDirectory" -Recurse -Force
    }

    # Generate client
    "Generating client"
    $logStream.WriteLine("Generating client")
    . { java -jar $generatorJar generate -i "$PSScriptRoot/$file" -l java -o "$PSScriptRoot/projects/$outputDirectory" -c $generatorConfig } 2>&1 | ForEach-Object { $logStream.WriteLine($_) } 

    # Build and install project
    if( !$noInstall ) {
        "Building and installing project"
        $logStream.WriteLine("Building and installing client")
        $pomPath = "projects/$outputDirectory/pom.xml"
        $mvnSuccess = $false
        . { mvn -f "$PSScriptRoot/$pomPath" install } 2>&1 | ForEach-Object {
            if( $_ -eq "[INFO] BUILD SUCCESS" ) {
                $mvnSuccess = $true;
            }
            $logStream.WriteLine($_)
        }
    
        if( !$mvnSuccess ) {
            Invoke-Item $PSScriptRoot
            throw "Maven build failed. Check log."
        }
    }

    Write-Host "`aFinished successfully" -ForegroundColor Green
}catch{
    throw "Something went wrong: $_"
}finally{
    # Cleaning up after build
    if( !$keepSources ) {
        "Cleaning up after build"
        Remove-Item -Path "projects/$outputDirectory" -Recurse -Force
    }

    # Close logger
    $logStream.Close();
}