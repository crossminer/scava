param(
    [Parameter(HelpMessage="Target version")]
    [string]
    $version,
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
$backupPath = "$PSScriptRoot/backup"
$backupPluginManifestFilePath = "$backupPath/MANIFEST.MF"
$backupFeatureXmlFilePath = "$backupPath/feature.xml"
$backupUpdateSiteXmlFilePath = "$backupPath/category.xml"

try{
    $logStream = [System.IO.StreamWriter] "$PSScriptRoot/setVersion.log";
    Push-Location "$PSScriptRoot/../../.."

    if( $restoreBackup ) {
        Copy-Item $backupPluginManifestFilePath $pluginManifestFilePath
        Copy-Item $backupFeatureXmlFilePath $featureXmlFilePath
        Copy-Item $backupUpdateSiteXmlFilePath $updateSiteXmlFilePath
        exit
    }

    if( $createBackup ) {
        Copy-Item $pluginManifestFilePath $backupPluginManifestFilePath
        Copy-Item $featureXmlFilePath $backupFeatureXmlFilePath
        Copy-Item $updateSiteXmlFilePath $backupUpdateSiteXmlFilePath
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
}catch{
    if( $createBackup ) {    
        Copy-Item $backupPluginManifestFilePath $pluginManifestFilePath
        Copy-Item $backupFeatureXmlFilePath $featureXmlFilePath
        Copy-Item $backupUpdateSiteXmlFilePath $updateSiteXmlFilePath
    }

    throw $_
}finally{
    Pop-Location
    $logStream.Close()
}
