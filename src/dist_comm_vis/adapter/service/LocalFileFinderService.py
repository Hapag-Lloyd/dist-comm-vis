import os

from dist_comm_vis.domain.model.File import File
from dist_comm_vis.domain.service.FileFinderService import FileFinderService


class LocalFileFinderService(FileFinderService):
    """Service that finds files on the local file system."""

    def __init__(self):
        super().__init__()

    def find_files(self, directory: str) -> File:
        """Generator that yields all files in a directory recursively."""

        for root, d_names, f_names in os.walk(directory):
            for f in f_names:
                yield File(os.path.join(root, f))
