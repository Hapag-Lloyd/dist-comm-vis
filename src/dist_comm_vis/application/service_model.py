import logging

from dist_comm_vis.domain.service import FileFinderService


class ServiceModelApplication:
    file_finder: FileFinderService
    logger: logging.Logger

    def __init__(self, file_finder: FileFinderService):
        self.file_finder = file_finder
        self.logger = logging.getLogger(__name__)

    def create_for_project(self, path: str):
        for file in self.file_finder.find_files(path):
            self.logger.info("Found file: %s", file.full_qualified_name)
