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
     --url="jdbc:postgresql://ec2-54-228-255-234.eu-west-1.compute.amazonaws.com:5432/dbq2rvsu94nh6f?user=fpkxyggrntzlax&password=e7307ff88a46bf6731e1a7c8f6a3588cf00dcce8bfd143b32a2bc170a6c516e5&sslmode=require" \
     migrate

echo ''
echo 'Running app...'

java $JAVA_OPTS -Dgrails.env=production -jar ../../server/webapp-runner.jar --port 8000 *.war

cd ../..