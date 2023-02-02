import auldfellas.AFQService;
import broker.LocalBrokerService;
import core.*;
import dodgydrivers.DDQService;
import girlpower.GPQService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import static org.junit.Assert.assertNotNull;


public class BrokerUnitTest {
        private static Registry registry;
        private static ClientInfo clientInfo;


        @BeforeClass
        public static void setup() {
                LocalBrokerService lbService = new LocalBrokerService();
                clientInfo = new ClientInfo("Niki Collier", ClientInfo.FEMALE, 43, 0, 5, "PQR254/1");
                try {
                        registry = LocateRegistry.createRegistry(1099);
                        BrokerService brokerService = (BrokerService) UnicastRemoteObject.exportObject(lbService,0);
                        registry.bind(Constants.BROKER_SERVICE, brokerService);
                } catch (Exception e) {
                        System.out.println("Trouble: " + e);
                }
        }

        @Test
        public void connectionTest() throws Exception {
                BrokerService service = (BrokerService) registry.lookup(Constants.BROKER_SERVICE);
                assertNotNull(service);
        }

        @Test
        public void getQuotationTestIfNotRegistered() throws RemoteException {
                LocalBrokerService lbService = new LocalBrokerService();
                List<Quotation> quotations = lbService.getQuotations(clientInfo);
                Assert.assertEquals(0,quotations.size());
        }

//        @Test
//        public void getQuotationTestIfRegistered() throws RemoteException, AlreadyBoundException {
//                LocalBrokerService lbService = new LocalBrokerService();
//                QuotationService ddqService = new DDQService();
//                QuotationService ddQuotationService = (QuotationService) UnicastRemoteObject.exportObject(ddqService,0);
//                registry.bind(Constants.DODGY_DRIVERS_SERVICE, ddQuotationService);
//                QuotationService afqService = new AFQService();
//                QuotationService AfQuotationService = (QuotationService) UnicastRemoteObject.exportObject(afqService,0);
//                registry.bind(Constants.AULD_FELLAS_SERVICE, AfQuotationService);
//
//                QuotationService gpqService = new GPQService();
//                QuotationService gpQuotationService = (QuotationService)
//                                UnicastRemoteObject.exportObject(gpqService,0);
//                registry.bind(Constants.GIRL_POWER_SERVICE, gpQuotationService);
//                List<Quotation>  quotations = lbService.getQuotations(clientInfo);
//                Assert.assertNotEquals(0,quotations.size());
//        }


}
