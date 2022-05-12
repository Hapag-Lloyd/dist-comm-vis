package com.hlag.tools.commvis.application.service;

import com.hlag.tools.commvis.application.port.in.CombineCommand;
import com.hlag.tools.commvis.application.port.in.CombineUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CombineService implements CombineUseCase {
    @Override
    public void combineModels(CombineCommand command) {
        // find all model files

        // build multiple internal models

        // export all models to one DOT file
        // nodes are named: ${model_id}#${id}
    }
}
