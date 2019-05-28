param ( # do not modify this section
    [string]$Version = $(Read-Host -Prompt "Build version number")
)

# CONFIGURATION

$pathToManifest = $PSScriptRoot + "/../../../org.eclipse.scava.root/bundles/org.eclipse.scava.plugin/META-INF/MANIFEST.MF";

# Do not modify anything under this line

# Check version in plug-in
if( Test-Path -Path $pathToManifest ) {
    $lookingFor = "Bundle-Version: $Version";
    $found = $false;
    Get-Content -Path $pathToManifest | ForEach-Object {
        if( $_ -eq $lookingFor ) {
            $found = $true;
        }
    }

    if( !$found ) {
        throw "Plug-in config MANIFEST.MF does not contain the correct version number: $Version";
    }
}