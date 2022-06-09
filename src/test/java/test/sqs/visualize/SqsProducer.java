package test.sqs.visualize;

import com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsProducer;

public class SqsProducer {
    @VisualizeSqsProducer(queueName = "locations", projectId = "myProject", projectName = "my very interesting project")
    public void produceLocationSyncEvent() {
    }
}
