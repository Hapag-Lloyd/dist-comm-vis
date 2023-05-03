from logging.config import fileConfig

from dependency_injector import containers, providers

from dist_comm_vis.application.service_model import ServiceModelApplication
from dist_comm_vis.domain.service.FileFinderService import LocalFileFinderService


class Container(containers.DeclarativeContainer):
    # configuration values
    config = providers.Configuration()

    # services
    file_finder_service = providers.Singleton(
        LocalFileFinderService
    )

    # applications
    service_model_application = providers.Singleton(
        ServiceModelApplication,
        file_finder_service
    )

    # utilities
    logging = providers.Resource(
        fileConfig,
        config.log_config_file
    )
