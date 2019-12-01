package count_commas;

import java.util.concurrent.atomic.AtomicInteger;

public class CommaCounter extends Thread {

    protected AtomicInteger commas = new AtomicInteger(0);
    private String text;
    private int total;

    public CommaCounter(String text){
        this.text = text;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public void run() {
        for (int i = 0; i < text.length(); i++) {
            if(text.charAt(i) == ','){
                synchronized (commas) {
                    commas.getAndIncrement();
                }
            }
        }
        this.total = commas.intValue();
    }
}
