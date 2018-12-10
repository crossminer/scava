Write-Host "Dependency Building module" -ForegroundColor Red;

# Configuration

$pathToPom = $PSScriptRoot+"/../../../org.eclipse.scava.root/bundles/org.eclipse.scava.plugin.dependencies/pom.xml";
$pathToLog = $PSScriptRoot+"/maven.log";

# Do not modify anything under this line

try {
    $logStream = [System.IO.StreamWriter] $pathToLog;
    $success = $false;

    # 2>&1 rediret error output to the standard outoput
    (mvn -f $pathToPom clean verify) 2>&1 | Foreach-Object {
        if( $_ -eq "[INFO] BUILD SUCCESS" ) {
            $success = $true;
        }
        $logStream.WriteLine($_);
    }

    $logStream.close();

    if( $success ) {
        Write-Host "`Dependency project build has been finished successfully" -ForegroundColor green;
    }else{
        throw "Maven build failed. Please check the log at $pathToLog";
    }  
}catch {
    Write-Error "`aSomething went wrong: $_";
}