[CmdletBinding()]
param(
    [Parameter(Mandatory=$True)]
    [string]$WebSiteName,

    [Parameter(Mandatory=$False)]
    [string]$Slot
)

$WebSite = Get-AzureRmWebApp -Name $WebSiteName
$ResourceGroupName = $WebSite.ResourceGroup

if($Slot)
{
    Stop-AzureRmWebAppSlot -Name $WebSiteName -ResourceGroupName $ResourceGroupName -Slot $Slot | Out-Null
    Write-Host "Stopped slot '$Slot' belonging to web app '$WebSiteName'"
}
else
{
    Stop-AzureRmWebApp -Name $WebSiteName -ResourceGroupName $ResourceGroupName | Out-Null
    Write-Host "Stopped web app '$WebSiteName'"
}

#Sleep for 30 seconds to ensure site is stopped properly before build continues
Write-Host "Sleeping for 30 seconds..."
Start-Sleep -s 30