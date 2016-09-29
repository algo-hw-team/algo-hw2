package main;

public class Line {

	public final static double INFINITY =  999999;
	
	/**
	 * 생성자
	 * @param _start 시작점
	 * @param _end 끝점
	 */
	public Line(DoublePair _start, DoublePair _end) {
		this.start = _start;
		this.end = _end;
		this.slope = calSlope(_start, _end);
	}

	public Line(Line l) {
        this.start = new DoublePair(l.start);
        this.end = new DoublePair(l.end);
        this.slope = l.slope;
    }
	
	
	/**
	 * 교점 반환. 없으면 null
	 * @param other
	 * @return
	 */
	public DoublePair getIntersection(Line other) {
		if (this.slope == other.slope) {
			return null;
		} else {
			double x = roundDouble(calX(this, other), 4);
			double y = roundDouble(calY(this, other), 4);
			DoublePair result = new DoublePair(x, y);
			if (this.IsOnLine(result) && other.IsOnLine(result)) {
				return result;
			} else {
				return null;
			}
		}
	}
	
	/**
	 * 기울기 반환
	 * @return
	 */
	public double getSlope() {
		return this.slope;
	}

	public void swapStartEnd() {
        DoublePair temp = start;
        this.start = end;
        this.end = temp;

        this.slope = calSlope(this.start, this.end);
    }
	

	public DoublePair start, end;
	public double slope;

	public boolean isEmptyLine() {
        return (start.x == end.x) && (start.y == end.y);
    }
	
	private static double calSlope(DoublePair a, DoublePair b) {
		
		double dx = b.x - a.x;
		double dy = b.y - a.y;

        if (dx == 0) {
            return dy > 0 ?
                    INFINITY :
                    -INFINITY;
        }

        return dy / dx;
	}
	
	private double calX (Line a, Line b) {
		double x1 = a.start.x;
		double y1 = a.start.y;
		double x2 = a.end.x;
		double y2 = a.end.y;
		double x3 = b.start.x;
		double y3 = b.start.y;
		double x4 = b.end.x;
		double y4 = b.end.y;
		
		return (((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4))
				/((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4)));
	}
	
	private double calY (Line a, Line b) {
		double x1 = a.start.x;
		double y1 = a.start.y;
		double x2 = a.end.x;
		double y2 = a.end.y;
		double x3 = b.start.x;
		double y3 = b.start.y;
		double x4 = b.end.x;
		double y4 = b.end.y;
		
		return (((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4))
				/((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4)));
	}
	
	public boolean IsOnLine (DoublePair point) {
		double length = Math.sqrt(Math.pow((this.start.x - this.end.x),2) + Math.pow((this.start.y - this.end.y),2));
		double distance1 = Math.sqrt(Math.pow((this.start.x - point.x),2) + Math.pow((this.start.y - point.y),2));
		double distance2 = Math.sqrt(Math.pow((point.x - this.end.x),2) + Math.pow((point.y - this.end.y),2));
		
		return (distance1 <= length && distance2 <= length);
	}

	public void print() {
        String str = "(" +this.start.x + "," + this.start.y + ")-";
        str += "(" +this.end.x + "," + this.end.y + ")";

        System.out.println(str);
    }

    private static double roundDouble(double a, int n){
        return Math.round(a * Math.pow(10,n)) / Math.pow(10,n);
    }
}
