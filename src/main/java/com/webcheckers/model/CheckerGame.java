package com.webcheckers.model;

import com.webcheckers.model.Board.*;
import com.webcheckers.model.Board.Enums.Color;
import com.webcheckers.model.Board.Enums.Type;
import com.webcheckers.model.Board.Piece;
import com.webcheckers.model.Board.Row;
import com.webcheckers.model.Move;

import java.util.*;


import java.util.ArrayList;

import com.webcheckers.model.Board.BoardView;

/*
 * This class contains the board and will play the game. It takes two Players
 * and creates a CheckersGame for them to play on
 *
 */
public class CheckerGame
{
    public enum GameWinner{
        red, white, ingame;
    }

    protected BoardView board;
    protected Player redPlayer;
    protected Player whitePlayer;
    protected Player currentPlayer;
    protected int numWhite;
    protected int numRed;
    protected Color activeColor;
    protected GameWinner winner;
    protected ArrayList<ArrayList<Move>> allMoves;
    protected ArrayList<ArrayList<Piece>> piecesTaken;
    protected ArrayList<Piece> pieceMoved;
    public MoveValidator validator;
    private Boolean multiMove;
    private ArrayList<Position> cantReturn = new ArrayList<>();
    private Boolean isKing;
    private int movectr;
    public boolean piecesBlocked = false;



    /**
     * Holds all moves made from current player's turn.
     */
    protected Stack<Move> moves = new Stack<>();

    /**
     * Constructor for the CheckerGame class
     */
    public CheckerGame(Player redPlayer, Player whitePlayer)
    {
        board = new BoardView();
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.currentPlayer = redPlayer;
        this.multiMove = false;
        activeColor = Color.RED;
        this.winner = GameWinner.ingame;
        numWhite =12;
        numRed  =12;
        validator = new MoveValidator(board, true);
        this.allMoves = new ArrayList<ArrayList<Move>>();
        this.piecesTaken = new ArrayList<ArrayList<Piece>>();
        this.pieceMoved = new ArrayList<Piece>();
        this.movectr = 0;
    }

    public CheckerGame(Player redPlayer, Player whitePlayer, ArrayList<ArrayList<Move>> allMoves, ArrayList<ArrayList<Piece>> piecesTaken, ArrayList<Piece> pieceMoved) {
        board = new BoardView();
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.currentPlayer = redPlayer;
        this.multiMove = false;
        activeColor = Color.RED;
        this.winner = GameWinner.ingame;
        numWhite = 12;
        numRed = 12;
        validator = new MoveValidator(board, true);
        this.allMoves = allMoves;
        this.piecesTaken = piecesTaken;
        this.pieceMoved = pieceMoved;
        this.movectr = 0;
    }
    
    /**
     * Returns the board of the CheckerGame
     *
     * @return The board of the CheckerGame
     */
    public BoardView getBoard() {
        return board;
    }
    /**
     * Gets RedPlayer's view
     * @return redPlayer's view
     */
    public BoardView getRedBoard() {
        return board.getRedPlayerView();
    }

    /**
     * Gets WhitePlayer's view
     * @return whitePlayer's view
     */
    public BoardView getWhiteBoard() {
        return board.getWhitePlayerView();
    }

    /**
     * get list of all moves
     * @return ArrayList<Move>
     */
    public ArrayList<ArrayList<Move>> getAllMoves() {
        return allMoves;
    }

    /**
     * get list of pieces that have been captured
     * @return ArrayList<ArrayList<Piece>>
     */
    public ArrayList<ArrayList<Piece>> getPiecesTaken() {
        return piecesTaken;
    }

    /**
     * get list of pieces moved
     * @return ArrayList<Piece>
     */
    public ArrayList<Piece> getPieceMoved() {
        return pieceMoved;
    }

    /**
     * return move counter
     * @return int movectr
     */
    public int getMoveCtr() {
        return movectr;
    }
    
