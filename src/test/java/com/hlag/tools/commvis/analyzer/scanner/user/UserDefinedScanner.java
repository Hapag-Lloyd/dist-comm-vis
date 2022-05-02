package com.hlag.tools.commvis.analyzer.scanner.user;

import com.hlag.tools.commvis.analyzer.model.ISenderReceiverCommunication;
import com.hlag.tools.commvis.analyzer.service.IScannerService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDefinedScanner implements IScannerService {
    @Override
    public Collection<ISenderReceiverCommunication> scanSenderAndReceiver(String s) {
        return Collections.emptySet();
    }
}
