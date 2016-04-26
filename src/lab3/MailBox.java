package lab3;

/**
 * Created by dat12jkr on 12/04/16.
 */
public class MailBox {
    private String message;

    public synchronized void setMessage(String m) {
        while(message != null){
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

    static class Deposit extends Thread {
        private MailBox mb;

        public Deposit(String name, MailBox mb) {
            super(name);
            this.mb = mb;
        }

        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep((long) (Math.random() * 100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mb.setMessage(super.getName());
            }

        }
    }

    static class Clearer extends Thread {
        private MailBox mb;

        public Clearer(String name, MailBox mb) {
            super(name);
            this.mb = mb;
        }

        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep((long) (Math.random() * 100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(mb.getMessage());

            }

        }
    }

    public static void main(String[] args) {
        MailBox mb = new MailBox();
        Deposit d = new Deposit("Depositer", mb);
        Clearer c = new Clearer("Clearer", mb);
        d.start();
        c.start();
        try {
            d.join();
            c.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

