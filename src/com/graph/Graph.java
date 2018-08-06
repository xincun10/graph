package com.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.junit.Test;

/**
 * ����ͼ�Ľṹ
 * @author carazheng
 *
 */
public class Graph {
	public static final boolean UNDIRECTED_GRAPH = false;//����ͼ
	public static final boolean DIRECTED_GRAPH = true;//����ͼ
	
	public static final boolean ADJACENCY_MATRIX = true;//�ڽӾ���ʵ��
	public static final boolean ADJACENCY_LIST = false;//�ڽӱ�ʵ��
	
	public static final int MAX_VALUE = Integer.MAX_VALUE;
	
	private boolean graphType;//ͼ������
	private boolean method;//ͼ��ʵ�ַ�ʽ
	private int vertexSize;//������Ŀ
	private int matrixMaxVertex;//ͼ�Ķ����������
	
	//�洢������Ϣ
	private Object[] vertexesArray;
	//�洢����֮�������ϵ�Ķ�ά����
	private int[][] edgesMatrix;
	//��¼�Ƿ񱻷��ʹ�
	private boolean[] visited;
	/**
	 * ��ʼ��ͼ
	 * @param graphType ͼ�����ͣ�����ͼ������ͼ
	 * @param method ͼ��ʵ�ַ�ʽ���ڽӾ����ڽӱ�
	 * @param size �����������
	 */
	public Graph(boolean graphType, boolean method, int size) {
		// TODO Auto-generated constructor stub
		this.graphType = graphType;
		this.method = method;
		this.matrixMaxVertex = size;
		this.vertexSize = 0;//ʵ�ʶ�������
		
		if(this.method)
		{
			this.visited = new boolean[this.matrixMaxVertex];
			this.vertexesArray = new Object[this.matrixMaxVertex];
			this.edgesMatrix = new int[this.matrixMaxVertex][this.matrixMaxVertex];
			//��ʼ����������
			for(int i=0; i<this.edgesMatrix.length; i++)
			{
				for(int j=0; j<this.edgesMatrix.length; j++)
				{
					this.edgesMatrix[i][j] = MAX_VALUE;
				}
			}
		}
	}
	/**
	 * ���һ������
	 * @param val
	 * @return
	 */
	public boolean addVertex(Object val)
	{
		assert (val!=null);
		this.vertexesArray[this.vertexSize] = val;
		this.vertexSize++;
		return true;
	}
	/**
	 * ���һ����
	 * @param start
	 * @param end
	 * @return
	 */
	public boolean addEdge(int start, int end)
	{
		assert (start>=0 && end>=0 && start!=end);
		//�ж��Ƿ�������ͼ
		if(graphType)
		{
			this.edgesMatrix[start][end] = 1;
		}
		else
		{
			this.edgesMatrix[start][end] = 1;
			this.edgesMatrix[end][start] = 1;
		}
		return true;
	}
	/*
	 * ������Ⱥ͹�����ȱ����㷨
	 * �ǵݹ�ʵ��
	 *
	 */
	/**
	 * �ǵݹ鷽ʽʵ��������ȱ���
	 * @return
	 */
	public Queue<Integer> DFS()
	{
		//��ʱ�洢�ڵ�����
		Stack<Integer> stack = new Stack<Integer>();
		//�洢���ս��
		Queue<Integer> queue = new LinkedList<>();
		//��ʼ����������
		for(int i=0; i<this.vertexSize; i++)
		{
			this.visited[i] = false;
		}
		for(int i=0; i<vertexSize; i++)
		{
			if(!visited[i])
			{
				//û�з��ʹ��Ľڵ���ѹջ
				stack.push(i);
				//���ʱ�־��Ϊtrue
				this.visited[i] = true;
				while(!stack.isEmpty())
				{
					//�ȵ�ջ�������
					int tmp = stack.pop();
					queue.add(tmp);
					//�����һ���ڵ㿪ʼ�������������ڵ���ɱߵĶ�ѹջ
					for(int j=vertexSize-1; j>=0; j--)
					{
						if(this.edgesMatrix[tmp][j]==1 && this.visited[j]==false)
						{
							stack.push(j);
							this.visited[j] = true;
						}
					}
				}				
			}
		}
		return queue;
	}
	/**
	 * �ǵݹ�ʵ��ͼ�Ĺ�����ȱ���
	 * @return
	 */
	public Queue<Integer> BFS()
	{
		Queue<Integer> queue = new LinkedList<>();
		Queue<Integer> queue2 = new LinkedList<>();
		//��ʼ����������
		for(int i=0; i<this.vertexSize; i++)
		{
			this.visited[i] = false;
		}
		for(int i=0; i<this.vertexSize; i++)
		{
			if(!visited[i])
			{
				queue2.add(i);
				visited[i] = true;
				//�ӵ�һ���ڵ㿪ʼ�������ͽڵ���ɱߵĶ����
				while(!queue2.isEmpty())
				{
					int tmp = queue2.poll();
					queue.add(tmp);
					for(int j=0; j<vertexSize; j++)
					{
						if(this.edgesMatrix[tmp][j]==1 && this.visited[j]==false)
						{
							queue2.add(j);
							this.visited[j] = true;
						}
					}
				}
			}
		}
		return queue;
	}
	
