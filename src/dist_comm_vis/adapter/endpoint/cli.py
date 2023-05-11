import argparse
import os
import sys
from typing import List

from dependency_injector.wiring import inject, Provide

from dist_comm_vis.adapter.dependency_injection.container import Container
from dist_comm_vis.application.ServiceModel import ServiceModelApplication


def bootstrap():
    # injects the configuration values from the command line arguments into the container
    arguments = parse_command_line_arguments(sys.argv[1:])
    configuration = create_configuration_values_from_command_line_arguments(arguments)

    container = Container()
    container.config.from_dict(configuration)
    container.init_resources()
    container.wire(modules=[__name__])

    main()


@inject
def main(service_model_application: ServiceModelApplication = Provide[Container.service_model_application],
         config: any = Provide[Container.config]):
    service_model_application.create_for_project(config.get("id"), config.get("name"), config.get("path"), config.get("output_path"))


def parse_command_line_arguments(args: List[str]) -> argparse.Namespace:
    parser = argparse.ArgumentParser()

    parser.add_argument("id", help="project id of the service")
    parser.add_argument("name", help="name of the service")
    parser.add_argument("path")
    parser.add_argument("--output-path", help="path to the output file", default=os.getcwd())
    parser.add_argument("--log-config-file", help="config file for logging in ini style",
                        default="../../../../logging.ini")

    return parser.parse_args(args)


def create_configuration_values_from_command_line_arguments(args) -> dict[str, str]:
    return {
        "id": args.id,
        "name": args.name,
        "path": args.path,

        "output_path": args.output_path,

        "log_config_file": args.log_config_file
    }
