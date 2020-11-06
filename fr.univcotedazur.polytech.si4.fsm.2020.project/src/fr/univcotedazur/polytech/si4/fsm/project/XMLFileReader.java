package fr.univcotedazur.polytech.si4.fsm.project;

import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
        ClassLoader classLoader = getClass().getClassLoader();
        File fileXML = new File(Objects.requireNonNull(classLoader.getResource(INGREDIENTS_FILENAME)).getFile());
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
}
