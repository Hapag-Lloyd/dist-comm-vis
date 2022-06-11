package test.sns.visualize;

import com.hlag.tools.commvis.analyzer.annotation.VisualizeSnsProducer;

public class SnsProducer {
    @VisualizeSnsProducer(topicName = "locations", projectId = "myProject", projectName = "my very interesting project")
    public void produceLocationSyncEvent() {
    }
}
