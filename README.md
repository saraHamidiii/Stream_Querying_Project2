STEP 1: DOWNLOAD AND INSTALL KAFKA
- Apache kafka : https//kafka.apache.org

STEP 2: OPEN 2 COMMAND TERMINAL AT APACHE KAFKA FOLDER
- first terminal : copy and paste ".\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties"
- press enter
- second terminal : copy and paste ".\bin\windows\kafka-server-start.bat .\config\server.properties"
- press enter
- THIS STARTS THE KAFKA ENVIRONMENT

STEP 3: CLICK RUN "StreamQueryingProject2Application" OR USE SHORTCUT 'SHIFT + F10'

THINGS TO NOTE: 
- IF CONSOLE INFINITELY SAYS "Connection to node -1 (localhost/127.0.0.1:9092) could not be established. Broker may not be available", IT MEANS YOU DID NOT RUN THE KAFKA ENVIRONMENT CORRECTLY AND WILL NEED TO REDO STEP 2