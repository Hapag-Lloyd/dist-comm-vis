from dist_comm_vis.adapter.endpoint.cli import parse_command_line_arguments, \
    create_configuration_values_from_command_line_arguments


def test_that_mandatory_parameters_are_parsed():
    # given
    given_args = ["my-path"]

    # when
    actual_args = parse_command_line_arguments(given_args)

    # then
    assert actual_args.path == "my-path"


def test_log_config_file_parameter_accepted():
    # given
    given_args = ["my-path", "--log-config-file", "my-log-config-file"]

    # when
    actual_args = parse_command_line_arguments(given_args)

    # then
    assert actual_args.log_config_file == "my-log-config-file"


def test_all_parameters_are_present_in_container_dictionary():
    # given
    given_all_args = ["my-path", "--log-config-file", "my-log-config-file", "--output-path", "my-output-path"]

    # when
    actual_args = create_configuration_values_from_command_line_arguments(parse_command_line_arguments(given_all_args))

    # then
    assert actual_args == {
        "path": "my-path",
        "output_path": "my-output-path",
        "log_config_file": "my-log-config-file"
    }
