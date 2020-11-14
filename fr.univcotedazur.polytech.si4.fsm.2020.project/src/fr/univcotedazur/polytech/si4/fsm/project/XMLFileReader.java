package fr.univcotedazur.polytech.si4.fsm.project;

import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class XMLFileReader {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private final String INGREDIENTS_FILENAME = "ingredients.xml";
    private Element ingredientsRoot;
    private Element clientsRoot;
    private Document clientsXml;
    private XPath path;

    public XMLFileReader() {
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        File ingredientsFileXML = new File("fr.univcotedazur.polytech.si4.fsm.2020.project\\src\\fr\\univcotedazur\\polytech\\si4\\fsm\\project\\ingredients.xml");
        File clientsFileXML = new File("fr.univcotedazur.polytech.si4.fsm.2020.project\\src\\fr\\univcotedazur\\polytech\\si4\\fsm\\project\\clients.xml");
        Document ingredientXml = null;
        clientsXml = null;
        try {
            ingredientXml = builder.parse(ingredientsFileXML);
            clientsXml = builder.parse(clientsFileXML);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ingredientsRoot = ingredientXml.getDocumentElement();
        clientsRoot = clientsXml.getDocumentElement();
        XPathFactory xpf = XPathFactory.newInstance();
        path = xpf.newXPath();
    }

    protected HashMap<String, Integer> readIngredientsList() throws XPathExpressionException {
        HashMap<String, Integer> ingredients = new HashMap<String, Integer>();

        String expressoPath = "/INGREDIENTS/expresso";
        String coffeePath = "/INGREDIENTS/coffee";
        String teaPath = "/INGREDIENTS/tea";
        String sugarPath = "/INGREDIENTS/sugar";
        String milkPath = "/INGREDIENTS/milk";
        String croutonsPath = "/INGREDIENTS/croutons";
        String maplePath = "/INGREDIENTS/mapleSirup";
        String vanillaPath = "/INGREDIENTS/iceCream";

        int expressoStock = ((Double) path.evaluate(expressoPath, ingredientsRoot, XPathConstants.NUMBER)).intValue();
        int coffeeStock = ((Double) path.evaluate(coffeePath, ingredientsRoot, XPathConstants.NUMBER)).intValue();
        int teaStock = ((Double) path.evaluate(teaPath, ingredientsRoot, XPathConstants.NUMBER)).intValue();
        int sugarStock = ((Double) path.evaluate(sugarPath, ingredientsRoot, XPathConstants.NUMBER)).intValue();
        int milkStock = ((Double) path.evaluate(milkPath, ingredientsRoot, XPathConstants.NUMBER)).intValue();
        int croutonsStock = ((Double) path.evaluate(croutonsPath, ingredientsRoot, XPathConstants.NUMBER)).intValue();
        int mapleStock = ((Double) path.evaluate(maplePath, ingredientsRoot, XPathConstants.NUMBER)).intValue();
        int vanillaStock = ((Double) path.evaluate(vanillaPath, ingredientsRoot, XPathConstants.NUMBER)).intValue();

        ingredients.put("expresso", expressoStock);
        ingredients.put("coffee", coffeeStock);
        ingredients.put("tea", teaStock);
        ingredients.put("sugar", sugarStock);
        ingredients.put("milk", milkStock);
        ingredients.put("croutons", croutonsStock);
        ingredients.put("mapleSirup", mapleStock);
        ingredients.put("iceCream", vanillaStock);
        return ingredients;
    }
    
    protected void writeIngredientsList(HashMap<String, Integer> list) {
    	try {
    		 
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
     
            Document doc = docBuilder.newDocument();
            Element racine = doc.createElement("INGREDIENTS");
            doc.appendChild(racine);
     
            list.forEach((k, v) -> {
            	Element element = doc.createElement(k);
                element.appendChild(doc.createTextNode(v.toString()));
                racine.appendChild(element);
            });
     
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult resultat = new StreamResult(new File("fr.univcotedazur.polytech.si4.fsm.2020.project\\src\\fr\\univcotedazur\\polytech\\si4\\\\fsm\\project\\ingredients.xml"));
     
            transformer.transform(source, resultat);
     
         } catch (ParserConfigurationException pce) {
             pce.printStackTrace();
         } catch (TransformerException tfe) {
             tfe.printStackTrace();
         }
    }

    protected HashMap<Integer, Customer> readCustomersList() throws XPathExpressionException {
        HashMap<Integer, Customer> customersList = new HashMap<Integer, Customer>();

        String expression = "/CLIENTS";

        NodeList clientNodeList = clientsXml.getElementsByTagName("client");
        for(int i = 0 ; i < clientNodeList.getLength(); i++){
            Node n = clientNodeList.item(i);
            int id = Integer.parseInt(((Element) n).getElementsByTagName("id").item(0).getTextContent());
            int numberOfCommand = Integer.parseInt(((Element) n).getElementsByTagName("numberOfCommand").item(0).getTextContent());
            ArrayList<Integer> listOfPrices= new ArrayList<Integer>();
            int discount = Integer.parseInt(((Element) n).getElementsByTagName("discount").item(0).getTextContent());

            expression = "listOfPrices/price";
            path.compile(expression);
            NodeList listNodeList = (NodeList)path.evaluate(expression, n, XPathConstants.NODESET);
            for(int j=0; j < listNodeList.getLength(); j++){
                Node priceNode = listNodeList.item(j);
                int price = Integer.parseInt(priceNode.getTextContent());
                listOfPrices.add(price);
            }
            Customer customer = new Customer(numberOfCommand, listOfPrices, discount);
            customersList.put(id, customer);
        }
        return customersList;
    }

    protected void writeCustomerList(HashMap<Integer, Customer> list){
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element racine = doc.createElement("CLIENTS");
            doc.appendChild(racine);

            list.forEach((k, v) -> {
                Element client = doc.createElement("client");
                racine.appendChild(client);

                Element id = doc.createElement("id");
                id.appendChild(doc.createTextNode(k.toString()));
                client.appendChild(id);

                Element numberOfCommand = doc.createElement("numberOfCommand");
                numberOfCommand.appendChild(doc.createTextNode(String.valueOf((v.getNumberOfCommand()))));
                client.appendChild(numberOfCommand);

                Element listOfPrices = doc.createElement("listOfPrices");
                client.appendChild(listOfPrices);

                Element discount = doc.createElement("discount");
                discount.appendChild(doc.createTextNode(String.valueOf(v.getDiscount())));
                client.appendChild(discount);

                for (int priceValue: v.getListOfPrices()) {
                    Element price = doc.createElement("price");
                    price.appendChild(doc.createTextNode(String.valueOf(priceValue)));
                    listOfPrices.appendChild(price);
                }
            });

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult resultat = new StreamResult(new File("fr.univcotedazur.polytech.si4.fsm.2020.project\\src\\fr\\univcotedazur\\polytech\\si4\\\\fsm\\project\\clients.xml"));

            transformer.transform(source, resultat);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

    }
}
