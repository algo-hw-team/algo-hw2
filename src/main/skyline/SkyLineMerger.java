package main.skyline;

import main.Line;
import main.DoublePair;

import java.util.ArrayList;

public class SkyLineMerger {

    public SkyLineMerger() {}

    // for internal calculation
    private SkyLineContainer upper, lower, source, target;

    /**
     * 두 skyLine을 merge한다.
     * @param _s1 skyLine 1
     * @param _s2 skyLine 2
     * @return SkyLine merged
     */
    public SkyLine merge(SkyLine _s1, SkyLine _s2) {
        SkyLineContainer s1 = new SkyLineContainer(_s1);
        SkyLineContainer s2 = new SkyLineContainer(_s2);
        ArrayList<Line> mergedLines = new ArrayList<>();

        // initialize
        if (s1.skyLine.getStartingX() <= s2.skyLine.getStartingX()) {
            upper = s1;
            lower = s2;
        } else {
            upper = s2;
            lower = s1;
        }

        source = upper;
        target = lower;

        // 예외처리: 두 skyLine이 만나지 않는 경우
        double sourceEndingX = source.skyLine.getEndingX();
        double targetStartingX = target.skyLine.getStartingX();

        if (sourceEndingX <= targetStartingX) {
            mergedLines.addAll(source.skyLine.getLines());

            // source의 끝좌표와 target의 시작좌표가 다르면, 두 skyline을 이어주는 line을 추가로 넣어준다.
            if (sourceEndingX != targetStartingX) {
                Line segment = new Line(new DoublePair(sourceEndingX, 0),
                        new DoublePair(targetStartingX, 0));
                mergedLines.add(segment);
            }

            mergedLines.addAll(target.skyLine.getLines());

            return new SkyLine(mergedLines);
        }

        while (!source.isFinished() && !target.isFinished()) {
            Line l1 = source.currentLine;
            Line l2 = target.currentLine;
            DoublePair intersection = l1.hasIntersect(l2);

            if (intersection != null) {
                Line segmentToInsert = new Line(upper.currentLine.start, intersection);
                mergedLines.add(segmentToInsert);

                if (source.currentLine.start.equals(intersection) ||
                        target.currentLine.start.equals(intersection)) {
                    swapUpperLowerByVelocity();

                } else {
                    source.currentLine.start = intersection;
                    target.currentLine.start = intersection;

                    // change upper & lower
                    swapUpperLower();
                }
            }

            // if source & target needs to be changed
            if (source.currentLine.end.x < target.currentLine.end.x) {
                swapSourceTarget();
            }

            // if target is upper
            if ((target == upper) && !target.currentLine.isEmptyLine()) {
                mergedLines.add(target.currentLine);
            }

            target.next();
        }

        if (!upper.isFinished()) {
            for (int i = upper.index; i < upper.size; i++) {
                mergedLines.add(upper.skyLine.getLine(i));
            }
        }

        return new SkyLine(mergedLines);
    }

    /**
     * 두 skyLine의 위/아래 변경
     */
    private void swapUpperLower() {
        SkyLineContainer temp = upper;
        upper = lower;
        lower = temp;
    }

    /**
     * 두 skyLine 중 비교 기준점이 될 skyLine 변경
     */
    private void swapSourceTarget() {
        SkyLineContainer temp = source;
        source = target;
        target = temp;
    }

    private void swapUpperLowerByVelocity() {
        if (upper.currentLine.slope < lower.currentLine.slope) {
            swapUpperLower();
        }
    }
}
