package application;

class ClickData{
	private int row;
	private int col;
	
	public ClickData(int row, int col) {
		this.col = col;
		this.row = row;
	}
	
	public int getCol() {
		return col;
	}
	
	public int getRow() {
		return row;
	}
}