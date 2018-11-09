@ECHO OFF
IF EXIST "C:\Program Files\Java\jre1.8.0_181" (
ECHO Recommended Java version found.
java MiniCadIntegrity
)
IF NOT EXIST "C:\Program Files\Java\jre1.8.0_181" (
echo Recommended Java version 1.8.0_181 not installed.
)
PAUSE