    /**
     * Check if its the players turn
     * @param player that is playing
     * @return turn
     */
    public boolean isMyTurn(Player player) {
        return (currentPlayer == player);
    }

    /**
     * Get which color's turn it is
     * @return Color
     */
    public Color getActiveColor()
    {
        return activeColor;
    }

    /**
     * Gets redPlayer
     * @return redPlayer
     */
    public Player getRedPlayer(){
        return redPlayer;
    }

    /**
     * Gets whitePlayer
     * @return whitePlayer
     */
    public Player getWhitePlayer(){
        return whitePlayer;
    }

    /**
     * gets opponent of the given player
     * @param pl given player
     * @return the opposing player
     */
    public Player getOpponent(Player pl) {
        if (pl == this.redPlayer) {
            return this.whitePlayer;
        } else if (pl == this.whitePlayer) {
            return this.redPlayer;
        } else {
            return null;
        }
    }
    /**
     * Checks if White player has remaining pieces
     * @return white has pieces or not
     */
    public Boolean whiteHasPieces(){
       return numWhite > 0;
    }

    /**
     * Checks if Red player has remaining pieces
     * @return red has pieces or not
     */
    public Boolean redHasPieces(){
        return numRed>0;
    }

    /**
     * sets number of white pieces left
     * @param number
     */
    public void setNumWhite(int number){
        numWhite=number;
    }

    /**
     * sets number of red pieces left
     */
    public void setNumRed(int number){
        numRed=number;
    }

    /**
     * if a player has no pieces, they lose
     */
    public void playerNoPiecesLose(){
        if (!redHasPieces()){
            winner=GameWinner.white;
        }
        else if (!whiteHasPieces()){
            winner=GameWinner.red;
        }
    }

    /**
     * player resigned, other player wins
     * @param pl
     */
    public void playerResigned(Player pl){
        if(pl == this.redPlayer){
            winner = GameWinner.white;
        }
        else if(pl == this.whitePlayer){
            winner = GameWinner.red;
        }
        if(currentPlayer == pl){
            switchCurrentTurn();
        }
    }

    /**
     * gets the GameWinner status
     * @return
     */
    public GameWinner getWinner() {
        return winner;
    }

    /**
     * sets the status of the GameWinner
     * @param win
     */
    public void setWinner(GameWinner win){
        this.winner = win;
    }

    /**
     * adds a move to the stack of moves
     * @param newMove
     */
    public void pushMove(Move newMove)
    {
        if (currentPlayer == whitePlayer)
            newMove.flipMove();

        moves.push(newMove);
    }

    /**
     * returns the stack
     * @return the stack of moves
     */
    public Stack<Move> getMoveStack(){
        return moves;
    }

    /**
     * if the current player still has moves left
     * @return boolean
     */
    public boolean playerHasMoves()
    {
        boolean hasMoves;
        //initial check for valid moves on a turn start
        if(moves.isEmpty()) {
            hasMoves = validator.genAllPossibleMoves();
            if(!hasMoves)   //player's pieces are blocked they must resign
                piecesBlocked = true;
        }
        //only gets called if a jump move was made to check if another one available
        else {
            Move move = moves.peek();
            if(move.isJump()) {
                cantReturn.add(move.getEnd());
                System.out.println(cantReturn);
                if (!multiMove){
                    isKing = board.getBoard().get(move.getStart().getRow()).getSpace(move.getStart().getCell()).getPiece().getType() == Type.KING;
                    multiMove = true;
                }
                hasMoves = validator.getPossibleJumpsOnPosition(move.getEnd(), isKing, cantReturn);

            }
            else
                hasMoves = false;
        }

        validator.printSet();
        return hasMoves;
    }

    /**
     * gets all the possible moves for current state of board
     * @return HashSet
     */
    public HashSet<Move> getPossibleMoves()
    {
        return validator.getPossibleMoves();
    }

    /**
     * if there is a jump move possible
     * @return boolean
     */
    public boolean jumpMoveAvailable()
    {
        return validator.jumpMoveAvailable();
    }

