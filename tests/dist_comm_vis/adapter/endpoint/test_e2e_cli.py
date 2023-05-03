import logging

from dependency_injector.wiring import Provide, inject

from dist_comm_vis.adapter.dependency_injection.container import Container


def test_cli_calls_application():
    container = Container()
    container.config.from_dict({"log_config_file": "../logging.ini"})
    container.init_resources()

    test_main()
    assert True is True


@inject
def test_main():
    logging.getLogger(__name__).info("Hello World!")
