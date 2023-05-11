import json

from dist_comm_vis.domain.model.Model import Model
from dist_comm_vis.domain.service.ModelReaderService import ModelReaderService


class JsonModelReaderService(ModelReaderService):
    def __init__(self):
        super().__init__()

    def read(self, model_representation: str) -> Model:
        json.loads(model_representation, object_hook=lambda d: Model(**d))