    /**
     * changes what players turn it is
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
    }

    /**
     * Submits all moves, to take a turn
     * @return
     */
    public Message submitTurn()
    {
        if (playerHasMoves()) {
            return new Message("Can't submit turn while jump still available",
                    MessageType.error);
        }

        Position finalPos;
        Position initialPos;
        try{
            finalPos = moves.peek().getEnd();
            initialPos = moves.peek().getStart();
        }
        catch(Exception e){
            if(currentPlayer == redPlayer){
                setWinner(GameWinner.white);
            }
            else{
                setWinner(GameWinner.red);
            }
            return new Message("You Win!", MessageType.info);
        }

        ArrayList<Move> turn = new ArrayList<Move>();
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        while(!moves.empty())
        {
            Move curMove = moves.pop();
            turn.add(0, curMove);
            if(curMove.isJump())
            {
                Position jumpedPos = curMove.getOpponentPiece();
                System.out.println("Opponent Piece" + jumpedPos);
                pieces.add(0,board.removeTakenPiece(jumpedPos));
                if(activeColor == Color.RED){
                    numWhite--;
                }
                else{
                    numRed--;
                }
                playerNoPiecesLose();
            }

            initialPos = curMove.getStart();
        }
        Move total = new Move(initialPos, finalPos);
        Piece curPiece = board.getSpace(initialPos).getPiece();
        Piece newPiece = new Piece(curPiece.getType(), curPiece.getColor());
        board.movePiece(total);
        allMoves.add(turn);
        piecesTaken.add(pieces);
        pieceMoved.add(newPiece);
        multiMove = false;
        cantReturn = new ArrayList<>();
        switchCurrentTurn();
        return new Message("All moves submitted", MessageType.info);
    }

    /**
     * gets Message for the nextTurn
     * @return Message
     */
    public Message nextTurn() {
        Position finalPos;
        Position initialPos;
        
        ArrayList<Move> turn = allMoves.get(movectr);
        initialPos = turn.get(0).getStart();
        finalPos = turn.get(0).getEnd();
        
        for (int i = 0; i < turn.size(); i++) {
            Move curMove = turn.get(i);
            if (curMove.isJump()) {
                Position jumpedPos = curMove.getOpponentPiece();
                board.removeTakenPiece(jumpedPos);
                if (activeColor == Color.RED) {
                    numWhite--;
                }
                else {
                    numRed--;
                }
            }
            finalPos = curMove.getEnd();
        }
        Move total = new Move(initialPos, finalPos);
        System.out.println(total);
        board.movePiece(total);
        switchCurrentTurn();
        movectr++;
        return new Message("true", MessageType.info);
    }

    /**
     * returns a message for the previous turn
     * @return Message
     */
    public Message previousTurn() {
        Position finalPos;
        Position initialPos;

        ArrayList<Move> turn = allMoves.get(movectr-1);
        ArrayList<Piece> pieces = piecesTaken.get(movectr-1);
        Piece prevPiece = pieceMoved.get(movectr-1);
        int last = turn.size()-1;
        initialPos = turn.get(last).getEnd();
        finalPos = turn.get(last).getStart();

        for (int i = last; i >= 0; i--) {
            Move curMove = turn.get(i);
            if (curMove.isJump()) {
                Position jumpedPos = curMove.getOpponentPiece();
                board.placeNewPiece(jumpedPos, pieces.get(i));
            }
            finalPos = curMove.getStart();
        }
        Move total = new Move(initialPos, finalPos);
        if (board.getSpace(initialPos).getPiece().getType() == Type.KING) {
            if (pieceMoved.get(movectr-1).getType() == Type.SINGLE) {
                board.getSpace(initialPos).getPiece().setSingle();
            }
        }
        board.movePiece(total);
        
        switchCurrentTurn();
        movectr--;
        return new Message("true", MessageType.info);
    }
}
