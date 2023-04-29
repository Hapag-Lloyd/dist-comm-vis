from abc import ABC

from dist_comm_vis.adapter.configuration import AbstractConfiguration
from dist_comm_vis.adapter.unit_of_work import AbstractUnitOfWork
from dist_comm_vis.application.service_model import ServiceModelApplication


class TestUnitOfWork(AbstractUnitOfWork, ABC):
    """A production implementation of the UnitOfWork"""

    def __init__(self):
        pass

    def __init__(self, configuration: AbstractConfiguration):
        self.configurations = [configuration]

    def __enter__(self):
        self.create_service_model_application = ServiceModelApplication()

        return super().__enter__()

    def __exit__(self, *args):
        super().__exit__(*args)

    def _commit(self):
        pass

    def rollback(self):
        pass
