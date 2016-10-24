package RPIS41.Fartushnov.wdad.data.managers;/**
 * Created by никита on 08.10.2016.
 */
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import static RPIS41.Fartushnov.wdad.utils.PreferencesConstantManager.*;

public class PreferencesManager {
    private static PreferencesManager instance;
    private static String PATH = "src/RPIS41/Fartushnov/wdad/resources/configurations/appconfig.xml";
    private Document doc;
    private static XPathFactory factory;
    private static XPath xPath;

    public static PreferencesManager getInstance() throws ParserConfigurationException, IOException, SAXException {
        factory = XPathFactory.newInstance();
        xPath = factory.newXPath();
        if (instance == null) {
            instance = new PreferencesManager();
        }
        return instance;
    }

    @Deprecated
    public boolean isCreateRegistry() {
        NodeList nodeList = doc.getElementsByTagName("createregistry");
        if (nodeList.item(0).getTextContent().equals("yes")) {
            return true;
        } else {
            return false;
        }
    }

    @Deprecated
    public void setCreateRegistry(boolean createRegistry) throws IOException {
        NodeList nodeList = doc.getElementsByTagName("createregistry");
        if (createRegistry) {
            nodeList.item(0).setTextContent("yes");
            } else {
            nodeList.item(0).setTextContent("no");
        }
        rewriteDoc();
    }

    @Deprecated
    public String getRegistryAddress() {
        NodeList nodeList = doc.getElementsByTagName("registryaddress");
        return nodeList.item(0).getTextContent();
    }

    @Deprecated
    public void setRegistryAddress(String s) throws IOException {
        NodeList nodeList = doc.getElementsByTagName("registryaddress");
        nodeList.item(0).setTextContent(s);
        rewriteDoc();
    }

    @Deprecated
    public int getRegistryPort() {
        NodeList nodeList = doc.getElementsByTagName("registryport");
        return Integer.parseInt(nodeList.item(0).getTextContent());
    }

    @Deprecated
    public void setRegistryPort(int registryPort) throws IOException {
        NodeList nodeList = doc.getElementsByTagName("registryport");
        nodeList.item(0).setTextContent(String.valueOf(registryPort));
        rewriteDoc();
    }

    @Deprecated
    public String getPolicyPath() {
        NodeList nodeList = doc.getElementsByTagName("policypath");
        return nodeList.item(0).getTextContent();
    }

    @Deprecated
    public void setPolicyPath(String s) throws IOException {
        NodeList nodeList = doc.getElementsByTagName("policypath");
        nodeList.item(0).setTextContent(s);
        rewriteDoc();
    }

    @Deprecated
    public boolean getUseCodeBaseOnly() {
        NodeList nodeList = doc.getElementsByTagName("usecodebaseonly");
        if (nodeList.item(0).getTextContent().equals("yes")) {
            return true;
        } else {
            return false;
        }
    }

    @Deprecated
    public void setUseCodeBaseOnly(boolean useCodeBaseOnly) throws IOException {
        NodeList nodeList = doc.getElementsByTagName("usecodebaseonly");
        if (useCodeBaseOnly) {
            nodeList.item(0).setTextContent("yes");
        } else {
            nodeList.item(0).setTextContent("no");
        }
        rewriteDoc();
    }

    @Deprecated
    public String getClassProvider() {
        NodeList nodeList = doc.getElementsByTagName("classprovider");
        return nodeList.item(0).getTextContent();
    }

    @Deprecated
    public void setClassProvider(String classproviderURL) throws IOException {
        NodeList nodeList = doc.getElementsByTagName("classprovider");
        nodeList.item(0).setTextContent(classproviderURL);
        rewriteDoc();
    }


    private void rewriteDoc() throws IOException {
        DOMImplementationLS domImplementationLS =
                (DOMImplementationLS) doc.getImplementation().getFeature("LS", "3.0");
        LSOutput lsOutput = domImplementationLS.createLSOutput();
        FileOutputStream outputStream = new FileOutputStream(PATH);
        lsOutput.setByteStream(outputStream);
        LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
        lsSerializer.write(doc, lsOutput);
        outputStream.close();
    }

    private String changePath(String key){
        return (key.replace('.','/'));
    }


    public void setProperty(String key, String value) throws IOException, XPathExpressionException {
        key = changePath(key);
        XPathExpression expr = xPath.compile(key);
        Node result = (Node) expr.evaluate(doc,XPathConstants.NODE);
        result.setTextContent(value);
        rewriteDoc();
    }

    public String getProperty(String key) throws XPathExpressionException {
        key = changePath(key);
        XPathExpression expr = xPath.compile(key);
        Node result = (Node) expr.evaluate(doc,XPathConstants.NODE);
        return result.getTextContent();
    }



    public void setProperties(Properties prop) throws IOException, XPathExpressionException {
        XPathExpression expr;
        String[] keys = {CREATE_REGISTRY,REGISTRY_ADDRESS,REGISTRY_PORT,POLICY_PATH,USE_CODE_BASE_ONLY,CLASS_PROVIDER};
        for (int i = 0; i < keys.length; i++ ){
            keys[i] = changePath(keys[i]);
            expr = xPath.compile(keys[i]);
            Node result = (Node) expr.evaluate(doc,XPathConstants.NODE);
            result.setTextContent(prop.getProperty(keys[i]));
        }
        rewriteDoc();
    }

    public Properties getProperties() throws XPathExpressionException {
        XPathExpression expr;
        String[] keys = {CREATE_REGISTRY,REGISTRY_ADDRESS,REGISTRY_PORT,POLICY_PATH,USE_CODE_BASE_ONLY,CLASS_PROVIDER};
        Properties prop = new Properties();
        for (int i = 0; i < keys.length; i++ ){
            keys[i] = changePath(keys[i]);
            expr = xPath.compile(keys[i]);
            Node result = (Node) expr.evaluate(doc,XPathConstants.NODE);
            prop.setProperty(keys[i],result.getTextContent());
        }
        return prop;
    }

    public void addBindedObject(String name, String className) throws IOException, XPathExpressionException {
        String key = changePath(className);
        XPathExpression expr = xPath.compile(key);
        Node result = (Node) expr.evaluate(doc,XPathConstants.NODE);
        Element property = doc.createElement(name);
        result.appendChild(property);
        rewriteDoc();
    }

    public void removeBindedObject(String name) throws XPathExpressionException, IOException {
        String key = changePath(name);
        XPathExpression expr = xPath.compile(key);
        Node result = (Node) expr.evaluate(doc,XPathConstants.NODE);
        result.getParentNode().removeChild(result);
        rewriteDoc();
    }
}
