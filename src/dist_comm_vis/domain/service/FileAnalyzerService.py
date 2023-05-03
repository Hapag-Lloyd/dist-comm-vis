import re
from abc import ABC, abstractmethod
from re import Pattern
from typing import AnyStr

from dist_comm_vis.domain.model.File import File
from dist_comm_vis.domain.service.FileReaderService import FileReaderService


class FileAnalyzerService(ABC):
    """Knows how to find the model information in source code files."""

    REST_API_CONSUMER: Pattern[AnyStr] = re.compile(r'\s*// dist-comm-vis direction=(?P<direction>\w+) type=(?P<type>\w+) target-project=(?P<target_project>\d+)\s*')

    file_reader_service: FileReaderService

    def __init__(self, file_reader_service: FileReaderService):
        self.file_reader_service = file_reader_service

    @abstractmethod
    def detect_model_relations(self, file: File):
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

    def __new__(cls, file_reader_service: FileReaderService):
        if cls._instance is None:
            cls._instance = super(JavaFileAnalyzerService, cls).__new__(cls)
            cls._instance.__init__(file_reader_service)

        return cls._instance

    def __init__(self, file_reader_service: FileReaderService):
        super().__init__(file_reader_service)

    def detect_model_relations(self, file: File):
        for line in self.file_reader_service.read_lines(file):
            model_comment_match = FileAnalyzerService.REST_API_CONSUMER.search(line)

            if model_comment_match:
                print(model_comment_match.group('direction'))
                print(model_comment_match.group('type'))
                print(line)
