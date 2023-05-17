package com.codegym.games.minesweeper;

import com.codegym.engine.cell.Game;
import com.codegym.engine.cell.Color;

public class MinesweeperGame extends Game {
    private static final int SIDE = 9;
    private GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private int countMinesOnField;
    private static final String MINE = "\uD83D\uDCA3";
    private static final String FLAG = "\uD83D\uDEA9";
    private int countFlags;
    private boolean isGameStopped;
    private int countClosedTiles = SIDE * SIDE;
    private int score;
   
      @Override
      public void initialize() { 
      setScreenSize(SIDE, SIDE);
      createGame();
      }
      
      
      private void createGame() {
          
          for (int y = 0; y < SIDE; y++) {
              for(int x = 0; x < SIDE; x++) {
                  setCellValue(x, y, "");
                  boolean addMine = false;
                  if(getRandomNumber(10) == 0) {
                  addMine = true;
                  countMinesOnField++;
                  }
                  gameField[y][x] = new GameObject(x, y, addMine);
                  setCellColor(x, y, Color.GREEN);
              }
          }
         countMineNeighbors();
         countFlags = countMinesOnField;
        
      }
      
      private void countMineNeighbors() {
          for(int y = 0; y < SIDE; y++) {
              for(int x = 0; x < SIDE; x++) {
                  if(gameField[y][x].getIsMine() == false) {
                    gameField[y][x].setCountMineNeighbors(getNeighbors(gameField[y][x]));
                  }
                  
              }
          }
      }
      
          public int getNeighbors(GameObject gameObject) {
          int x = gameObject.getX();
          int y = gameObject.getY();
          int countMines = 0;
         int[][] neighbors = new int[3][3];
          for(int i = -1; i < 2 ; i++) {
              for(int k = -1; k < 2; k++) {
                  try {
                     if(gameField[y+i][x+k].getIsMine()) {
                        neighbors[i+1][k+1] = 1;
                        countMines++;
                         
                     }
                  } catch (Exception e) {
                      neighbors[i+1][k+1] = -1; 
                  }
                }
            } 
        return countMines;
          
      }      

      @Override
      public void onMouseLeftClick(int x, int y) {
          if(isGameStopped == false) openTile(x, y);
          else if(isGameStopped == true) restart();
      }
      
      @Override
      public void onMouseRightClick(int x, int y) {
          markTile(x, y);
        
      }

      private void openTile(int x, int y) {
          GameObject tile = gameField[y][x];
           if(tile.getIsOpen() == false && tile.getIsFlag() == false 
                && isGameStopped == false) {   
              if(tile.getIsMine()){
                  setCellValueEx(x, y, Color.RED, MINE);
                  gameOver();
                  
              } 
              if(!tile.getIsMine()) {
                  
                  tile.setIsOpen(true);
                  countClosedTiles--;
                  setScore(score += 5);
                  setCellColor(x, y, Color.YELLOW);
    
                  if(getNeighbors(tile) == 0) {
                      int[][] neighbors = new int[3][3];
                      for(int i = -1; i < 2 ; i++) {
                        for(int k = -1; k < 2; k++) {
                          try {
                             if(gameField[y+i][x+k].getIsMine() == false && 
                                 gameField[y+i][x+k].isOpen == false) {
                                   openTile(x+k, y+i);
                             }
                          } catch (Exception e) {
                              neighbors[i+1][k+1] = -1; 
                          }
                        }
    
                        }
                  }  if(gameField[y][x].getCountMineNeighbors() == 0) {
                      setCellValue(x, y, "");
                  }
                     else setCellNumber(x, y, gameField[y][x].getCountMineNeighbors());           
              } if(countClosedTiles == countMinesOnField) {
                  win();
              } 
           }
      }
      
      private void markTile(int x, int y) {
        if(isGameStopped == false) {
          
            GameObject tile = gameField[y][x];
    
              if(tile.getIsOpen() == false && countFlags > 0 && tile.getIsFlag() == false) {
                  tile.setIsFlag(true);
                  countFlags--;
                  setCellValue(x, y, FLAG);
                  setCellColor(x, y, Color.ORANGE);
              }
               else if(tile.getIsOpen() == false && tile.getIsFlag() == true) {
                  tile.setIsFlag(false);
                  countFlags++;
                  setCellValue(x, y, "");
                  setCellColor(x, y, Color.GREEN);
              }
         }
      }
      
      
      private void gameOver() {
          isGameStopped = true;
          String endGameMessage = "Mine exploded. I'm sorry you lost";
          showMessageDialog(Color.RED, endGameMessage, Color.BLACK, 22);
      }
      
      private void win() {
          isGameStopped = true;
          String won = "Congratulations, you won!";
          showMessageDialog(Color.WHITE, won, Color.BLACK, 22);
      }
      
      private void restart() {
          isGameStopped = false;
          countClosedTiles = SIDE * SIDE;
          score = 0;
          setScore(score);
          countMinesOnField = 0;
          createGame();
      }
}