from logging.config import fileConfig

from dependency_injector import containers, providers

from dist_comm_vis.adapter.service.LocalFileReaderService import LocalFileReaderService
from dist_comm_vis.application.service_model import ServiceModelApplication
from dist_comm_vis.domain.service.FileAnalyzerService import FileAnalyzerServiceFactory
from dist_comm_vis.domain.service.FileFinderService import LocalFileFinderService


class Container(containers.DeclarativeContainer):
    # configuration values
    config = providers.Configuration()

    # services
    file_finder_service = providers.Singleton(
        LocalFileFinderService
    )

    file_reader_service = providers.Singleton(
        LocalFileReaderService
    )

    # factories
    file_analyzer_service_factory = providers.Singleton(
        FileAnalyzerServiceFactory,
        file_reader_service
    )

    # applications
    service_model_application = providers.Singleton(
        ServiceModelApplication,
        file_finder_service,
        file_analyzer_service_factory
    )

    # utilities
    logging = providers.Resource(
        fileConfig,
        config.log_config_file
    )
