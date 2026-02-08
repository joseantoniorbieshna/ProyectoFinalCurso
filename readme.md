# PRIMEROS PASOS
1. Descargar Spring Tools Suite for eclipse 4.32.0
2. Instalar Lombok en eclipse de spring, ubicado en la carpeta "ComplementosPrevios"
3. Crear base de datos llamada "faltas"

# IMPORTANTE
Hay un XML de ejemplo para hacer pruebas en la carpeta "ComplementosPrevios"

# SONARQUBE
1. Nos iremos al proyecto
2. Click derecho Run As
3. Run configuration...
4. Crearemos un nuevo Maven Build
5. En goals pondremos el siguiente código con sus respectivo host y login  

clean verify sonar:sonar -Dsonar.projectKey=faltasProject -Dsonar.host.url=conexionLocal/conexionExterna  -Dsonar.login=hashLogin

# COMPILAR
install -Dmaven.test.skip=true

# BUILD DOCKER IMAGE AND UPLOAD IN DOCKERHUB
docker build -t mrjoshrb/proyecto-fin-curso-app-faltas:alfa-0.1 .   

docker push mrjoshrb/proyecto-fin-curso-app-faltas:alfa-0.1   

Cambiar version cada vez que se suba uno nuevo