web:

echo $JAVA_OPTS
java -jar ./target/tomcat.8000/work/Tomcat/localhost/_/WEB-INF/lib/liquibase-core-3.5.3.jar \
     --driver=org.postgresql.Driver \
     --classpath=build/libs/cake-catalog-monolith-0.1.war \
     --changeLogFile=changelog.xml \
     --url="jdbc:postgresql://ec2-54-75-230-140.eu-west-1.compute.amazonaws.com:5432/d3bifn5ooqrvhe?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory" \
     --username=kgmevmhljblkht \
     --password= \
     migrate
cd build/libs ;
