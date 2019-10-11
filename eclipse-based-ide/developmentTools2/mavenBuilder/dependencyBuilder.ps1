try {
    Push-Location $PSScriptRoot/../../org.eclipse.scava.root/bundles/org.eclipse.scava.plugin.dependencies
    $logStream = [System.IO.StreamWriter] "$PSScriptRoot/dependencyBuilder.log"

    $success = $false
    . { mvn clean verify } | Foreach-Object {
        if( $_ -eq "[INFO] BUILD SUCCESS" ) {
            $success = $true
        }
        $logStream.WriteLine($_)
    }

    if( $success ) {
        Write-Host "`aBuild finished successfully" -ForegroundColor Green
    }else{
        Invoke-Item $PSScriptRoot
        throw "Maven build failed. Check log."
    }  
}finally{
    $logStream.close();

    Pop-Location
}