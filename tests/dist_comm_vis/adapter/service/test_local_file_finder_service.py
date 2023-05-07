import os
import pytest

from dist_comm_vis.adapter.service.LocalFileFinderService import LocalFileFinderService
from dist_comm_vis.definitions import ROOT_DIR


@pytest.fixture
def service():
    return LocalFileFinderService()


def test_find_files_recursively(service: LocalFileFinderService):
    # given
    given_local_path = os.path.abspath(os.path.join(ROOT_DIR, "tests/data/local_file_finder_service"))

    # when
    actual_files = list(service.find_files(given_local_path))

    # then
    assert len(actual_files) == 3

    assert actual_files[0].full_qualified_name == os.path.abspath(given_local_path + "/a.txt")
    assert actual_files[1].full_qualified_name == os.path.abspath(given_local_path + "/dir/b.txt")
    assert actual_files[2].full_qualified_name == os.path.abspath(given_local_path + "/dir/dir/c.txt")
