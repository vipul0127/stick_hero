package com.example.g;
//for implementation of facade pattern
class GameFacade {
    private GameEngine gameEngine;
    private GameUI gameUi;

    public GameFacade(GameEngine gameEngine, GameUI gameUi) {
        this.gameEngine = gameEngine;
        this.gameUi = gameUi;
    }

    public void startGame() {
        gameUi.createPreliminaryUI(gameUi.getScene());
        gameEngine.handleStartButtonClick();
    }

    public void restartGame() {
        gameEngine.onClickRestart();
    }

    public void continueGame() {
        gameEngine.onClickContinue();
    }

    public void pauseGame() {
        gameEngine.onClickPause();
    }

    public void homeScreen() {
        gameEngine.onClickHome();
    }

    public void showScoreSystem() {
        gameUi.scoresystemUi();
    }

    public void startMovingLine() {
        gameUi.startMovingLine();
    }

    public void stopMovingLine() {
        gameUi.stopMovingLine();
    }

    public void moveCharacter() {
        try {
            gameUi.moveChar();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Add more methods as needed for other game-related functionality
}