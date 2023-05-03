import ntpath

from dist_comm_vis.adapter.service.LocalFileReaderService import LocalFileReaderService
from dist_comm_vis.definitions import ROOT_DIR
from dist_comm_vis.domain.model.File import File
from dist_comm_vis.domain.service.FileAnalyzerService import JavaFileAnalyzerService


def test_detects_model_string_in_double_slash_comment():
    # given
    given_local_path = ntpath.abspath(ntpath.join(ROOT_DIR, "tests/data/source/java"))
    given_file = File(ntpath.join(given_local_path, "Class1.java"))

    assert a == __file__

    # when
    actual_model = JavaFileAnalyzerService(LocalFileReaderService()).detect_model_relations(given_file)

    # then
    assert actual_model.direction == "up"
    assert actual_model.type == "string"
