import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import core.ClientInfo;
import core.Constants;
import core.Quotation;
import core.QuotationService;
import auldfellas.AFQService;
import org.junit.*;


import static org.junit.Assert.assertNotNull;


public class AuldfellasUnitTest {
        private static Registry registry;
        private static ClientInfo clientInfo;
        private static QuotationService afqService;

        @BeforeClass
        public static void setup() {
                afqService = new AFQService();
                clientInfo = new ClientInfo("Niki Collier", ClientInfo.FEMALE, 43, 0, 5, "PQR254/1");
                try {
                        registry = LocateRegistry.createRegistry(1099);
                        QuotationService quotationService = (QuotationService) UnicastRemoteObject.exportObject(afqService,0);
                        registry.bind(Constants.AULD_FELLAS_SERVICE, quotationService);

                } catch (Exception e) {
                        System.out.println("Trouble: " + e);
                }
        }

        @Test
        public void connectionTest() throws Exception {
                QuotationService service = (QuotationService) registry.lookup(Constants.AULD_FELLAS_SERVICE);
                assertNotNull(service);
        }
        /**
         * Test if Auldfellas service can generate quotation
         */
        @Test
        public void generateQuotationTest() throws RemoteException {
                Quotation q = afqService.generateQuotation(clientInfo);
                Assert.assertNotNull(q);
        }
}

