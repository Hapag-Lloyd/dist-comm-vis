package integration.endpoint;

import com.hlag.tools.commvis.analyzer.annotation.*;

public class MasterDataEndpoint {
    @VisualizeKafkaConsumer(topicName = "location-invalidated", projectName = "Master Data Catalog")
    public void locationInvalidated() {

    }

    @VisualizeSqsViaSnsConsumer(topicName = "new-country", projectName = "Master Data Catalog")
    public void countryAdded() {

    }

    @VisualizeSqsConsumer(queueName = "revoke-credit-line", projectName = "Master Data Catalog")
    public void revokeCreditLine() {

    }
}
