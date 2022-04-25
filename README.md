[![Actions](https://github.com/Hapag-Lloyd/dist-comm-vis/workflows/Release/badge.svg)](https://github.com/Hapag-Lloyd/dist-comm-vis/actions)

# Distributed Communication Visualization

This tool analyzes JAR files and creates a diagram to show
- published endpoints
- event receivers and senders

It also creates a high level diagram of multi project communication, e.g. service 1 calls service 2.

Especially useful to visualize the communication between several services. This documentation is always
up-to-date as it is generated automatically based on the current version of the software.

## Features
- extract JAX RS endpoints
- writing a `model.json` file

## Planned Features
- extract endpoints from Swagger YAML
- create DOT file for visualization via [GraphViz](https://gitlab.com/graphviz/graphviz)
- support user defined scanners for endpoints