echo ''
echo 'Executing DB migrations'

java -jar ./server/liquibase-core-3.5.3.jar \
--driver=org.postgresql.Driver \
--classpath=./build/libs/*.war \
--changeLogFile=changelog.xml \
--url="$(echo $JDBC_DATABASE_URL)&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory" migrate;

echo ''
echo 'Running app...'

cd build/libs ;
java $JAVA_OPTS -Dgrails.env=prod -jar ../../server/webapp-runner.jar --port $PORT *.war