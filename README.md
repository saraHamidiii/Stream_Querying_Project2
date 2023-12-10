---------------------------------------------------------------------------------------------------------
Required to download Apache Kafka:
https://kafka.apache.org/

---------------------------------------------------------------------------------------------------------
Before starting the cluster you have to create the firewall rules in the VPC
---------------------------------------------------------------------------------------------------------
"command" firewall rule
1. IF YOU ARE ABLE TO SSH TO THE VMs, YOU CAN SKIP THIS RULE
2. Run the SSH to the VM to get the IP address needed to connect
3. Go to the VPC page on Google Cloud
4. Click create firewall rule
5. Name it "command"
6. Set the target tag to "All instances in the network"
7. Put in the IP given after the SSH errored for the "*Source IPv4 ranges"
8. In the "Protocol and ports" section, select "Specified protocols and ports"
9. Enable TCP and put port 22
10. Then click create rule

"cluster-connect" firewall rule
1. Click create firewall rule
2. Name it "cluster-connect"
3. Set the target tag to "All instances in the network"
4. In "Source IPv4 ranges*", look up your public ip and paste it in followed by the mask /32, this will allow your pc to connect
5. Put the rest of the 3 VMs external IPs in the "*Source IPv4 ranges" followed by the mask /32
6. Click "Specified protocols and ports" in the "Protocol and ports" section and set the TCP to port 9092
7. Click create rule
NOTE: For the first time starting the cluster you have to add your public ip in the firewall rule,
but after that you don't have to redo this part once you add yours in, so anyone else connecting needs to put their
public ip into this firewall rule


"zookeeper" firewall rule
1. Click create firewall rule
2. Name it "zookeeper"
3. Set the target tag to "All instances in the network"
4. In "Source IPv4 ranges*", add the internal IP addresses of the VMs followed by the mask /32
5. Click allow all in the "Protocol and ports" section
6. Click create rule
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------
To start the kafka cluster and connect:

Start up the 3 VMs then
1. Open SSH on the 3 VMs (1 SSH each)
2. Paste in each console
   "sudo /usr/local/kafka/bin/zookeeper-server-start.sh /usr/local/kafka/config/zookeeper.properties"
3. Then press enter on each one, one by one

4. Open 3 more SSH on the 3 VMs (Another 1 SSH each)
5. Paste in each console
   "sudo /usr/local/kafka/bin/kafka-server-start.sh /usr/local/kafka/config/server.properties"
6. Then press enter on each one, one by one

7. Check the Google Compute page for the 3 VMs external IP address
8. Go to application.properties in the project
9. Put in the spring.kafka.consumers.bootstrap-servers section, the ip_address:port, where the port is 9092
   followed by a comma and put the other two etc.
10. Then for spring.kafka.producer.bootstrap-server section, select one of the external IP addresses (any one is fine)
    and put it, along with the port for each being 9092
11. Then go to MyKafkaConsumer.java and paste the 3 VMs ip_address:port, in the bootstrap.servers section in
    MyKafkaConsumer.java

12. Open notepad, run as administrator
13. Click file and open
14. Navigate to C:\Windows\System32\drivers\etc
15. Click on hosts, if you don't see it make sure you change the view to all files instead of text documents
16. Then put in the following, where you change the "IP_ADDRESS#" part to the external IP address of the VM,
    the name "broker-1" below will match with the external IP address of the VM named "broker-1" and so on

IP_ADDRESS1 broker-1.us-central1-a.c.sapient-logic-400819.internal

IP_ADDRESS2 broker-2.us-central1-a.c.sapient-logic-400819.internal

IP_ADDRESS3 broker-3.us-central1-a.c.sapient-logic-400819.internal


After that you can run the application to start the cluster
