run it by maven:
```
mvn spring-boot:run
```
test it by maven:
```
mvn test
```
When starting the platform, you can set configuration application parameters in properly configuration file:
* deploy configuration file at ```src/test/resources/application.properties```.
* test cnfiguration file at ```src/test/resources/application.properties```.
The configuration file is a typical Java properties file. The properties that can be configured are:
```
spring.data.mongodb.host=<DB_HOST>
spring.data.mongodb.port=<DB_PORT>
spring.data.mongodb.database=<DB_NAME>
lucene.index.folder=<path_to_lucene_index>
egit.github.token=<GITHUB_TOKEN>
ossmeter.url=<OSSMETER_URL>
```
An instance of configuration file:
```
spring.data.mongodb.host=localhost 
spring.data.mongodb.port=27017 
spring.data.mongodb.database=CROSSMINER_TEST
lucene.index.folder=/home/user/CROSSMiner_lucene/
egit.github.token=<github_token>
ossmeter.url=http://localhost:8182/
```
import Demo DB
```
mongorestore mongo_dump/dump_2017_12_14/
```

