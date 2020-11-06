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
import java.util.HashMap;
import java.util.Objects;

public class XMLFileReader {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private final String INGREDIENTS_FILENAME = "ingredients.xml";
    private Element ingredientsRoot;
    private XPath path;

    public XMLFileReader() {
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        File fileXML = new File("fr.univcotedazur.polytech.si4.fsm.2020.project\\src\\fr\\univcotedazur\\polytech\\si4\\fsm\\project\\ingredients.xml");
        Document ingredientXml = null;
        try {
            ingredientXml = builder.parse(fileXML);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ingredientsRoot = ingredientXml.getDocumentElement();
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
     
            System.out.println("Fichier sauvegardé avec succès!");
     
         } catch (ParserConfigurationException pce) {
             pce.printStackTrace();
         } catch (TransformerException tfe) {
             tfe.printStackTrace();
         }
    }
}
