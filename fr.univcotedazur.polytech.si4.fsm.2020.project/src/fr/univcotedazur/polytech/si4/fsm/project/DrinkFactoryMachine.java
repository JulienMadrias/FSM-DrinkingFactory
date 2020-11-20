package fr.univcotedazur.polytech.si4.fsm.project;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.xpath.XPathExpressionException;

import fr.univcotedazur.polytech.si4.fsm.project.drinkingMachine.DrinkingFactoryStatemachine;



public class DrinkFactoryMachine extends JFrame {

	private static final long serialVersionUID = 2030629304432075314L;
	private JPanel contentPane;
	protected DrinkingFactoryStatemachine theFSM;
	private JLabel lblValue, lblPot, lblChange, lblSugar;
	private JFormattedTextField idField;
	private JButton coffeeButton, expressoButton, teaButton, soupButton;
	private JToggleButton milkButton, croutonButton, mapleButton, vanillaButton;
	private JSlider sizeSlider, sugarSlider, temperatureSlider;
	private boolean milkState=false, croutonState=false, mapleState=false, vanillaState=false;
	private boolean finalMilkState=true, finalCroutonState=true, finalMapleState=true, finalVanillaState=true;
	private boolean cbDataRegistered = false;
	private int cashValue = 0;
	private int coin = 0;
	private int maxPrice = 150;
	private int price = maxPrice;
	private int additive, size, temperature;
	private enum drink{COFFEE, EXPRESSO, TEA, SOUP};
	private drink choosedDrink;
	private Recipe recette;
	JLabel labelForPictures;
	JProgressBar progressBar;
	private HashMap<String, Integer> stock;
	private HashMap<Integer, Customer> customers;
	private Customer currentCustomer;
	private boolean isConnected = false;
	private int currentCustomerId;
	private XMLFileReader fileReader;
	private double advancementTime = 100;
	private int maxSugar, maxDrinkDose, maxCoffeeDose, maxTeaDose, maxExpressoDose, maxSoupDose, maxSpices, maxOption;
	private boolean ownCup = false;

	
	/**
	 * @wbp.nonvisual location=311,475
	 */
	private final ImageIcon imageIcon = new ImageIcon();

	protected void cancelOrder() {
		if(cbDataRegistered) {
			lblPot.setText("Transaction annulée");
			cbDataRegistered = false;
		}
		else {
			lblPot.setText("");
			giveBackChange(cashValue);
		}
		choosedDrink = null;
		resetOptionsState();
		idField.setValue(null);
		isConnected = false;
		milkButton.setEnabled(false);
		croutonButton.setEnabled(false);
		mapleButton.setEnabled(false);
		vanillaButton.setEnabled(false);
		price = 0;
		ownCup = false;
		lblValue.setText("Price: " + price + "€");
		advancementTime = 100;
		progressBar.setValue(0);
		doChangeImgVide();
		System.out.println("Bienvenue, vous pouvez commander");

	}
	
