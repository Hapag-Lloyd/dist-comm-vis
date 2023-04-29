from __future__ import annotations
import abc

from dist_comm_vis.application.service_model import ServiceModelApplication


class AbstractUnitOfWork(abc.ABC):
    """Abstract class to ease testing"""
    service_model_application: ServiceModelApplication

    def __enter__(self) -> AbstractUnitOfWork:
        return self

    def __exit__(self, *args):
        self.rollback()

    def commit(self):
        """Commits the current work"""
        self._commit()

    @abc.abstractmethod
    def _commit(self):
        raise NotImplementedError

    @abc.abstractmethod
    def rollback(self):
        """Discards the current work"""
        raise NotImplementedError


class DefaultUnitOfWork(AbstractUnitOfWork):
    """A production implementation of the UnitOfWork"""
    def __init__(self):
        pass

    def __enter__(self):
        self.service_model_application = ServiceModelApplication()

        return super().__enter__()

    def __exit__(self, *args):
        super().__exit__(*args)

    def _commit(self):
        pass

    def rollback(self):
        pass
