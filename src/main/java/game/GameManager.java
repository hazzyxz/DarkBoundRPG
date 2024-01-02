package game;

public class GameManager {
    private Creature player;

    public GameManager(Creature player) {
        this.player = player;
    }

    public Creature getPlayer() {
        return player;
    }

    public void setPlayer(Creature player) {
        this.player = player;
    }

    // Add other necessary game state information and methods here
}

