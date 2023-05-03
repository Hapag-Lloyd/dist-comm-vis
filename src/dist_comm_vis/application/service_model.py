import logging

from dist_comm_vis.domain.service import FileFinderService
from dist_comm_vis.domain.service.FileAnalyzerService import FileAnalyzerServiceFactory


class ServiceModelApplication:
    file_finder: FileFinderService
    file_analyzer_service_factory: FileAnalyzerServiceFactory
    logger: logging.Logger

    def __init__(self, file_finder: FileFinderService, file_analyzer_service_factory: FileAnalyzerServiceFactory):
        self.file_finder = file_finder
        self.file_analyzer_service_factory = file_analyzer_service_factory
        self.logger = logging.getLogger(__name__)

    def create_for_project(self, path: str):
        # find all files
        # create an analyzer per file
        # extract all connection strings
        # build the resulting model

        for file in self.file_finder.find_files(path):
            self.logger.info("Found file: %s", file.full_qualified_name)

            analyzer = self.file_analyzer_service_factory.create(file)
            analyzer.detect_model_relations(file)
