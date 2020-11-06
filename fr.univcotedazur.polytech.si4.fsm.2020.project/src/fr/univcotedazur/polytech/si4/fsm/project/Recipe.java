package fr.univcotedazur.polytech.si4.fsm.project;

public abstract class Recipe {
	int sugar;
	int size;
	int temperature;
	DrinkFactoryMachine theFSM;
	
	public Recipe(int sugar, int size, int temperature, DrinkFactoryMachine theFSM) {
		this.size = size;
		this.sugar = sugar;
		this.temperature = temperature;
		this.theFSM = theFSM;
	}
	
	abstract void TakeIngredient();
	abstract void StartHeatedWater();
	abstract void PrepPouring();
	abstract void WaitHeatedWater();
	abstract void PutSugar();
	abstract void PouringWater();
	abstract void PrepSupp();
	abstract void NextStep(boolean prep1, boolean prep2);
	abstract void time(int size, int sugar, int temperature);
}
