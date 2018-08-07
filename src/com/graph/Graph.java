package com.graph;

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
	
	public boolean graphType;//图的类型
	public boolean method;//图的实现方式
	public int vertexSize;//顶点数目
	public int matrixMaxVertex;//图的顶点最大数量
	
	//存储顶点信息
	public Object[] vertexesArray;
	//存储顶点之间关联关系的二维数组
	public int[][] edgesMatrix;
	//记录是否被访问过
	public boolean[] visited;
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
	
}
