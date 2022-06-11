package test.sqs.visualize;

import com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsProducer;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsProducers;

public class SqsProducer {
    @VisualizeSqsProducer(queueName = "locations", projectId = "myProject", projectName = "my very interesting project")
    public void produceLocationSyncEvent() {
    }

    @VisualizeSqsProducers(@VisualizeSqsProducer(queueName = "location", projectId = "myProject", projectName = "my very interesting project"))
    public void produceLocationSyncEvents() {
    }
}
