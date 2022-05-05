package com.hlag.tools.commvis.example.order;

import com.hlag.tools.commvis.analyzer.annotation.VisualizeHttpsCall;

public class CustomerDetail {
    @VisualizeHttpsCall(type="GET", path="customers/{customerId}", projectId = "1", projectName = "Customer")
    public void getCustomerData() {

    }
}
