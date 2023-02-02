package core;

import java.rmi.Remote;
import java.util.List;

//import service.registry.Service;

/**
 * Interface for describe the behaviours of the broker service
 *
 *
 */
public interface BrokerService extends java.rmi.Remote {
        public List<Quotation> getQuotations(ClientInfo info) throws java.rmi.RemoteException;
        //abstract method for solving java rmi restriction to make java rmi
        // can be used between two machines
        public void registerService(String name, Remote service) throws java.rmi.RemoteException;
}
