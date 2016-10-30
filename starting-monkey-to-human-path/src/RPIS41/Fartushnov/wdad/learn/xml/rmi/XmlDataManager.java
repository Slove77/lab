package RPIS41.Fartushnov.wdad.learn.xml.rmi;

/**
 * Created by никита on 29.10.2016.
 */

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;

public interface XmlDataManager extends Remote{

    public int earningsTotal(Officiant officiant, Calendar calendar) throws RemoteException;

    public void removeDay(Calendar calendar) throws IOException;

    public void changeOfficiantName(Officiant oldOfficiant, Officiant newOfficiant) throws IOException;

    public List<Order> getOrders(Calendar calendar) throws RemoteException;

    public Calendar lastOfficiantWorkDate(Officiant officiant) throws NoSuchOfficiantException, RemoteException;
}
