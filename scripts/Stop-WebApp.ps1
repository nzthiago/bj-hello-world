[CmdletBinding()]
param(
    [Parameter(Mandatory=$True)]
    [string]$WebSiteName,

    [Parameter(Mandatory=$False)]
    [string]$Slot
)

if($Slot)
{
    Stop-AzureWebsite -Name $WebSiteName -Slot $Slot | Out-Null
    Write-Host "Stopped slot '$Slot' belonging to web app '$WebSiteName'"
}
else
{
    Stop-AzureWebsite -Name $WebSiteName | Out-Null
    Write-Host "Stopped web app '$WebSiteName'"
}

#Sleep for 30 seconds to ensure site is stopped properly before build continues
Write-Host "Sleeping for 30 seconds..."
Start-Sleep -s 30