(1)server:
java -classpath TcpIpTest.jar test.Server <port>

example:
java -classpath TcpIpTest.jar test.Server 1234


(2)client:
java -classpath TcpIpTest.jar test.Client <host> <port> <FileToBeMonitored>

example:
java -classpath TcpIpTest.jar test.Client 127.0.0.1 1234 c:\test.txt


