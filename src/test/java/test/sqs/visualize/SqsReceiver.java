package test.sqs.visualize;

import com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsConsumer;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsConsumers;

public class SqsReceiver {
    @VisualizeSqsConsumer(queueName = "locations", projectName = "master data")
    public void receiveLocationSyncEvent() {

    }

    @VisualizeSqsConsumers({@VisualizeSqsConsumer(queueName = "customer_data", projectName = "master data"), @VisualizeSqsConsumer(queueName = "account_data", projectName = "master data")})
    public void receiveFromManyQueues() {

    }
}
