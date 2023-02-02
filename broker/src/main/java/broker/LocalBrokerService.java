package broker;

import core.BrokerService;
import core.ClientInfo;
import core.Quotation;
import core.QuotationService;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.List;


/**
 * Implementation of the broker service that uses the Service Registry.
 * 
 * @author Rem
 *
 */
public class LocalBrokerService implements BrokerService {
	Registry registry;
	@Override
	public List<Quotation> getQuotations(ClientInfo info) throws RemoteException {
		List<Quotation> quotations = new LinkedList<Quotation>();
		registry = LocateRegistry.getRegistry();
		for (String name : registry.list()) {
			if (name.startsWith("qs-")) {
				try {
					QuotationService service = (QuotationService) registry.lookup(name);
					quotations.add(service.generateQuotation(info));
				} catch(NotBoundException e) {
					System.out.println(e);
				}
			}
		}

		return quotations;
	}

	@Override
	public void registerService(String name,
		            java.rmi.Remote service) throws RemoteException {
		try{
			registry = LocateRegistry.getRegistry();
			registry.bind(name,service);

		} catch (AccessException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
	}
}
