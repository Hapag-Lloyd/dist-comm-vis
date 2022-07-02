package test.kafka.visualize;

import com.hlag.tools.commvis.analyzer.annotation.VisualizeKafkaConsumer;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeKafkaConsumers;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsViaSnsConsumer;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsViaSnsConsumers;

public class KafkaConsumer {
    @VisualizeKafkaConsumer(topicName = "locations", projectName = "master data")
    public void receiveLocationSyncEvent() {

    }

    @VisualizeKafkaConsumers({@VisualizeKafkaConsumer(topicName = "customer_data", projectName = "master data"), @VisualizeKafkaConsumer(topicName = "account_data", projectName = "master data")})
    public void receiveFromManyTopics() {

    }
}
