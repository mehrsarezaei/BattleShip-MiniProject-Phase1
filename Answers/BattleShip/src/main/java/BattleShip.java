package org.example.java;

import java.util.Random;
import java.util.Scanner;


public class BattleShip {

    static final int [] ShipSize = {5, 4, 3, 2};
    static final int GRID_SIZE = 10;

    static char[][] player1Grid = new char[GRID_SIZE][GRID_SIZE];

    static char[][] player2Grid = new char[GRID_SIZE][GRID_SIZE];

    static char[][] player1TrackingGrid = new char[GRID_SIZE][GRID_SIZE];

    static char[][] player2TrackingGrid = new char[GRID_SIZE][GRID_SIZE];


    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize grids for both players
        initializeGrid(player1Grid);
        initializeGrid(player2Grid);
        initializeGrid(player1TrackingGrid);
        initializeGrid(player2TrackingGrid);

        placeShips(player1Grid);
        placeShips(player2Grid);

        boolean player1Turn = true;
        while (!isGameOver()) {
            if (player1Turn) {
                System.out.println("Player 1's turn:");
                printGrid(player1TrackingGrid);
                playerTurn(player2Grid, player1TrackingGrid);
            } else {
                System.out.println("Player 2's turn:");
                printGrid(player2TrackingGrid);
                playerTurn(player1Grid, player2TrackingGrid);
            }
            player1Turn = !player1Turn;
        }
        System.out.println("Game Over!");
    }
    static void initializeGrid(char[][] grid) {
        for(int i = 0; i < GRID_SIZE; i++) {
            for(int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = '~';
            }
        }
    }
    static void placeShips(char[][] grid) {
        Random random = new Random();
        for( int size : ShipSize) {
            boolean placed = false;
            for (int i = 0; i < 4; i++) {
                while(!placed) {
                    int row = random.nextInt(9);
                    int col = random.nextInt(9);
                    boolean horizontal = random.nextBoolean();
                    if(canPlaceShip(grid, row, col, size, horizontal)){
                        for (int j = 0; j < size ; j++) {
                            if (horizontal) {
                                grid[row][col + j] = 'S';
                            } else {
                                grid[row + j][col] = 'S';
                            }
                        }
                        placed = true;
                    }
                }
            }
        }
    }
    static boolean canPlaceShip(char[][] grid, int row, int col, int size, boolean horizontal) {
        if (horizontal) {
            for (int i = 0; i < size; i++) {
                if(col+ i >= GRID_SIZE) return false;
                if(grid[row][col + i] != '~') return false;
            }
        }
        else{
            for (int i = 0; i < size; i++) {
                if(row+ i >= GRID_SIZE) return false;
                if(grid[row + i][col] != '~') return false;
            }
        }
        return true;
    }
    static void playerTurn(char[][] opponentGrid, char[][] trackingGrid) {
        if(allShipsSunk(opponentGrid)) {
            isGameOver();
        }
        else {
            String PlayersMove = scanner.nextLine();
            boolean b = isValidInput(PlayersMove);
            if(b) {
                int row = (int)(PlayersMove.charAt(0)) - 65;
                int col = Integer.parseInt(PlayersMove.substring(1));
                if (opponentGrid[row][col] == 'S') {
                    trackingGrid[row][col] = 'X';
                    opponentGrid[row][col] = 'X';
                    System.out.println("Hit");
                }
                else if(opponentGrid[row][col] == 'X' || opponentGrid[row][col] == '0') {
                    System.out.println("invalid input try again :");
                    playerTurn(opponentGrid, trackingGrid);
                }
                else{
                    trackingGrid[row][col] = '0';
                    System.out.println("Miss");
                }

            }
            if(b == false) {
                System.out.println("invalid input try again :");
                playerTurn(opponentGrid, trackingGrid);
            }
        }
    }
    static boolean isGameOver() {
        return allShipsSunk(player1Grid) || allShipsSunk(player2Grid);
    }
    static boolean allShipsSunk(char[][] grid) {
        int count = 0;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == 'X') {
                    count++;
                }
            }
        }
        if (count == 14) return true;
        else return false;
    }
    static boolean isValidInput(String input) {
        if (input.length() !=2) return false;
        else if((input.charAt(0) < 'A') || (input.charAt(0) > 'Z')) return false;
        else if ((input.charAt(1) < '0') || (input.charAt(1) > '9')) return false;
        else return true;
    }
    static void printGrid(char[][] grid) {
        System.out.print("  ");
        for (int i = 0; i < 10; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.print((char) (65 + i) + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print( grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}
