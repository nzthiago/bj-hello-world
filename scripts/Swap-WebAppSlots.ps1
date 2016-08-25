[CmdletBinding()]
param(
    [Parameter(Mandatory=$True)]
    [string]$WebSiteName
)

Switch-AzureWebsiteSlot -Name $WebSiteName | Out-Null
Write-Host "Swappet web app '$WebSiteName'"