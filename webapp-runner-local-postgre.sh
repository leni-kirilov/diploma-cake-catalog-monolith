echo ''
echo 'Building war...'
gradle stage

echo ''
echo 'Executing DB migrations'

cd build/libs ;
java -jar ../../server/liquibase-core-3.5.3.jar \
     --driver=org.postgresql.Driver \
     --classpath=$(ls *.war) \
     --changeLogFile=changelog.xml \
     --url="jdbc:postgresql://ec2-54-75-230-140.eu-west-1.compute.amazonaws.com:5432/d3bifn5ooqrvhe?user=kgmevmhljblkht&password=LEUEvRKfyeiNuOmUZDPshGGAqs&sslmode=require&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory" \
     migrate

echo ''
echo 'Running app...'

#java $JAVA_OPTS -Dgrails.env=production -jar ../../server/webapp-runner.jar --port 8000 *.war

cd ../..