import ntpath

from dist_comm_vis.adapter.service.LocalFileReaderService import LocalFileReaderService
from dist_comm_vis.definitions import ROOT_DIR
from dist_comm_vis.domain.model.File import File


def test_returns_file_content():
    # given
    given_full_qualified_name = ntpath.join(ROOT_DIR, "tests/data/local_file_reader_service/a.txt")
    given_content = "my-content"
    given_file = File(given_full_qualified_name)
    given_file.content = given_content

    # when
    actual_content = [line for line in LocalFileReaderService().read_lines(given_file)]

    # then
    assert actual_content[0] == "test1\n"
    assert actual_content[1] == "test2\n"
