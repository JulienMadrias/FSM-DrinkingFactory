package fr.univcotedazur.polytech.si4.fsm.project;

public class Tea extends Recipe {
	
	boolean prep1 = false;
	boolean prep2 = false;
	int step1;
	int step2;
	int step3;
	int step4;
	int step5;
	int totalTime;

	public Tea(int sugar, int size, int temperature, DrinkFactoryMachine theFSM) {
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
		System.out.println("Positionnement du gobelet");
		prep1 = true;
		NextStep(prep1,prep2);
	}

	@Override
	void WaitHeatedWater() {
		// TODO Auto-generated method stub
		System.out.println("Fin du chauffage de l'eau");
		prep2 = true;
		NextStep(prep1,prep2);
	}

	@Override
	void PutSugar() {
		// TODO Auto-generated method stub
		System.out.println("Ajout du sucre");
		prep1 = true;
		NextStep(prep1,prep2);
	}

	@Override
	void PouringWater() {
		// TODO Auto-generated method stub
		System.out.println("Versement de café");
		prep2 = true;
		NextStep(prep1,prep2);
	}

	@Override
	void PrepSupp() {
		// TODO Auto-generated method stub
		System.out.println("infusion du thé");
		System.out.println("retrait du sachet");
		theFSM.theFSM.raisePrepSupp();
	}

	@Override
	void NextStep(boolean prep1, boolean prep2) {
		// TODO Auto-generated method stub
		if(prep1 & prep2) {
			theFSM.theFSM.raiseStartPrep();
			theFSM.theFSM.raiseStartServ();
			theFSM.theFSM.raiseDeliver();
			this.prep1 = false;
			this.prep2 = false;
		}
	}

	@Override
	void time(int size, int sugar, int temperature) {
		// TODO Auto-generated method stub
		step1 = (int) temperature * 1000;
		step2 = (int) size * 1000;
		step3 = (int) sugar * 500 + size * 1500;
		step4 = (int) size * 5000;
		step5 = (int) 15000;
		totalTime = step1 + step2 + step3 + step4 + step5;
	}

}
