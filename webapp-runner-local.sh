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

java $JAVA_OPTS -Dgrails.env=development -jar ../../server/webapp-runner.jar --port 8000 *.war

cd ../..

jdbc:postgresql://ec2-54-75-230-140.eu-west-1.compute.amazonaws.com:5432/d3bifn5ooqrvhe?user=kgmevmhljblkht&password=LEUEvRKfyeiNuOmUZDPshGGAqs&sslmode=require&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory