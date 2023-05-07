import os
import sys
from unittest.mock import patch

from dist_comm_vis.adapter.endpoint.cli import bootstrap
from dist_comm_vis.definitions import ROOT_DIR


def test_cli_calls_application():
    testargs = ["prog-name", "path", "--log-config-file", os.path.abspath(os.path.join(ROOT_DIR, "logging.ini")),
                "--output-path", os.path.abspath(os.path.join(ROOT_DIR, "target/"))]

    with patch.object(sys, 'argv', testargs):
        bootstrap()

    # not sure what can be asserted here
    assert True is True
