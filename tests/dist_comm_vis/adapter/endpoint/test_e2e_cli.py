import logging

from dependency_injector.wiring import Provide, inject

from dist_comm_vis.adapter.dependency_injection.container import Container


def test_cli_calls_application():
    container = Container()
    container.config.from_dict({"LOG_LEVEL": "DEBUG", "LOG_FORMAT": "PLAIN"})
    test_main()
    assert True is True


@inject
def test_main(logger: logging.Logger = Provide[Container.plain_logger]):
    logger.info("Hello World!")
