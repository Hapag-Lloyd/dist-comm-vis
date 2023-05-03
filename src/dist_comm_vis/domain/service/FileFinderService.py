from abc import ABC, abstractmethod

from dist_comm_vis.domain.model.File import File


class FileFinderService(ABC):
    def __init__(self):
        pass

    @abstractmethod
    def find_files(self, directory: str) -> File:
        pass
