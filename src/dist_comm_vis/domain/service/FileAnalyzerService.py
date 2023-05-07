import re
from abc import ABC, abstractmethod
from re import Pattern
from typing import AnyStr, List

from dist_comm_vis.domain.model.File import File
from dist_comm_vis.domain.model.Model import ModelRelation, ModelRelationFactory
from dist_comm_vis.domain.service.FileReaderService import FileReaderService


class FileAnalyzerService(ABC):
    """Knows how to find the model information in source code files."""

    MODEL_REGEXES: List[Pattern[AnyStr]] = [
        # published REST API endpoint
        r'dist-comm-vis type=(rest) direction=(in)\s*',
        # REST API call
        r'dist-comm-vis type=(rest) direction=(out) target-project-id=(?P<target_project>\d+)\s*'
    ]

    file_reader_service: FileReaderService

    def __init__(self, file_reader_service: FileReaderService):
        self.file_reader_service = file_reader_service

    @abstractmethod
    def detect_model_relations(self, file: File) -> List[ModelRelation]:
        raise NotImplementedError


class FileAnalyzerServiceFactory:
    file_reader_service: FileReaderService

    def __init__(self, file_reader_service: FileReaderService):
        self.file_reader_service = file_reader_service

    def create(self, file: File) -> FileAnalyzerService:
        match file.extension:
            case "java":
                return JavaFileAnalyzerService(self.file_reader_service)
            case _:
                raise Exception("No analyzer found for file: " + file.full_qualified_name)


class JavaFileAnalyzerService(FileAnalyzerService):
    _instance = None

    model_regexes: List[Pattern[AnyStr]] = [
    ]

    def __new__(cls, file_reader_service: FileReaderService):
        if cls._instance is None:
            cls._instance = super(JavaFileAnalyzerService, cls).__new__(cls)
            cls._instance.__init__(file_reader_service)

            cls._instance._init_model_regex()

        return cls._instance

    def __init__(self, file_reader_service: FileReaderService):
        super().__init__(file_reader_service)

    def _init_model_regex(self):
        prefixes: List[str] = [
            r"\s*// "
        ]

        for prefix in prefixes:
            for regex in FileAnalyzerService.MODEL_REGEXES:
                self.model_regexes.append(re.compile(prefix + regex))

    def detect_model_relations(self, file: File) -> List[ModelRelation]:
        relations: List[ModelRelation] = []

        for line in self.file_reader_service.read_lines(file):
            for regex in self.model_regexes:
                model_match = regex.search(line)

                if model_match:
                    target_project_id = model_match.group('target-project-id') if \
                        "target-project-id" in model_match.groupdict().keys() else None

                    relations.append(ModelRelationFactory.create(model_match.group(1), model_match.group(2),
                                                                 target_project_id))

        return relations
