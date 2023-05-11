from abc import ABC, abstractmethod
from typing import List

from dist_comm_vis.domain.model.ModelAttributes import Direction


class ModelRelation(ABC):
    def __init__(self, endpoint_id: str):
        self.endpoint_id = endpoint_id

    @abstractmethod
    def get_direction(self) -> Direction:
        raise NotImplementedError


class OutgoingRelation(ModelRelation, ABC):
    def __init__(self, endpoint_id: str, target_model_id: str, target_endpoint_id: str):
        super().__init__(endpoint_id)

        self.target_model_id = target_model_id
        self.target_endpoint_id = target_endpoint_id

    def get_direction(self) -> Direction:
        return Direction.OUT


class IncomingRelation(ModelRelation, ABC):
    def __init__(self, endpoint_id: str):
        super().__init__(endpoint_id)

    def get_direction(self) -> Direction:
        return Direction.IN


class Model:
    def __init__(self, identifier: str, name: str, incoming_relations: List[IncomingRelation],
                 outgoing_relations: List[OutgoingRelation]):
        self.identifier = identifier
        self.name = name
        self.incoming_relations = incoming_relations
        self.outgoing_relations = outgoing_relations

    @staticmethod
    def from_model(model: 'Model'):
        return Model(model.identifier, model.name, model.incoming_relations, model.outgoing_relations)

    def add_relation(self, relation: ModelRelation) -> None:
        if relation.get_direction() == Direction.IN:
            self.incoming_relations.append(IncomingRelation(relation))
        elif relation.get_direction() == Direction.OUT:
            self.outgoing_relations.append(OutgoingRelation(relation))
        else:
            raise Exception("Unknown direction: " + relation.get_direction().value)

    def get_relation(self, target_endpoint_id: str) -> IncomingRelation:
        for relation in self.incoming_relations:
            if relation.endpoint_id == target_endpoint_id:
                return relation

        raise Exception("No relation found for target endpoint id: " + target_endpoint_id)


class ModelRelationFactory:
    def __init__(self):
        pass

    @staticmethod
    def create(relation_type: str, direction: str, endpoint_id: str, target_model_id: str = None,
               target_endpoint_id: str = None) -> ModelRelation:
        if relation_type == "rest" and direction == "in":
            return RestApiEndpoint(endpoint_id)
        elif relation_type == "rest" and direction == "out":
            return RestApiCall(endpoint_id, target_model_id, target_endpoint_id)
        else:
            raise Exception("No relation found for relation type: " + relation_type)


class RestApiEndpoint(IncomingRelation):
    def __init__(self, endpoint_id: str):
        super().__init__(endpoint_id)

        self.type = "rest"

    def __str__(self):
        return "RestApiEndpoint"


class RestApiCall(OutgoingRelation):
    def __init__(self, endpoint_id: str, target_model_id: str, target_endpoint_id: str):
        super().__init__(endpoint_id, target_model_id, target_endpoint_id)

        self.type = "rest"

    def __str__(self):
        return f"RestApiCall to project {self.target_model_id} endpoint {self.target_endpoint_id}"
