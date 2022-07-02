package integration.endpoint;

import com.hlag.tools.commvis.analyzer.annotation.VisualizeKafkaProducer;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeSnsProducer;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsProducer;

public class CustomerService {
    @VisualizeSqsProducer(queueName = "revoke-credit-line", projectId = "2", projectName = "Customer Service")
    public void revokeCreditLineFromCustomer() {

    }

    @VisualizeKafkaProducer(topicName = "vip-customer-created", projectId = "2", projectName = "Customer Service")
    public void addVipCustomer() {

    }

    @VisualizeSnsProducer(topicName = "stop-customer-business", projectId = "2", projectName = "Customer Service")
    public void stopCustomerBusiness() {

    }
}
