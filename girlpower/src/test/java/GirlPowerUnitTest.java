import core.ClientInfo;
import core.Constants;
import core.Quotation;
import core.QuotationService;
import girlpower.GPQService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static org.junit.Assert.assertNotNull;


public class GirlPowerUnitTest {
        private static Registry registry;
        private static ClientInfo clientInfo;
        private static QuotationService gpqService;

        @BeforeClass
        public static void setup() {
                gpqService = new GPQService();
                clientInfo = new ClientInfo("Niki Collier", ClientInfo.FEMALE, 43, 0, 5, "PQR254/1");
                try {
                        registry = LocateRegistry.createRegistry(1099);
                        QuotationService quotationService = (QuotationService)
                                        UnicastRemoteObject.exportObject(gpqService,0);
                        registry.bind(Constants.GIRL_POWER_SERVICE, quotationService);
                } catch (Exception e) {
                        System.out.println("Trouble: " + e);
                }
        }

        @Test
        public void connectionTest() throws Exception {
                QuotationService service = (QuotationService)
                                registry.lookup(Constants.GIRL_POWER_SERVICE);
                assertNotNull(service);
        }

        /**
         * Test if girl power server can generate quotation
         */
        @Test
        public void generateQuotationTest() throws RemoteException {
                Quotation q = gpqService.generateQuotation(clientInfo);
                Assert.assertNotNull(q);
        }

}
