package integration.endpoint;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

@MessageDriven(name = "CustomerCancelledOrderReceiver", activationConfig = {@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"), @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/customer/orderCancelled"), @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class CustomerCancelledOrderReceiver {
}
