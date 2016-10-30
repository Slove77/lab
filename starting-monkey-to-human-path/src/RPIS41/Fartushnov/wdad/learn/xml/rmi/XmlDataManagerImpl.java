package RPIS41.Fartushnov.wdad.learn.xml.rmi;/**
 * Created by никита on 29.10.2016.
 */

import RPIS41.Fartushnov.wdad.learn.xml.XmlTask;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;

import java.util.List;

public class XmlDataManagerImpl implements XmlDataManager,Serializable{
    private final XmlTask xmlTask = new XmlTask();

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
        return xmlTask.getOrders(calendar);
    }

    public Calendar lastOfficiantWorkDate(Officiant officiant) throws NoSuchOfficiantException {
        Calendar date = xmlTask.lastOfficiantWorkDate(officiant.getFirstName(),officiant.getSecondName());
        if (date == null)
            throw new NoSuchOfficiantException("Officiant not exist: "+officiant.getFirstName()+" "+officiant.getSecondName());
        return date;
    }

}
