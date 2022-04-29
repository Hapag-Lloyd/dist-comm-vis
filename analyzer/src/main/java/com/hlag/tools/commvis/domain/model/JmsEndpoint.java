package com.hlag.tools.commvis.domain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class JmsEndpoint implements IEndpoint {
    private String className;
    // e.g. "javax.jms.Queue"
    private String destinationType;
    // e.g. "jms/catalogs/customer"
    private String destination;

    @Override
    public void visit(AbstractCommunicationModelVisitor visitor) {
        visitor.visit(this);
    }
}
