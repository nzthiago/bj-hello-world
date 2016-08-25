[CmdletBinding()]
param(
    [Parameter(Mandatory=$True)]
    [string]$WebSiteName
)

Switch-AzureWebsiteSlot -Name $WebSiteName -Force | Out-Null
Write-Host "Swapped web app '$WebSiteName'"