package com.webcheckers.model;

import java.util.HashSet;
import com.webcheckers.model.Board.Enums.*;

/**
 * A modified CheckerGame, which handles the case when a 
 * player plays against a bot
 */
public class BotGame extends CheckerGame {

    public BotGame(Player pl1, Player pl2){
        super(pl1, pl2);
        if(pl1.isBot())
            botMove();

    }

    /**
     * Randomly takes a move available
     * Takes repeated jump moves if available
     */
    public void botMove(){
        boolean moved = false;
        while(playerHasMoves()){
            moved = true;
            HashSet<Move> poss = getPossibleMoves();
            boolean movesAreJumps = jumpMoveAvailable();
            Object[] possibleMoves = poss.toArray();
            Move m;
            do{
                int x = possibleMoves.length;
                x = (int)(Math.random() * x);
                m = (Move)(possibleMoves[x]);
            }while(m.validateMove(poss, movesAreJumps).getType() != MessageType.info);
            pushMove(m);
        }
        if(moved){
            submitTurn();
        }
        else{
            if(currentPlayer == redPlayer){
                setWinner(GameWinner.white);
            }
            else{
                setWinner(GameWinner.red);
            }
        }
        
    }

    /**
     * modified switch turn, which calls the botMove() function when the player
     * is done.
     */
    public void switchCurrentTurn()
    {
        if(currentPlayer == redPlayer) {
            currentPlayer = whitePlayer;
            activeColor = Color.WHITE;
        }
        else {
            currentPlayer = redPlayer;
            activeColor = Color.RED;
        }
        validator.switchActivePlayer();
        if(currentPlayer.isBot()){
            botMove();
        }
    }

}