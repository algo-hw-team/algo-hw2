package main.skyline;

import main.Line;
import main.DoublePair;

import java.util.ArrayList;
import java.util.List;

public class SkyLineMerger {

    public SkyLineMerger() {}

    // for internal calculation
    private SkyLineContainer upper, lower;
    private ArrayList<Line> mergedLines = null;
    private boolean hasDelayedSegment;
    private Line delayedSegment;
    private boolean skipPop;

    public Skyline merge(Skyline _s1, Skyline _s2) {
        SkyLineContainer s1 = new SkyLineContainer(_s1);
        SkyLineContainer s2 = new SkyLineContainer(_s2);
        hasDelayedSegment = false;
        delayedSegment = null;
        skipPop = false;
        mergedLines = new ArrayList<>();

        // initialize upper & lower
        double x1 = s1.skyline.getStartingX();
        double x2 = s2.skyline.getStartingX();
        if (x1 < x2) {
            upper = s1;
            lower = s2;
        } else {
            upper = s2;
            lower = s1;
        }


        while (!upper.isFinished() && !lower.isFinished()) {
            Line upperLine = upper.currentLine;
            Line lowerLine = lower.currentLine;
            skipPop = false;

            // if two lines are identical
            if (upperLine.start.equals(lowerLine.start) &&
                    upperLine.end.equals(lowerLine.end)) {
                mergedLines.add(upperLine);
                upper.pop();
                lower.pop();
                continue;
            }

            // handle delayed segment
            if (hasDelayedSegment) {

                // case 1: upper & lower needs to be recalculated
                boolean hasChanged = upperLine.slope <= lowerLine.slope;

                if (hasChanged &&
                        (delayedSegment != null) &&
                        !delayedSegment.isEmptyLine()) {
                    mergedLines.add(delayedSegment);
                }

                hasDelayedSegment = false;
                delayedSegment = null;

            }

            handleIntersection();

            if (skipPop) {
                continue;
            }

            // remove one line from current two lines

            SkyLineContainer skylineToPop = (s1.currentLine.end.x <= s2.currentLine.end.x) ?
                    s1 :
                    s2;

            if (skylineToPop == upper && !hasDelayedSegment) {
                mergedLines.add(skylineToPop.currentLine);
            }

            skylineToPop.pop();
        }

        // handle any left lines

        if (delayedSegment != null) {
            mergedLines.add(delayedSegment);
        }
        if (!upper.isFinished()) {
            mergedLines.add(upper.currentLine);
            upper.pop();
            List<Line> leftLines = upper.skyline.getLines().subList(upper.index, upper.size);
            mergedLines.addAll(leftLines);

        } else if (!lower.isFinished()) {
            if (upper.skyline.getEndingX() < lower.currentLine.start.x) {
                mergedLines.add(new Line(upper.skyline.getLastLine().end, lower.currentLine.start));
            }

            mergedLines.add(lower.currentLine);
            lower.pop();
            List<Line> leftLines = lower.skyline.getLines().subList(lower.index, lower.size);
            mergedLines.addAll(leftLines);
        }

        return new Skyline(mergedLines);
    }
    
    private void handleIntersection() {
        Line upperLine = upper.currentLine;
        Line lowerLine = lower.currentLine;
        DoublePair intersection = upperLine.getIntersection(lowerLine);

        // if no intersection
        if (intersection == null) {

            if ((upperLine.slope == -Line.INFINITY) && (lowerLine.slope == Line.INFINITY)) {
                double upperY = upperLine.start.y;
                double lowerY = lowerLine.end.y;
                skipPop = true;

                if (upperY < lowerY) {
                    lowerLine.start = upperLine.start;
                    upper.pop();
                } else if (upperY > lowerY) {
                    upperLine.end = lowerLine.end;
                    mergedLines.add(upperLine);
                    upper.pop();
                } else {
                    upper.pop();
                    lower.pop();
                }
                return;
            }

            if (upperLine.start.equals(lowerLine.start)) {
                // case 3: one line includes other line
                // set shorter one to be lower
                hasDelayedSegment = true;

                if (upperLine.end.x < lowerLine.end.x) {
                    swapUpperLower();
                }

                delayedSegment = new Line(lower.currentLine);
            }

            return;
        }

        // cases when intersection exists

        if (upperLine.end.equals(lowerLine.start)) {
            return;
        }

         if (intersection.equals(upperLine.start) || intersection.equals(lowerLine.start)) {
            swapUpperLowerByVelocity();

        } else if (intersection.equals(upperLine.end) && intersection.equals(lowerLine.end)) {
            mergedLines.add(upper.currentLine);
            upper.pop();
            lower.pop();
            skipPop = true;

        } else if (intersection.equals(upperLine.end) || intersection.equals(lowerLine.end)) {
            delayedSegment = new Line(upperLine.start, intersection);

            if (intersection.equals(upperLine.end)) {
                lowerLine.start = intersection;
            } else {
                upperLine.start = intersection;
            }

            hasDelayedSegment = true;

        } else {
            Line segmentToInsert = new Line(upper.currentLine.start, intersection);
            mergedLines.add(segmentToInsert);
            upperLine.start = intersection;
            lowerLine.start = intersection;

            swapUpperLower();
        }
    }

    /**
     * 두 skyline의 위/아래 변경
     */
    private void swapUpperLower() {
        SkyLineContainer temp = upper;
        upper = lower;
        lower = temp;
    }

    private void swapUpperLowerByVelocity() {
        if (upper.currentLine.slope < lower.currentLine.slope) {
            swapUpperLower();
        }
    }
}
