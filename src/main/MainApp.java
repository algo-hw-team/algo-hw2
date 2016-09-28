package main;

import main.skyline.SkyLine;
import main.skyline.SkyLineMerger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainApp {

	public static void main(String[] args) {

		String InputPath = "/Users/Join/dev/homeworks-0302/algo/hw2/input.txt";
		String OutputPath = "/Users/Join/dev/homeworks-0302/algo/hw2/2013147550.txt";
		
		String OutputString = "";
		
		StringBuilder builder = new StringBuilder();
		
		try {
			//모든 인풋 텍스트를 라인단위로 리스트에 저장한다.
			BufferedReader br = new BufferedReader(new FileReader(InputPath));
			BufferedWriter bw = new BufferedWriter(new FileWriter(OutputPath));
			String sCurrentLine;
			ArrayList<String> inputlist = new ArrayList<>();
			while ((sCurrentLine = br.readLine()) != null) {
				inputlist.add(sCurrentLine);
			}
			
			//algorithm
			int indexOfInput = 0;
			int numOfTest = Integer.parseInt(inputlist.get(indexOfInput++));
			ArrayList<SkyLine> skyLineList = new ArrayList<>();
			DecimalFormat format = new DecimalFormat("0.#");
			for (int indexOfTest = 0; indexOfTest < numOfTest; indexOfTest++) {
				int numOfSkyLine = Integer.parseInt(inputlist.get(indexOfInput++));
				for (int i = 0; i < numOfSkyLine; i++) {
					String[] coords = inputlist.get(indexOfInput++).split(" ");
					DoublePair point1 = new DoublePair(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));
					DoublePair point2 = new DoublePair(Double.parseDouble(coords[2]), Double.parseDouble(coords[3]));
					DoublePair point3 = new DoublePair(Double.parseDouble(coords[4]), Double.parseDouble(coords[5]));

					Line line1 = new Line(point1, point2);
					Line line2 = new Line(point2, point3);
					ArrayList<Line> lineList = new ArrayList<>();
					lineList.add(line1);
					lineList.add(line2);
					skyLineList.add(new SkyLine(lineList));
				}
				
				SkyLine result = mergeSkyLine(skyLineList);
				
				ArrayList<Line> lines = result.getLines();
				for (int i = 0; i < lines.size(); i++) {
					builder.append(format.format(Math.abs(lines.get(i).start.x))).append(" ").append(format.format(Math.abs(lines.get(i).start.y))).append(" ");
				}
				builder.append(format.format(Math.abs(lines.get(lines.size() - 1).end.x))).append(" ").append(format.format(Math.abs(lines.get(lines.size() - 1).end.y)));
				builder.append(System.getProperty("line.separator"));
			}
			
			OutputString = builder.toString();
			OutputString = OutputString.trim();
			bw.write(OutputString);
			bw.flush();
			bw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		ArrayList<Line> lines = new ArrayList<Line>();
		lines.add(new Line(new DoublePair(0, 0), new DoublePair(1,2)));
		lines.add(new Line(new DoublePair(1, 2), new DoublePair(4,0)));
		SkyLine sl1 = new SkyLine(lines);
		ArrayList<Line> lines2 = new ArrayList<Line>();
		lines2.add(new Line(new DoublePair(2, 0), new DoublePair(2.5,3)));
		lines2.add(new Line(new DoublePair(2.5, 3), new DoublePair(3,3)));
		SkyLine sl2 = new SkyLine(lines2);
		SkyLine result = sl1.merge(sl2);
		
		StringBuilder builder = new StringBuilder();
		lines = result.getLines();
		for (int i = 0; i < lines.size(); i++) {
			builder.append(lines.get(i).start.x).append(" ").append(lines.get(i).start.y).append(" ");
		}
		builder.append(lines.get(lines.size() - 1).end.x).append(" ").append(lines.get(lines.size() - 1).end.y);
		builder.append(System.getProperty("line.separator"));
		System.out.println(builder.toString());
		*/
	}

	private static SkyLineMerger merger = new SkyLineMerger();
	
	private static SkyLine mergeSkyLine (ArrayList<SkyLine> skyLineList) {
		
		int size = skyLineList.size();
		if (size <= 1) {
			return skyLineList.get(0);
		}
		int midIndex = size / 2;
		SkyLine leftSkyLine = mergeSkyLine(new ArrayList<>(skyLineList.subList(0, midIndex)));
		SkyLine rightSkyLine = mergeSkyLine(new ArrayList<>(skyLineList.subList(size / 2, size)));


		
		return merger.merge(leftSkyLine, rightSkyLine);
	}
	
}
