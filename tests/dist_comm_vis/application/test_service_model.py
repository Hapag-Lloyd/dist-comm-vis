import pytest

from dist_comm_vis.application.service_model import ServiceModelApplication


@pytest.fixture
def application():
    return ServiceModelApplication()


def test_dummy(application):
    # when
    application.create_for_project()
