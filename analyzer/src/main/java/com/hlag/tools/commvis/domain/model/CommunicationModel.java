package com.hlag.tools.commvis.domain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;

/**
 * Root level object holding the complete model of incoming and outgoing communication points.
 */
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CommunicationModel {
    @Getter
    private String projectId;
    @Getter
    private String projectName;
    private Collection<IEndpoint> endpoints;

    public void visit(AbstractCommunicationModelVisitor visitor) {
        visitor.visit(this);

        endpoints.forEach(e -> e.visit(visitor));
    }
}
