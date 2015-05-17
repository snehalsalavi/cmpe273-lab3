# CMPE273-Lab3
#Server side
cd to 'server' directory and then execute following commands

Type 'mvn clean package'

Initiate 3 Server instances 

1. For Server A: Type 'java -jar target/server-0.0.1-SNAPSHOT.jar server config/server_A_config.yml'

2. For Server B: Type 'java -jar target/server-0.0.1-SNAPSHOT.jar server config/server_B_config.yml'

3. For Server C: Type 'java -jar target/server-0.0.1-SNAPSHOT.jar server config/server_C_config.yml'

#Client Side

$ cd to 'server' directory and then execute following commands

$ Type 'mvn clean package'

$ Initiate Client instance
  - Type 'java -jar target/client-0.0.1-SNAPSHOT.jar'

#Cache Server endpoints:
Server_A => http://localhost:3000/cache/ Server_B => http://localhost:3001/cache/ Server_C => http://localhost:3002/cache/
