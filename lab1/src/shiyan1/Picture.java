package shiyan1;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Picture { // 以邻接表方式构建图
	private class ENode {
		String ivex; // 该边所指向的顶点
		int weight; // 权重
		ENode nextEdge; // 指向下一条弧的指针
		VNode father = null; // 父节点，便于遍历图时寻找最短路径
		int visit = 0;
		int num;
	}

	// 邻接表中表的顶点
	private class VNode {
		String data; // 顶点单词
		ENode firstEdge; // 指向第一条依附该顶点的弧
		VNode father;
		int num; // 序号
		int visit = 0; //便于随机游走查看是否访问过
	};

	private int slen;
	private int elen;
	private static VNode[] mVexs; // 顶点数组
	private ENode[] bridgeWord;
	private int bridgeCount = 0;

	public Picture(String s) {
		// 确定顶点和边的数量
		String[] splited = s.split("\\s+");
		String[] splited_filter = new String[100];
		boolean sign = true;
		slen = 0; // 真正顶点的数量
		int vlen = splited.length; // 计算重复顶点的数量
		elen = vlen - 1; // 边的数量
		if (vlen < 1 || elen < 1 || (elen > (vlen * (vlen - 1)))) {
			System.out.printf("图构建错误！\n");
			return;
		}
		// 排除相同单词的顶点
		for (int i = 0; i < vlen; i++) {
			for (int k = 0; k < slen; k++) {
				if (splited_filter[k].equals(splited[i])) {
					sign = false;
					break;
				}
			}
			if (sign == true) {
				splited_filter[slen] = splited[i];
				slen++;
			}
			sign = true;
		}
		// 顶点赋值
		mVexs = new VNode[slen];
		for (int i = 0; i < mVexs.length; i++) {
			mVexs[i] = new VNode();
			mVexs[i].data = splited_filter[i];
			mVexs[i].firstEdge = null;
		}
		// 初始化边
		ENode myNode = new ENode();
		ENode q = new ENode();
		sign = true;
		for (int i = 0; i < elen; i++) {
			for (int k = 0; k < slen; k++) {
				if (splited[i].equals(mVexs[k].data)) {
					myNode = new ENode();
					myNode.ivex = splited[i + 1];
					if (mVexs[k].firstEdge == null) {
						mVexs[k].firstEdge = myNode;
						myNode.nextEdge = null;
						myNode.weight = 1;
					} else {
						q = mVexs[k].firstEdge;
						while (q != null) {
							// System.out.println(q.ivex);
							if (q.ivex.equals(splited[i + 1])) {
								q.weight++;
								sign = false;
								break;
							}
							q = q.nextEdge;
						}
						if (sign == true) {
							q = mVexs[k].firstEdge;
							while (q.nextEdge != null) {
								q = q.nextEdge;
							}
							q.nextEdge = myNode;
							myNode.nextEdge = null;
							myNode.weight = 1;
						}
						sign = true;
						break;
					}
				}
			}
		}
	}

	public void showPicture() {
		ENode myENode;
		for (int i = 0; i < slen; i++) {
			System.out.println("顶点:" + mVexs[i].data + " 边:");
			myENode = new ENode();
			myENode = mVexs[i].firstEdge;
			while (myENode != null) {
				System.out.println(myENode.ivex + "," + myENode.weight + " ");
				myENode = myENode.nextEdge;
			}
		}
	}

	public void Bridge(String s1, String s2) {
		ENode myENode = new ENode();
		ENode q = new ENode();
		boolean sign = false, sign1 = false, sign2 = false;
		for (int i = 0; i < slen; i++) {
			if (s1.equals(mVexs[i].data)) {
				sign1 = true;
			}
			if (s2.equals(mVexs[i].data)) {
				sign2 = true;
			}
		}
		if (sign1 == true && sign2 == true) {
			for (int i = 0; i < slen; i++) {
				if (s1.equals(mVexs[i].data)) {
					if (mVexs[i].firstEdge != null) {
						myENode = mVexs[i].firstEdge;
						sign = true;
					} else
						sign = false;
					break;
				}
			}
			if (sign == true) {
				sign = false;
				while (true) {
					for (int k = 0; k < slen; k++) {
						if (myENode.ivex.equals(mVexs[k].data)) {
							if (mVexs[k].firstEdge != null)
								q = mVexs[k].firstEdge;
							if (q.ivex.equals(s2)) {
								System.out.println("The bridge words from " + s1 + " to " + s2 + " are:" + myENode.ivex);
								sign = true;
								break;
							}
							while (q != null) {
								if (q.ivex.equals(s2)) {
									System.out.println("The bridge words from " + s1 + " to " + s2 + " are:" + myENode.ivex);
									sign = true;
									break;
								}
								q = q.nextEdge;
							}
							break;
						}
					}
					myENode = myENode.nextEdge;
					if (myENode == null)
						break;
				}
				if (sign == false)
					System.out.println("No bridge words from " + s1 + " to " + s2);
			}
		} else {
			System.out.println("No " + s1 + " or " + s2 + " in the graph!");
		}
	}

	public ENode[] Bridge2(String s1, String s2) {
		ENode[] bridgeWord = new ENode[100];
		bridgeCount = 0;
		ENode myENode = new ENode();
		ENode q = new ENode();
		boolean sign = false, sign1 = false, sign2 = false;
		for (int i = 0; i < slen; i++) {
			if (s1.equals(mVexs[i].data)) {
				sign1 = true;
			}
			if (s2.equals(mVexs[i].data)) {
				sign2 = true;
			}
		}
		if (sign1 == true && sign2 == true) {
			for (int i = 0; i < slen; i++) {
				if (s1.equals(mVexs[i].data)) {
					if (mVexs[i].firstEdge != null) {
						myENode = mVexs[i].firstEdge;
						sign = true;
					} else
						sign = false;
					break;
				}

			}
			if (sign == true) {
				sign = false;
				while (true) {
					for (int k = 0; k < slen; k++) {
						if (myENode.ivex.equals(mVexs[k].data)) {
							if (mVexs[k].firstEdge != null)
								q = mVexs[k].firstEdge;
							if (q.ivex.equals(s2)) {
								// System.out.println("The bridge words from " +
								// s1 + " to " + s2 + " are:" + myENode.ivex);
								bridgeWord[bridgeCount++] = myENode;
								sign = true;
								break;
							}
							while (q != null) {
								if (q.ivex.equals(s2)) {
									// System.out.println("The bridge words from
									// " + s1 + " to " + s2 + " are:" +
									// myENode.ivex);
									bridgeWord[bridgeCount++] = myENode;
									sign = true;
									break;
								}
								q = q.nextEdge;
							}
							break;
						}
					}
					myENode = myENode.nextEdge;
					if (myENode == null)
						break;
				}
			}
		}
		return bridgeWord;
	}

	public void newtext(String s) {
		String[] splited = s.split("\\s+");
		String[] splitedChange = new String[2 * splited.length];
		Random random = new Random();
		int count = 0;
		for (int i = 0; i < splited.length - 1; i++) {
			splitedChange[count++] = splited[i];
			Bridge2(splited[i], splited[i + 1]);
			if (bridgeCount != 0) {
				splitedChange[count++] = Bridge2(splited[i], splited[i + 1])[random.nextInt(bridgeCount)].ivex;
			}
		}
		splitedChange[count++] = splited[splited.length - 1];
		for (int i = 0; i < count; i++) {
			System.out.print(splitedChange[i] + " ");
		}
		System.out.print("\n");
	}

	int[] minlength = new int[200];
	int minlen = 1000000, count = 1;
	private ENode[] queue = new ENode[100];
	private int queueFront = 0, queueBack = 0;

	public void inQueue(ENode n) {
		queue[queueBack++] = n;
	}

	public void outQueue() {
		queueFront++;
	}

	public void emptyQueue() {
		queueFront = 0;
		queueBack = 1;
	}

	public void showQueue() {
		if (queueFront - queueBack >= 1) {
			for (int i = queueFront + 1; i <= queueBack; i++)
				System.out.print(queue[i].ivex + "*");
		} else
			System.out.print("队列空!\n");
	}

	public boolean calcShortestPath(String s1, String s2) {
		boolean sign1 = false, sign2 = false;
		for (int i = 0; i < mVexs.length; i++) {
			if (s1.equals(mVexs[i].data))
				sign1 = true;
			if (s2.equals(mVexs[i].data))
				sign2 = true;
		}
		if (sign1 && sign2) {
			calcShort(s1, s2);
			return true;
		} else {
			System.out.println("未找到节点!");
			return false;
		}
	}

	public void calcShort(String s1, String s2) {
		ENode q = new ENode();
		for (int i = 0; i < mVexs.length; i++) {
			if (s1.equals(mVexs[i].data)) {
				// mVexs[i].num = count;
				//System.out.println(count);
				if (s1.equals(s2)) {
					minlen = minlength[count - 1];
					return;
				}
				if (minlength[count] >= minlen) {
					return;
				}
				if (mVexs[i].firstEdge == null) {
					minlen = minlength[count - 1];
					return;
				}
				q = mVexs[i].firstEdge;
				while (q != null) {
					// System.out.println("q:"+q.ivex+"s1:"+s1);
					for (int k = 0; k < mVexs.length; k++) {
						if (q.ivex.equals(mVexs[k].data)) {
							mVexs[k].num = count;
							mVexs[k].father = mVexs[i];
							break;
						}
					}
					q.father = mVexs[i];
					if (q.father == null)
						minlength[count] += q.weight;
					else {
						minlength[count] += minlength[q.father.num] + q.weight;
						//System.out.println(q.ivex + "," + q.father.data + "," + q.father.num);
						// System.out.println(mVexs[i].data+":"+minlength[count]+"
						// count:"+count);
					}
					inQueue(q);
					count++;
					q.num = count;
					q = q.nextEdge;
				}
				outQueue();
				break;
			}
		}
		calcShort(queue[queueFront].ivex, s2);
	}

	public int showShortestPath(String s1, String s2) {
		VNode q = new VNode();
		String[] path = new String[100];
		int cnt = 0;
		for (int i = 0; i < mVexs.length; i++) {
			if (s2.equals(mVexs[i].data)) {
				q = mVexs[i];
				if (q.father == null)
				{
					System.out.println("不可达");
					return 0;
				}
				while (!q.data.equals(s1)) {
					path[cnt++] = q.data;
					q = q.father;
				}
				path[cnt] = s1;
				break;
			}
		}
		System.out.print("最短路径为:");
		for (int i = cnt; i >= 0; i--)
			System.out.print(path[i] + " ");
		return minlen;
	}
	
	public String randomWalk()
	{
		boolean sign = false;
		String randomS = new String();
		System.out.println("请输入起始位置:");
		Scanner scanner = new Scanner(System.in);
		String s = scanner.nextLine();
		scanner.close();
		for (int i = 0; i < mVexs.length; i++) {
			if (s.equals(mVexs[i].data)) 
			{
				sign = true;
				String s1_pre = s;
				String s1_aft = s;
				randomS += s;
				while (true) {
					s1_aft = randomWalk_s(s1_pre);
					if (s1_pre.equals(s1_aft))
					{
						break;
					}
					randomS += " ";
					randomS += s1_aft;
					s1_pre = s1_aft;
				}
				break;
			}
		}
		if (sign == false)
			System.out.println("输入的位置有误!");
		return randomS;
	}
	
	public String randomWalk_s(String s)
	{
		ENode q = new ENode();
		ENode[] rand = new ENode[100];
		Random random = new Random();
		int cnt = 0;	
		for (VNode mVex : mVexs) {
			if (s.equals(mVex.data))
			{
				if(mVex.firstEdge == null)
				{
					System.out.println("没有出边!");
					return s;
				}
				q = mVex.firstEdge;
				while (q != null)
				{
					rand[cnt++] = q;
					q = q.nextEdge;
				}
				q = rand[random.nextInt(cnt)];
				if (!visited(s,q.ivex))
				{
					System.out.println("重复边:"+s+"->"+q.ivex);
					return s;
				}
				q.visit = 1;
				return q.ivex;
			}
		}
		return s;
	}
	/*
	public String randomWalk_k(String s)
	{
		ENode q = new ENode();
		ENode[] rand = new ENode[100];
		Random random = new Random();
		String s1 = new String();
		int cnt = 0;	
		for (VNode mVex : mVexs) {
			if (s.equals(mVex.data))
			{
				if (mVex.visit == 1)
					return s;
				mVex.visit = 1;
				if(mVex.firstEdge == null)
					return s;
				q = mVex.firstEdge;
				while (q != null)
				{
					rand[cnt++] = q;
					q = q.nextEdge;
				}
				return rand[random.nextInt(cnt)].ivex;
			}
		}
		return s1;
	}
	*/
	
	public void shortroad(String s1,String s2)
	{
		int sign1=0,sign2=0;
		ENode q= new ENode();
		int[][] sr=new int[mVexs.length][mVexs.length];
		int[][] rr= new int[mVexs.length][mVexs.length];
		for(int i = 0;i<mVexs.length;i++)
		{
			for(int j = 0;j<mVexs.length;j++)
			{
				rr[i][j]=-1;
				sr[i][j]=100;
			}
		}
		for(int i = 0;i<mVexs.length;i++)
		{
			q=mVexs[i].firstEdge;
			while(q!=null)
			{
				for(int j=0;j<mVexs.length;j++)
				{
					if(q.ivex.equals(mVexs[j].data))
					{
						sr[i][j]=q.weight;
						q=q.nextEdge;
						break;
					}
						
					
				}
			}
		}
		for(int h = 0;h<mVexs.length;h++)
		{
			for(int i = 0;i<mVexs.length;i++)
			{
				for(int j = 0;j<mVexs.length;j++)
				{
					if(sr[i][j]>(sr[i][h]+sr[h][j]))
					{
						sr[i][j]=(sr[i][h]+sr[h][j]);
						rr[i][j]=h;
					}					
				}
			}
		}
		for (int i = 0; i < slen; i++) {
			if (s1.equals(mVexs[i].data)) {
				sign1 = i;
			}
			if (s2.equals(mVexs[i].data)) {
				sign2 = i;
			}
		}
		System.out.println("最短路径长度:"+sr[sign1][sign2]);
		System.out.print("最短路径是:"+mVexs[sign1].data+" ");
		sout(sign1,sign2,sr,rr);
		System.out.print("\n");
		/*String[] ss = new String[100];
		int cnt = 0;
		while(rr[sign1][sign2] != -1)
		{
			ss[cnt++] = mVexs[rr[sign1][sign2]].data;
			sign2=rr[sign1][sign2];
		}
		for(int i = cnt - 1; i >= 0; i--)
			System.out.print(ss[i]+" ");
		System.out.print(s2+"\n");*/
	}
	public void sout(int s1,int s2,int[][] sr,int[][] rr)
	{
		int tmp;
		if(rr[s1][s2]==-1)
			System.out.print(mVexs[s2].data+" ");
		else
		{
			tmp=rr[s1][s2];
			sout(s1,tmp,sr,rr);
			sout(tmp,s2,sr,rr);
		}
	}
	public boolean visited(String s1, String s2)
	{
		ENode q = new ENode();
		for (VNode mVex : mVexs) {
			if(mVex.data.equals(s1))
			{
				q = mVex.firstEdge;
				while (q != null)
				{
					if(q.ivex.equals(s2))
					{
						if(q.visit == 0)
							return true;
						else
							return false;	
					}
					q = q.nextEdge;
				}
				break;
			}
		}
		return false;
	}
	public String graph()
	{
		ENode q = new ENode();
		String s = new String();
		for(VNode mVex : mVexs)
		{
			q = mVex.firstEdge;
			while(q != null)
			{
				s += mVex.data + "->" + q.ivex + "[label="+q.weight+"]"+";";
				q = q.nextEdge;
			}
		}
		return s;
	}
}
