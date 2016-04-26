package lab3.chat;

/**
 * Created by dat12jkr on 12/04/16.
 */
public class MailBox {
    private String message;
    

    public synchronized void setMessage(String m) {
        while (message != null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.message = m;
        notifyAll();
    }

    public synchronized String getMessage() {
        while (message == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String temp = message;
        message = null;
        notifyAll();
        return temp;
    }




}

