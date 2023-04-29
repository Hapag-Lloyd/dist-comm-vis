from __future__ import annotations
import abc
import logging
from pythonjsonlogger import jsonlogger

from dist_comm_vis.adapter.configuration import AbstractConfiguration
from dist_comm_vis.application.service_model import ServiceModelApplication


class AbstractUnitOfWork(abc.ABC):
    """Creates an abstraction layer. Dependencies are managed exclusively here"""

    service_model_application: ServiceModelApplication
    configurations: list[AbstractConfiguration] = []

    def __enter__(self) -> AbstractUnitOfWork:
        logging.basicConfig(
            level=getattr(logging, self.get_configuration_value(AbstractConfiguration.LOG_LEVEL).upper())
        )

        return self

    def get_logger(self, name: str) -> logging.Logger:
        log_format = getattr(jsonlogger, self.get_configuration_value(AbstractConfiguration.LOG_FORMAT.upper()))
        logger = logging.getLogger(name)

        if log_format == "JSON":
            log_handler = logging.StreamHandler()
            formatter = jsonlogger.JsonFormatter()
            log_handler.setFormatter(formatter)
            logger.addHandler(log_handler)

        return logger

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

    def add_configuration(self, configuration: AbstractConfiguration):
        self.configurations.append(configuration)

    def get_configuration_value(self, name: str) -> str:
        if name not in [AbstractConfiguration.LOG_LEVEL, AbstractConfiguration.LOG_FORMAT]:
            raise ValueError(f"Unknown configuration key '{name}'")

        for configuration in self.configurations:
            value = configuration.get_configuration_value(name)
            if value:
                return value

        return ""


class DefaultUnitOfWork(AbstractUnitOfWork):
    """A production implementation of the UnitOfWork"""

    def __init__(self, configuration: AbstractConfiguration):
        self.configurations = [configuration]

    def __enter__(self):
        self.service_model_application = ServiceModelApplication()

        return super().__enter__()

    def __exit__(self, *args):
        super().__exit__(*args)

    def _commit(self):
        pass

    def rollback(self):
        pass
