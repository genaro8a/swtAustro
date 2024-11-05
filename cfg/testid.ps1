# Obtener información del sistema, incluido el SID
$computerInfo = Get-WmiObject -Class Win32_ComputerSystem
$computerName = $computerInfo.Name
$userInfo = Get-WmiObject -Class Win32_UserAccount -Filter "LocalAccount=True"
$userInfo | Select-Object Name, SID | Format-Table -AutoSize