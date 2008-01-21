@echo off

IF "%JAVA_HOME%"=="" SET LOCAL_JAVA=java
IF NOT "%JAVA_HOME%"=="" SET LOCAL_JAVA=%JAVA_HOME%\bin\java

set JASTROLOGY_HOME=$INSTALL_PATH

REM non-installation mode, have not parsed by Izpack.
if not exist "%JASTROLOGY_HOME%\jastrology.bat" set JASTROLOGY_HOME=.

set MAIN_CLASS=com.ivstars.astrology.gui.Chart
cd /d "%JASTROLOGY_HOME%"
set TMP_CP="%JASTROLOGY_HOME%\classes"

REM we are in develop mode 
if not exist "%TMP_CP%" set TMP_CP="%JASTROLOGY_HOME%\target\build"

dir /b "%JASTROLOGY_HOME%\lib\*.jar" > temp.tmp
FOR /F %%I IN (temp.tmp) DO CALL "%JASTROLOGY_HOME%\addpath.bat" "%JASTROLOGY_HOME%\lib\%%I"
SET TMP_CP=%TMP_CP%;"%CLASSPATH%"
SET TMP_PARMS=--jastrology-home "%JASTROLOGY_HOME%" %1 %2 %3 %4 %5 %6 %7 %8 %9
echo %TMP_CP%
@rem Run with a command window.
@rem "%LOCAL_JAVA%" -cp %TMP_CP% %MAIN_CLASS% %TMP_PARMS%

@rem To change the language edit and uncomment this line:
@rem start "Jastrology" /B "%LOCAL_JAVA%w" -Xmx256m -cp %TMP_CP%;<your working dir here> -Duser.language=<your language here> %MAIN_CLASS% %TMP_PARMS%


@rem Run with no command window. This may not work with older versions of Windows. Use the command above then.
start "Jastrology" /B "%LOCAL_JAVA%w" -Xmx256m -cp %TMP_CP% %MAIN_CLASS% %TMP_PARMS%
del temp.tmp
