package main;

import main.skyline.Skyline;
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

		String InputPath = "c:/hw2/input.txt";
		String OutputPath = "c:/hw2/2013147550.txt";
		
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
			//삼각형 하나를 skyline으로 만들어서 n개의 skyline을 merge한다.
			int indexOfInput = 0;
			int numOfTest = Integer.parseInt(inputlist.get(indexOfInput++));
			DecimalFormat format = new DecimalFormat("0.#");
			for (int indexOfTest = 0; indexOfTest < numOfTest; indexOfTest++) {
				int numOfSkyLine = Integer.parseInt(inputlist.get(indexOfInput++));
				ArrayList<Skyline> skylineList = new ArrayList<>();
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
					skylineList.add(new Skyline(lineList));
				}
				
				Skyline result = mergeSkyLine(skylineList);
				
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
	}

	private static SkyLineMerger merger = new SkyLineMerger();
	
	//divide and conquer 구현 재귀함수
	private static Skyline mergeSkyLine (ArrayList<Skyline> skylineList) {
		
		int size = skylineList.size();
		if (size <= 1) {
			return skylineList.get(0);
		}
		int midIndex = size / 2;
		Skyline leftSkyline = mergeSkyLine(new ArrayList<>(skylineList.subList(0, midIndex)));
		Skyline rightSkyline = mergeSkyLine(new ArrayList<>(skylineList.subList(midIndex, size)));

        return merger.merge(leftSkyline, rightSkyline);
	}
	
}
