package fr.univcotedazur.polytech.si4.fsm.project;

public class Coffee extends Recipe{
	
	boolean prep1 = false;
	boolean prep2 = false;
	
	
	public Coffee(int sugar, int size, int temperature, DrinkFactoryMachine theFSM) {
		super(sugar, size, temperature, theFSM);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	void TakeIngredient() {
		// TODO Auto-generated method stub
		System.out.println("Récupération de la dosette");
		System.out.println("Positionnement de la dosette");
		prep1 = true;
		NextStep(prep1,prep2);
	}

	@Override
	void StartHeatedWater() {
		// TODO Auto-generated method stub
		System.out.println("Démarrage du chauffage de l'eau");
		prep2 = true;
		NextStep(prep1,prep2);
	}

	@Override
	void PrepPouring() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void WaitHeatedWater() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void PutSugar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void PouringWater() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void PrepSupp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void NextStep(boolean prep1, boolean prep2) {
		// TODO Auto-generated method stub
		if(prep1 & prep2) {
			theFSM.theFSM.raiseStartPrep();
			theFSM.theFSM.raiseStartServ();
			theFSM.theFSM.raisePrepSupp();
			theFSM.theFSM.raiseDeliver();
			this.prep1 = false;
			this.prep2 = false;
		}
	}
	
	

}
