set "lj=%~dp0"

cd\
%~d0
cd %lj%
xcopy startup.cmd "%lj%\publish\startup.cmd" /q/r/y/d
cd target
xcopy patchproducer.jar "%lj%\publish\patchproducer.jar" /q/r/y/d
cd..
cd src\main\resources
xcopy application.yaml "%lj%\publish\config\application.yaml" /q/r/y/d
xcopy README.md "%lj%\publish\README.md" /q/r/y/d

pause
