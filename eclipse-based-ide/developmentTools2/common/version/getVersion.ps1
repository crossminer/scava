param(
    [Parameter(HelpMessage="Ignore format of versions")]
    [switch]
    $ignoreFormat,
    [Parameter(HelpMessage="Print the read versions")]
    [switch]
    $print,
    [Parameter(HelpMessage="Set variables in the caller scope")]
    [switch]
    $setVariables
)

$versionPattern = "^(\d+)\.(\d+)\.(\d+)\.rev(\d+)$";
$pluginManifestFilePath = "./org.eclipse.scava.root/bundles/org.eclipse.scava.plugin/META-INF/MANIFEST.MF"
$featureXmlFilePath = "./org.eclipse.scava.root/features/org.eclipse.scava.feature/feature.xml"
$updateSiteXmlFilePath = "./org.eclipse.scava.root/releng/org.eclipse.scava.update/category.xml"

try{
    Push-Location "$PSScriptRoot/../../.."

    # MANIFEST.MF
    (Get-Content $pluginManifestFilePath) | ForEach-Object {
        if( $_ -match "Bundle-Version:\s+(.+)" ) {
            $pluginVersion = $Matches[1]
        }
    }
    if( $setVariables ) {
        Set-Variable -Name "pluginVersion" -Value $pluginVersion -Scope 1
    }
    if( $print ) {
        Write-Host "Plug-in version:`t`t$pluginVersion"
    }

    # feature.xml
    (Get-Content $featureXmlFilePath) | ForEach-Object { 
        if( $_ -match "<feature .* version=`"([^`"]+)`"" ) {
            $featureVersion = $Matches[1]
        }
    }
    if( $setVariables ) {
        Set-Variable -Name "featureVersion" -Value $featureVersion -Scope 1
    }
    if( $print ) {
        Write-Host "Feature version:`t`t$featureVersion"
    }

    # category.xml
    (Get-Content $updateSiteXmlFilePath) | ForEach-Object {
        if( $_ -match "<feature .* version=`"([^`"]+)`"" ) {
            $referencedFeatureVersion = $Matches[1]
        }
    }
    if( $setVariables ) {
        Set-Variable -Name "referencedFeatureVersion" -Value $referencedFeatureVersion -Scope 1
    }
    if( $print ) {
        Write-Host "Referenced feature version:`t$referencedFeatureVersion"
    }

    # check if the read versions are in the required format
    if( !$ignoreFormat ) {
        if( $pluginVersion -notmatch $versionPattern -or $featureVersion -notmatch $versionPattern -or $referencedFeatureVersion -notmatch $versionPattern ) {
            throw "The versions of some components do not match with the required pattern (0.0.0.rev0)"
        }
    }

    # check if the read versions are the same
    if( $pluginVersion -ne $featureVersion -or $featureVersion -ne $referencedFeatureVersion ) {
        throw "Some component have different versions than others."
    }

    $pluginVersion
}catch{
    throw $_
}finally{
    Pop-Location
}
