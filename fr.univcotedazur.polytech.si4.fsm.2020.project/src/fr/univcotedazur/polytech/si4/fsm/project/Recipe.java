package fr.univcotedazur.polytech.si4.fsm.project;

public abstract class Recipe {
	int sugar;
	int size;
	int temperature;
	DrinkFactoryMachine theFSM;
	int time1;
	int time2;
	int time3;
	int time4;
	int time5;
	
	public Recipe(int sugar, int size, int temperature) {
		this.size = size;
		this.sugar = sugar;
		this.temperature = temperature;
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
