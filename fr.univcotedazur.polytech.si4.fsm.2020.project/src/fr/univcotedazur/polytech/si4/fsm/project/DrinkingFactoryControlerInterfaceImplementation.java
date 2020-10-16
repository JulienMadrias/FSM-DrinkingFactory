package fr.univcotedazur.polytech.si4.fsm.project;

import fsmdrinkingfactory.drinkingfactory.IDrinkingFactoryStatemachine.SCInterfaceListener;

public class DrinkingFactoryControlerInterfaceImplementation implements SCInterfaceListener {
	
	DrinkFactoryMachine theDFM;
	public DrinkingFactoryControlerInterfaceImplementation(DrinkFactoryMachine sw) {
		theDFM = sw;
	}

	@Override
	public void onDoCancelRaised() {
		// TODO Auto-generated method stub
		theDFM.cancelOrder();
	}

	@Override
	public void onDoWelcomeRaised() {
		// TODO Auto-generated method stub
		theDFM.startSystem();
	}

	@Override
	public void onDoHotDrinkRaised() {
		// TODO Auto-generated method stub
		theDFM.hotDrinkSelected();
	}

	@Override
	public void onDoCBRaised() {
		// TODO Auto-generated method stub
		theDFM.payInCB();
	}

	@Override
	public void onDoAddCashRaised() {
		// TODO Auto-generated method stub
		theDFM.addCash();
	}

	@Override
	public void onDoValidRaised() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDoPrepRaised() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDoServRaised() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDoWaitRaised() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDoWashRaised() {
		// TODO Auto-generated method stub
		
	}

}
