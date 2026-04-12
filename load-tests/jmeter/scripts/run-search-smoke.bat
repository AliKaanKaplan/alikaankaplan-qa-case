@echo off
setlocal

echo ==========================================
echo Running JMeter smoke search test...
echo ==========================================

REM --------------------------------------------------
REM Resolve JMeter command
REM Priority:
REM 1) Local repo JMeter: ..\..\jmeter-tools\bin\jmeter.bat
REM 2) Global PATH: jmeter
REM --------------------------------------------------
set "LOCAL_JMETER=%~dp0..\..\jmeter-tools"
set "USE_LOCAL_JMETER=false"
set "JMETER_CMD="

if exist "%LOCAL_JMETER%\bin\jmeter.bat" (
    echo Using local JMeter: %LOCAL_JMETER%
    set "USE_LOCAL_JMETER=true"
    set "JMETER_CMD=%LOCAL_JMETER%\bin\jmeter.bat"
) else (
    where jmeter >nul 2>nul
    if %errorlevel%==0 (
        echo Local JMeter not found. Using global JMeter from PATH...
        set "JMETER_CMD=jmeter"
    ) else (
        echo ERROR: JMeter not found.
        echo.
        echo Checked local path:
        echo %LOCAL_JMETER%\bin\jmeter.bat
        echo.
        echo Also could not find 'jmeter' in system PATH.
        echo.
        echo Fix one of these:
        echo 1. Put JMeter under repo at: load-tests\jmeter-tools
        echo 2. Add JMeter bin to PATH, for example:
        echo    C:\tools\apache-jmeter-5.6.3\bin
        exit /b 1
    )
)

REM --------------------------------------------------
REM TIMESTAMP
REM --------------------------------------------------
for /f "tokens=1-4 delims=/.- " %%a in ("%date%") do (
    set "D1=%%a"
    set "D2=%%b"
    set "D3=%%c"
    set "D4=%%d"
)

for /f "tokens=1-2 delims=: " %%a in ("%time%") do (
    set "HH=%%a"
    set "MIN=%%b"
)

if 1%HH% LSS 110 set "HH=0%HH:~-1%"

set "TIMESTAMP=%D1%_%D2%_%D3%_%D4%_%HH%%MIN%"

REM --------------------------------------------------
REM Paths
REM --------------------------------------------------
set "TEST_PLAN=%~dp0..\test-plans\search_smoke.jmx"
set "RESULT_FILE=%~dp0..\results\jtl\search_smoke_%TIMESTAMP%.jtl"
set "REPORT_DIR=%~dp0..\results\reports\search_smoke_%TIMESTAMP%"
set "LOG_FILE=%~dp0..\logs\jmeter_smoke_%TIMESTAMP%.log"

REM --------------------------------------------------
REM Ensure required directories exist
REM --------------------------------------------------
if not exist "%~dp0..\logs" mkdir "%~dp0..\logs"
if not exist "%~dp0..\results" mkdir "%~dp0..\results"
if not exist "%~dp0..\results\jtl" mkdir "%~dp0..\results\jtl"
if not exist "%~dp0..\results\reports" mkdir "%~dp0..\results\reports"

echo JMeter Command: %JMETER_CMD%
echo Test Plan: %TEST_PLAN%
echo Result File: %RESULT_FILE%
echo Report Dir: %REPORT_DIR%
echo Log File: %LOG_FILE%

if not exist "%TEST_PLAN%" (
    echo ERROR: Test plan not found:
    echo %TEST_PLAN%
    exit /b 1
)

echo Starting JMeter in non-GUI mode...

if /i "%USE_LOCAL_JMETER%"=="true" (
    call "%JMETER_CMD%" -n -t "%TEST_PLAN%" -l "%RESULT_FILE%" -e -o "%REPORT_DIR%" -j "%LOG_FILE%"
) else (
    call jmeter -n -t "%TEST_PLAN%" -l "%RESULT_FILE%" -e -o "%REPORT_DIR%" -j "%LOG_FILE%"
)

if %errorlevel% neq 0 (
    echo.
    echo JMeter execution failed with exit code %errorlevel%.
    exit /b %errorlevel%
)

echo.
echo Execution finished successfully.
pause