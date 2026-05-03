package myapp.restapi;

import myapp.MazeGameModel.*;

import java.util.List;

public class ApiBoardDTO {
    public int boardWidth;
    public int boardHeight;
    public ApiLocationDTO mouseLocation;
    public ApiLocationDTO cheeseLocation;
    public List<ApiLocationDTO> catLocations;
    public boolean[][] hasWalls;
    public boolean[][] isVisible;

    public ApiBoardDTO(MazeGame game) {
        boardWidth = game.getMazeSizeWidth();
        boardHeight = game.getMazeSizeHeight();
        mouseLocation = new ApiLocationDTO(game.getPlayerLocation());
        cheeseLocation = new ApiLocationDTO(game.getCheeseLocation());

        List<Cat> cats = game.getCats();
        catLocations = cats.stream()
                .map(cat -> {
                    ApiLocationDTO locationDTO = new ApiLocationDTO(cat.getLocation());
                    return locationDTO;
                })
                .toList();

        hasWalls = new boolean[boardHeight][boardWidth];
        isVisible = new boolean[boardHeight][boardWidth];

        Maze maze = game.getMaze();
        CellState[][] board = maze.getBoard();
        for(int i = 0; i < game.getMazeSizeHeight(); i++) {
            for(int j = 0; j < game.getMazeSizeWidth(); j++) {
                hasWalls[i][j] = board[i][j].isWall();
                isVisible[i][j] = board[i][j].isVisible();
            }
        }
    }

    // Accept whatever object(s) you need to populate this object.
    public static ApiBoardWrapper makeFromGame(MazeGame game) {
        ApiBoardWrapper wrapper = new ApiBoardWrapper();
        wrapper.boardWidth = game.getMazeSizeWidth(); // Fill this in, along with all the other fields.
        wrapper.boardHeight = game.getMazeSizeHeight();

        wrapper.mouseLocation = new ApiLocationDTO(game.getPlayerLocation());
        wrapper.cheeseLocation = new ApiLocationDTO(game.getCheeseLocation());

        List<Cat> cats = game.getCats();
        wrapper.catLocations = cats.stream()
                .map(cat -> {
                    ApiLocationDTO locationDTO = new ApiLocationDTO(cat.getLocation());
                    return locationDTO;
                })
                .toList();

        Maze maze = game.getMaze();
        CellState[][] board = maze.getBoard();
        for(int i = 0; i < game.getMazeSizeHeight(); i++) {
            for(int j = 0; j < game.getMazeSizeWidth(); j++) {
                wrapper.hasWalls[i][j] = board[i][j].isWall();
                wrapper.isVisible[i][j] = board[i][j].isVisible();
            }
        }

        return wrapper;
    }
}
