import core.ClientInfo;
import core.Constants;
import core.Quotation;
import core.QuotationService;
import dodgydrivers.DDQService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static org.junit.Assert.assertNotNull;

public class DodgydriversUnitTest {
        private static Registry registry;
        private static ClientInfo clientInfo;
        private static QuotationService ddqService;

        @BeforeClass
        public static void setup() {
                ddqService = new DDQService();
                clientInfo = new ClientInfo("Niki Collier", ClientInfo.FEMALE, 43, 0, 5, "PQR254/1");
                try {
                        registry = LocateRegistry.createRegistry(1099);
                        QuotationService quotationService = (QuotationService)
                                        UnicastRemoteObject.exportObject(ddqService,0);
                        registry.bind(Constants.DODGY_DRIVERS_SERVICE, quotationService);
                } catch (Exception e) {
                        System.out.println("Trouble: " + e);
                }
        }

        @Test
        public void connectionTest() throws Exception {
                QuotationService service = (QuotationService)
                                registry.lookup(Constants.DODGY_DRIVERS_SERVICE);
                assertNotNull(service);
        }
        /**
         * Test if Dodgydrivers server can generate quotation
         */
        @Test
        public void generateQuotationTest() throws RemoteException {
                Quotation q = ddqService.generateQuotation(clientInfo);
                Assert.assertNotNull(q);
        }
}
