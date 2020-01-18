
# Compilation Steps
1) Compile the interfaces into a JAR file
- In the src directory type `javac -cp <root_from_C:_to_\src interfaces\*.java`
  This will compile the interfaces into .class files
- Then type `jar cvf bank.jar interfaces\*.class` 
   Again from the `src` directory. This will compile a class out of the interface files. 

2) Compile the client files
- in the `src` directory again type `javac client\ATM.java`

3) Compile the server files
- in the `src` directory type `javac server\*.java`

3) Now all the files are compiled. The next step is to start the rmiregistry:
- type `rmiregistry 7777` (can be any port, this will start the registry on port 7777)

4) Note: You will need to change the `all.policy` file to update the path to be the path to the `src` directory on your machine to make the RMI SecurityManager work. There is an example `all.policy` file included as an example.

5) In a new console tab start the server:
- type `java -Djava.security.policy=all.policy server.Bank 7777` (needs to be the same port as the rmiregistry.

6) In a new console tab start the client and use client actions:
- type `java client.ATM localhost 7777 login user1 pass1` (this will open the client )



