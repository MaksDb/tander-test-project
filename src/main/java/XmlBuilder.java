package main.java;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Maxim on 07.07.2017.
 */
public class XmlBuilder {

    private  String xmlName;
    private ArrayList<Integer> fieldList;
    public XmlBuilder(ArrayList<Integer> fieldList, String xmlName) {

        this.fieldList = fieldList;
        this.xmlName = xmlName;

    }

    public void buildXML(){
        Document doc = null;
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = builder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Element entries = doc.createElement("entries");
        doc.appendChild(entries);
        for (int i=0;i<fieldList.size();i++){
            Element entry = doc.createElement("entry");
            Element field = doc.createElement("field");
            field.setTextContent(fieldList.get(i).toString());
            entry.appendChild(field);
            entries.appendChild(entry);
        }
        File file = new File(xmlName);
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(file));
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        System.out.println("XML файл создан");

    }

    public void xsltTransformation(){
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource("field.xsl"));
            transformer.transform(new StreamSource(xmlName),new StreamResult("2.xml"));
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        System.out.println("Преобразование завершено");
    }

    public long summFields() throws  ParserConfigurationException, IOException, SAXException{
        long summ = 0;
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        df.setValidating(false);
        DocumentBuilder builder = df.newDocumentBuilder();
        Document document = builder.parse(new File("2.xml"));
        Node entries = document.getChildNodes().item(0);
        NodeList entryList = entries.getChildNodes();
        for (int i=0;i<entryList.getLength();i++){
            if (entryList.item(i).getNodeName() == "entry"){
                NamedNodeMap attrubutes = entryList.item(i).getAttributes();
                String field = attrubutes.getNamedItem("field").getNodeValue();
                summ += Integer.parseInt(field);
            }
        }



        return summ;
    }

    public void setFieldList(ArrayList<Integer> fieldList){
        this.fieldList = fieldList;
    }

    public void setXmlName(String name){
        this.xmlName = name;
    }

    public String getXmlName() {
        return xmlName;
    }

}
