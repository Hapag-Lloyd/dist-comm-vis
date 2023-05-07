from abc import ABC, abstractmethod

from dist_comm_vis.domain.model.File import File


class FileWriterService(ABC):
    def __init__(self):
        pass

    @abstractmethod
    def write(self, file: File, content: str) -> None:
        """Writes the given content to the given file. The file is overwritten if it already exists."""
        pass
