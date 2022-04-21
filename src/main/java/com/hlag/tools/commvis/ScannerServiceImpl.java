package com.hlag.tools.commvis;

import org.reflections.Reflections;
import org.springframework.stereotype.Service;

@Service("ScannerService")
public class ScannerServiceImpl implements IScannerService {
    @Override
    public void scanClasspath(String rootPackageName) {
        new Reflections(rootPackageName);
    }
}
