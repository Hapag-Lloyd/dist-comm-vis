from abc import ABC
from typing import List


class ModelRelation(ABC):
    def __init__(self):
        pass


class Model:
    def __init__(self, relations: List[ModelRelation]):
        self.relations = relations


class ModelRelationFactory:
    def __init__(self):
        pass

    @staticmethod
    def create(relation_type: str, direction: str, target_project_id: str) -> ModelRelation:
        if relation_type == "rest" and direction == "in":
            return RestApiEndpoint()
        elif relation_type == "rest" and direction == "out":
            return RestApiCall(target_project_id)
        else:
            raise Exception("No relation found for relation type: " + relation_type)


class RestApiEndpoint(ModelRelation):
    def __init__(self):
        super().__init__()

        self.type = "rest"
        self.direction = "out"

    def __str__(self):
        return "RestApiEndpoint"


class RestApiCall(ModelRelation):
    def __init__(self, target_project_id: str):
        super().__init__()

        self.type = "rest"
        self.direction = "out"
        self.target_project_id = target_project_id

    def __str__(self):
        return "RestApiCall to " + self.target_project_id
