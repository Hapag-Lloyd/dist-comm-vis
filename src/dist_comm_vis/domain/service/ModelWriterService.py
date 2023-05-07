from abc import ABC, abstractmethod

from dist_comm_vis.domain.model.Model import Model


class ModelWriterService(ABC):
    def __init__(self):
        pass

    @abstractmethod
    def write(self, model: Model) -> str:
        raise NotImplementedError
