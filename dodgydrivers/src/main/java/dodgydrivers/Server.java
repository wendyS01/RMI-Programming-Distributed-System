package dodgydrivers;

import core.BrokerService;
import core.Constants;
import core.QuotationService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
/**
 * Server class for running dodgydrivers server
 */
public class Server {
        public static void main(String[] args) {
                QuotationService ddqService = new DDQService();

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
                                        UnicastRemoteObject.exportObject(ddqService,0);
//                         Register the object with the RMI Registry
//                        registry.bind(Constants.DODGY_DRIVERS_SERVICE, quotationService);

                        //to find the broker service which is already bound
                        // with the registry
                        BrokerService brokerService = (BrokerService) registry.lookup(Constants.BROKER_SERVICE);
                        //use the registry created by Broker server to bind
                        // dodgydrivers service
                        brokerService.registerService(Constants.DODGY_DRIVERS_SERVICE,quotationService);
                        System.out.println("STOPPING SERVER SHUTDOWN");
                        while (true) {Thread.sleep(1000); }
                } catch (Exception e) {
                        System.out.println("Trouble: " + e);
                }
        }
}
