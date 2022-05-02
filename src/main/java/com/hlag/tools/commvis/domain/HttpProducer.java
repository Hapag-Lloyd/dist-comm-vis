package com.hlag.tools.commvis.domain;

import com.hlag.tools.commvis.analyzer.model.AbstractCommunicationModelVisitor;
import com.hlag.tools.commvis.analyzer.model.ISenderReceiverCommunication;
import lombok.Value;

@Value
public class HttpProducer implements ISenderReceiverCommunication {
    private final String method;
    private final String path;
    private final String projectId;

    @Override
    public void visit(AbstractCommunicationModelVisitor abstractCommunicationModelVisitor) {

    }
}
