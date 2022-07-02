package test.kafka.visualize;

import com.hlag.tools.commvis.analyzer.annotation.VisualizeKafkaProducer;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeKafkaProducers;

public class KafkaProducer {
    @VisualizeKafkaProducer(topicName = "locations", projectId = "myProject", projectName = "my very interesting project")
    public void produceLocationSyncEvent() {
    }

    @VisualizeKafkaProducers({@VisualizeKafkaProducer(topicName = "locations", projectId = "myProject", projectName = "my very interesting project"), @VisualizeKafkaProducer(topicName = "locations2", projectId = "myProject", projectName = "my very interesting project")})
    public void produceSomeEvents() {

    }
}