	protected void startSystem() {
		fileReader = new XMLFileReader();
		try {
			stock = fileReader.readIngredientsList();
			customers = fileReader.readCustomersList();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		verifStock();
		resetMoneyDisplay();
		resetOptionsState();
		idField.setValue(null);
		isConnected = false;
	}
	
	private void verifStock() {
		if(stock.get("expresso") == 0) {expressoButton.setEnabled(false);}
		if(stock.get("coffee") == 0) {coffeeButton.setEnabled(false);}
		if(stock.get("tea") == 0) {teaButton.setEnabled(false);}
		if(stock.get("soup") == 0){soupButton.setEnabled(false);}
		if(stock.get("milk") == 0) {finalMilkState = false;}
		if(stock.get("croutons") == 0) {finalCroutonState = false;}
		if(stock.get("mapleSirup") == 0) {finalMapleState = false;}
		if(stock.get("iceCream") == 0) {finalVanillaState = false;}
		maxExpressoDose = stock.get("expresso");
		maxCoffeeDose = stock.get("coffee");
		maxTeaDose = stock.get("tea");
		maxSoupDose = stock.get("soup");
		maxSugar = stock.get("sugar");
		maxSpices = stock.get("spices");
		maxOption = maxSugar;
	}

	protected void resetMoneyDisplay() {
		lblPot.setText("");
		lblChange.setText("");
		lblValue.setText("");
	}
	
	protected void payInCB() {
		cbDataRegistered = true;
		if(idField.getValue() != null) {
			currentCustomerId = ((Long)idField.getValue()).intValue();
			connectUser(currentCustomerId);
		}
		lblPot.setText("Carte acceptée");
		verifSelection();
	}
	
	private void connectUser(int id) {
		if(customers.containsKey(id)) {
			currentCustomer = customers.get(id);
		}
		else {
			currentCustomer = new Customer(id);
		}
		isConnected = true;
	}

	protected void addCash() {
		cashValue += coin;
		coin = 0;
		if(cashValue > maxPrice) {
			giveBackChange(cashValue - maxPrice);
		}
		if(cashValue < 100) {
			lblPot.setText("0." + cashValue + " €");
		}
		else if(cashValue == 100) {lblPot.setText("1.00 €");}
		else {
			int cents = cashValue;
			cents -= 100;
			lblPot.setText("1." + cents + " €");
		}
		verifSelection();
	}
	
	protected void drinkSelected() {
		displayValue();
		setOptionsButtons();
		verifSelection();
	}
	
	protected void giveBackChange(int change) {
		cashValue -= change;
		lblChange.setText("Change: " + change);
	}
	
	protected void verifSelection() {
		if(choosedDrink == null) {}
		else if(cbDataRegistered) {
		    if(isConnected && currentCustomer.useDiscount(price)){
                lblPot.setText("Boisson offerte");
            }
            else if(isConnected){
                lblPot.setText("Transaction effectuée");
                currentCustomer.addToList(price);
            }
            else{ lblPot.setText("Transaction effectuée");}
			cbDataRegistered = false;
			theFSM.raiseValidate();
		}
		else {
			if(cashValue>=price) {
				giveBackChange(cashValue-price);
				cashValue = 0;
				theFSM.raiseValidate();
			}
		}
	}
	
	private void resetOptionsState() {
		additive = 0;
		size = 0;
		temperature = 0;
		sugarSlider.setValue(0);
		sizeSlider.setValue(0);
		temperatureSlider.setValue(0);
		milkState=false;
		croutonState=false;
		mapleState=false;
		vanillaState=false;
		milkButton.setSelected(false);
		croutonButton.setSelected(false);
		mapleButton.setSelected(false);
		vanillaButton.setSelected(false);
		lblSugar.setText("Sugar");
		maxOption = maxSugar;
	}
	
	private void setOptionsButtons() {
		resetOptionsState();
		switch(choosedDrink) {
			case COFFEE:
				milkButton.setEnabled(true && finalMilkState);
				croutonButton.setEnabled(false);
				mapleButton.setEnabled(true && finalMapleState);
				vanillaButton.setEnabled(true && finalVanillaState);
                setOptionSlider(true);
				break;
			case TEA:
				milkButton.setEnabled(true && finalMilkState);
				croutonButton.setEnabled(false);
				mapleButton.setEnabled(true && finalMapleState);
				vanillaButton.setEnabled(false);
                setOptionSlider(true);
				break;
			case EXPRESSO:
				milkButton.setEnabled(true && finalMilkState);
				croutonButton.setEnabled(false);
				mapleButton.setEnabled(true && finalMapleState);
				vanillaButton.setEnabled(true && finalVanillaState);
                setOptionSlider(true);
				break;
			case SOUP:
				milkButton.setEnabled(false);
				croutonButton.setEnabled(true && finalCroutonState);
				mapleButton.setEnabled(false);
				vanillaButton.setEnabled(false);
                setOptionSlider(false);
                break;
			default: 
				milkButton.setEnabled(false);
				croutonButton.setEnabled(false);
				mapleButton.setEnabled(false);
				vanillaButton.setEnabled(false);
				setOptionSlider(true);
				break;	
		}
	}

	private void setOptionSlider(boolean sweetDrink){
		if(sweetDrink){
			lblSugar.setText("Sugar");
			maxOption = maxSugar;
		}
		else{
			lblSugar.setText("Spices");
			maxOption = maxSpices;
		}
	}
	
	private void displayValue() {
		if(price < 100) {lblValue.setText("Price: 0." + price + "€");}
		else if(price == 100) {lblValue.setText("Price: 1.00 €");}
		else {
			int cents = price;
			cents -= 100;
			lblValue.setText("Price: 1." + cents + "€");
		}
	}
	
	protected void startMachine() {
		resetMoneyDisplay();
		switch(choosedDrink) {
		case COFFEE:
			stock.put("coffee", stock.get("coffee")-(size+1));
			stock.put("sugar", stock.get("sugar")-additive);
			if(milkState) {stock.put("milk", stock.get("milk")-1);}
			if(mapleState) {stock.put("mapleSirup", stock.get("mapleSirup")-1);}
			if(vanillaState) {stock.put("iceCream", stock.get("iceCream")-1);}
			recette = new Coffee(additive, size, temperature, ownCup, milkState, mapleState, vanillaState);
			break;
		case TEA:
			stock.put("tea", stock.get("tea")-(size+1));
			stock.put("sugar", stock.get("sugar")-additive);
			if(milkState) {stock.put("milk", stock.get("milk")-1);}
			if(mapleState) {stock.put("mapleSirup", stock.get("mapleSirup")-1);}
			recette = new Tea(additive, size, temperature, ownCup, milkState, mapleState);
			break;
		case EXPRESSO:
			stock.put("expresso", stock.get("coffee")-(size+1));
			stock.put("sugar", stock.get("sugar")-additive);
			if(milkState) {stock.put("milk", stock.get("milk")-1);}
			if(mapleState) {stock.put("mapleSirup", stock.get("mapleSirup")-1);}
			if(vanillaState) {stock.put("iceCream", stock.get("iceCream")-1);}
			recette = new Expresso(additive, size, temperature, ownCup, milkState, mapleState, vanillaState);
			break;
		case SOUP:
			stock.put("soup", stock.get("soup")-(size-1));
			stock.put("spices", stock.get("spices")-additive);
			if(croutonState) {stock.put("croutons", stock.get("croutons")-1);}
			recette = new Soup(additive, size, temperature, ownCup, croutonState);
			break;
		default: 
			break;
			}
		fileReader.writeIngredientsList(stock);
		if(isConnected){
		    customers.put(currentCustomerId, currentCustomer);
		    fileReader.writeCustomerList(customers);
		}
		recette.time();
		theFSM.setTime1(recette.time1);
		theFSM.setTime2(recette.time2);
		theFSM.setTime3(recette.time3);
		theFSM.setTime4(recette.time4);
		theFSM.setTime5(recette.time5);
		theFSM.setTotalTime(recette.totalTime);
	}
	
	protected void doChangeImgGobelet() {
		if(!ownCup) {
			BufferedImage myPicture = null;
			try {
				myPicture = ImageIO.read(new File("./fr.univcotedazur.polytech.si4.fsm.2020.project/picts/gobeletPolluant.jpg"));
			} catch (IOException ee) {
				ee.printStackTrace();
			}
			labelForPictures.setIcon(new ImageIcon(myPicture));
		}
	}
	
	protected void doChangeImgVide() {
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("./fr.univcotedazur.polytech.si4.fsm.2020.project/picts/vide2.jpg"));
		} catch (IOException ee) {
			ee.printStackTrace();
		}
		labelForPictures.setIcon(new ImageIcon(myPicture));
	}
	
	protected void doChangeImgCup() {
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("./fr.univcotedazur.polytech.si4.fsm.2020.project/picts/ownCup.jpg"));
		} catch (IOException ee) {
			ee.printStackTrace();
		}
		labelForPictures.setIcon(new ImageIcon(myPicture));
	}
	
	protected void progressBarAdvancement() {
		advancementTime += 100;
		BigDecimal valeur = new BigDecimal((advancementTime / recette.totalTime)*100);
		progressBar.setValue(valeur.intValue());
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DrinkFactoryMachine frame = new DrinkFactoryMachine();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DrinkFactoryMachine() {
		
		theFSM = new DrinkingFactoryStatemachine();
		TimerService timer = new TimerService();
	    theFSM.setTimerService(timer);
	    
	    theFSM.getDoAddCash().subscribe(e -> this.addCash());
	    theFSM.getDoCancel().subscribe(e -> this.cancelOrder());
	    theFSM.getDoWelcome().subscribe(e -> this.startSystem());
	    theFSM.getDoCB().subscribe(e -> this.payInCB());
	    theFSM.getDoHotDrink().subscribe(e -> this.drinkSelected());
	    
	    theFSM.getDoStartMachine().subscribe(e -> this.startMachine());
	    theFSM.getDoTakeIngr().subscribe(e -> recette.TakeIngredient());
	    theFSM.getDoStartHeated().subscribe(e -> recette.StartHeatedWater());
	    theFSM.getDoPrepPouring().subscribe(e -> recette.PrepPouring());
	    theFSM.getDoWaitHeated().subscribe(e -> recette.WaitHeatedWater());
	    theFSM.getDoSugar().subscribe(e -> recette.PutSugar());
	    theFSM.getDoPouring().subscribe(e -> recette.PouringWater());
	    theFSM.getDoPrepSupp().subscribe(e -> recette.PrepSupp());
	    theFSM.getDoWaitRecup().subscribe(e -> recette.WaitRecup());
	    theFSM.getDoWash().subscribe(e -> this.doChangeImgVide());
	    theFSM.getDoWash().subscribe(e -> recette.WashingMashine());
	    theFSM.getDoProgressBar().subscribe(e -> this.progressBarAdvancement());
	    theFSM.getDoPrepPouring().subscribe(e -> this.doChangeImgGobelet());
	    

		theFSM.enter();
		
		setForeground(Color.WHITE);
		setFont(new Font("Cantarell", Font.BOLD, 22));
		setBackground(Color.DARK_GRAY);
		setTitle("Drinking Factory Machine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 650);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel messagesToUser = new JLabel("<html>This is<br>place to communicate <br> with the user");
		messagesToUser.setForeground(Color.WHITE);
		messagesToUser.setHorizontalAlignment(SwingConstants.LEFT);
		messagesToUser.setVerticalAlignment(SwingConstants.TOP);
		messagesToUser.setToolTipText("message to the user");
		messagesToUser.setBackground(Color.WHITE);
		messagesToUser.setBounds(126, 34, 165, 57);
		contentPane.add(messagesToUser);

		JLabel lblCoins = new JLabel("Coins");
		lblCoins.setForeground(Color.WHITE);
		lblCoins.setHorizontalAlignment(SwingConstants.CENTER);
		lblCoins.setBounds(538, 12, 44, 15);
		contentPane.add(lblCoins);
		
		JLabel lblOptions = new JLabel("Options");
		lblOptions.setForeground(Color.WHITE);
		lblOptions.setHorizontalAlignment(SwingConstants.CENTER);
		lblOptions.setBounds(126, 91, 50, 15);
		contentPane.add(lblOptions);

		coffeeButton = new JButton("Coffee");
		coffeeButton.setForeground(Color.WHITE);
		coffeeButton.setBackground(Color.DARK_GRAY);
		coffeeButton.setBounds(12, 34, 96, 25);
		coffeeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				choosedDrink = drink.COFFEE;
				if(ownCup) {
					price = 25;
				}else {
					price = 35;
				}
				maxDrinkDose = maxCoffeeDose;
				theFSM.raiseSelectHotDrink();
			}
		});
		contentPane.add(coffeeButton);

		expressoButton = new JButton("Expresso");
		expressoButton.setForeground(Color.WHITE);
		expressoButton.setBackground(Color.DARK_GRAY);
		expressoButton.setBounds(12, 71, 96, 25);
		expressoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				choosedDrink = drink.EXPRESSO;
				if(ownCup) {
					price = 40;
				}else {
					price = 50;
				}
				maxDrinkDose = maxExpressoDose;
				theFSM.raiseSelectHotDrink();
			}
		});
		contentPane.add(expressoButton);

		teaButton = new JButton("Tea");
		teaButton.setForeground(Color.WHITE);
		teaButton.setBackground(Color.DARK_GRAY);
		teaButton.setBounds(12, 108, 96, 25);
		teaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				choosedDrink = drink.TEA;
				if(ownCup) {
					price = 30;
				}else {
					price = 40;
				}
				maxDrinkDose = maxTeaDose;
				theFSM.raiseSelectHotDrink();
			}
		});
		contentPane.add(teaButton);

		soupButton = new JButton("Soup");
		soupButton.setForeground(Color.WHITE);
		soupButton.setBackground(Color.DARK_GRAY);
		soupButton.setBounds(12, 145, 96, 25);
		soupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				choosedDrink = drink.SOUP;
				price = 75;
				maxDrinkDose = maxSoupDose;
				theFSM.raiseSelectHotDrink();
			}
		});
		contentPane.add(soupButton);
		
		milkButton = new JToggleButton("Milk Cloud");
		milkButton.setForeground(Color.WHITE);
		milkButton.setBackground(Color.DARK_GRAY);
		milkButton.setBounds(130, 108, 96, 25);
		milkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!milkState) {
					price += 10;
					milkState = true;
				}
				else {
					price -= 10;
					milkState = false;
				}
				displayValue();
				theFSM.raiseSelectParam();
			}
		});
		contentPane.add(milkButton);
		milkButton.setEnabled(false);
		
		croutonButton = new JToggleButton("Croutons");
		croutonButton.setForeground(Color.WHITE);
		croutonButton.setBackground(Color.DARK_GRAY);
		croutonButton.setBounds(130, 145, 96, 25);
		croutonButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!croutonState) {
					price += 30;
					croutonState = true;
				}
				else {
					price -= 30;
					croutonState = false;
				}
				displayValue();
				theFSM.raiseSelectParam();
			}
		});
		contentPane.add(croutonButton);
		croutonButton.setEnabled(false);
		
		mapleButton = new JToggleButton("Maple Sirup");
		mapleButton.setForeground(Color.WHITE);
		mapleButton.setBackground(Color.DARK_GRAY);
		mapleButton.setBounds(130, 182, 96, 25);
		mapleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!mapleState) {
					price += 10;
					mapleState = true;
				}
				else {
					price -= 10;
					mapleState = false;
				}
				displayValue();
				theFSM.raiseSelectParam();
			}
		});
		contentPane.add(mapleButton);
		mapleButton.setEnabled(false);
		
		vanillaButton = new JToggleButton("Ice Cream");
		vanillaButton.setForeground(Color.WHITE);
		vanillaButton.setBackground(Color.DARK_GRAY);
		vanillaButton.setBounds(130, 219, 96, 25);
		vanillaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!vanillaState) {
					price += 40;
					vanillaState = true;
				}
				else {
					price -= 40;
					vanillaState = false;
				}
				displayValue();
				theFSM.raiseSelectParam();
			}
		});
		contentPane.add(vanillaButton);
		vanillaButton.setEnabled(false);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setValue(0);
		progressBar.setForeground(Color.LIGHT_GRAY);
		progressBar.setBackground(Color.DARK_GRAY);
		progressBar.setBounds(12, 254, 622, 26);
		contentPane.add(progressBar);

		sugarSlider = new JSlider();
		sugarSlider.setValue(0);
		sugarSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sugarSlider.setBackground(Color.DARK_GRAY);
		sugarSlider.setForeground(Color.WHITE);
		sugarSlider.setPaintTicks(true);
		sugarSlider.setMinorTickSpacing(1);
		sugarSlider.setMajorTickSpacing(1);
		sugarSlider.setMaximum(4);
		sugarSlider.setBounds(301, 51, 200, 36);
		additive = sugarSlider.getValue();
		sugarSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				if(sugarSlider.getValue() > maxOption) {sugarSlider.setValue(maxOption);}
				additive = sugarSlider.getValue();
				theFSM.raiseSelectParam();
			}
		});
		contentPane.add(sugarSlider);

		sizeSlider = new JSlider();
		sizeSlider.setPaintTicks(true);
		sizeSlider.setValue(0);
		sizeSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sizeSlider.setBackground(Color.DARK_GRAY);
		sizeSlider.setForeground(Color.WHITE);
		sizeSlider.setMinorTickSpacing(1);
		sizeSlider.setMaximum(2);
		sizeSlider.setMajorTickSpacing(1);
		sizeSlider.setBounds(301, 125, 200, 36);
		size = sizeSlider.getValue();
		sizeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				if(sizeSlider.getValue()+1 > maxDrinkDose) {sizeSlider.setValue(maxDrinkDose-1);}
				size = sizeSlider.getValue();
				theFSM.raiseSelectParam();
			}
		});
		contentPane.add(sizeSlider);

		temperatureSlider = new JSlider();
		temperatureSlider.setPaintLabels(true);
		temperatureSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		temperatureSlider.setValue(2);
		temperatureSlider.setBackground(Color.DARK_GRAY);
		temperatureSlider.setForeground(Color.WHITE);
		temperatureSlider.setPaintTicks(true);
		temperatureSlider.setMajorTickSpacing(1);
		temperatureSlider.setMaximum(3);
		temperatureSlider.setBounds(301, 188, 200, 54);

		Hashtable<Integer, JLabel> temperatureTable = new Hashtable<Integer, JLabel>();
		temperatureTable.put(0, new JLabel("20°C"));
		temperatureTable.put(1, new JLabel("35°C"));
		temperatureTable.put(2, new JLabel("60°C"));
		temperatureTable.put(3, new JLabel("85°C"));
		for (JLabel l : temperatureTable.values()) {
			l.setForeground(Color.WHITE);
		}
		temperatureSlider.setLabelTable(temperatureTable);
		temperature = temperatureSlider.getValue();
		temperatureSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				temperature = temperatureSlider.getValue();
				theFSM.raiseSelectParam();
			}
		});
		contentPane.add(temperatureSlider);

		JButton icedTeaButton = new JButton("Iced Tea");
		icedTeaButton.setForeground(Color.WHITE);
		icedTeaButton.setBackground(Color.DARK_GRAY);
		icedTeaButton.setBounds(12, 182, 96, 25);
		contentPane.add(icedTeaButton);

		lblSugar = new JLabel("Sugar");
		lblSugar.setForeground(Color.WHITE);
		lblSugar.setBackground(Color.DARK_GRAY);
		lblSugar.setHorizontalAlignment(SwingConstants.CENTER);
		lblSugar.setBounds(380, 34, 44, 15);
		contentPane.add(lblSugar);

		JLabel lblSize = new JLabel("Size");
		lblSize.setForeground(Color.WHITE);
		lblSize.setBackground(Color.DARK_GRAY);
		lblSize.setHorizontalAlignment(SwingConstants.CENTER);
		lblSize.setBounds(380, 113, 44, 15);
		contentPane.add(lblSize);

		JLabel lblTemperature = new JLabel("Temperature");
		lblTemperature.setForeground(Color.WHITE);
		lblTemperature.setBackground(Color.DARK_GRAY);
		lblTemperature.setHorizontalAlignment(SwingConstants.CENTER);
		lblTemperature.setBounds(363, 173, 96, 15);
		contentPane.add(lblTemperature);

		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		lblCoins.setLabelFor(panel);
		panel.setBounds(538, 25, 96, 97);
		contentPane.add(panel);

		JButton money50centsButton = new JButton("0.50 €");
		money50centsButton.setForeground(Color.WHITE);
		money50centsButton.setBackground(Color.DARK_GRAY);
		money50centsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				coin = 50;
				theFSM.raiseAddCash();
			}
		});
		panel.add(money50centsButton);

		JButton money25centsButton = new JButton("0.25 €");
		money25centsButton.setForeground(Color.WHITE);
		money25centsButton.setBackground(Color.DARK_GRAY);
		money25centsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				coin = 25;
				theFSM.raiseAddCash();
			}
		});
		panel.add(money25centsButton);

		JButton money10centsButton = new JButton("0.10 €");
		money10centsButton.setForeground(Color.WHITE);
		money10centsButton.setBackground(Color.DARK_GRAY);
		money10centsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				coin = 10;
				theFSM.raiseAddCash();
			}
		});
		panel.add(money10centsButton);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		panel_1.setBounds(538, 154, 96, 40);
		contentPane.add(panel_1);

		JButton nfcBiiiipButton = new JButton("biiip");
		nfcBiiiipButton.setForeground(Color.WHITE);
		nfcBiiiipButton.setBackground(Color.DARK_GRAY);
		nfcBiiiipButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				theFSM.raisePayCB();
			}
		});
		panel_1.add(nfcBiiiipButton);

		idField = new JFormattedTextField(NumberFormat.getNumberInstance());
		idField.setForeground(Color.WHITE);
		idField.setBackground(Color.DARK_GRAY);
		idField.setBounds(552, 190, 65, 20);
		contentPane.add(idField);

		JLabel lblNfc = new JLabel("NFC");
		lblNfc.setForeground(Color.WHITE);
		lblNfc.setHorizontalAlignment(SwingConstants.CENTER);
		lblNfc.setBounds(541, 139, 41, 15);
		contentPane.add(lblNfc);

		JSeparator separator = new JSeparator();
		separator.setBounds(12, 292, 622, 15);
		contentPane.add(separator);

		JButton addCupButton = new JButton("Add cup");
		addCupButton.setForeground(Color.WHITE);
		addCupButton.setBackground(Color.DARK_GRAY);
		addCupButton.setBounds(45, 336, 96, 25);
		contentPane.add(addCupButton);
		
		Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
		
		lblValue = new JLabel();
		lblValue.setForeground(Color.WHITE);
		lblValue.setBackground(Color.DARK_GRAY);
		lblValue.setFont(new Font("Courier", Font.PLAIN, 13));
		lblValue.setBorder(border);
		lblValue.setBounds(30, 370, 125, 25);
		contentPane.add(lblValue);
		
		lblPot = new JLabel();
		lblPot.setForeground(Color.WHITE);
		lblPot.setBackground(Color.DARK_GRAY);
		lblPot.setBorder(border);
		lblPot.setFont(new Font("Courier", Font.PLAIN, 13));
		lblPot.setBounds(30, 405, 125, 25);
		contentPane.add(lblPot);
		
		lblChange = new JLabel();
		lblChange.setForeground(Color.WHITE);
		lblChange.setBackground(Color.DARK_GRAY);
		lblChange.setBorder(border);
		lblChange.setFont(new Font("Courier", Font.PLAIN, 13));
		lblChange.setBounds(45, 440, 96, 25);
		contentPane.add(lblChange);

		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("./fr.univcotedazur.polytech.si4.fsm.2020.project/picts/vide2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		labelForPictures = new JLabel(new ImageIcon(myPicture));
		labelForPictures.setBounds(175, 319, 286, 260);
		contentPane.add(labelForPictures);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.DARK_GRAY);
		panel_2.setBounds(538, 217, 96, 33);
		contentPane.add(panel_2);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setForeground(Color.WHITE);
		cancelButton.setBackground(Color.DARK_GRAY);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				theFSM.raiseCancel();
			}
		});
		panel_2.add(cancelButton);

		// listeners
		addCupButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!ownCup) {
					BufferedImage myPicture = null;
					try {
						myPicture = ImageIO.read(new File("./fr.univcotedazur.polytech.si4.fsm.2020.project/picts/ownCup.jpg"));
					} catch (IOException ee) {
						ee.printStackTrace();
					}
					labelForPictures.setIcon(new ImageIcon(myPicture));
					ownCup = true;
					price -= 10;
					displayValue();
				}
                else{
                    BufferedImage myPicture = null;
                    try {
                        myPicture = ImageIO.read(new File("./fr.univcotedazur.polytech.si4.fsm.2020.project/picts/vide2.jpg"));
                    } catch (IOException ee) {
                        ee.printStackTrace();
                    }
                    labelForPictures.setIcon(new ImageIcon(myPicture));
                    addCupButton.setText("Add cup");
                    ownCup = false;
                    price += 10;
                    displayValue();
                }
			}
		});

	}
}
