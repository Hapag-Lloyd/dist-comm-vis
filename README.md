<!-- place the badges at the very top, looks better -->
<!-- markdownlint-disable-next-line MD041 -->
[![Actions](https://github.com/Hapag-Lloyd/dist-comm-vis/workflows/Release/badge.svg)](https://github.com/Hapag-Lloyd/dist-comm-vis/actions)

# Distributed Communication Visualization

This tool analyzes Java source code and creates a diagram to show

- incoming/outgoing HTTP(S) traffic
- event receivers and senders

It also creates a high level diagram of multi project communication, e.g. service 1 calls service 2.

Especially useful to visualize the communication between several services. This documentation is always
up-to-date as it is generated automatically based on the current version of the software.

# Example
```shell
# make sure that all dependencies are added to the classpath! 
python --name=ServiceNameHere

yum install graphviz
dot -Tpng model.dot > model.png
```

![Communication](image/communication.png)

## Features

## Planned Features

- extract HTTP(S) consumers: JAX RS, Spring
- extract JMS consumers
- extract producers via special annotation: Kafka, SQS, SNS, HTTP(S)
- extract consumers via special annotation: Kafka, SQS
- write a `model.json` file
- write a `model.dot` file for [GraphViz](https://gitlab.com/graphviz/graphviz)
- support user defined scanners (see https://github.com/Hapag-Lloyd/dist-comm-vis-api)

- extract endpoints from Swagger YAML
- extract JMS producers
