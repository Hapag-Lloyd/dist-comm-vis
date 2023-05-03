import argparse
import logging

from dependency_injector.wiring import inject, Provide

from dist_comm_vis.adapter.dependency_injection.container import Container
from dist_comm_vis.application.service_model import ServiceModelApplication


def bootstrap():
    # injects the configuration values from the command line arguments into the container
    arguments = parse_command_line_arguments()
    configuration = create_configuration_values_from_command_line_arguments(arguments)

    container = Container()
    container.config.from_dict(configuration)
    container.init_resources()
    container.wire(modules=[__name__])

    main()


@inject
def main(service_model_application: ServiceModelApplication = Provide[Container.service_model_application]):
    service_model_application.create_for_project()


def parse_command_line_arguments() -> argparse.Namespace:
    parser = argparse.ArgumentParser()

    parser.add_argument("--log-config-file", help="config file for logging in ini style", default="../../../../logging.ini")

    return parser.parse_args()


def create_configuration_values_from_command_line_arguments(args) -> dict[str, str]:
    return {
        "log_config_file": args.log_config_file
    }


if __name__ == "__main__":
    bootstrap()
