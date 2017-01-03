echo ''
echo 'Executing DB migrations'

cd build/libs ;
java -jar ../../server/liquibase-core-3.5.3.jar \
    --driver=org.postgresql.Driver \
    --classpath=$(ls *.war) \
    --changeLogFile=changelog.xml \
    --url="$(echo $JDBC_DATABASE_URL)&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory" migrate;

echo ''
echo 'Running app...'

java $JAVA_OPTS -javaagent:../../server/newrelic/newrelic.jar -Dnewrelic.environment=production -Dgrails.env=prod -jar ../../server/webapp-runner.jar --port $PORT *.war