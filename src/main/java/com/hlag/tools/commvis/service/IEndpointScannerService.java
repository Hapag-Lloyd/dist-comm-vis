package com.hlag.tools.commvis.service;

import com.hlag.tools.commvis.domain.model.IEndpoint;

import java.util.Set;

/**
 * Scans the classpath for endpoints which allow incoming communication.
 */
public interface IEndpointScannerService {
    Set<IEndpoint> scanClasspath(String rootPackageName);
}
