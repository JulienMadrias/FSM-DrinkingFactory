package fr.univcotedazur.polytech.si4.fsm.project;

public class Expresso extends Recipe{

	public Expresso(int sugar, int size, int temperature, boolean milk, boolean maple, boolean vanilla) {
		super(sugar, size, temperature, milk, maple, vanilla);
		// TODO Auto-generated constructor stub
	}

	@Override
	void TakeIngredient() {
		// TODO Auto-generated method stub
		System.out.println("broyage des grains");
	}

	@Override
	void StartHeatedWater() {
		// TODO Auto-generated method stub
		System.out.println("Démarrage du chauffage de l'eau");
	}

	@Override
	void PrepPouring() {
		// TODO Auto-generated method stub
		System.out.println("Positionnement du gobelet");
		System.out.println("Tassage du café");
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
		System.out.println("Versement de l'expresso");
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
		time5 = 10000;
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
