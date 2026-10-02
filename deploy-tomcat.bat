@echo off
setlocal

set "PROJECT_DIR=%~dp0"
set "TOMCAT_DIR=C:\Program Files\Apache Software Foundation\Tomcat 11.0"
set "WEBAPPS_DIR=%TOMCAT_DIR%\webapps"
set "WAR_NAME=exo.war"
set "WAR_PATH=%PROJECT_DIR%target\%WAR_NAME%"

echo Compilation du projet...
pushd "%PROJECT_DIR%"
call mvn clean package
if errorlevel 1 (
    echo ERREUR : la compilation Maven a echoue.
    popd
    exit /b 1
)
popd

if not exist "%TOMCAT_DIR%" (
    echo ERREUR : Tomcat est introuvable : "%TOMCAT_DIR%"
    exit /b 1
)

echo Preparation de l'application exo...
if not exist "%WEBAPPS_DIR%" mkdir "%WEBAPPS_DIR%"
del /q "%WEBAPPS_DIR%\%WAR_NAME%" 2>nul
if exist "%WEBAPPS_DIR%\exo" rmdir /s /q "%WEBAPPS_DIR%\exo"
copy /y "%WAR_PATH%" "%WEBAPPS_DIR%\%WAR_NAME%" >nul
if errorlevel 1 (
    echo ERREUR : impossible de copier le WAR vers Tomcat.
    exit /b 1
)

echo Deploiement termine : "%WEBAPPS_DIR%\%WAR_NAME%"
echo URL : http://localhost:8080/exo/
echo JSON : http://localhost:8080/exo/json
echo Vue : http://localhost:8080/exo/vue
exit /b 0