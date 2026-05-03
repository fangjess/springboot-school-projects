package myapp.restapi;

import java.util.List;

public class ApiBoardWrapper {
    public int boardWidth;
    public int boardHeight;
    public ApiLocationDTO mouseLocation;
    public ApiLocationDTO cheeseLocation;
    public List<ApiLocationDTO> catLocations;
    public boolean[][] hasWalls;
    public boolean[][] isVisible;
}
