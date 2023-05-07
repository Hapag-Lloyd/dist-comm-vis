from pathlib import Path
from dist_comm_vis.domain.model.File import File
from dist_comm_vis.domain.service.FileWriterService import FileWriterService


class LocalFileWriterService(FileWriterService):
    def write(self, file: File, content: str) -> None:
        Path(file.full_qualified_name).parent.mkdir(parents=True, exist_ok=True)

        with open(file.full_qualified_name, 'w') as f:
            f.write(content)
