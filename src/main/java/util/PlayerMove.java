package util;

import board.Move;
import database.DBDocument;
import game.Player;
import org.bson.Document;

public class PlayerMove implements DBDocument {
    private final int playerId;
    private final int points;
    private final Move move;

    public PlayerMove(int playerId, int points, Move move) {
        this.playerId = playerId;
        this.points = points;
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    public int getPlayerId() {
        return playerId;
    }

    @Override
    public Document toDocument() {
        return new Document("playerid", playerId)
                .append("points", points)
                .append("from", move.getFrom().toDocument())
                .append("to", move.getTo().toDocument());
    }
}