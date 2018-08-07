package com.minpath;

import com.graph.Graph;

/**
 * �����·�����㷨
 * 1.dijkstra�㷨
 * 2.Floyd�㷨 
 * @author carazheng
 *
 */
public class MinPath {
	public static final int MAX_VALUE = Integer.MAX_VALUE;

	/**
	 * dijkstra�㷨
	 * @param start ���
	 * @param graph ͼ
	 * @return �������·������
	 */
	public int[] dijkstra(int start, Graph graph)
	{
		//��������
		int[] dist = new int[graph.vertexSize];
		//�ڵ��Ƿ��������·����־
		boolean[] flag = new boolean[graph.vertexSize];
		
		//��ʼ����������ͱ�־����
		for(int i=0; i<graph.vertexSize; i++)
		{
			dist[i] = graph.edgesMatrix[start][i];
			flag[i] = false;
		}
		dist[start] = 0;
		flag[start] = true;
		
		//����ѭ������
		int times = 1;
		while(times++<graph.vertexSize)
		{
			//Ѱ��dist������û�������·���е���Сֵ
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
			//��������
			dist[index] = min;
			flag[index] = true;
			
			//����dist����
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
	 * floyd�㷨
	 * @param graph
	 * @return ����·��
	 */
	public int[][] floyd(Graph graph)
	{
		//path[i][j]=k��ʾ��"����i"��"����j"�����·���ᾭ������k
		int[][] path = new int[graph.vertexSize][graph.vertexSize];
		//dist[i][j]=sum��ʾ��"����i"��"����j"�����·���ĳ�����sum
		int[][] dist = new int[graph.vertexSize][graph.vertexSize];
		
		//��ʼ������
		for(int i=0; i<graph.vertexSize; i++)
		{
			for(int j=0; j<graph.vertexSize; j++)
			{
				dist[i][j] = graph.edgesMatrix[i][j];
				path[i][j] = j;
			}
		}
		
		//һ����Ҫ���µĴ����Ƕ���ĸ���
		for(int i=0; i<graph.vertexSize; i++)
		{
			//�������·������������
			for(int j=0; j<graph.vertexSize; j++)
			{
				for(int k=0; k<graph.vertexSize; k++)
				{
					//�Ƚ�j��k֮��ľ�����j����i��k֮��ľ���
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
	
	//����dijkstra�㷨
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
