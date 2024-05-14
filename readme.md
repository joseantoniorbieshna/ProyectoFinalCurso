# SONARQUBE
1. Nos iremos al proyecto
2. Click derecho Run As
3. Run configuration...
4. Crearemos un nuevo Maven Build
5. En goals pondremos el siguiente código con sus respectivo host y login  

clean verify sonar:sonar  -Dsonar.projectKey=faltasProject -Dsonar.host.url=conexionLocal/conexionExterna  -Dsonar.login=hashLogin