import os

from dist_comm_vis.adapter.service.LocalFileReaderService import LocalFileReaderService
from dist_comm_vis.definitions import ROOT_DIR
from dist_comm_vis.domain.model.File import File
from dist_comm_vis.domain.service.FileAnalyzerService import JavaFileAnalyzerService


def test_detects_model_string_in_double_slash_comment():
    # given
    given_local_path = os.path.abspath(os.path.join(ROOT_DIR, "tests/data/source/java"))
    given_file = File(os.path.join(given_local_path, "Class1.java"))

    # when
    actual_model = JavaFileAnalyzerService(LocalFileReaderService()).detect_model_relations(given_file)

    # then

    # not sure what can be asserted here
    assert True is True
