[CmdletBinding()]
param(
    [Parameter(Mandatory=$True)]
    [string]$WebSiteName
)

Switch-AzureWebsiteSlot -Name $WebSiteName -Force | Out-Null
Write-Host "Swappet web app '$WebSiteName'"