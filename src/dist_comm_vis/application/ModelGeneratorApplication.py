import logging
import os

from dist_comm_vis.adapter.service.ModelWriterService import JsonModelWriterService
from dist_comm_vis.domain.model.File import File
from dist_comm_vis.domain.model.Model import Model
from dist_comm_vis.domain.service import FileFinderService, ModelWriterService
from dist_comm_vis.domain.service.FileAnalyzerService import FileAnalyzerServiceFactory
from dist_comm_vis.domain.service.FileWriterService import FileWriterService


class ModelGeneratorApplication:
    file_finder_service: FileFinderService
    file_analyzer_service_factory: FileAnalyzerServiceFactory
    file_writer_service: FileWriterService
    logger: logging.Logger

    def __init__(self, file_finder: FileFinderService, file_analyzer_service_factory: FileAnalyzerServiceFactory,
                 file_writer_service: FileWriterService, model_writer_service: ModelWriterService):
        self.file_finder_service = file_finder
        self.file_analyzer_service_factory = file_analyzer_service_factory
        self.file_writer_service = file_writer_service
        self.model_writer_service = model_writer_service
        self.logger = logging.getLogger(__name__)

    def create_for_project(self, path_to_analyze: str, output_path: str):
        # find all files
        # create an analyzer per file
        # extract all connection strings
        # build the resulting model

        json_file = File(os.path.abspath(os.path.join(output_path, "model.json")))

        for file in self.file_finder_service.find_files(path_to_analyze):
            self.logger.info("Found file: %s", file.full_qualified_name)

            analyzer = self.file_analyzer_service_factory.create(file)
            model_relations = analyzer.detect_model_relations(file)

            model = Model(model_relations)
            self.file_writer_service.write(json_file, JsonModelWriterService().write(model))
