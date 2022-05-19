package com.hlag.tools.commvis.application.service;

import com.hlag.tools.commvis.adapter.in.CommunicationModelFromJsonFileAdapter;
import com.hlag.tools.commvis.application.port.in.CombineCommand;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

class CombineServiceTest {
    @Test
    void shouldCombineMultipleModelsReferencingEachOther_whenCombineModelAsDot() {
        CombineService combineService = new CombineService(new CommunicationModelFromJsonFileAdapter());
        CombineCommand command = new CombineCommand("src/test/resources/combine-models", "test");
        String actualDotModel = combineService.combineModelsAsDot(command);

        Assertions.assertThat(actualDotModel).isEqualTo(Assertions.contentOf(new File("src/test/resources/combine-models/combined-model.dot")));
    }
}