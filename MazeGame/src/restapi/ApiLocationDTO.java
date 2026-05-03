package myapp.restapi;

import myapp.MazeGameModel.CellLocation;

public class ApiLocationDTO {
    public int x;
    public int y;

    public ApiLocationDTO(CellLocation location) {
        this.x = location.getX();
        this.y = location.getY();
    }

}
