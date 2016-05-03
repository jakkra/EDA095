package lab5;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dat12jkr on 03/05/16.
 */
public class TimeServer1 {
    public DateFormat dfTime;
    public DateFormat dfDate;

    public TimeServer1(){
        this.dfTime = new SimpleDateFormat("hh:mm:ss");
        this.dfDate = DateFormat.getDateInstance(DateFormat.SHORT, Locale.CHINESE);

        //receive(client, command, parameters);

        //send(client, handleCommand());
        
    }
    
    public String handleCommand(char command){
        String result = "";
        switch (command) {
            case 'd':
                result = dfDate.format(new Date());
                break;
            case 't':
                result = dfTime.format(new Date());
                break;
            default:
        }
        return result;

    }
}
