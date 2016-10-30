package RPIS41.Fartushnov.wdad.learn.xml.rmi;

import RPIS41.Fartushnov.wdad.data.managers.PreferencesManager;
import RPIS41.Fartushnov.wdad.utils.PreferencesConstantManager;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Calendar;

/**
 * Created by никита on 30.10.2016.
 */
public class Client {
    private static PreferencesManager prefManager;
    private static final String XML_DATA_MANAGER = "XmlDataManager";
    private static final String CODEBASE_URL = "file:/c:/RMILAB/Codebase\\";

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, XPathExpressionException {
        try {
            prefManager = PreferencesManager.getInstance();
        }catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
            System.err.println("appconfig.xml damaged");
        }

        System.out.println("Preparing ...");
        System.setProperty("java.rmi.server.codebase", CODEBASE_URL);
        System.setProperty("java.rmi.server.useCodebaseOnly", prefManager.getProperty(PreferencesConstantManager.USE_CODE_BASE_ONLY));
        System.setProperty("java.security.policy", prefManager.getProperty(PreferencesConstantManager.POLICY_PATH));
        System.out.println("prepared");
        System.setSecurityManager(new SecurityManager());
        Registry registry = null;
        try{
            registry = LocateRegistry.getRegistry(
                    prefManager.getProperty(PreferencesConstantManager.REGISTRY_ADDRESS),
                    Integer.parseInt(prefManager.getProperty(PreferencesConstantManager.REGISTRY_PORT)));
        } catch (RemoteException re) {
            re.printStackTrace();
            System.err.println("cant locate registry");
        }
        if(registry != null){
            try{
                XmlDataManager xmlDataManager = (XmlDataManager) registry.lookup(XML_DATA_MANAGER);
                try {
                    doWork(xmlDataManager);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchOfficiantException e) {
                    e.printStackTrace();
                }
            }catch (RemoteException | NotBoundException e){
                System.err.println("Your code don't work");
                e.printStackTrace();
            }
        }
    }

    private static void doWork(XmlDataManager xmlDataManager) throws IOException, NoSuchOfficiantException {
        Calendar calendarEarnings = Calendar.getInstance();
        calendarEarnings.set(2016, Calendar.JANUARY, 8);//дата для проверки прибыли за день
        Calendar calendarRemove = Calendar.getInstance();
        calendarRemove.set(2015, Calendar.APRIL, 25);//дата для удаления
        try {
            System.out.println("Прибыль Сидорова за 8 января 2016 года: " + xmlDataManager.earningsTotal(new Officiant("dimas", "sidorov"), calendarEarnings));
            xmlDataManager.removeDay(calendarRemove);
            xmlDataManager.changeOfficiantName(new Officiant("alexander", "petrov"), new Officiant("sashka", "ivanov"));
            for (Order order : xmlDataManager.getOrders(calendarEarnings))
                System.out.println(order.getOfficiant().getFirstName() + " " + order.getOfficiant().getSecondName() + " " + order.getCountItems());
            Calendar lastKolyanWorkDay = xmlDataManager.lastOfficiantWorkDate(new Officiant("kolyan", "ivanov"));
            System.out.println("last work day kolyan ivanov: " +
                    lastKolyanWorkDay.get(Calendar.DAY_OF_MONTH) + " " +
                    lastKolyanWorkDay.get(Calendar.MONTH) + " " +
                    lastKolyanWorkDay.get(Calendar.YEAR));
        }catch (RemoteException re){re.printStackTrace();}
    }
}
