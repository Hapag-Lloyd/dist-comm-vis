import ntpath

from dist_comm_vis.definitions import ROOT_DIR
from dist_comm_vis.domain.model.File import File
from dist_comm_vis.domain.service.FileFinderService import LocalFileFinderService

finder_service = LocalFileFinderService()


def test_find_files_recursively():
    # given
    given_local_path = ntpath.abspath(ntpath.join(ROOT_DIR, "tests/data/local_file_finder_service"))

    # when
    actual_files = list(finder_service.find_files(given_local_path))

    # then
    assert len(actual_files) == 3

    assert actual_files[0].full_qualified_name == ntpath.abspath(given_local_path + "/a.txt")
    assert actual_files[1].full_qualified_name == ntpath.abspath(given_local_path + "/dir/b.txt")
    assert actual_files[2].full_qualified_name == ntpath.abspath(given_local_path + "/dir/dir/c.txt")
