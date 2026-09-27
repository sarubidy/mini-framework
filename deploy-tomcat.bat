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
    echo.
    echo ERREUR : la compilation Maven a echoue.
    popd
    exit /b 1
)
popd

if not exist "%TOMCAT_DIR%" (
    echo.
    echo ERREUR : Tomcat est introuvable :
    echo "%TOMCAT_DIR%"
    echo Modifiez TOMCAT_DIR dans ce fichier si votre installation est ailleurs.
    exit /b 1
)

echo Preparation de l'application exo...
if not exist "%WEBAPPS_DIR%" mkdir "%WEBAPPS_DIR%"
if errorlevel 1 (
    echo ERREUR : impossible de creer "%WEBAPPS_DIR%".
    exit /b 1
)

del /q "%WEBAPPS_DIR%\%WAR_NAME%" 2>nul
if exist "%WEBAPPS_DIR%\exo" rmdir /s /q "%WEBAPPS_DIR%\exo"
copy /y "%WAR_PATH%" "%WEBAPPS_DIR%\%WAR_NAME%" >nul
if errorlevel 1 (
    echo ERREUR : impossible de copier le WAR vers Tomcat.
    echo Fermez Tomcat ou lancez ce script avec les droits necessaires.
    exit /b 1
)

echo.
echo Deploiement termine :
echo "%WEBAPPS_DIR%\%WAR_NAME%"
echo URL de test : http://localhost:8080/exo/
echo Routes JSON et vue : /exo/json et /exo/vue
echo Tomcat redeploiera automatiquement le WAR.
exit /b 0