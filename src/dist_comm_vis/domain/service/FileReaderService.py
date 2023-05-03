from abc import ABC, abstractmethod

from dist_comm_vis.domain.model.File import File


class FileReaderService(ABC):
    """Knows how to read files."""

    def __init__(self):
        pass

    @abstractmethod
    def read_lines(self, file: File) -> str:
        raise NotImplementedError
