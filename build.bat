@echo off

set build="%JAVA_HOME%\bin\javac"
for /f "usebackq tokens=*" %%a in (`%build% -version`) do (
    echo Compiling reflex with %%a
)

set build=%build% -encoding UTF-8 -cp src -d build\classes --release 11
%build% src\Main.java
set jar="%JAVA_HOME%\bin\jar"
%jar% --create --file build\reflex.jar --manifest src\META-INF\MANIFEST.MF -C build\classes .

echo OK
