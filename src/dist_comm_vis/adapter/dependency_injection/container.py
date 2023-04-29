import logging

from dependency_injector import containers, providers
from pythonjsonlogger import jsonlogger

from dist_comm_vis.application.service_model import ServiceModelApplication


class Container(containers.DeclarativeContainer):

    config = providers.Configuration()

    # Applications
    service_model_application = providers.Factory(
        ServiceModelApplication
    )

    # Utilities
    plain_logger = providers.Resource(
        logging.basicConfig,
        level="INFO"#getattr(logging, "INFO") #config.get("LOG_LEVEL"))
    )

    json_logger = providers.Resource(
        logging.basicConfig,
        level="INFO"#getattr(logging, config.get("LOG_LEVEL"))
    )

    @staticmethod
    def get_json_handler():
        log_handler = logging.StreamHandler()
        formatter = jsonlogger.JsonFormatter()
        log_handler.setFormatter(formatter)

        return log_handler
