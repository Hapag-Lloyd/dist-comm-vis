import pytest

from dist_comm_vis.adapter.service.LocalFileFinderService import LocalFileFinderService
from dist_comm_vis.adapter.service.LocalFileReaderService import LocalFileReaderService
from dist_comm_vis.application.service_model import ServiceModelApplication
from dist_comm_vis.domain.service.FileAnalyzerService import FileAnalyzerServiceFactory


@pytest.fixture
def application():
    return ServiceModelApplication(LocalFileFinderService(), FileAnalyzerServiceFactory(LocalFileReaderService()))


def test_dummy(application):
    # when
    application.create_for_project("test_path")
