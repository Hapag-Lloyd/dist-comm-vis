import pytest

from dist_comm_vis.adapter.service.LocalFileReaderService import LocalFileReaderService
from dist_comm_vis.domain.model.File import File
from dist_comm_vis.domain.service.FileAnalyzerService import FileAnalyzerServiceFactory, JavaFileAnalyzerService


def test_returns_an_analyzer_for_java_files():
    # given
    given_file = File("my-file.java")

    # when
    actual_analyzer = FileAnalyzerServiceFactory(LocalFileReaderService()).create(given_file)

    # then
    assert isinstance(actual_analyzer, JavaFileAnalyzerService)


def test_throws_exception_for_unknown_file_extension():
    # given
    given_file = File("my-file.unknown")

    with pytest.raises(Exception):
        FileAnalyzerServiceFactory(LocalFileReaderService()).create(given_file)
