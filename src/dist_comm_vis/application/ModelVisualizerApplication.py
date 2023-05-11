from typing import List, Dict

from dist_comm_vis.domain.model.File import File
from dist_comm_vis.domain.model.Model import Model
from dist_comm_vis.domain.model.ModelDecorators import OutgoingRelationDecorator
from dist_comm_vis.domain.service.FileReaderService import FileReaderService
from dist_comm_vis.domain.service.ModelReaderService import ModelReaderService
from dist_comm_vis.domain.service.ModelWriterService import ModelWriterService


class ModelVisualizerApplication:
    def __init__(self, file_reader_service: FileReaderService, model_reader_service: ModelReaderService,
                 model_writer_service: ModelWriterService):
        self.file_reader_service = file_reader_service
        self.model_reader_service = model_reader_service
        self.model_writer_service = model_writer_service

    def visualize_models(self, model_json_files: List[str]) -> None:
        models: Dict[str, Model] = {}

        # read all models
        for model_json_file in model_json_files:
            model = self.model_reader_service.read(self.file_reader_service.read_lines(File(model_json_file)))
            models[model.identifier] = model

        # maps from model id to model
        linked_models: Dict[str, Model] = {}

        for model in models.values():
            linked_models[model.identifier] = Model.from_model(model)

            for relation in model.incoming_relations:
                linked_models[model.identifier].add_relation(relation)

            # find the incoming relation this outgoing relation is pointing to
            for relation in model.outgoing_relations:
                target_model_endpoint = models[relation.target_model_id].get_relation(relation.target_endpoint_id)

                linked_models[model.identifier].add_relation(OutgoingRelationDecorator(relation,
                                                                                       target_model_endpoint))

        self.model_writer_service.write(list(linked_models.values()))
