package main;

public class MainApp {

	public static void main(String[] args) {
		/*
		String InputPath = "C:/hw2/input.txt";
		String OutputPath = "C:/hw2/2013147550.txt";
		
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
			
			
			OutputString = builder.toString();
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
		*/

		//테스트 코드
		//선분 두 개 생성해서 교점 출력
		DoublePair p1 = new DoublePair(1, 2);
		DoublePair p2 = new DoublePair(4, 0);
		Line l1 = new Line(p1, p2);
		DoublePair p3 = new DoublePair(2, 0);
		DoublePair p4 = new DoublePair(2, 2);
		Line l2 = new Line(p3, p4);
		DoublePair inter = l1.hasIntersect(l2);
		System.out.println(inter.x + " " + inter.y);
	}

}
