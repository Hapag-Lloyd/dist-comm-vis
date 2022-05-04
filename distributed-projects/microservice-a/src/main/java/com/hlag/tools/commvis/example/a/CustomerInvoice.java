package com.hlag.tools.commvis.example.a;

import com.hlag.tools.commvis.analyzer.annotation.VisualizeHttpsCall;

public class CustomerInvoice {
    @VisualizeHttpsCall(type="GET", path="invoices/{invoiceId}", projectId = "12", projectName = "Invoices")
    public void getInvoice() {

    }
}
