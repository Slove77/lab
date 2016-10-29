package RPIS41.Fartushnov.wdad.data.managers;

import RPIS41.Fartushnov.wdad.utils.PreferencesConstantManager;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by никита on 29.10.2016.
 */
public class TestPrefManager {

    public static void main(String[] args) {
        try {
            PreferencesManager manager = PreferencesManager.getInstance();

            System.out.println(manager.getProperty(PreferencesConstantManager.CLASS_PROVIDER));
            System.out.println(manager.getProperty(PreferencesConstantManager.CREATE_REGISTRY));
            System.out.println(manager.getProperty(PreferencesConstantManager.POLICY_PATH));

            manager.setProperty(PreferencesConstantManager.CLASS_PROVIDER,"http://master.jar");
            manager.setProperty(PreferencesConstantManager.POLICY_PATH,"mega.policy");

            Properties prop = new Properties();
            prop.put(PreferencesConstantManager.CLASS_PROVIDER,"http://www.my.free.ru/cp/cp.jar");
            prop.put(PreferencesConstantManager.CREATE_REGISTRY,"no");
            manager.setProperties(prop);

            prop = manager.getProperties();
            manager.addBindedObject("ololo","newClass");

            manager.removeBindedObject("ololo");
        } catch (SAXException | XPathExpressionException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }
}
