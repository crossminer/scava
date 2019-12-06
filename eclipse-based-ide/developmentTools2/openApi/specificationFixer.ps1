param(
    [Parameter(Mandatory, HelpMessage="The file the fix should be run on")]
    [string]
    [ValidateNotNullOrEmpty()]
    $file
)

$path = "$PSScriptRoot/$file"

try{
    ( Get-Content $path ) | ForEach-Object {

        while( $_ -match "(«([^»]*)»)" ) {
            $_ = $_ -replace $Matches[1], "Of$($Matches[2])"
        }

        while( $_ -match "(`"type`"\s*:\s*`"ref`")" ) {
            $_ = $_ -replace $Matches[1], "`"type`": `"object`""
        }

        $_
    } | Set-Content $path
    Write-Host "`aThe specification fix finished successfully" -ForegroundColor Green
}catch{
    throw "Something went wrong during the fixing: $_"
}

