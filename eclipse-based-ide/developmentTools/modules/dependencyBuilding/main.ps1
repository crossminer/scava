Write-Host "Dependency Building module" -ForegroundColor Red;

$pathToPom = $PSScriptRoot+"/../../../org.eclipse.scava.root/bundles/org.eclipse.scava.plugin.dependencies/pom.xml";
$pathToLog = $PSScriptRoot+"/maven.log";

# remove old log
Remove-Item -Path $pathToLog -ErrorAction Ignore;

# log stream
$logStream = [System.IO.StreamWriter] $pathToLog;

# indicator of success
$success = $false;

# 2>&1 rediret error output to the standard outoput
(mvn -f $pathToPom clean verify) 2>&1 | Foreach-Object {
    if( $_ -eq "[INFO] BUILD SUCCESS" ) {
        $success = $true;
    }
    
    $logStream.WriteLine($_);
}

# close log stream
$logStream.close();

# check if it was a successful build
if( $success ) {
    Write-Host "`aSUCCESSFUL dependency build" -ForegroundColor green;
}else{
    Write-Host "`aSomething went wrong. Please see the $pathToLog log." -ForegroundColor Red;
}