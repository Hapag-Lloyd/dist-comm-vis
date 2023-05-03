import os
from abc import ABC, abstractmethod

from dist_comm_vis.domain.model.File import File


class FileFinderService(ABC):
    def __init__(self):
        pass

    @abstractmethod
    def find_files(self, directory: str) -> File:
        pass


class LocalFileFinderService(FileFinderService):
    """Service that finds files on the local file system."""

    def __init__(self):
        super().__init__()

    def find_files(self, directory: str) -> File:
        """Generator that yields all files in a directory recursively."""

        for root, d_names, f_names in os.walk(directory):
            for f in f_names:
                yield File(os.path.join(root, f))
