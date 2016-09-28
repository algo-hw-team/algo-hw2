package main.skyline;

import main.DoublePair;
import main.Line;

import java.util.ArrayList;

public class Skyline {

    private ArrayList<Line> lines;

    public Skyline(ArrayList<Line> _lines) {
        lines = new ArrayList<Line>(_lines);
    }

    /**
     * index에 해당하는 line 반환
     * @param index
     * @return
     */
    public Line getLine(int index) {
        return lines.get(index);
    }

    /**
     * skyLine의 line 갯수 반환
     * @return
     */
    public int size() {
        return lines.size();
    }

    /**
     * skyLine의 모든 lines를 출력. 디버그 용도
     */
    public void print() {
        for (Line line: lines) {
            DoublePair point = line.start;
            String out = "(" + point.x + "," + point.y + ") -- ";
            point = line.end;
            out += "(" + point.x + "," + point.y + ")";

            System.out.println(out);
        }
    }

    /**
     * lines 반환
     * @return
     */
    public ArrayList<Line> getLines() {
        return lines;
    }

    /**
     * skyline 시잠점의 x 좌표 반환
     * @return
     */
    public double getStartingX() {
        return lines.get(0).start.x;
    }

    public double getEndingX() {
        return getLastLine().end.x;
    }

    /**
     * skyLine의 마지막 line 반환
     * @return
     */
    public Line getLastLine() {
        return lines.get(size() - 1);
    }
}
