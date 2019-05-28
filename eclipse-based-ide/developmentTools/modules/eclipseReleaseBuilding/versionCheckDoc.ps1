param ( # do not modify this section
    [string]$Version = $(Read-Host -Prompt "Build version number")
)

# CONFIGURATION

$pathToDoc = $PSScriptRoot + "/../../../doc/main.tex";

# Do not modify anything under this line

# Check version in doc
if( Test-Path -Path $pathToDoc ) {
    $lookingFor = "\date{Version $Version}";
    $found = $false;
    Get-Content -Path $pathToDoc | ForEach-Object {
        if( $_ -eq $lookingFor ) {
            $found = $true;
        }
    }

    if( !$found ) {
        throw "Document main.text does not contain the correct version number: $Version";
    }
}