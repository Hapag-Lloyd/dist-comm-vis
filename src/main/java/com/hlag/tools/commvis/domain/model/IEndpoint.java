package com.hlag.tools.commvis.domain.model;

public interface IEndpoint {
    String getClassName();

    void visit(AbstractCommunicationModelVisitor visitor);
}
