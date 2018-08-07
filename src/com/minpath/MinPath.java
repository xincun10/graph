package com.minpath;

import com.graph.Graph;

/**
 * 求最短路径的算法
 * 1.dijkstra算法
 * 2.Floyd算法 
 * @author carazheng
 *
 */
public class MinPath {
	public static final int MAX_VALUE = Integer.MAX_VALUE;

	/**
	 * dijkstra算法
	 * @param start 起点
	 * @param graph 图
	 * @return 返回最短路径长度
	 */
	public int[] dijkstra(int start, Graph graph)
	{
		//距离数组
		int[] dist = new int[graph.vertexSize];
		//节点是否已在最短路径标志
		boolean[] flag = new boolean[graph.vertexSize];
		
		//初始化距离数组和标志数组
		for(int i=0; i<graph.vertexSize; i++)
		{
			dist[i] = graph.edgesMatrix[start][i];
			flag[i] = false;
		}
		dist[start] = 0;
		flag[start] = true;
		
		//定义循环次数
		int times = 1;
		while(times++<graph.vertexSize)
		{
			//寻找dist数组中没有在最短路径中的最小值
			int min = Graph.MAX_VALUE;
			int index = -1;
			for(int i=0; i<graph.vertexSize; i++)
			{
				if(flag[i]==false && dist[i]<min)
				{
					min = dist[i];
					index = i;
				}
			}
			//更新数组
			dist[index] = min;
			flag[index] = true;
			
			//调整dist数组
			for(int i=0; i<graph.vertexSize; i++)
			{
				int tmp = graph.edgesMatrix[index][i];
				if(tmp!=Graph.MAX_VALUE && dist[i]>dist[index]+tmp)
				{
					dist[i] = dist[index]+tmp;
				}
			}
		}
		return dist;
	}
	/**
	 * floyd算法
	 * @param graph
	 * @return 返回路径
	 */
	public int[][] floyd(Graph graph)
	{
		//path[i][j]=k表示，"顶点i"到"顶点j"的最短路径会经过顶点k
		int[][] path = new int[graph.vertexSize][graph.vertexSize];
		//dist[i][j]=sum表示，"顶点i"到"顶点j"的最短路径的长度是sum
		int[][] dist = new int[graph.vertexSize][graph.vertexSize];
		
		//初始化数组
		for(int i=0; i<graph.vertexSize; i++)
		{
			for(int j=0; j<graph.vertexSize; j++)
			{
				dist[i][j] = graph.edgesMatrix[i][j];
				path[i][j] = j;
			}
		}
		
		//一共需要更新的次数是顶点的个数
		for(int i=0; i<graph.vertexSize; i++)
		{
			//计算最短路径，更新数组
			for(int j=0; j<graph.vertexSize; j++)
			{
				for(int k=0; k<graph.vertexSize; k++)
				{
					//比较j、k之间的距离与j经过i到k之间的距离
					int tmp = (dist[j][i]==MAX_VALUE || dist[i][k]==MAX_VALUE)? MAX_VALUE 
							: (dist[j][i]+dist[i][k]);
					if(tmp<dist[j][k])
					{
						dist[j][k] = tmp;
						path[j][k] = path[j][i];
					}
				}
			}
		}
		return path;
	}
	
	//测试dijkstra算法
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
		
		MinPath minPath = new MinPath();
//		int[] dist= minPath.dijkstra(3, g);
//		for(int i:dist)
//		{
//			System.out.println(i);
//		}
		int[][] path = minPath.floyd(g);
		for(int i=0; i<path.length; i++)
		{
			for(int j=0; j<path.length; j++)
			{
				System.out.printf("%2d", path[i][j]);
			}
			System.out.println();
		}
	}
}
