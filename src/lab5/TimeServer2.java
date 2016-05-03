package lab5;

import java.io.IOException;

/**
 * Created by dat12jkr on 03/05/16.
 */
public class TimeServer2 {

    public static void main(String[] args) {
        TimeServer1 ts = new TimeServer1();
        int inChar;
        while(true){
            try {
                inChar = System.in.read();
                System.out.println(ts.handleCommand(Character.toChars(inChar)[0]));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
