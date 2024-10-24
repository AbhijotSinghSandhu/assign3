package com.mazegame.cs.View;

import com.mazegame.cs.Model.GameController;
import com.mazegame.cs.Model.Maze;
import com.mazegame.cs.Model.Mouse;

import java.util.Scanner;

public class TextUI {
    private final GameController controller;

    public TextUI(GameController controller) {
        this.controller = controller;
    }

    public void start() {
        displayHeader();
        displayInstructions();
        char userMove;
        GameController.outputCases cases = GameController.outputCases.VALID_MOVE;
        controller.updateMaze();
        displayMaze();
        do {
            userMove = takeUserInput();
            if (userMove == 'C') {
                controller.setCheeseToWin(1);
            } else if (userMove == 'M') {
                revealMaze();
            } else {
                cases = switch (userMove) {
                    case 'W' -> {
                        yield controller.gameMove(Mouse.Direction.UP);
                    }
                    case 'S' -> {
                        yield controller.gameMove(Mouse.Direction.DOWN);
                    }
                    case 'A' -> {
                        yield controller.gameMove(Mouse.Direction.LEFT);
                    }
                    case 'D' -> {
                        yield controller.gameMove(Mouse.Direction.RIGHT);
                    }
                    default -> {
                        yield GameController.outputCases.VALID_MOVE;
                    }
                };
            }
            if (cases == GameController.outputCases.INVALID_MOVE) {
                System.out.println("Invalid move: you cannot move through walls!");
                continue;
            }
            displayMaze();
        }while(cases != GameController.outputCases.WIN && cases != GameController.outputCases.LOSE);
        if (cases == GameController.outputCases.LOSE) {
            System.out.println("I'm sorry, you have been eaten!");
            displayMaze();
            System.out.println("GAME OVER; please try again.");
        }
    }

    private static void displayHeader() {
        System.out.println("-----------------------------------------");
        System.out.println("Welcome to Cat and Mouse Maze Adventure!");
        System.out.println("by Abhijot Singh Sandhu and Srinjay Mitra");
        System.out.println("-----------------------------------------");
    }

    private static void displayInstructions() {
        System.out.println("DIRECTIONS:");
        System.out.println("        Find 5 cheese before a cat eats you!");
        System.out.println("LEGEND:");
        System.out.println("        #: Wall");
        System.out.println("        @: You (a mouse)");
        System.out.println("        !: Cat");
        System.out.println("        $: Cheese");
        System.out.println("        .: Unexplored space");
        System.out.println("MOVES:");
        System.out.println("        Use W (up), A (left), S (down) and D (right) to move.");
        System.out.println("        (You must press enter after each move).");
    }

    private void displayMaze() {
        System.out.println();
        System.out.println("Maze:");
        for (int y = 0; y < controller.getMazeHeight(); y++){
            for(int x = 0; x < controller.getMazeWidth(); x++) {
                switch (controller.getGrid()[y][x]) {
                    case Maze.Cell.CATOVERMOUSE -> System.out.print("X");
                    case Maze.Cell.WALL -> System.out.print("#");
                    case Maze.Cell.CAT -> System.out.print("!");
                    case Maze.Cell.MOUSE -> System.out.print("@");
                    case Maze.Cell.CHEESE -> System.out.print("$");
                    case Maze.Cell.PATH -> System.out.print(".");
                }
            }
            System.out.println();
        }
        System.out.println("Cheese collected: " + controller.getCheeseCollected() + " of " + controller.getNumOfCheeseToWin());
    }

    private char takeUserInput() {
        Scanner keyboard = new Scanner(System.in);
        char userChoice;
        do {
            System.out.print("Enter your move [WASD]: ");
            String userInput = keyboard.nextLine().toUpperCase();
            userChoice = userInput.charAt(0);
        } while (userChoice != 'W' && userChoice != 'A' && userChoice != 'S'
                && userChoice != 'D' && userChoice != 'M' && userChoice != 'C');
        return userChoice;
    }

    private void revealMaze() {

    }
}
