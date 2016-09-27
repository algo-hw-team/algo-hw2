package main;

import java.util.ArrayList;

public class SkyLine {

    private ArrayList<Line> lines;

    public SkyLine(ArrayList<Line> _lines) {
        lines = new ArrayList<Line>(_lines);
    }

    public Line getLine(int index) {
        return lines.get(index);
    }

    public int size() {
        return lines.size();
    }

    public SkyLine merge(SkyLine s2) {
        ArrayList<Line> mergedLines = new ArrayList<Line>();
        SkyLine s1 = this;
        boolean isS1Upper = true;
        boolean isS1Source = true;
        int s1Index = 0;
        int s2Index = 0;
        int s1Size = s1.size();
        int s2Size = s2.size();

        if (s1.getStartingX() > s2.getStartingX()) {
            isS1Upper = false;
            isS1Source = false;
        }

        while ((s1Index < s1Size) && (s2Index < s2Size)) {
            Line l1 = s1.getLine(s1Index);
            Line l2 = s2.getLine(s2Index);
            DoublePair intersection = l1.hasIntersect(l2);

            if (intersection != null) {
                Line upperLine = isS1Upper ?
                        l1 :
                        l2;

                Line segmentToInsert = new Line(upperLine.start, intersection);
                mergedLines.add(segmentToInsert);
                l1.start = intersection;
                l2.start = intersection;

                s1.lines.set(s1Index, l1);
                s2.lines.set(s2Index, l2);

                // change upper & lower
                isS1Upper = !isS1Upper;
            }

            Line sourceLine, targetLine;

            if (isS1Source) {
                sourceLine = l1;
                targetLine = l2;
            } else {
                sourceLine = l2;
                targetLine = l1;
            }

            // if source and target need to be changed
            if (sourceLine.end.x < targetLine.end.x) {
                isS1Source = !isS1Source;
            }

            // if target == upper
            if (isS1Upper && !isS1Source) {
                mergedLines.add(l1);
            } else if (!isS1Upper && isS1Source) {
                mergedLines.add(l2);
            }

            // move target to next line
            if (isS1Source) {
                s2Index++;
            } else {
                s1Index++;
            }
        }

        // adds remaining lines in upper skyLine
        int upperIndex, upperSize;
        SkyLine upper;

        if (isS1Upper) {
            upperIndex = s1Index;
            upperSize = s1Size;
            upper = s1;
        } else {
            upperIndex = s2Index;
            upperSize = s2Size;
            upper = s2;
        }

        if (upperIndex < upperSize) {
            for (int i = upperIndex; i < upperSize; i++) {
                mergedLines.add(upper.lines.get(i));
            }
        }

        return new SkyLine(mergedLines);
    }

    public void print() {
        for (Line line: lines) {
            DoublePair point = line.start;
            String out = "(" + point.x + "," + point.y + ") -- ";
            point = line.end;
            out += "(" + point.x + "," + point.y + ")";

            System.out.println(out);
        }
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    private double getStartingX() {
        return lines.get(0).start.x;
    }
}
