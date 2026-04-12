@echo off
setlocal

echo ==========================================
echo Running JMeter smoke search test...
echo ==========================================

set JMETER_HOME=%~dp0..\..\jmeter-tools

REM TIMESTAMP
for /f "tokens=1-4 delims=/.- " %%a in ("%date%") do (
    set D1=%%a
    set D2=%%b
    set D3=%%c
    set D4=%%d
)

for /f "tokens=1-2 delims=: " %%a in ("%time%") do (
    set HH=%%a
    set MIN=%%b
)

if 1%HH% LSS 110 set HH=0%HH:~-1%

set TIMESTAMP=%D1%_%D2%_%D3%_%D4%_%HH%%MIN%

set TEST_PLAN=%~dp0..\test-plans\search_smoke.jmx
set RESULT_FILE=%~dp0..\results\jtl\search_smoke_%TIMESTAMP%.jtl
set REPORT_DIR=%~dp0..\results\reports\search_smoke_%TIMESTAMP%
set LOG_FILE=%~dp0..\logs\jmeter_smoke_%TIMESTAMP%.log

REM Ensure required directories exist
if not exist "%~dp0..\logs" mkdir "%~dp0..\logs"
if not exist "%~dp0..\results" mkdir "%~dp0..\results"
if not exist "%~dp0..\results\jtl" mkdir "%~dp0..\results\jtl"
if not exist "%~dp0..\results\reports" mkdir "%~dp0..\results\reports"

echo JMeter Home: %JMETER_HOME%
echo Test Plan: %TEST_PLAN%
echo Result File: %RESULT_FILE%
echo Report Dir: %REPORT_DIR%
echo Log File: %LOG_FILE%

echo Starting JMeter in non-GUI mode...
"%JMETER_HOME%\bin\jmeter.bat" -n -t "%TEST_PLAN%" -l "%RESULT_FILE%" -e -o "%REPORT_DIR%" -j "%LOG_FILE%"

echo.
echo Execution finished.
pause