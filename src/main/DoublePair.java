package main;

public class DoublePair {
	public double x, y;
	
	public DoublePair(double _x, double _y) {
		x = _x;
		y = _y;
	}

	public DoublePair(DoublePair dp) {
        x = dp.x;
        y = dp.y;
    }
	
	@Override
	public boolean equals(Object input) {
		if (input == this) return true;
		if (!(input instanceof DoublePair)) return false;
		
		DoublePair pair = (DoublePair) input;
		
		return (x == pair.x) && (y == pair.y);
	}
}
