package sud;

import java.io.Console;

import static sud.GameUtil.*;

public class GameMap {
    public static Console console = System.console();
    public void start(){
        try{
            createNewPlayer();
            while(true){
                try{
                    askWhatToDoWithoutMenu();
                }catch(EndOfGameException e){
                    break;
                }
                randomizeTheGuards();
            }
        } catch (EndOfGameException e) {
            System.out.println("End of Game");
        }

    }
}
