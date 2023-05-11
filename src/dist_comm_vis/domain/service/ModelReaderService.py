from abc import ABC, abstractmethod

from dist_comm_vis.domain.model.Model import Model


class ModelReaderService(ABC):
    def __init__(self):
        pass

    @abstractmethod
    def read(self, model_representation: str) -> Model:
        raise NotImplementedError
