[![Actions](https://github.com/Hapag-Lloyd/dist-comm-vis/workflows/Release/badge.svg)](https://github.com/Hapag-Lloyd/dist-comm-vis/actions)

# Distributed Communication Visualization

This tool analyzes JAR files and creates a diagram to show
- incoming/outgoing HTTP(S) traffic
- event receivers and senders

It also creates a high level diagram of multi project communication, e.g. service 1 calls service 2.

Especially useful to visualize the communication between several services. This documentation is always
up-to-date as it is generated automatically based on the current version of the software.

# Example
```shell
release="1.11.1"
curl -o analyzer.jar https://github.com/Hapag-Lloyd/dist-comm-vis/releases/download/${RELEASE}/analyzer-${RELEASE}.jar

# make sure that all dependencies are added to the classpath! 
java -cp "analyzer.jar:distributed-projects/microservice-a/target/test-classes" org.springframework.boot.loader.JarLauncher scan com.hlag.tools.commvis.example 1 --name=Customer

yum install graphviz
dot -Tpng model.dot > model.png
```

For a more sophisticated example check the [scan.sh](scripts/scan.sh)
```shell
scripts/scan.sh distributed-projects/microservice-a/target 1234 service-a
scripts/scan.sh distributed-projects/microservice-b/target 2345 service-b
```

![Communication](image/communication.png)


## Features
- extract HTTP(S) consumers: JAX RS, Spring
- extract JMS consumers
- extract producers via special annotation: HTTP(S)
- write a `model.json` file
- write a `model.dot` file for [GraphViz](https://gitlab.com/graphviz/graphviz)
- support user defined scanners (see https://github.com/Hapag-Lloyd/dist-comm-vis-api)

## Planned Features
- extract endpoints from Swagger YAML
- extract Kafka consumers and producers
- extract JMS producers
- extract SNS producers
- extract SQS consumers and producers
