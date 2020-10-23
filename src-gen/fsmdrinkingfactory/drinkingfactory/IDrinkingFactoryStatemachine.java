/** Generated by YAKINDU Statechart Tools code generator. */
package fsmdrinkingfactory.drinkingfactory;

import fsmdrinkingfactory.IStatemachine;
import fsmdrinkingfactory.ITimerCallback;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public interface IDrinkingFactoryStatemachine extends ITimerCallback,IStatemachine {
	public interface SCInterface {
	
		public void raiseWelcome();
		
		public void raiseCancel();
		
		public void raiseSelectHotDrink();
		
		public void raiseSelectParam();
		
		public void raisePayCB();
		
		public void raiseAddCash();
		
		public void raiseValidate();
		
		public void raiseStartPrep();
		
		public void raiseStartServ();
		
		public void raiseDeliver();
		
		public void raiseAction();
		
		public void raiseFinishWash();
		
		public void raisePrepSupp();
		
		public boolean isRaisedDoCancel();
		
		public boolean isRaisedDoWelcome();
		
		public boolean isRaisedDoCB();
		
		public boolean isRaisedDoAddCash();
		
		public boolean isRaisedDoHotDrink();
		
		public boolean isRaisedDoStartMachine();
		
		public boolean isRaisedDoPrep();
		
		public boolean isRaisedDoPouring();
		
		public boolean isRaisedDoWaitRecup();
		
		public boolean isRaisedDoWash();
		
		public boolean isRaisedDoPrepSupp();
		
		public boolean isRaisedDoTakeIngr();
		
		public boolean isRaisedDoStartHeated();
		
		public boolean isRaisedDoPrepPouring();
		
		public boolean isRaisedDoWaitHeated();
		
		public boolean isRaisedDoSugar();
		
		public boolean isRaisedDoPouging();
		
	public List<SCInterfaceListener> getListeners();
	}
	
	public interface SCInterfaceListener {
	
		public void onDoCancelRaised();
		public void onDoWelcomeRaised();
		public void onDoCBRaised();
		public void onDoAddCashRaised();
		public void onDoHotDrinkRaised();
		public void onDoStartMachineRaised();
		public void onDoPrepRaised();
		public void onDoPouringRaised();
		public void onDoWaitRecupRaised();
		public void onDoWashRaised();
		public void onDoPrepSuppRaised();
		public void onDoTakeIngrRaised();
		public void onDoStartHeatedRaised();
		public void onDoPrepPouringRaised();
		public void onDoWaitHeatedRaised();
		public void onDoSugarRaised();
		public void onDoPougingRaised();
		}
	
	public SCInterface getSCInterface();
	
}
