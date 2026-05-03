package myapp.restapi;

import myapp.MazeGameModel.MazeGame;

public class ApiGameDTO {
    public int gameNumber;
    public boolean isGameWon;
    public boolean isGameLost;
    public int numCheeseFound;
    public int numCheeseGoal;

    public static ApiGameDTO makeFromGame(int gameNumber, MazeGame game) {
        ApiGameDTO dto = new ApiGameDTO();
        dto.setGameNumber(gameNumber);
        dto.setIsGameWon(game.hasUserWon());
        dto.setIsGameLost(game.hasUserLost());
        dto.setNumCheeseFound(game.getNumberCheeseCollected());
        dto.setNumCheeseGoal(game.getNumberCheeseToCollect());
        return dto;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }

    public void setIsGameWon(boolean isGameWon) {
        this.isGameWon = isGameWon;
    }

    public void setIsGameLost(boolean isGameLost) {
        this.isGameLost = isGameLost;
    }

    public void setNumCheeseFound(int numCheeseFound) {
        this.numCheeseFound = numCheeseFound;
    }

    public void setNumCheeseGoal(int numCheeseGoal) {
        this.numCheeseGoal = numCheeseGoal;
    }

    public int getGameNumber() {
        return gameNumber;
    }

}
