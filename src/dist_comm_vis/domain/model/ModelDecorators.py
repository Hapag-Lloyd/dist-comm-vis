from dist_comm_vis.domain.model.Model import ModelRelation, Model, OutgoingRelation, IncomingRelation


class OutgoingRelationDecorator(OutgoingRelation):
    """Wraps a model relation and adds a reference to the destination model. It's an outgoing relation to somewhere
       else."""
    def __init__(self, relation: OutgoingRelation, target_model_relation: IncomingRelation):
        super().__init__(relation.endpoint_id, relation.target_model_id, relation.target_endpoint_id)

        self.relation = relation
        self.target_relation = target_model_relation
