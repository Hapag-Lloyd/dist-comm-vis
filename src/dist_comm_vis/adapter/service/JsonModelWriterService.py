import json
from json import JSONEncoder

from dist_comm_vis.domain.model.Model import Model
from dist_comm_vis.domain.service.ModelWriterService import ModelWriterService


class JsonModelWriterService(ModelWriterService):
    def __init__(self):
        super().__init__()

    def write(self, model: Model) -> str:
        return json.dumps(model.__dict__, indent=4, cls=JsonModelEncoder)


class JsonModelEncoder(JSONEncoder):
    def default(self, o):
        return o.__dict__
