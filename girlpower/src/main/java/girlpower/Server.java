package girlpower;

import core.BrokerService;
import core.Constants;
import core.QuotationService;

import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
/**
 * Server class for running girlpower server
 */
public class Server {
        public static void main(String[] args) {
                QuotationService gpqService = new GPQService();

                try {
                        // Connect to the RMI Registry - creating the registry will be the
                        // responsibility of the broker.
                        Registry registry = null;
                        if (args.length == 0) {
                                registry = LocateRegistry.createRegistry(1099);
                        } else {
                                String host = args[0];
                                registry = LocateRegistry.getRegistry(host, 1099);
                        }
                        // Create the Remote Object
                        QuotationService quotationService = (QuotationService)
                                        UnicastRemoteObject.exportObject(gpqService,0);
                        // Register the object with the RMI Registry
                        //registry.bind(Constants.GIRL_POWER_SERVICE, quotationService);

                        //to find the broker service which is already bound
                        // with the registry
                        BrokerService brokerService = (BrokerService) registry.lookup(Constants.BROKER_SERVICE);
                        //use the registry created by Broker server to bind
                        // girlpower service
                        brokerService.registerService(Constants.GIRL_POWER_SERVICE, quotationService);
                        System.out.println("STOPPING SERVER SHUTDOWN");
                        while (true) {Thread.sleep(1000); }
                } catch (Exception e) {
                        System.out.println("Trouble: " + e);
                }
        }
}
