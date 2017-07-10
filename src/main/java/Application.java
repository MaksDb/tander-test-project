package main.java;


import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Maxim on 06.07.2017.
 */
public class Application {
    public static void main(String [] args){
        String dbName = "TestDB.s3db";
        Scanner scan = new Scanner(System.in);
        System.out.println("Input N: ");
        int N = scan.nextInt();
        DataBase dataBase = new DataBase(dbName, N);
        dataBase.deleteDB();
        dataBase.writeDB();
        dataBase.readDB();
        dataBase.closeDB();
        String name = "1.xml";
        XmlBuilder xml = new XmlBuilder(dataBase.getField(),name);
        xml.buildXML();
        xml.xsltTransformation();
        try {
            System.out.println("Сумма всех элементов = " + xml.summFields());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }
}
