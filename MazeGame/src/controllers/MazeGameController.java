package myapp.controllers;

import myapp.MazeGameModel.CellState;
import myapp.MazeGameModel.Maze;
import myapp.MazeGameModel.MazeGame;
import myapp.MazeGameModel.MoveDirection;
import myapp.restapi.ApiBoardDTO;
import myapp.restapi.ApiGameDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MazeGameController {

    private List<MazeGame> games = new ArrayList<>();

//    @GetMapping("/hello")
//    public String getHelloMessage() {
//        return "Hello World, from Spring!";
//    }

    @GetMapping("/api/about")
    public String aboutMe() {
        return "Jessica Fang, 301559688";
    }

    /**
     *  Games Section
     */

    @PostMapping("/api/games")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiGameDTO createNewGame() {
        MazeGame game = new MazeGame();
        games.add(game);
        int gameNumber = games.size() - 1;

        return ApiGameDTO.makeFromGame(gameNumber, game);
    }

    @GetMapping("/api/games")
    public List<ApiGameDTO> listGames() {
        // Map each game to an ApiGameDTO with its index as the ID
        List<ApiGameDTO> gameDTOs = new ArrayList<>();
        for (int i = 0; i < games.size(); i++) {
            MazeGame game = games.get(i);
            ApiGameDTO dto = new ApiGameDTO();
            dto.makeFromGame(i, game);
            gameDTOs.add(dto);
        }
        return gameDTOs;
    }

    @GetMapping("/api/games/{id}")
    public ApiGameDTO getGame(@PathVariable int id) {
        if (id < 0 || id >= games.size()) {
            throw new GameNotFound("Invalid board id: " + id);
        }
        MazeGame game = games.get(id);
        return ApiGameDTO.makeFromGame(id, game);
    }

    /**
     *  Board Section
     */

    @GetMapping("/api/games/{id}/board")
    public ApiBoardDTO getCurrentBoardState(@PathVariable int id) {
        if (id < 0 || id >= games.size()) {
            throw new GameNotFound("Invalid board id: " + id);
        }
        MazeGame game = games.get(id);
        return new ApiBoardDTO(game);
    }

    /**
     *  Moves Section
     */
    @PostMapping("/api/games/{id}/moves")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void makeMove(@PathVariable int id, @RequestBody String command) {
        if (id < 0 || id >= games.size()) {
            throw new GameNotFound("Invalid game id: " + id);
        }
        MazeGame game = games.get(id);
        Maze maze = game.getMaze();

        MoveDirection move;
        switch(command) {
            case "MOVE_UP" -> {
                move = MoveDirection.MOVE_UP;
            }
            case "MOVE_RIGHT" -> {
                 move = MoveDirection.MOVE_RIGHT;
            }
            case "MOVE_DOWN" -> {
                move = MoveDirection.MOVE_DOWN;
            }
            case "MOVE_LEFT" -> {
                move = MoveDirection.MOVE_LEFT;
            }
            case "MOVE_CATS" -> {
                game.doCatMoves();
                return;
            }
            default -> throw new UnknownCommand("Unknown command: " + command);
        }

        if(!game.isValidPlayerMove(move)) {
            throw new InvalidPlayerMove("Cannot move in this direction!");
        } else {
            game.recordPlayerMove(move);

        }
    }

    /**
     * Cheats Section
     */

    @PostMapping("/api/games/{id}/cheatstate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void activateCheatState(@PathVariable int id, @RequestBody String command) {
        if (id < 0 || id >= games.size()) {
            throw new GameNotFound("Invalid game id: " + id);
        }
        MazeGame game = games.get(id);

        switch(command) {
            case "1_CHEESE" -> game.setNumberCheeseToCollect(1);
            case "SHOW_ALL" -> {
                Maze maze = game.getMaze();
                CellState[][] board = maze.getBoard();

                for(int i = 0; i < board.length; i++) {
                    for(int j = 0; j < board[i].length; j++) {
                        board[i][j] = board[i][j].makeVisible();
                    }
                }
            }
            default -> throw new UnknownCommand("Unknown command: " + command);
        }
    }

    /**
     * Exceptions
     */

    @ResponseStatus(HttpStatus.NOT_FOUND)
    static class GameNotFound extends RuntimeException {
        public GameNotFound(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    static class UnknownCommand extends RuntimeException {
        public UnknownCommand(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    static class InvalidPlayerMove extends RuntimeException {
        public InvalidPlayerMove(String message) {
            super(message);
        }
    }
}
