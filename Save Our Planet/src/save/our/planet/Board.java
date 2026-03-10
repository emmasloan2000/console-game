/**
 * 
 */
package save.our.planet;

import java.util.ArrayList;

/**
 * 
 */
public class Board {
	
	private int charityPosition1;
	private int charityPosition2;
	private int charityPosition3;
	private int charityPosiiton4;
	private ArrayList<Square> squares;
	
	public Board() {
		
	}
	
	
	/**
	 * @param charityPosition1
	 * @param charityPosition2
	 * @param charityPosition3
	 * @param charityPosiiton4
	 * @param squares
	 */
	public Board(ArrayList<Square> squares, int charityPosition1, int charityPosition2, int charityPosition3, int charityPosiiton4) {
		this.squares = squares;
		this.charityPosition1 = charityPosition1;
		this.charityPosition2 = charityPosition2;
		this.charityPosition3 = charityPosition3;
		this.charityPosiiton4 = charityPosiiton4;
		
	}


	/**
	 * @return the charityPosition1
	 */
	public int getCharityPosition1() {
		return charityPosition1;
	}


	/**
	 * @param charityPosition1 the charityPosition1 to set
	 */
	public void setCharityPosition1(int charityPosition1) {
		this.charityPosition1 = charityPosition1;
	}


	/**
	 * @return the charityPosition2
	 */
	public int getCharityPosition2() {
		return charityPosition2;
	}


	/**
	 * @param charityPosition2 the charityPosition2 to set
	 */
	public void setCharityPosition2(int charityPosition2) {
		this.charityPosition2 = charityPosition2;
	}


	/**
	 * @return the charityPosition3
	 */
	public int getCharityPosition3() {
		return charityPosition3;
	}


	/**
	 * @param charityPosition3 the charityPosition3 to set
	 */
	public void setCharityPosition3(int charityPosition3) {
		this.charityPosition3 = charityPosition3;
	}


	/**
	 * @return the charityPosiiton4
	 */
	public int getCharityPosiiton4() {
		return charityPosiiton4;
	}


	/**
	 * @param charityPosiiton4 the charityPosiiton4 to set
	 */
	public void setCharityPosiiton4(int charityPosiiton4) {
		this.charityPosiiton4 = charityPosiiton4;
	}


	/**
	 * @return the squares
	 */
	public ArrayList<Square> getSquares() {
		return squares;
	}


	/**
	 * @param squares the squares to set
	 */
	public void setSquares(ArrayList<Square> squares) {
		this.squares = squares;
	}
	
	
	
	public int findNewPosition(int old, int roll) {
		
		int newP  = old + roll;
		int newPosition = ((newP - 1) % 12) + 1;
		
		if (newP > 12) {
	
			if (newPosition == 1) {
				System.out.println("You have landed on the Activation Zone! You have collected new volunteers! \n Collect 500 volunteer hours!");
			} else {
				System.out.println("You have passes the Activation Zone! Collect 200 volunteer hours!");
			}
		} 
		
		return newPosition;
		
	}
	
	
	
	public void addPlayerToSquare(Player currentPlayer, int pos) {
		
		currentPlayer.setPosition(pos);
		
	}
	
	

	
	
	public Square findPlayerSquare(int pos) {
		
		int index = pos - 1;
		
		if (pos < 1 || pos > squares.size()) {
			throw new IllegalArgumentException("Invalid board position");
		}
		
		return squares.get(index);
	}
	
	

}
