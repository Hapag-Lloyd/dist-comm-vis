package com.hlag.tools.commvis.service;

import com.hlag.tools.commvis.domain.model.IEndpoint;

import java.util.Set;

public interface IEndpointScannerService {
    Set<IEndpoint> scanClasspath(String rootPackageName);
}
