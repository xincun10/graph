package com.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.junit.Test;

/**
 * 定义图的结构
 * @author carazheng
 *
 */
public class Graph {
	public static final boolean UNDIRECTED_GRAPH = false;//无向图
	public static final boolean DIRECTED_GRAPH = true;//有向图
	
	public static final boolean ADJACENCY_MATRIX = true;//邻接矩阵实现
	public static final boolean ADJACENCY_LIST = false;//邻接表实现
	
	public static final int MAX_VALUE = Integer.MAX_VALUE;
	
	private boolean graphType;//图的类型
	private boolean method;//图的实现方式
	private int vertexSize;//顶点数目
	private int matrixMaxVertex;//图的顶点最大数量
	
	//存储顶点信息
	private Object[] vertexesArray;
	//存储顶点之间关联关系的二维数组
	private int[][] edgesMatrix;
	//记录是否被访问过
	private boolean[] visited;
	/**
	 * 初始化图
	 * @param graphType 图的类型：有向图、无向图
	 * @param method 图的实现方式：邻接矩阵、邻接表
	 * @param size 顶点最大数量
	 */
	public Graph(boolean graphType, boolean method, int size) {
		// TODO Auto-generated constructor stub
		this.graphType = graphType;
		this.method = method;
		this.matrixMaxVertex = size;
		this.vertexSize = 0;//实际顶点数量
		
		if(this.method)
		{
			this.visited = new boolean[this.matrixMaxVertex];
			this.vertexesArray = new Object[this.matrixMaxVertex];
			this.edgesMatrix = new int[this.matrixMaxVertex][this.matrixMaxVertex];
			//初始化顶点数组
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
	 * 添加一个顶点
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
	 * 添加一条边
	 * @param start
	 * @param end
	 * @return
	 */
	public boolean addEdge(int start, int end)
	{
		assert (start>=0 && end>=0 && start!=end);
		//判断是否是有向图
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
	 * 深度优先和广度优先遍历算法
	 * 非递归实现
	 *
	 */
	/**
	 * 非递归方式实现深度优先遍历
	 * @return
	 */
	public Queue<Integer> DFS()
	{
		//临时存储节点数据
		Stack<Integer> stack = new Stack<Integer>();
		//存储最终结果
		Queue<Integer> queue = new LinkedList<>();
		//初始化访问数组
		for(int i=0; i<this.vertexSize; i++)
		{
			this.visited[i] = false;
		}
		for(int i=0; i<vertexSize; i++)
		{
			if(!visited[i])
			{
				//没有访问过的节点先压栈
				stack.push(i);
				//访问标志置为true
				this.visited[i] = true;
				while(!stack.isEmpty())
				{
					//先弹栈，入队列
					int tmp = stack.pop();
					queue.add(tmp);
					//从最后一个节点开始遍历，跟弹出节点组成边的都压栈
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
	 * 非递归实现图的广度优先遍历
	 * @return
	 */
	public Queue<Integer> BFS()
	{
		Queue<Integer> queue = new LinkedList<>();
		Queue<Integer> queue2 = new LinkedList<>();
		//初始化访问数组
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
				//从第一个节点开始遍历，和节点组成边的都入队
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
	 * 求最小生成树
	 * 
	 */
	/**
	 * Prim算法
	 * @return
	 */
	public Queue<Integer> prim()
	{
		Queue<Integer> queue = new LinkedList<>();
		//最小树的顶点
		int start = 0;
		//存储到第i个顶点的最小权值
		int[] weight = new int[this.vertexSize];
		//初始化顶点的权值数组
		//将每个顶点的权值初始化为第start个顶点到该顶点的权值
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
			//遍历weight数组，找出其中的最小值加入到队列中
			for(int i=0; i<vertexSize; i++)
			{				
				if(weight[i]!=0 && weight[i]<min)
				{
					//若weight[i]=0，意味着第i个节点已经加入到队列当中了
					min = weight[i];
					index = i;
				}
			}
			weight[index] = 0;
			queue.add(index);
			//更新weight数组
			for(int i=0; i<vertexSize; i++)
			{
				//节点没有加到队列当中，且小于新加入节点权值，则更新
				if(weight[i]!=0 && weight[i]>edgesMatrix[index][i])
				{
					weight[i] = edgesMatrix[index][i];
				}
			}
		}
		
		return queue;
	}
	/*
	 * 克鲁斯卡尔(Kruskal)算法
	 * 
	 */
	//定义边
	private static class EData implements Comparable<EData>
	{
		Object start;//边的起点
		Object end;//边的终点
		int weight;//边的权重
		
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
	//获取所有边
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
     * 获取i的终点
     */
    private int getEnd(int[] vends, int i) {
        while (vends[i] != 0)
            i = vends[i];
        return i;
    }
    /*
     * 返回顶点位置
     */
    private int getPosition(Object o) {
        for(int i=0; i<vertexSize; i++)
            if(vertexesArray[i].equals(o))
                return i;
        return -1;
    }
	/**
	 * 克鲁斯卡尔(Kruskal)算法
	 * @return
	 */
	public ArrayList<EData> kruskal()
	{
		//获取所有的边
		ArrayList<EData> edges = this.getEdges();
		//对所有的边进行排序
		Collections.sort(edges);
		// 用于保存"已有最小生成树"中每个顶点在该最小树中的终点。
		int[] vends = new int[vertexSize];
		// 结果数组，保存kruskal最小生成树的边
		ArrayList<EData> rets = new ArrayList<>();
		int index = 0;
				
		for(int i=0; i<edges.size(); i++)
		{
			//获取边起点和终点
			int start = getPosition(edges.get(i).start);
			int end = getPosition(edges.get(i).end);
			// 获取start在"已有的最小生成树"中的终点
			int m = getEnd(vends, start);
            // 获取end在"已有的最小生成树"中的终点
            int n = getEnd(vends, end);
            // 如果m!=n，意味着"边i"与"已经添加到最小生成树中的顶点"没有形成环路
            if (m != n) {
                vends[m] = n;                       // 设置m在"已有的最小生成树"中的终点为n
                rets.add(edges.get(i));          // 保存结果
            }
		}
		return rets;
	}
	
	//测试遍历方法
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
	//测试最小生成树
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
