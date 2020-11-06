package fr.univcotedazur.polytech.si4.fsm.project;

public class Expresso extends Recipe{

	public Expresso(int sugar, int size, int temperature) {
		super(sugar, size, temperature);
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
		System.out.println("Ajout du sucre");
	}

	@Override
	void PouringWater() {
		// TODO Auto-generated method stub
		System.out.println("Versement de l'expresso");
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