	/*
	 * ����С������
	 * 
	 */
	/**
	 * Prim�㷨
	 * @return
	 */
	public Queue<Integer> prim()
	{
		Queue<Integer> queue = new LinkedList<>();
		//��С���Ķ���
		int start = 0;
		//�洢����i���������СȨֵ
		int[] weight = new int[this.vertexSize];
		//��ʼ�������Ȩֵ����
		//��ÿ�������Ȩֵ��ʼ��Ϊ��start�����㵽�ö����Ȩֵ
		for(int i=0; i<this.vertexSize; i++)
		{
			weight[i] = this.edgesMatrix[start][i];
		}
		weight[start] = 0;
		queue.add(start);
		
		for(int j=1; j<vertexSize; j++)
		{
			int index = 0;
			int min = MAX_VALUE;
			//����weight���飬�ҳ����е���Сֵ���뵽������
			for(int i=0; i<vertexSize; i++)
			{				
				if(weight[i]!=0 && weight[i]<min)
				{
					//��weight[i]=0����ζ�ŵ�i���ڵ��Ѿ����뵽���е�����
					min = weight[i];
					index = i;
				}
			}
			weight[index] = 0;
			queue.add(index);
			//����weight����
			for(int i=0; i<vertexSize; i++)
			{
				//�ڵ�û�мӵ����е��У���С���¼���ڵ�Ȩֵ�������
				if(weight[i]!=0 && weight[i]>edgesMatrix[index][i])
				{
					weight[i] = edgesMatrix[index][i];
				}
			}
		}
		
		return queue;
	}
	/*
	 * ��³˹����(Kruskal)�㷨
	 * 
	 */
	//�����
	private static class EData implements Comparable<EData>
	{
		Object start;//�ߵ����
		Object end;//�ߵ��յ�
		int weight;//�ߵ�Ȩ��
		
		public EData(Object start, Object end, int weight) {
			this.start = start;
			this.end = end;
			this.weight = weight;
		}

