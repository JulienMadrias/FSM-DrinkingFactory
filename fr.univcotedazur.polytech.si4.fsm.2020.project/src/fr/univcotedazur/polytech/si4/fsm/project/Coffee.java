package fr.univcotedazur.polytech.si4.fsm.project;

public class Coffee extends Recipe{

	
	public Coffee(int sugar, int size, int temperature, boolean ownCup, boolean milk, boolean maple, boolean vanilla) {
		super(sugar, size, temperature, ownCup, milk, maple, vanilla);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	void TakeIngredient() {
		// TODO Auto-generated method stub
		System.out.println("Récupération de la dosette");
		System.out.println("Positionnement de la dosette");
	}

	@Override
	void StartHeatedWater() {
		// TODO Auto-generated method stub
		System.out.println("Démarrage du chauffage de l'eau");
	}

	@Override
	void PrepPouring() {
		// TODO Auto-generated method stub
		if(!ownCup) {
			System.out.println("Positionnement du gobelet");
		}
	}

	@Override
	void WaitHeatedWater() {
		// TODO Auto-generated method stub
		System.out.println("Fin du chauffage de l'eau");
	}

	@Override
	void PutSugar() {
		// TODO Auto-generated method stub
		if(maple == true) {
			System.out.println("Ajout du sirop d'érable");
		}else {
			System.out.println("Ajout du sucre");
		}
		
	}

	@Override
	void PouringWater() {
		// TODO Auto-generated method stub
		System.out.println("Versement de café");
	}

	@Override
	void PrepSupp() {
		// TODO Auto-generated method stub
		if(vanilla == true) {
			System.out.println("Ajout de la glace vanille");
			System.out.println("Mixage en cours...");
		}
		if(milk == true) {
			System.out.println("Ajout du nuage de lait");
		}
	}

	@Override
	void time() {
		// TODO Auto-generated method stub
		time1 = (int) temperature * 2000;
		time2 = (int) temperature * 2000;
		time3 = (int) sugar * 250 + size * 2500 + 7500 ;
		if(vanilla == true && milk == true) {
			time4 = (int) 12000 + 3000;
		}else if(vanilla == true) {
			time4 = (int) 12000;
		}else if(milk == true) {
			time4 = (int) 3000;
		}else {
			time4 = 0;
		}
		time5 = 5000;
		totalTime = time1 + time2 + time3 + time4;
	}


	@Override
	void WaitRecup() {
		// TODO Auto-generated method stub
		System.out.println("Récupérer votre boisson");
	}


	@Override
	void WashingMashine() {
		// TODO Auto-generated method stub
		System.out.println("Lavage des composants de la machine");
	}
	
	

}
