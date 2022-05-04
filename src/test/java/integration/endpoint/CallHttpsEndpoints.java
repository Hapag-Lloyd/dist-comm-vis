package integration.endpoint;

import com.hlag.tools.commvis.analyzer.annotation.VisualizeHttpsCall;

public class CallHttpsEndpoints {
    @VisualizeHttpsCall(path = "customers/{customerId}/invoices", type = "GET", projectId = "4711", projectName = "accounting service")
    public void getCustomerInvoices() {

    }
}
