try {
    Push-Location $PSScriptRoot/../../org.eclipse.scava.root
    $logStream = [System.IO.StreamWriter] "$PSScriptRoot/updatesiteBuilder.log"

    $success = $false
    . { mvn clean verify } | Foreach-Object {
        if( $_ -eq "[INFO] BUILD SUCCESS" ) {
            $success = $true
        }
        $logStream.WriteLine($_)
    }

    if( $success ) {
        Invoke-Item "$PSScriptRoot/../../org.eclipse.scava.root/releng/org.eclipse.scava.update/target/repository"
        Write-Host "`aBuild finished successfully" -ForegroundColor Green
    }else{
        Invoke-Item $PSScriptRoot
        throw "Maven build failed. Check log."
    }  
}finally{
    $logStream.close();

    Pop-Location
}