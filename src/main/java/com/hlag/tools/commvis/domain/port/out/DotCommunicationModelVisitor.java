package com.hlag.tools.commvis.domain.port.out;

import com.hlag.tools.commvis.domain.model.AbstractCommunicationModelVisitor;
import com.hlag.tools.commvis.domain.model.CommunicationModel;
import com.hlag.tools.commvis.domain.model.HttpEndpoint;
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
    public void visit(HttpEndpoint httpEndpoint) {
        String label = httpEndpoint.getClassName() + "." + httpEndpoint.getMethodName() + "\\n" + httpEndpoint.getType();
        nodeDefinitions.append(String.format("  \"%d\" [label=\"%s\"]\n", nodes, label));

        graphDefinitions.append(String.format("  \"%d\" -> \"application\"\n", nodes));

        ++nodes;
    }
}
