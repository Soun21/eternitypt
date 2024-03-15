package testrelou;


public class PuzzlePiece {
	
	int northValue;
	int westValue;
	int southValue;
	int eastValue;

    public PuzzlePiece(int northValue, int westValue, int southValue, int eastValue) {
        this.northValue = northValue;
        this.eastValue = eastValue;
        this.southValue = southValue;
        this.westValue = westValue;
    }
    
    public int getSouthValue() {
		return this.southValue;
	}
    public int getNorthValue() {
    	 return this.northValue;
    }

	public int getEastValue() {
		return this.eastValue;
	}

	public int getWestValue() {
		return this.westValue;
    }
			

	public int getSouthValue(int orientation) {
           switch (orientation) {
			case 0:
				return this.southValue;
			case 3:
				return this.eastValue;
			case 2:
				return this.northValue;
			case 1:
				return this.westValue;
			default:
				return -1;
           }
	   }
	public int getNorthValue(int orientation) {
		switch (orientation) {
		case 0:
			return this.northValue;
		case 3:
			return this.westValue;
		case 2:
			return this.southValue;
		case 1:
			return this.eastValue;
		default:
			return -1;
		}
	}
			   
	public int getEastValue(int orientation) {
		switch (orientation) {
		case 0:
			return this.eastValue;
		case 3:
			return this.northValue;
		case 2:
			return this.westValue;
		case 1:
			return this.southValue;
		default:
			return -1;
		}
	}
    
   public int getWestValue(int orientation) {
	    switch (orientation) {
		case 0:
			return this.westValue;
		case 3:
			return this.southValue;
		case 2:
			return this.eastValue;
		case 1:
			return this.northValue;
		default:
			return -1;			
       }
   }		

}
