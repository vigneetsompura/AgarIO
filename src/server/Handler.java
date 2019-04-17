package server;

import agario.Food;
import agario.Game;
import agario.Player;

import java.util.List;

class Handler {

    private Game game;

    Handler() {
        this.game = new Game();
    }

    private List<Food> getFoodList() {
        return game.getFoodList();
    }

    Player getPlayer(int playerID) {
        return game.getPlayer(playerID);
    }

    void addPlayer(Player player) {
        game.addPlayer(player);
    }

    void removePlayer(int playerID) {
        game.removePlayer(playerID);
    }

    void addFood() {
        game.addFood();
    }

    Game getGame() {
        return this.game;
    }

    void tick() {
        game.lock();
        List.copyOf(game.getPlayers().values())
                .stream()
                .filter(predator -> getPlayer(predator.getPlayerID()) != null)
                .forEach(this::tryEat);
        game.unlock();
    }

    private void tryEat(Player predator) {
        getFoodList().forEach(predator::tryEat);
        tryEatPrey(predator);
    }

    private void tryEatPrey(Player predator) {
        List.copyOf(game.getPlayers().values())
                .stream()
                .filter(prey -> prey.isNotPredator(predator))
                .filter(predator::ifAtePrey)
                .forEach(prey -> removePlayer(prey.getPlayerID()));
    }
}

