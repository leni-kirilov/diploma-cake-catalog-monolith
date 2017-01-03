echo ''
echo 'Building war...'
gradle stage

echo ''
echo 'Executing DB migrations'

cd build/libs ;
java -jar ../../server/liquibase-core-3.5.3.jar \
     --driver=com.mysql.jdbc.Driver \
     --classpath=$(ls *.war) \
     --changeLogFile=changelog.xml \
     --url="jdbc:mysql://localhost:3306/cake_catalog_monolith" \
     --username=root \
     --password= \
     migrate

echo ''
echo 'Running app...'

java $JAVA_OPTS -javaagent:../../server/newrelic/newrelic.jar -Dnewrelic.environment=development -Dgrails.env=development -jar ../../server/webapp-runner.jar --port 8000 *.war

cd ../..