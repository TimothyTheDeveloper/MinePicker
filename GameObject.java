package com.codegym.games.minesweeper;


public class GameObject {
  
    public int x;
    public int y;
    public boolean isMine;
    public boolean isOpen;
    public int countMineNeighbors;
    public boolean isFlag;
    
    
    public GameObject(int x, int y, boolean isMine) {
        this.x = x;
        this.y = y;
        this.isMine = isMine;
    }
    
    public void setCountMineNeighbors(int countMineNeighbors) {
        this.countMineNeighbors = countMineNeighbors;
    }
    
    public int getCountMineNeighbors() {
        return countMineNeighbors;
    }
    
    public boolean getIsMine() {
        return isMine;
    }
    
    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }
    
    public boolean getIsOpen() {
        return isOpen;
    }
    
    public void setIsFlag(boolean isFlag) {
        this.isFlag = isFlag;
    }
    
    public boolean getIsFlag() {
        return isFlag;
    }
    
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    } 
}