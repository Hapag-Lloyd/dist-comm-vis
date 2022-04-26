package com.hlag.tools.commvis.service;

import com.hlag.tools.commvis.domain.model.IEndpoint;

import java.util.Collection;

public interface IExportModelService {
    void export(Collection<IEndpoint> endpoints, String filename);
}
