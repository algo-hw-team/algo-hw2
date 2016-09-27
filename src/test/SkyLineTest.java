package test;

import main.skyline.SkyLine;
import main.skyline.SkyLineMerger;
import org.junit.Test;
import static org.junit.Assert.*;

import main.*;

import java.util.ArrayList;

public class SkyLineTest {

    SkyLineMerger merger = new SkyLineMerger();

    @Test
    public void testMerge() throws Exception {
        ArrayList<Line> list1 = new ArrayList<>();
        ArrayList<Line> list2 = new ArrayList<>();
        list1.add(new Line(new DoublePair(1, 0), new DoublePair(8, 10)));
        list1.add(new Line(new DoublePair(8, 10), new DoublePair(12, 4)));
        list1.add(new Line(new DoublePair(12, 4), new DoublePair(13, 5)));
        list1.add(new Line(new DoublePair(13, 5), new DoublePair(14, 4)));
        list1.add(new Line(new DoublePair(14, 4), new DoublePair(15, 5)));
        list1.add(new Line(new DoublePair(15, 5), new DoublePair(17, 0)));

        list2.add(new Line(new DoublePair(2, 0), new DoublePair(4, 10)));
        list2.add(new Line(new DoublePair(4, 10), new DoublePair(9, 1)));
        list2.add(new Line(new DoublePair(9, 1), new DoublePair(10, 3)));
        list2.add(new Line(new DoublePair(10, 3), new DoublePair(11, 2)));
        list2.add(new Line(new DoublePair(11, 2), new DoublePair(12, 5)));
        list2.add(new Line(new DoublePair(12, 5), new DoublePair(14, 0)));

        SkyLine s1 = new SkyLine(list1);
        SkyLine s2 = new SkyLine(list2);

        SkyLine merged = merger.merge(s1, s2);

        ArrayList<Line> lines = merged.getLines();

        Line l1 = lines.get(0);
        Line l2 = lines.get(2);
        Line l3 = lines.get(4);
        Line l4 = lines.get(6);
        Line l5 = lines.get(8);
        Line l6 = lines.get(10);

        assertEquals(l1.start, new DoublePair(1.0, 0.0));
        assertEquals(l2.start, new DoublePair(4.0, 10.0));
        assertEquals(l3.start, new DoublePair(8.0, 10.0));
        assertEquals(l4.start, new DoublePair(12.0, 5.0));
        assertEquals(l5.start, new DoublePair(13.0, 5.0));
        assertEquals(l6.start, new DoublePair(15.0, 5.0));
    }

    @Test
    public void testMerge2() throws Exception {
        ArrayList<Line> list1 = new ArrayList<>();
        ArrayList<Line> list2 = new ArrayList<>();
        list1.add(new Line(new DoublePair(0, 0), new DoublePair(1, 2)));
        list1.add(new Line(new DoublePair(1, 2), new DoublePair(4, 0)));

        list2.add(new Line(new DoublePair(2, 0), new DoublePair(2.5, 3)));
        list2.add(new Line(new DoublePair(2.5, 3), new DoublePair(3, 0)));

        SkyLine s1 = new SkyLine(list1);
        SkyLine s2 = new SkyLine(list2);

        SkyLine merged = merger.merge(s1, s2);

        merged.print();
    }

}