package board;

import board.drawable.pawn.Pawn;
import board.drawable.tile.Tile;
import board.views.BoardView;

import java.util.*;

public class Board {
    private HashMap<HexVector, Tile> tiles = new HashMap<>();
    private HashMap<HexVector, Pawn> pawns = new HashMap<>();
    private BoardView view;
    private int size;

    public Board(BoardView view, int size) {
        this.view = view;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public HashMap<HexVector, Tile> getTilesCopy() {
        return new HashMap<>(tiles);
    }

    public HashMap<HexVector, Pawn> getPawnsCopy() {
        return new HashMap<>(pawns);
    }

    public void addPawn(Pawn pawn, HexVector position) {
        pawns.put(position, pawn);
        view.drawPawn(pawn, position);
    }

    public Collection<HexVector> getTilePositions() {
        return tiles.keySet();
    }

    public int getNumOfPawns() {
        return pawns.size();
    }

    boolean hasPawnAt(HexVector position) {
        return pawns.containsKey(position);
    }

    public ArrayList<HexVector> getPawnsPositions(int id) {
        ArrayList<HexVector> result = new ArrayList<>();
        for(HexVector position : pawns.keySet()) {
            Pawn pawn = pawns.get(position);
            if(pawn.getId() == id)
                result.add(position);
        }
        return result;
    }

    public boolean hasTileAt(HexVector position) {
        return tiles.containsKey(position);
    }

    public Tile getTileAt(HexVector position) {
        return tiles.get(position);
    }

    public boolean addTile(Tile tile, HexVector position) {
        if(hasTileAt(position))
            return false;

        tiles.put(position,tile);
        view.drawTile(tile,position);
        return true;
    }

    public void removeTile(HexVector position) {
        tiles.remove(position);
        view.removeTile(position);
    }

    private void removePawn(HexVector position) {
        pawns.remove(position);
        view.removePawn(position);
    }

    public void movePawn(Move move) {
        int id = pawns.get(move.getFrom()).getId();
        removePawn(move.getFrom());
        Pawn newPawn = new Pawn(id);
        addPawn(newPawn, move.getTo());
        view.playMoveTransition(newPawn, move);
    }

    public void switchPawnSelection(HexVector position, boolean highlightPossibleMoves) {
        view.switchPawnSelection(position);
        if (highlightPossibleMoves) {
            StandardMoveChecker moveChecker = new StandardMoveChecker(this);
            for(Move move : moveChecker.getPossibleMoves(position))
                view.switchTileGlow(move.getTo());
        }
    }

    public void clear() {
        for(HexVector position : tiles.keySet())
            view.removeTile(position);
        for(HexVector position : pawns.keySet())
            view.removePawn(position);
        tiles.clear();
        pawns.clear();
    }
}
