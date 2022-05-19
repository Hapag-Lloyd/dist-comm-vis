package com.hlag.tools.commvis.application.port.out;

import com.hlag.tools.commvis.analyzer.model.*;
import org.springframework.stereotype.Component;

/**
 * Transforms the internal model into a DOT representation for GraphViz.
 */
@Component
public class DotCommunicationModelVisitor extends AbstractCommunicationModelVisitor {
    private StringBuilder nodeDefinitions = new StringBuilder();
    private StringBuilder graphDefinitions = new StringBuilder();

    private int nodes = 0;

    public String getDotContent() {
        return "digraph G {\n" + nodeDefinitions.toString() + "\n" + graphDefinitions.toString() + "}";
    }

    @Override
    public void visit(CommunicationModel model) {
        nodeDefinitions.append(String.format("  \"application\" [label=\"%s\" shape=\"rectangle\"]\n", model.getProjectName()));
    }

    @Override
    public void visit(HttpConsumer consumer) {
        String label = consumer.getClassName() + "." + consumer.getMethodName() + "\\n" + consumer.getPath() + "\\n" + consumer.getType();
        nodeDefinitions.append(String.format("  \"%d\" [label=\"%s\" shape=\"ellipse\"]\n", nodes, label));

        graphDefinitions.append(String.format("  \"%d\" -> \"application\"\n", nodes));

        ++nodes;
    }

    @Override
    public void visit(HttpProducer producer) {
        String label = producer.getClassName() + "." + producer.getMethodName() + "\\n" + producer.getPath() + "\\n" + producer.getType();
        nodeDefinitions.append(String.format("  \"%d\" [label=\"%s\" shape=\"ellipse\"]\n", nodes, label));

        graphDefinitions.append(String.format("  \"application\" -> \"%d\"\n", nodes));

        ++nodes;
    }

    @Override
    public void visit(JmsReceiver endpoint) {
        String label = endpoint.getClassName() + "\\n" + endpoint.getDestination() + "\\n" + endpoint.getDestinationType();
        nodeDefinitions.append(String.format("  \"%d\" [label=\"%s\" shape=\"diamond\"]\n", nodes, label));

        graphDefinitions.append(String.format("  \"%d\" -> \"application\"\n", nodes));

        ++nodes;
    }

    @Override
    public void visit(SqsConsumer sqsConsumer) {
        String label = sqsConsumer.getClassName() + "\\n" + sqsConsumer.getMethodName() + "\\n" + sqsConsumer.getQueueName();
        nodeDefinitions.append(String.format("  \"%d\" [label=\"%s\" shape=\"diamond\"]\n", nodes, label));

        graphDefinitions.append(String.format("  \"%d\" -> \"application\"\n", nodes));

        ++nodes;
    }
}
