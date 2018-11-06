while ( $true ) {
    Clear-Host;
    Write-Host "Available DevelopmentTools:" -ForegroundColor Red;

    # Store the avaible otions
    $modules = @();

    # get available modules under ./modules dir
    Get-ChildItem ./modules -Recurse -File -Depth 1 -Filter "main.ps1" | ForEach-Object {
        $modules += @{
            label   = $_.Directory.Name;
            command = $_.FullName;
        };
    };

    # add additional exit option
    $modules += @{ label = "exit"; command = "exit"; };

    # iterate over the options
    for ($i = 0; $i -lt $modules.Length; $i++) {
        $moduleName = $modules[$i].label;

        # Camel-case to readable name
        $moduleName  | Select-String -Pattern "([a-z])([A-Z])" -AllMatches -CaseSensitive | ForEach-Object { $_.Matches } | ForEach-Object { $moduleName = $moduleName.Replace($_.value, $_.Groups[1].value + " " + $_.Groups[2].value.toLower()) };

        # print option
        Write-Host "$($i+1)" -ForegroundColor Green -NoNewline
        Write-Host ". $moduleName";
    }

    # read user input
    Write-Host "Please give the number of the wanted option: " -ForegroundColor Yellow -NoNewline;
    $read = (Read-Host) - 1;

    # execute input
    Clear-Host;
    Invoke-Expression $modules[$read].command;
    pause;
}
