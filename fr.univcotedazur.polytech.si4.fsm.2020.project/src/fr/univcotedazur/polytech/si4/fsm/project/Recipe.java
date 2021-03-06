package fr.univcotedazur.polytech.si4.fsm.project;

public abstract class Recipe {
	int sugar;
	int spices;
	int size;
	int temperature;
	boolean milk = false;
	boolean crouton = false;
	boolean maple = false;
	boolean vanilla = false;
	DrinkFactoryMachine theFSM;
	int time1;
	int time2;
	int time3;
	int time4;
	int time5;
	int totalTime;
	boolean ownCup;
	
	public Recipe(int sugar, int size, int temperature, boolean ownCup, boolean milk, boolean crouton, boolean maple, boolean vanilla) {
		this.size = size;
		this.sugar = sugar;
		this.temperature = temperature;
		this.ownCup = ownCup;
		this.milk = milk;
		this.crouton = crouton;
		this.maple = maple;
		this.vanilla = vanilla;
	}
	
	public Recipe(int sugar, int size, int temperature, boolean ownCup, boolean milk, boolean maple,  boolean vanilla) {
		this.size = size;
		this.sugar = sugar;
		this.temperature = temperature;
		this.ownCup = ownCup;
		this.milk = milk;
		this.vanilla = vanilla;
		this.maple = maple;
	}
	
	public Recipe(int sugar, int size, int temperature, boolean ownCup, boolean milk, boolean maple) {
		this.size = size;
		this.sugar = sugar;
		this.temperature = temperature;
		this.ownCup = ownCup;
		this.milk = milk;
		this.maple = maple;
	}

	public Recipe(int spices, int size, int temperature, boolean ownCup, boolean croutons) {
		this.size = size;
		this.spices = spices;
		this.temperature = temperature;
		this.ownCup = ownCup;
		this.crouton = croutons;
	}
	
	abstract void TakeIngredient();
	abstract void StartHeatedWater();
	abstract void PrepPouring();
	abstract void WaitHeatedWater();
	abstract void PutSugar();
	abstract void PouringWater();
	abstract void PrepSupp();
	abstract void WaitRecup();
	abstract void WashingMashine();
	abstract void time();
}
