package fr.univcotedazur.polytech.si4.fsm.project;

public class Soup extends Recipe {

    public Soup(int spices, int size, int temperature, boolean ownCup, boolean croutons) {
        super(spices, size, temperature, ownCup, croutons);
        // TODO Auto-generated constructor stub
    }

    @Override
    void TakeIngredient() {
        System.out.println("Récupération de la dosette");
        System.out.println("Positionnement de la dosette");
    }

    @Override
    void StartHeatedWater() {
        System.out.println("Démarrage du chauffage de l'eau");
    }

    @Override
    void PrepPouring() {
        if(!ownCup) {
            System.out.println("Positionnement du gobelet");
        }
    }

    @Override
    void WaitHeatedWater() {
        System.out.println("Fin du chauffage de l'eau");
    }

    @Override
    void PutSugar() {
        System.out.println("Ajout des épices");
    }

    @Override
    void PouringWater() {
        System.out.println("Versement de la soupe");
    }

    @Override
    void PrepSupp() {
        if(crouton == true) {
            System.out.println("Ajout des croutons");
        }
    }

    @Override
    void WaitRecup() {
        System.out.println("Récupérer votre boisson");
    }

    @Override
    void WashingMashine() {
        System.out.println("Lavage des composants de la machine");
    }

    @Override
    void time() {
        time1 = (int) temperature * 2000;
        time2 = (int) temperature * 2000;
        time3 = (int) spices * 250 + size * 2500 + 7500 ;
        if(crouton == true) {
            time4 = (int) 2000;
        }else {
            time4 = 0;
        }
        time5 = 5000;
        totalTime = time1 + time2 + time3 + time4;
    }
}
