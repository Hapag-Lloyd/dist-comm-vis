package com.hlag.tools.commvis.domain.model;

/**
 * The visitor pattern to transform the internal model.
 */
public abstract class AbstractCommunicationModelVisitor {
    public abstract void visit(HttpEndpoint endpoint);
    public abstract void visit(JmsEndpoint endpoint);
}
