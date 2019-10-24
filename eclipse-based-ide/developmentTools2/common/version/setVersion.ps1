param(
    [Parameter(HelpMessage="Target version")]
    [string]
    $version,
    [Parameter(HelpMessage="Ignore documentation")]
    [switch]
    $noDoc,
    [Parameter(HelpMessage="Backup realted files before writing new versions")]
    [switch]
    $createBackup,
    [Parameter(HelpMessage="Restore version files from backup")]
    [switch]
    $restoreBackup
)

$pluginManifestFilePath = "./org.eclipse.scava.root/bundles/org.eclipse.scava.plugin/META-INF/MANIFEST.MF"
$featureXmlFilePath = "./org.eclipse.scava.root/features/org.eclipse.scava.feature/feature.xml"
$updateSiteXmlFilePath = "./org.eclipse.scava.root/releng/org.eclipse.scava.update/category.xml"
$docTexFilePath = "./doc/user-guide.tex"
$docPdfFilePath = "./doc/user-guide.pdf"
$docPath = "./doc"
$backupPath = "$PSScriptRoot/backup"
$backupPluginManifestFilePath = "$backupPath/MANIFEST.MF"
$backupFeatureXmlFilePath = "$backupPath/feature.xml"
$backupUpdateSiteXmlFilePath = "$backupPath/category.xml"
$backupDocTexFilePath = "$backupPath/user-guide.tex"
$backupDocPdfFilePath = "$backupPath/user-guide.pdf"

try{
    $logStream = [System.IO.StreamWriter] "$PSScriptRoot/setVersion.log";
    Push-Location "$PSScriptRoot/../../.."

    if( $restoreBackup ) {
        Copy-Item $backupPluginManifestFilePath $pluginManifestFilePath
        Copy-Item $backupFeatureXmlFilePath $featureXmlFilePath
        Copy-Item $backupUpdateSiteXmlFilePath $updateSiteXmlFilePath
        if( !$noDoc ) {
            Copy-Item $backupDocTexFilePath $docTexFilePath
            Copy-Item $backupDocPdfFilePath $docPdfFilePath
        }
        exit
    }

    if( $createBackup ) {
        Copy-Item $pluginManifestFilePath $backupPluginManifestFilePath
        Copy-Item $featureXmlFilePath $backupFeatureXmlFilePath
        Copy-Item $updateSiteXmlFilePath $backupUpdateSiteXmlFilePath
        if( !$noDoc ) {
            Copy-Item $docTexFilePath $backupDocTexFilePath
            Copy-Item $docPdfFilePath $backupDocPdfFilePath
        }
    }

    # MANIFEST.MF
    (Get-Content $pluginManifestFilePath) | ForEach-Object {
        if( $_ -match "Bundle-Version:\s+(.+)" ) {
            $_ = $_ -replace $Matches[1], $version
        }
        $_
    } | Set-Content $pluginManifestFilePath;

    # feature.xml
    (Get-Content $featureXmlFilePath) | ForEach-Object { 
        if( $_ -match "<feature .* version=`"([^`"]+)`"" ) {
            $_ = $_ -replace $Matches[1], $version
        }
        $_
    } | Set-Content $featureXmlFilePath;

    # category.xml
    (Get-Content $updateSiteXmlFilePath) | ForEach-Object {
        if( $_ -match "<feature .* version=`"([^`"]+)`"" ) {
            $_ = $_ -replace $Matches[1], $version
        }
        $_
    } | Set-Content $updateSiteXmlFilePath;

    # tex and pdf
    if( !$noDoc ) {
        (Get-Content $docTexFilePath) | ForEach-Object {
            if( $_ -match "\\date\{Version\s+([^\{]*)\}" ) {
                $_ = $_ -replace $Matches[1], $version
            }
            $_
        } | Set-Content $docTexFilePath;
        . { pdflatex.exe -synctex=1 -interaction=nonstopmode -aux-directory="$docPath" -output-directory="$docPath" "$docTexFilePath" 2>$1 } | ForEach-Object { $logStream.WriteLine($_) }
        . { pdflatex.exe -synctex=1 -interaction=nonstopmode -aux-directory="$docPath" -output-directory="$docPath" "$docTexFilePath" 2>$1 } | ForEach-Object { $logStream.WriteLine($_) }
    }
}catch{
    if( $createBackup ) {    
        Copy-Item $backupPluginManifestFilePath $pluginManifestFilePath
        Copy-Item $backupFeatureXmlFilePath $featureXmlFilePath
        Copy-Item $backupUpdateSiteXmlFilePath $updateSiteXmlFilePath
        if( !$noDoc ) {
            Copy-Item $backupDocTexFilePath $docTexFilePath
            Copy-Item $backupDocPdfFilePath $docPdfFilePath
        }
    }

    throw $_
}finally{
    Pop-Location
    $logStream.Close()
}
