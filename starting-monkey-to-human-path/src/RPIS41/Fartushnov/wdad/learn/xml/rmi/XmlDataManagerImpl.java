package RPIS41.Fartushnov.wdad.learn.xml.rmi;/**
 * Created by никита on 29.10.2016.
 */

import RPIS41.Fartushnov.wdad.learn.xml.XmlTask;

import java.io.IOException;
import java.util.Calendar;

import java.rmi.Remote;
import java.util.List;

public class XmlDataManagerImpl implements Remote, XmlDataManager{
    private XmlTask xmlTask;

    public int earningsTotal(Officiant officiant, Calendar calendar) {
        return xmlTask.earningsTotal(officiant.getSecondName(), calendar);
    }

    public void removeDay(Calendar calendar) throws IOException {
        xmlTask.removeDay(calendar);
    }

    public void changeOfficiantName(Officiant oldOfficiant, Officiant newOfficiant) throws IOException {
        xmlTask.changeOfficiantName(oldOfficiant.getFirstName(), oldOfficiant.getSecondName(), newOfficiant.getFirstName(), newOfficiant.getSecondName());
    }

    public List<Order> getOrders(Calendar calendar) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Calendar lastOfficiantWorkDate(Officiant officiant) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
