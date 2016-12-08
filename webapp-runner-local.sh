echo ''
echo 'Building war...'
gradle stage

echo ''
echo 'Executing DB migrations'

java -jar ./server/liquibase-core-3.5.3.jar \
     --driver=com.mysql.jdbc.Driver \
     --classpath=$(ls build/libs/cake-catalog-monolith*.war) \
     --changeLogFile=changelog.xml \
     --url="jdbc:mysql://localhost:3306/cake_catalog_monolith" \
     --username=root \
     --password= \
     migrate

echo ''
echo 'Running app...'
cd build/libs ; java $JAVA_OPTS -Dgrails.env=dev -jar ../../server/webapp-runner.jar --port 8000 *.war