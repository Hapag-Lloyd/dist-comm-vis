from typing import List

from dist_comm_vis.domain.model.Model import Model
from dist_comm_vis.domain.service.ModelWriterService import ModelWriterService


class DotModelWriterService(ModelWriterService):
    """Writes a list of connected models to a dot file."""

    def __init__(self):
        super().__init__()

    def write(self, models: List[Model]) -> str:
        result = "digraph G {\n"

        for model in models:
            result += f"    \"{model.identifier}\"[label=\"{model.name}\", shape=\"rectangle\"]\n"

            for relation in model.incoming_relations:
                result += f"    \"{model.identifier}-{relation.endpoint_id}\"[label=\"{relation.endpoint_id}\"," \
                          f" shape=\"ellipse\"]\n"
                result += f"    {model.identifier}-{relation.endpoint_id} -> {model.identifier}\n"

            for relation in model.outgoing_relations:
                result += f"    {model.identifier} -> {relation.target_model_id}-{relation.target_endpoint_id}\n"

        result += "}"

        return result
