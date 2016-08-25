[CmdletBinding()]
param(
    [Parameter(Mandatory=$True)]
    [string]$WebSiteName
)

Switch-AzureWebsiteSlot -Name $WebSiteName