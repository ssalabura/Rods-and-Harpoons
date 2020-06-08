package board;

import board.drawable.pawn.Pawn;
import board.drawable.tile.Tile;
import java.util.function.Consumer;

public interface BoardView {
    void drawTile(Tile tile, HexVector position);
    void drawPawn(Pawn pawn, HexVector position);
    void removeTile(HexVector position);
    void removePawn(HexVector position);

    void switchPawnSelection(HexVector position);
    void switchTileGlow(HexVector position);

    void setActionOnClickForExistingTiles(Consumer<HexVector> onMouseClick);
}
