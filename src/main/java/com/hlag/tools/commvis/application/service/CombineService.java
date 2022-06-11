package com.hlag.tools.commvis.application.service;

import com.hlag.tools.commvis.adapter.in.CommunicationModelFromJsonFileAdapter;
import com.hlag.tools.commvis.analyzer.model.*;
import com.hlag.tools.commvis.application.port.in.CombineCommand;
import com.hlag.tools.commvis.application.port.in.CombineUseCase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
// output format should always contain \n as line break. It shouldn't be platform dependent
@SuppressWarnings({"squid:S3457"})
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

        dotModel.append(nodeDefinitions);
        dotModel.append(graphDefinitions);
        dotModel.append("}");

        return dotModel.toString();
    }

    @RequiredArgsConstructor
    private static class ModelVisitor extends AbstractCommunicationModelVisitor {
        private final Map<String, CommunicationModel> modelsById;

        @Getter
        private final StringBuilder nodeDefinitions = new StringBuilder();
        @Getter
        private final StringBuilder graphDefinitions = new StringBuilder();

        private String modelId;

        @Override
        public void visit(CommunicationModel communicationModel) {
            modelId = communicationModel.getProjectId();
        }

        @Override
        public void visit(HttpConsumer consumer) {
            String label = String.format("%s.%s\\n%s\\n%s", consumer.getClassName(), consumer.getMethodName(), consumer.getPath(), consumer.getType());
            String id = String.format("%s#%s", modelId, consumer.getId());

            nodeDefinitions.append(String.format("  \"%s\" [label=\"%s\" shape=\"ellipse\"]\n", id, label));
            graphDefinitions.append(String.format("  \"%s\" -> \"%s\"\n", id, modelId));
        }

        @Override
        public void visit(HttpProducer producer) {
            String label = producer.getClassName() + "." + producer.getMethodName();
            String id = String.format("%s#%s", modelId, producer.getId());

            nodeDefinitions.append(String.format("  \"%s\" [label=\"%s\" shape=\"ellipse\"]\n", id, label));
            findConsumerIdFor(producer).ifPresent(consumerId -> graphDefinitions.append(String.format("  \"%s\" -> \"%s\"\n", id, consumerId)));
            graphDefinitions.append(String.format("  \"%s\" -> \"%s\"\n", modelId, id));
        }

        private Optional<String> findConsumerIdFor(IProducer producer) {
            CommunicationModel destinationModel = modelsById.get(producer.getDestinationProjectId());

            ConsumerFinderVisitor consumerFinderVisitor = new ConsumerFinderVisitor(producer);
            destinationModel.visit(consumerFinderVisitor);

            return consumerFinderVisitor.getConsumer().map(consumer -> producer.getDestinationProjectId() + "#" + consumer.getId());
        }

        @Override
        public void visit(JmsReceiver endpoint) {
            String label = String.format("%s\\n%s\\n%s", endpoint.getClassName(), endpoint.getDestination(), endpoint.getDestinationType());
            String id = String.format("%s#%s", modelId, endpoint.getId());

            nodeDefinitions.append(String.format("  \"%s\" [label=\"%s\" shape=\"diamond\"]\n", id, label));
            graphDefinitions.append(String.format("  \"%s\" -> \"%s\"\n", id, modelId));
        }

        @Override
        public void visit(SqsConsumer sqsConsumer) {
            String label = String.format("%s.%s\\n%s", sqsConsumer.getClassName(), sqsConsumer.getMethodName(), sqsConsumer.getQueueName());
            String id = String.format("%s#%s", modelId, sqsConsumer.getId());

            nodeDefinitions.append(String.format("  \"%s\" [label=\"%s\" shape=\"diamond\"]\n", id, label));
            graphDefinitions.append(String.format("  \"%s\" -> \"%s\"\n", id, modelId));
        }

        @Override
        public void visit(SqsViaSnsConsumer sqsViaSnsConsumer) {
            String label = String.format("%s.%s\\n%s", sqsViaSnsConsumer.getClassName(), sqsViaSnsConsumer.getMethodName(), sqsViaSnsConsumer.getTopicName());
            String id = String.format("%s#%s", modelId, sqsViaSnsConsumer.getId());

            nodeDefinitions.append(String.format("  \"%s\" [label=\"%s\" shape=\"diamond\"]\n", id, label));
            graphDefinitions.append(String.format("  \"%s\" -> \"%s\"\n", id, modelId));
        }

        @Override
        public void visit(SqsProducer sqsProducer) {
            String label = String.format("%s.%s\\n%s", sqsProducer.getClassName(), sqsProducer.getMethodName(), sqsProducer.getQueueName());
            String id = String.format("%s#%s", modelId, sqsProducer.getId());

            nodeDefinitions.append(String.format("  \"%s\" [label=\"%s\" shape=\"diamond\"]\n", id, label));
            findConsumerIdFor(sqsProducer).ifPresent(consumerId -> graphDefinitions.append(String.format("  \"%s\" -> \"%s\"\n", id, consumerId)));
            graphDefinitions.append(String.format("  \"%s\" -> \"%s\"\n", modelId, id));
        }

        @Override
        public void visit(SnsProducer snsProducer) {
            String label = String.format("%s.%s\\n%s", snsProducer.getClassName(), snsProducer.getMethodName(), snsProducer.getTopicName());
            String id = String.format("%s#%s", modelId, snsProducer.getId());

            nodeDefinitions.append(String.format("  \"%s\" [label=\"%s\" shape=\"diamond\"]\n", id, label));
            findConsumerIdFor(snsProducer).ifPresent(consumerId -> graphDefinitions.append(String.format("  \"%s\" -> \"%s\"\n", id, consumerId)));
            graphDefinitions.append(String.format("  \"%s\" -> \"%s\"\n", modelId, id));
        }
    }

    @RequiredArgsConstructor
    private static class ConsumerFinderVisitor extends AbstractCommunicationModelVisitor {
        private final IProducer producer;

        @Getter
        private Optional<ISenderReceiverCommunication> consumer = Optional.empty();

        @Override
        public void visit(CommunicationModel communicationModel) {
            // it's a ConsumerFinder and this is not a consumer
        }

        @Override
        public void visit(HttpConsumer httpConsumer) {
            if (producer instanceof HttpProducer) {
                HttpProducer httpProducer = (HttpProducer) this.producer;

                if (httpConsumer.isProducedBy(httpProducer)) {
                    consumer = Optional.of(httpConsumer);
                }
            }
        }

        @Override
        public void visit(HttpProducer httpProducer) {
            // it's a ConsumerFinder and this is not a consumer
        }

        @Override
        public void visit(JmsReceiver jmsReceiver) {
            // we have no producers so far
        }

        @Override
        public void visit(SqsConsumer sqsConsumer) {
            if (producer instanceof SqsProducer) {
                SqsProducer sqsProducer = (SqsProducer) this.producer;

                if (sqsConsumer.isProducedBy(sqsProducer)) {
                    consumer = Optional.of(sqsConsumer);
                }
            }
        }

        @Override
        public void visit(SqsViaSnsConsumer sqsViaSnsConsumer) {
            if (producer instanceof SnsProducer) {
                SnsProducer snsProducer = (SnsProducer) this.producer;

                if (sqsViaSnsConsumer.isProducedBy(snsProducer)) {
                    consumer = Optional.of(sqsViaSnsConsumer);
                }
            }
        }

        @Override
        public void visit(SqsProducer sqsProducer) {
            // it's a ConsumerFinder and this is not a consumer
        }

        @Override
        public void visit(SnsProducer snsProducer) {
            // it's a ConsumerFinder and this is not a consumer
        }
    }
}
