package com.hlag.tools.commvis.application.service;

import com.hlag.tools.commvis.adapter.in.CommunicationModelFromJsonFileAdapter;
import com.hlag.tools.commvis.analyzer.model.*;
import com.hlag.tools.commvis.application.port.in.CombineCommand;
import com.hlag.tools.commvis.application.port.in.CombineUseCase;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CombineService implements CombineUseCase {
    private final CommunicationModelFromJsonFileAdapter modelReader;

    @Override
    public String combineModelsAsDot(CombineCommand command) {
        Map<String, CommunicationModel> models = new HashMap<>();

        // find all model files and read them
        modelReader.getModelFiles(command.getBaseDirectory()).forEach(p -> {
             CommunicationModel model = modelReader.getModelFromFile(p);
            models.put(model.getProjectId(), model);
        });

        // export all models to one DOT file
        StringBuilder dotModel = new StringBuilder();
        StringBuilder nodeDefinitions = new StringBuilder();
        StringBuilder graphDefinitions = new StringBuilder();

        dotModel.append("digraph G {\n");
        models.forEach((id, m) -> dotModel.append(String.format("  \"%s\" [label=\"%s\" shape=\"rectangle\"]\n", m.getProjectId(), m.getProjectName())));
        dotModel.append("\n");

        models.forEach((id, m) -> {
            ModelVisitor visitor = new ModelVisitor(models);
            m.visit(visitor);

            nodeDefinitions.append(visitor.getNodeDefinitions().toString());
            graphDefinitions.append(visitor.getGraphDefinitions().toString());
        });

        dotModel.append(nodeDefinitions.toString());
        dotModel.append(graphDefinitions.toString());
        dotModel.append("}");

        return dotModel.toString();
    }

    @RequiredArgsConstructor
    private static class ModelVisitor extends AbstractCommunicationModelVisitor {
        private final Map<String, CommunicationModel> modelsById;

        @Getter
        private StringBuilder nodeDefinitions = new StringBuilder();
        @Getter
        private StringBuilder graphDefinitions = new StringBuilder();

        private String modelId;

        @Override
        public void visit(CommunicationModel communicationModel) {
            modelId = communicationModel.getProjectId();
        }

        @Override
        public void visit(HttpConsumer consumer) {
            String label = consumer.getClassName() + "." + consumer.getMethodName() + "\\n" + consumer.getPath() + "\\n" + consumer.getType();
            String id = String.format("%s#%s", modelId, consumer.getId());

            nodeDefinitions.append(String.format("  \"%s\" [label=\"%s\" shape=\"ellipse\"]\n", id, label));
            graphDefinitions.append(String.format("  \"%s\" -> \"%s\"\n", id, modelId));
        }

        @Override
        public void visit(HttpProducer producer) {
            String label = producer.getClassName() + "." + producer.getMethodName();
            String id = String.format("%s#%s", modelId, producer.getId());

            nodeDefinitions.append(String.format("  \"%s\" [label=\"%s\" shape=\"ellipse\"]\n", id, label));
            graphDefinitions.append(String.format("  \"%s\" -> \"%s\"\n", id, findConsumerIdFor(producer)));
            graphDefinitions.append(String.format("  \"%s\" -> \"%s\"\n", modelId, id));
        }

        private String findConsumerIdFor(HttpProducer producer) {
            CommunicationModel destinationModel = modelsById.get(producer.getDestinationProjectId());

            ConsumerFinderVisitor consumerFinderVisitor = new ConsumerFinderVisitor(producer);
            destinationModel.visit(consumerFinderVisitor);

            return producer.getDestinationProjectId() + "#" + consumerFinderVisitor.getConsumer().getId();
        }

        @Override
        public void visit(JmsReceiver endpoint) {
            String label = endpoint.getClassName() + "\\n" + endpoint.getDestination() + "\\n" + endpoint.getDestinationType();
            String id = String.format("%s#%s", modelId, endpoint.getId());

            nodeDefinitions.append(String.format("  \"%s\" [label=\"%s\" shape=\"diamond\"]\n", id, label));
            graphDefinitions.append(String.format("  \"%s\" -> \"%s\"\n", id, modelId));
        }

        @Override
        public void visit(SqsConsumer sqsConsumer) {}
    }

    @RequiredArgsConstructor
    private static class ConsumerFinderVisitor extends AbstractCommunicationModelVisitor {
        private final ISenderReceiverCommunication producer;
        @Getter
        private ISenderReceiverCommunication consumer;

        @Override
        public void visit(CommunicationModel communicationModel) {

        }

        @Override
        public void visit(HttpConsumer httpConsumer) {
            if (producer instanceof HttpProducer) {
                HttpProducer producer = (HttpProducer) this.producer;

                if (httpConsumer.isProducedBy(producer)) {
                    consumer = httpConsumer;
                }
            }
        }

        @Override
        public void visit(HttpProducer httpProducer) {

        }

        @Override
        public void visit(JmsReceiver jmsReceiver) {

        }

        @Override
        public void visit(SqsConsumer sqsConsumer) {}
    }
}
