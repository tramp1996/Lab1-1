package shiyan1;

import java.io.*;
import java.util.regex.*;
import java.util.Scanner;

public class Read {
	public static void main(String[] args) throws IOException {
		show();
		/*
		 * String str = new String(); try { Reader reader = new
		 * FileReader("input_text.txt"); int ch = reader.read(); StringBuffer
		 * buffer = new StringBuffer(); while (ch != -1) { // 读取成功
		 * buffer.append((char) ch); ch = reader.read(); } str =
		 * change(buffer.toString()); str = filter(str);
		 * System.out.println(str);
		 * 
		 * Picture myPicture = new Picture(str); myPicture.showPicture();
		 * Scanner scanner = new Scanner(System.in);
		 * System.out.println("请输入桥接词的两侧词语:"); String s1 = scanner.nextLine();
		 * String s2 = scanner.nextLine();
		 * 
		 * myPicture.Bridge(s1,s2); System.out.println("请输入根据桥接词生成文本的初始文本:");
		 * String s3 = scanner.nextLine(); myPicture.newtext(s3);
		 * myPicture.emptyQueue(); System.out.println("请输入查询最短路径的两个词语:"); s1 =
		 * scanner.nextLine(); s2 = scanner.nextLine(); myPicture.shortroad(s1,
		 * s2); //if (myPicture.calcShortestPath(s1, s2) &&
		 * myPicture.showShortestPath(s1, s2) != 0)
		 * //System.out.println("最短路径长度是" + myPicture.minlen); String s4 =
		 * myPicture.randomWalk(); System.out.println("随机游走结果:" + s4);
		 * write(s4); reader.close(); scanner.close(); } catch
		 * (FileNotFoundException e) { System.out.println("要读取的文件不存在：" +
		 * e.getMessage()); } catch (IOException e) {
		 * System.out.println("文件读取错误：" + e.getMessage()); }
		 */
	}

	@SuppressWarnings("static-access")
	public static void show() {
		String str = new String();
		GraphViz myGraph = new GraphViz();
		try {
			Reader reader = new FileReader("input_text.txt");
			int ch = reader.read();
			StringBuffer buffer = new StringBuffer();
			while (ch != -1) { // 读取成功
				buffer.append((char) ch);
				ch = reader.read();
			}
			str = change(buffer.toString());
			str = filter(str);
			System.out.println(str);

			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("要读取的文件不存在：" + e.getMessage());
		} catch (IOException e) {
			System.out.println("文件读取错误：" + e.getMessage());
		}
		Scanner scanner = new Scanner(System.in);
		String s, s1, s2, s3, s4;
		while (true) {
			System.out.println("功能如下:\n1.退出\n2.生成图\n3.展示图\n4.查询桥接词\n5.生成新文本\n6.查询最短路径\n7.查询一个单词到其他单词最短路径\n8.随机游走");
			System.out.println("请输入功能序号:");
			s = scanner.nextLine();
			Picture myPicture = new Picture(str);
			switch (s) {
			case "1":
				System.out.println("退出成功!");
				scanner.close();
				return;
			case "2": {
				// System.out.println(myPicture.graph());
				myGraph.createDotGraph(myPicture.graph(), "Graph1");
				System.out.println("生成成功!");
				break;
			}
			case "3": {
				myPicture.showPicture();
				break;
			}
			case "4": {
				System.out.println("请输入桥接词的两侧词语:");
				s1 = scanner.nextLine();
				s2 = scanner.nextLine();
				myPicture.Bridge(s1, s2);
				break;
			}
			case "5": {
				System.out.println("请输入根据桥接词生成文本的初始文本:");
				s3 = scanner.nextLine();
				s3 = change(s3);
				s3 = filter(s3);
				myPicture.newtext(s3);
				break;
			}

			case "6": {
				System.out.println("请输入查询最短路径的两个词语:");
				s1 = scanner.nextLine();
				s2 = scanner.nextLine();
				myPicture.shortroad(s1, s2);
				System.out.println("是否生成图像？Y/N");
				s2 = scanner.nextLine();
				if (s2.equals("Y")) {
					myGraph.createDotGraph(myPicture.shortRoadGraph(), "Graph2");
					System.out.println("图像生成成功!");
				}
				break;
			}
			case "7": {
				System.out.println("请输入查询最短路径的一个词语:");
				s1 = scanner.nextLine();
				myPicture.shortroad2(s1);
				break;
			}
			case "8": {
				System.out.println("请输入起始位置:");
				s3 = scanner.nextLine();
				s4 = myPicture.randomWalk(s3);
				System.out.println("随机游走结果:" + s4);
				System.out.println("是否写入文件？Y/N");
				s2 = scanner.nextLine();
				if (s2.equals("Y"))
					write(s4);
				System.out.println("是否生成图像？Y/N");
				s2 = scanner.nextLine();
				if (s2.equals("Y"))
					myGraph.createDotGraph(myPicture.randomGraph(s4.split("\\s+")), "Graph3");
				break;
			}
			default:
				System.out.println("请勿输入其他字符!");
			}
		}
	}

	public static String change(String s) {
		// 替换非字母字符为空格
		String REGEX = "[^A-Za-z]";
		String REPLACE = " ";
		Pattern p = Pattern.compile(REGEX);
		Matcher m = p.matcher(s);
		s = m.replaceAll(REPLACE);
		// 大写转化小写
		String Lowstr = s.toLowerCase();
		return Lowstr;
	}

	public static String filter(String s) {
		// 删除多余的空格
		String REGEX = "  ";
		String REPLACE = " ";
		Pattern p = Pattern.compile(REGEX);
		while (true) {
			Matcher m = p.matcher(s);
			if (!m.find())
				break;
			s = m.replaceAll(REPLACE);
		}
		return s;
	}

	public static void write(String s) {
		try {
			File file = new File("output_text.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fileWritter = new FileWriter(file.getName(), false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(s);
			bufferWritter.close();
			System.out.println("写入成功!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}