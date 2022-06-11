package test.sqs.visualize;

import com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsViaSnsConsumer;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsViaSnsConsumers;

public class SqsViaSnsReceiver {
    @VisualizeSqsViaSnsConsumer(topicName = "locations", projectName = "master data")
    public void receiveLocationSyncEvent() {

    }

    @VisualizeSqsViaSnsConsumers({@VisualizeSqsViaSnsConsumer(topicName = "customer_data", projectName = "master data"), @VisualizeSqsViaSnsConsumer(topicName = "account_data", projectName = "master data")})
    public void receiveFromManyQueues() {

    }
}
