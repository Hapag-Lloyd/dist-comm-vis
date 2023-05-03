import re
from abc import ABC, abstractmethod
from re import Pattern
from typing import AnyStr

from dist_comm_vis.domain.model.File import File
from dist_comm_vis.domain.service.FileReaderService import FileReaderService


class FileAnalyzerService(ABC):
    """Knows how to find the model information in source code files."""

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

    def __new__(cls):
        if cls._instance is None:
            cls._instance = super(JavaFileAnalyzerService, cls).__new__(cls)

        return cls._instance

    def __init__(self):
        super().__init__()

    model_comment_finder: Pattern[AnyStr] = re.compile(r'\s*// dist-comm-vis direction=(?P<direction>\w+) type=(?P<type>\w+) target-project=(?P<target_project>\d+)\s*')

    def detect_model_relations(self, file: File):
        for line in self.file_reader_service.read_lines(file):
            model_comment_match = self.model_comment_finder.search(line)

            if model_comment_match:
                print(model_comment_match.group('direction'))
                print(model_comment_match.group('type'))
                print(line)
