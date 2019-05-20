package model;
import java.awt.*;
import java.text.DateFormat;

import java.util.Date;

public class Zegar implements Runnable, Obserwowany{
    public String aktualnyCzas="0";

    Obserwator obserwator;

    public void subscribe(Obserwator o) {
        obserwator = o;
    }

    public void unsubscribe(Obserwator o) {
        obserwator = null;
    }

    public void inform_All() {
    }

    public void uruchom(){
        Thread watek = new Thread(this);
        if(!watek.isAlive()){
            watek.start();
        }

    }
    public void run(){
        for(;;){
            Date czas = new Date();
            DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.MEDIUM);
            aktualnyCzas = dateFormat.format(czas);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
            obserwator.inform();
        }

    }
}
