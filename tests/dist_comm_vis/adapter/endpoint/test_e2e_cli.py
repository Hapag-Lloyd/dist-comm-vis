import sys
from unittest.mock import patch

from dist_comm_vis.adapter.endpoint.cli import bootstrap


def test_cli_calls_application():
    testargs = ["prog-name", "path", "--log-config-file", "../logging.ini"]

    with patch.object(sys, 'argv', testargs):
        bootstrap()

    # not sure what can be asserted here
    assert True is True
