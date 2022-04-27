package com.hlag.tools.commvis.domain.model;

public interface IEndpoint {
    String getClassName();
    String getMethodName();
    String getType();

    void visit(AbstractCommunicationModelVisitor visitor);
}
