param(
    [Parameter(Mandatory, HelpMessage="The address of the server the OpenAPI specification will be downloaded from")]
    [string]
    [ValidateNotNullOrEmpty()]
    $address,
    [Parameter(Mandatory, HelpMessage="The name of the output file")]
    [string]
    [ValidateNotNullOrEmpty()]
    $file
)

$url = "$address/v2/api-docs"

try{
    Invoke-WebRequest -Uri $url -OutFile "$PSScriptRoot/$file"
    Write-Host "`aSpecifiaction successfully fetched" -ForegroundColor Green
}catch{
    throw "Something went wring during fetching the OpenAPI specification: $_"
}
