package com.hlag.tools.commvis.domain.port.out;

import com.hlag.tools.commvis.domain.model.AbstractCommunicationModelVisitor;
import com.hlag.tools.commvis.domain.model.CommunicationModel;
import com.hlag.tools.commvis.domain.model.HttpEndpoint;
import com.hlag.tools.commvis.domain.model.JmsEndpoint;
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
    public void visit(HttpEndpoint endpoint) {
        String label = endpoint.getClassName() + "." + endpoint.getMethodName() + "\\n" + endpoint.getPath() + "\\n" + endpoint.getType();
        nodeDefinitions.append(String.format("  \"%d\" [label=\"%s\" shape=\"ellipse\"]\n", nodes, label));

        graphDefinitions.append(String.format("  \"%d\" -> \"application\"\n", nodes));

        ++nodes;
    }

    @Override
    public void visit(JmsEndpoint endpoint) {
        String label = endpoint.getClassName() + "\\n" + endpoint.getDestination() + "\\n" + endpoint.getDestinationType();
        nodeDefinitions.append(String.format("  \"%d\" [label=\"%s\" shape=\"diamond\"]\n", nodes, label));

        graphDefinitions.append(String.format("  \"%d\" -> \"application\"\n", nodes));

        ++nodes;
    }
}
