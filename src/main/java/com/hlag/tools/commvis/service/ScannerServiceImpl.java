package com.hlag.tools.commvis.service;

import com.hlag.tools.commvis.service.IScannerService;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

@Service("ScannerService")
public class ScannerServiceImpl implements IScannerService {
    @Override
    public void scanClasspath(String rootPackageName) {
        new Reflections(rootPackageName);
    }
}
