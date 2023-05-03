from dist_comm_vis.domain.model.File import File
from dist_comm_vis.domain.service.FileReaderService import FileReaderService


class LocalFileReaderService(FileReaderService):
    def read_lines(self, file: File) -> str:
        with open(file.full_qualified_name, 'r') as f:
            for line in f:
                yield line
