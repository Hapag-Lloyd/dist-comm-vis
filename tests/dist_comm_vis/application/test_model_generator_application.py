import os

import pytest

from dist_comm_vis.adapter.service.JsonModelWriterService import JsonModelWriterService
from dist_comm_vis.adapter.service.LocalFileFinderService import LocalFileFinderService
from dist_comm_vis.adapter.service.LocalFileReaderService import LocalFileReaderService
from dist_comm_vis.adapter.service.LocalFileWriterService import LocalFileWriterService
from dist_comm_vis.application.ServiceModel import ServiceModelApplication
from dist_comm_vis.definitions import ROOT_DIR
from dist_comm_vis.domain.service.FileAnalyzerService import FileAnalyzerServiceFactory


@pytest.fixture
def application():
    return ServiceModelApplication(LocalFileFinderService(), FileAnalyzerServiceFactory(LocalFileReaderService()),
                                   LocalFileWriterService(), JsonModelWriterService())


def test_dummy(application):
    # given
    given_path_to_analyze = os.path.abspath(os.path.join(ROOT_DIR, "tests/data/service_model"))
    given_output_path = os.path.abspath(os.path.join(ROOT_DIR, "target"))

    # when
    application.create_for_project(given_path_to_analyze, given_output_path)
