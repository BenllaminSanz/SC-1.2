@echo off
setlocal EnableDelayedExpansion
cd /d "%~dp0"

echo.
echo  Pool de pruebas — Software Capacitacion Legacy
echo.

set "BUILD=build\classes"
set "TEST_BUILD=build\test\classes"
set "CP=%BUILD%"

for %%j in (lib\*.jar) do set "CP=!CP!;%%j"

if not exist "%BUILD%\Querys\Conexion.class" (
    echo [1/3] Compilando proyecto principal...
    if not exist "%BUILD%" mkdir "%BUILD%"
    dir /s /b src\*.java > "%TEMP%\sc_sources.txt"
    javac -encoding UTF-8 -source 1.8 -target 1.8 -d "%BUILD%" -cp "%CP%" @"%TEMP%\sc_sources.txt"
    if errorlevel 1 (
        echo Error al compilar el proyecto principal.
        exit /b 1
    )
) else (
    echo [1/3] Recompilando clases modificadas...
    javac -encoding UTF-8 -source 1.8 -target 1.8 -d "%BUILD%" -cp "%CP%" ^
        src\Querys\Conexion.java src\Querys\ConsultasLogin.java ^
        src\Querys\ConsultasTrabajador.java src\Querys\ConsultasBaja.java ^
        src\Functions\QueryFunctions.java src\Tables\CargarTabla.java ^
        src\Controller\CtrlTrabajador.java
)

echo [2/3] Compilando suites de integracion...
if not exist "%TEST_BUILD%" mkdir "%TEST_BUILD%"
javac -encoding UTF-8 -source 1.8 -target 1.8 -d "%TEST_BUILD%" -cp "%CP%" test\integration\*.java
if errorlevel 1 (
    echo Error al compilar tests.
    exit /b 1
)

echo [3/3] Ejecutando pool...
java -cp "%CP%;%TEST_BUILD%" integration.RunTestPool %*
set "RC=%ERRORLEVEL%"
exit /b %RC%