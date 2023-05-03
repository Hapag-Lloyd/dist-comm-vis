from dist_comm_vis.domain.model.attribute.Direction import Direction
from dist_comm_vis.domain.model.attribute.Type import Type


class ModelRelation:
    source_project_id: int
    target_project_id: int
    direction: Direction
    type: Type

    def __init__(self):
        pass
