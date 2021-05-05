@echo off

set build="%JAVA_HOME%\bin\javac"
set jar="%JAVA_HOME%\bin\jar"

:find

for /f "usebackq tokens=*" %%a in (`%build% -version`) do (
    echo Compiling reflex with %%a
)

if %ERRORLEVEL% GTR 0 (
    if %build%=="javac" (
        echo "A valid jdk hasn't been found in %JAVA_HOME% nor %PATH%"
        goto :end
    )
    set build="javac"
    set jar="jar"
    goto :find
)

%build% -encoding UTF-8 -cp src -d build\classes --release 11 src\reflex\Main.java
%jar% --create -f build\reflex.jar -m src\META-INF\MANIFEST.MF -C build\classes . -C resources .

echo OK. Run `java -jar build\reflex.jar`.
:end
