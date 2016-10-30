package RPIS41.Fartushnov.wdad.learn.xml;

import RPIS41.Fartushnov.wdad.learn.xml.rmi.Item;
import RPIS41.Fartushnov.wdad.learn.xml.rmi.Officiant;
import RPIS41.Fartushnov.wdad.learn.xml.rmi.Order;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.Calendar;
import java.io.FileOutputStream;
import java.util.LinkedList;


import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;


public class XmlTask {

    private Document doc;
    String path="src/RPIS41/Fartushnov/wdad/learn/xml/firstXml.xml";

    public XmlTask() {

        try {
            File xmlFile = new File(path);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(xmlFile);
            checkTotalCost();
            } catch (Exception e) {
            e.printStackTrace();
            }
    }

    public int earningsTotal(String officiantSecondName, Calendar calendar) {
        int result = 0;
        NodeList dateList = doc.getElementsByTagName("date");
        NamedNodeMap dateInfo;
        NodeList orders;
        NodeList orderInfo;
        boolean isOfficiantFound = false;
        for (int i = 0; i < dateList.getLength(); i++) {
            dateInfo = dateList.item(i).getAttributes();
            if ((Integer.valueOf(dateInfo.getNamedItem("day").getNodeValue()) == calendar.get(Calendar.DAY_OF_MONTH)) &&
                    (Integer.valueOf(dateInfo.getNamedItem("month").getNodeValue()) == (calendar.get(Calendar.MONTH)+1)) &&
                    (Integer.valueOf(dateInfo.getNamedItem("year").getNodeValue()) == calendar.get(Calendar.YEAR))) {
                orders = dateList.item(i).getChildNodes();
                for (int j = 0; j < orders.getLength(); j++) {
                    orderInfo = orders.item(j).getChildNodes();
                    for (int k = 0; k < orderInfo.getLength(); k++) {
                        if ((orderInfo.item(k).getNodeName().equals("officiant")) &&
                                orderInfo.item(k).getAttributes().getNamedItem("secondname").getNodeValue().equals(officiantSecondName)) {
                            isOfficiantFound = true;
                        }
                        if ((orderInfo.item(k).getNodeName().equals("totalcost")) && isOfficiantFound) {
                            result += Integer.parseInt(orderInfo.item(k).getTextContent());
                            }
                        }
                    }
                break;
                }
            }
    return result;
    }

    public void removeDay(Calendar calendar) throws IOException {
        NodeList dates = doc.getElementsByTagName("date");
        NamedNodeMap dateInfo;
        for (int i = 0; i < dates.getLength(); i++) {
            dateInfo = dates.item(i).getAttributes();
            if ((Integer.valueOf(dateInfo.getNamedItem("day").getNodeValue()) == calendar.get(Calendar.DAY_OF_MONTH)) &&
                    (Integer.valueOf(dateInfo.getNamedItem("month").getNodeValue()) == (calendar.get(Calendar.MONTH)+1)) &&
                    (Integer.valueOf(dateInfo.getNamedItem("year").getNodeValue()) == calendar.get(Calendar.YEAR))) {
                dates.item(i).getParentNode().removeChild(dates.item(i));
                break;
            }
        }
    rewriteDoc();
    }

    public void changeOfficiantName(String oldFirstName,String oldSecondName,String newFirstName,String newSecondName)throws IOException{
        NodeList officiants = doc.getElementsByTagName("officiant");

        for (int i = 0; i < officiants.getLength(); i++) {
            NamedNodeMap officiantsInfo = officiants.item(i).getAttributes();
            if ((officiantsInfo.item(0).getNodeValue().equals(oldFirstName)) &&
                    (officiantsInfo.item(1).getNodeValue().equals(oldSecondName))) {
                officiantsInfo.item(0).setNodeValue(newFirstName);
                officiantsInfo.item(1).setNodeValue(newSecondName);
            }
        }
        rewriteDoc();
    }

    private void rewriteDoc() throws IOException{
        DOMImplementationLS domImplementationLS = (DOMImplementationLS) doc.getImplementation().getFeature("LS", "3.0");
        LSOutput lsOutput = domImplementationLS.createLSOutput();
        FileOutputStream outputStream = new FileOutputStream(path);
        lsOutput.setByteStream(outputStream);
        LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
        lsSerializer.write(doc, lsOutput);
        outputStream.close();
    }

