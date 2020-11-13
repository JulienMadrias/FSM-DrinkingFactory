package fr.univcotedazur.polytech.si4.fsm.project;

import java.util.ArrayList;

public class Customer {

    private int numberOfCommand;
    private ArrayList<Integer> listOfPrices;
    private int discount;

    protected Customer(int numberOfCommand){
        this.numberOfCommand = numberOfCommand;
        this.listOfPrices = new ArrayList<Integer>();
        this.discount = 0;
    }

    protected Customer(int numberOfCommand, ArrayList<Integer> listOfPrices, int discount){
        this.numberOfCommand = numberOfCommand;
        this.listOfPrices = listOfPrices;
        this.discount = discount;
    }

    protected void setDiscount(){
        if(numberOfCommand == 10){
            int sum = 0;
            for (int i=0; i<10; i++) {
                sum += listOfPrices.get(i);
            }
            this.discount = sum/10;
        }
    }

    protected int getDiscount(){
        return this.discount;
    }

    protected ArrayList<Integer> getListOfPrices(){
        return this.listOfPrices;
    }

    protected int getNumberOfCommand(){
        return this.numberOfCommand;
    }

    protected void addToList(int newPrice){
        if(numberOfCommand<10){
            listOfPrices.add(newPrice);
            numberOfCommand+=1;
        }
        if(numberOfCommand == 10){setDiscount();}
    }

    protected boolean useDiscount(int price){
        if(numberOfCommand == 10 && price <= this.discount){
            numberOfCommand = 0;
            discount = 0;
            listOfPrices.clear();
            return true;
        }
        return false;
    }
}
