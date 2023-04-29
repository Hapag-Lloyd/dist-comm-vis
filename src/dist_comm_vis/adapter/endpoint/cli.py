import argparse

from dist_comm_vis.adapter.configuration import CommandlineConfiguration
from dist_comm_vis.adapter.unit_of_work import DefaultUnitOfWork


def main():
    arguments = parse_command_line_arguments()
    configuration = create_configuration_values_from_command_line_arguments(arguments)

    DefaultUnitOfWork(configuration).service_model_application.create_for_project()


def parse_command_line_arguments() -> argparse.Namespace:
    parser = argparse.ArgumentParser()

    parser.add_argument("--log-level", help="debug, info, warning, error, critical")
    parser.add_argument("--log-format", help="plain, json")

    return parser.parse_args()


def create_configuration_values_from_command_line_arguments(args) -> CommandlineConfiguration:
    return CommandlineConfiguration({
        "LOG_LEVEL": args.log_level,
        "LOG_FORMAT": args.log_format
    })
