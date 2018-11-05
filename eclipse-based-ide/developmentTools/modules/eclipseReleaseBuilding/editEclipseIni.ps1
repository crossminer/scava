param (
    [Parameter(Mandatory=$true)][string]$Path
)

(Get-Content $Path) | ForEach-Object {
    if( $_ -eq "-Dosgi.instance.area.default=@user.home/eclipse-workspace" ) {
        "-Dosgi.instance.area.default=./eclipse-workspace";
        "-Dorg.osgi.framework.bundle.parent=ext";
        "-Dosgi.framework.extensions=org.eclipse.wst.jsdt.nashorn.extension";
    }else{
        $_;
    }
} | Set-Content $Path;