		@Override
		public int compareTo(EData edge) {
			// TODO Auto-generated method stub
			return Integer.compare(this.weight, edge.weight);
		}
		
	}
	//��ȡ���б�
	private ArrayList<EData> getEdges()
	{
		ArrayList<EData> edatas = new ArrayList<>();
		for(int i=0; i<vertexSize; i++)
		{
			for(int j=i+1; j<vertexSize; j++)
			{
				if(edgesMatrix[i][j] != MAX_VALUE)
				{
					EData tmp = new EData(vertexesArray[i], vertexesArray[j], edgesMatrix[i][j]);
					edatas.add(tmp);
				}
			}
		}
		return edatas;
	}
	/*
     * ��ȡi���յ�
     */
    private int getEnd(int[] vends, int i) {
        while (vends[i] != 0)
            i = vends[i];
        return i;
    }
    /*
     * ���ض���λ��
     */
    private int getPosition(Object o) {
        for(int i=0; i<vertexSize; i++)
            if(vertexesArray[i].equals(o))
                return i;
        return -1;
    }
	/**
	 * ��³˹����(Kruskal)�㷨
	 * @return
	 */
	public ArrayList<EData> kruskal()
	{
		//��ȡ���еı�
		ArrayList<EData> edges = this.getEdges();
		//�����еı߽�������
		Collections.sort(edges);
		// ���ڱ���"������С������"��ÿ�������ڸ���С���е��յ㡣
		int[] vends = new int[vertexSize];
		// ������飬����kruskal��С�������ı�
		ArrayList<EData> rets = new ArrayList<>();
		int index = 0;
				
		for(int i=0; i<edges.size(); i++)
		{
			//��ȡ�������յ�
			int start = getPosition(edges.get(i).start);
			int end = getPosition(edges.get(i).end);
			// ��ȡstart��"���е���С������"�е��յ�
			int m = getEnd(vends, start);
            // ��ȡend��"���е���С������"�е��յ�
            int n = getEnd(vends, end);
            // ���m!=n����ζ��"��i"��"�Ѿ���ӵ���С�������еĶ���"û���γɻ�·
            if (m != n) {
                vends[m] = n;                       // ����m��"���е���С������"�е��յ�Ϊn
                rets.add(edges.get(i));          // ������
            }
		}
		return rets;
	}
	
	//���Ա�������
	@Test
	public void test1()
	{
		Graph g = new Graph(Graph.DIRECTED_GRAPH, Graph.ADJACENCY_MATRIX, 6);
		g.addVertex("1");
		g.addVertex("2");
		g.addVertex("3");
		g.addVertex("4");
		g.addVertex("5");
		g.addVertex("6");
		 
		g.addEdge(0, 1);
		g.addEdge(0, 2);
		g.addEdge(1, 3);
		g.addEdge(1, 4);
		g.addEdge(2, 1);
		g.addEdge(2, 4);
		g.addEdge(3, 5);
		g.addEdge(2, 4);
		g.addEdge(4, 5);
		
		Queue<Integer> q = g.BFS();
		while(!q.isEmpty())
		{
			System.out.println(q.poll());
		}
	}
	//������С������
	public static void main(String[] args)
	{
		Graph g = new Graph(Graph.DIRECTED_GRAPH, Graph.ADJACENCY_MATRIX, 7);
		g.addVertex("A");
		g.addVertex("B");
		g.addVertex("C");
		g.addVertex("D");
		g.addVertex("E");
		g.addVertex("F");
		g.addVertex("G");
		
		int tmp[][] = {
                /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
         /*A*/ {   0,  12, MAX_VALUE, MAX_VALUE, MAX_VALUE,  16,  14},
         /*B*/ {  12,   0,  10, MAX_VALUE, MAX_VALUE,   7, MAX_VALUE},
         /*C*/ { MAX_VALUE,  10,   0,   3,   5,   6, MAX_VALUE},
         /*D*/ { MAX_VALUE, MAX_VALUE,   3,   0,   4, MAX_VALUE, MAX_VALUE},
         /*E*/ { MAX_VALUE, MAX_VALUE,   5,   4,   0,   2,   8},
         /*F*/ {  16,   7,   6, MAX_VALUE,   2,   0,   9},
         /*G*/ {  14, MAX_VALUE, MAX_VALUE, MAX_VALUE,   8,   9,   0}};
		g.edgesMatrix = tmp;
		
//		Queue<Integer> q = g.prim();
//		while(!q.isEmpty())
//		{
//			System.out.println(q.poll());
//		}
		ArrayList<EData> results = g.kruskal();
		for(EData e:results)
		{
			System.out.println(e.start+","+e.end);
		}
	}
}
