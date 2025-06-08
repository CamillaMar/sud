package sud.game;

import sud.exceptions.EndOfGameException;

import java.io.Console;

import static sud.game.GameUtil.*;

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
