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
	public void onDoPrepRaised() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onDoWashRaised() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDoStartMachineRaised() {
		// TODO Auto-generated method stub
		theDFM.startMachine();
	}

	@Override
	public void onDoPouringRaised() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDoWaitRecupRaised() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDoPrepSuppRaised() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDoHotDrinkRaised() {
		// TODO Auto-generated method stub
		theDFM.drinkSelected();
	}

}
