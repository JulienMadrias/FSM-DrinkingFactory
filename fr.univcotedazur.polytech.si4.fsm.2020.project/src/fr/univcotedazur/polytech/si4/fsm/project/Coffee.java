package fr.univcotedazur.polytech.si4.fsm.project;

public class Coffee extends Recipe{

	
	public Coffee(int sugar, int size, int temperature) {
		super(sugar, size, temperature);
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
		System.out.println("Positionnement du gobelet");
	}

	@Override
	void WaitHeatedWater() {
		// TODO Auto-generated method stub
		System.out.println("Fin du chauffage de l'eau");
	}

	@Override
	void PutSugar() {
		// TODO Auto-generated method stub
		System.out.println("Ajout du sucre");
	}

	@Override
	void PouringWater() {
		// TODO Auto-generated method stub
		System.out.println("Versement de café");
	}

	@Override
	void PrepSupp() {
		// TODO Auto-generated method stub
	}

	@Override
	void time() {
		// TODO Auto-generated method stub
		time1 = (int) temperature * 2000;
		time2 = (int) temperature * 2000;
		time3 = (int) sugar * 250 + size * 2500 + 7500 ;
		time4 = 0;
		time5 = 5000;
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
