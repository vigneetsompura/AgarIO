package server;

import agario.Game;
import agario.Player;

class Handler {

    private Game game;

    Handler() {
        this.game = new Game();
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
        game.getPlayers()
                .stream()
                .filter(predator -> getPlayer(predator.getPlayerID()) != null)
                .forEach(this::tryEat);
        game.unlock();
    }

    private void tryEat(Player predator) {
        game.getFoodList().forEach(predator::tryEat);
        tryEatPrey(predator);
    }

    private void tryEatPrey(Player predator) {
        game.getPlayers()
                .stream()
                .filter(prey -> prey.isNotPredator(predator))
                .filter(predator::didEatPrey)
                .forEach(prey -> removePlayer(prey.getPlayerID()));
    }
}