    private void checkTotalCost() throws IOException {
        NodeList orders = doc.getElementsByTagName("order");
        int totalCost;
        boolean isTotalCostFound;
        for (int i = 0; i < orders.getLength(); i++) {
            totalCost = 0;
            isTotalCostFound = false;
            NodeList orderInfo = orders.item(i).getChildNodes();
            for (int j = 0; j < orderInfo.getLength(); j++) {
                if (orderInfo.item(j).getNodeName().equals("item")) {
                    totalCost += Integer.parseInt(orderInfo.item(j).getAttributes().item(0).getNodeValue());
                    } else if (orderInfo.item(j).getNodeName().equals("totalcost")) {
                    orderInfo.item(j).setTextContent(String.valueOf(totalCost));
                    isTotalCostFound = true;
                    rewriteDoc();
                }
            }
            if (!isTotalCostFound) {
                Element totalCostElement = doc.createElement("totalcost");
                totalCostElement.setTextContent(String.valueOf(totalCost));
                orders.item(i).appendChild(totalCostElement);
                rewriteDoc();
            }
        }
    }

    private int countItems(Calendar calendar){
        NodeList dates = doc.getElementsByTagName("date");
        NamedNodeMap dateInfo;
        int countItems=0;
        for (int i = 0; i < dates.getLength(); i++) {
            dateInfo = dates.item(i).getAttributes();
            if ((Integer.valueOf(dateInfo.getNamedItem("day").getNodeValue()) == calendar.get(Calendar.DAY_OF_MONTH)) &&
                    (Integer.valueOf(dateInfo.getNamedItem("month").getNodeValue()) == (calendar.get(Calendar.MONTH)+1)) &&
                    (Integer.valueOf(dateInfo.getNamedItem("year").getNodeValue()) == calendar.get(Calendar.YEAR))) {
                NodeList items = ((Element)dates.item(i)).getElementsByTagName("item");
                countItems+=items.getLength();
            }
        }
        return countItems;
    }

    public Calendar lastOfficiantWorkDate(String firstName, String secondName) {
        NodeList officiants = doc.getElementsByTagName("officiant");
        NamedNodeMap officiantInfo;
        Calendar calendar = null;
        for (int i = officiants.getLength() - 1; i >= 0; i--) {
            officiantInfo = officiants.item(i).getAttributes();
            if ((officiantInfo.getNamedItem("firstname").getNodeValue().equals(firstName)) &&
                    (officiantInfo.getNamedItem("secondname").getNodeValue().equals(secondName))) {
                Node date = officiants.item(i).getParentNode().getParentNode();
                calendar = Calendar.getInstance();
                NamedNodeMap dateInfo = date.getAttributes();
                calendar.set(
                        Integer.parseInt(dateInfo.getNamedItem("year").getTextContent()),
                        Integer.parseInt(dateInfo.getNamedItem("month").getTextContent()),
                        Integer.parseInt(dateInfo.getNamedItem("day").getTextContent())
                );
                return calendar;
            }
        }
        return null;
    }

    public LinkedList<Order> getOrders(Calendar date){
        LinkedList<Order> result = new LinkedList<>();
        NamedNodeMap dateInfo;
        NodeList dates = doc.getElementsByTagName("date");
        for (int i=0; i<dates.getLength(); i++){
            dateInfo = dates.item(i).getAttributes();
            if ((Integer.valueOf(dateInfo.getNamedItem("day").getNodeValue()) == date.get(Calendar.DAY_OF_MONTH)) &&
                    (Integer.valueOf(dateInfo.getNamedItem("month").getNodeValue()) == (date.get(Calendar.MONTH)+1)) &&
                    (Integer.valueOf(dateInfo.getNamedItem("year").getNodeValue()) == date.get(Calendar.YEAR))) {
                NodeList orders = dates.item(i).getChildNodes();
                for(int j = 0; j < orders.getLength(); j++){
                    Element order = (Element)orders.item(j);
                    LinkedList<Item> addingItems = new LinkedList<Item>();
                    Element officiant = (Element)order.getElementsByTagName("officiant").item(0);
                    Officiant addingOfficiant = new Officiant(officiant.getAttribute("firstname"), officiant.getAttribute("secondname"));
                    NodeList items = order.getElementsByTagName("item");
                    for(int k = 0; k < items.getLength(); k++){
                        Element item = (Element)items.item(k);
                        addingItems.add(new Item(item.getAttribute("name"), Integer.parseInt(item.getAttribute("cost"))));
                    }
                    result.add(new Order(addingOfficiant, addingItems));
                }
                break;
            }
        }
        return result;
    }
}
