Write-Host "Open API Client Building module" -ForegroundColor Red;

# Configuration

$generatorFile = $PSScriptRoot + "/swagger-codegen-cli-3.0.7.jar";
$generatorConfigFile = $PSScriptRoot + "/codegen-java-config.json";
$specificationFile = $PSScriptRoot + "/openapi.json";
$generatedProjectDir = $PSScriptRoot + "/client";

$codegenLogPath = $PSScriptRoot + "/codegen.log";
$mavenLogPath = $PSScriptRoot + "/maven.log";

# Do not modify anything under this line

$pathToPom = $generatedProjectDir+"/pom.xml";

try{
    # get loggers
    $codegenLog = [System.IO.StreamWriter] $codegenLogPath;
    $mavenLog = [System.IO.StreamWriter] $mavenLogPath;

    # cleaning up after previous build
    if( Test-Path -Path $generatedProjectDir ) {
        Write-Host "Cleaning up after previous build.";
        Remove-Item -Path $generatedProjectDir -Recurse -Force -ErrorAction Ignore;
    }

    # checks
    if( !(Test-Path -Path $generatorFile) ) {
        throw "Swagger codegen jar was not found at $generatorFile";
    }
    if( !(Test-Path -Path $generatorConfigFile) ) {
        throw "Swagger codegen config json was not found at $generatorConfigFile";
    }
    if( !(Test-Path -Path $specificationFile) ) {
        throw "Open API specification not was found at $specificationFile. Probably you should run the updater to fetch it from the server.";
    }

    # generate project
    Write-Host "Generate project from specification";
    (java -jar $generatorFile generate -i $specificationFile -l java -o $generatedProjectDir -c $generatorConfigFile) 2>&1 | Foreach-Object {
        $codegenLog.WriteLine($_);
    }
    $codegenLog.close();

    # check if something has been generated
    if( !(Test-Path -Path $pathToPom ) ) {
        throw "Generator not created the POM.XML file. Check log at $codegenLogPath";
    }
    
    # build and install project
    Write-Host "Build and install generated project";
    $mavenBuildSuccess = $false;
    (mvn -f $pathToPom install) 2>&1 | Foreach-Object {
        if( $_ -eq "[INFO] BUILD SUCCESS" ) {
            $mavenBuildSuccess = $true;
        }
        $mavenLog.WriteLine($_);
    }
    $mavenLog.Close();

    # check if something has been generated
    if( !$mavenBuildSuccess ) {
        throw "Maven build not finished with success. Check log at $mavenLogPath";
    }

    Write-Host "`aClient build has been finished successfully" -ForegroundColor Green;
}catch{
    Write-Error "`aSomething went wrong: $_";
}finally{
    # cleaning up after build
    if( Test-Path -Path $generatedProjectDir ) {
        Remove-Item -Path $generatedProjectDir -Recurse -Force -ErrorAction Ignore;
    }
}

