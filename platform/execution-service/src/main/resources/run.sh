mvn package -T 4 -Dmaven.test.skip -DskipTests -Duser.home=/var/maven
java -jar target/kogito-quickstart-1.0-SNAPSHOT-runner.jar &