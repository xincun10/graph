package com.minTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import com.graph.Graph;

public class MinTree {
	public static final int MAX_VALUE = Integer.MAX_VALUE;

	/*
	 * 求最小生成树
	 * 
	 */
	/**
	 * Prim算法
	 * @return
	 */
	public Queue<Integer> prim(Graph graph)
	{
		Queue<Integer> queue = new LinkedList<>();
		//最小树的顶点
		int start = 0;
		//存储到第i个顶点的最小权值
		int[] weight = new int[graph.vertexSize];
		//初始化顶点的权值数组
		//将每个顶点的权值初始化为第start个顶点到该顶点的权值
		for(int i=0; i<graph.vertexSize; i++)
		{
			weight[i] = graph.edgesMatrix[start][i];
		}
		weight[start] = 0;
		queue.add(start);
		
		for(int j=1; j<graph.vertexSize; j++)
		{
			int index = 0;
			int min = Graph.MAX_VALUE;
			//遍历weight数组，找出其中的最小值加入到队列中
			for(int i=0; i<graph.vertexSize; i++)
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
			for(int i=0; i<graph.vertexSize; i++)
			{
				//节点没有加到队列当中，且小于新加入节点权值，则更新
				if(weight[i]!=0 && weight[i]>graph.edgesMatrix[index][i])
				{
					weight[i] = graph.edgesMatrix[index][i];
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
	public static class EData implements Comparable<EData>
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
	private ArrayList<EData> getEdges(Graph graph)
	{
		ArrayList<EData> edatas = new ArrayList<>();
		for(int i=0; i<graph.vertexSize; i++)
		{
			for(int j=i+1; j<graph.vertexSize; j++)
			{
				if(graph.edgesMatrix[i][j] != Graph.MAX_VALUE)
				{
					EData tmp = new EData(graph.vertexesArray[i], graph.vertexesArray[j], graph.edgesMatrix[i][j]);
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
     * 返回顶点在数组中的位置
     */
    private int getPosition(Object o, Graph graph) {
        for(int i=0; i<graph.vertexSize; i++)
            if(graph.vertexesArray[i].equals(o))
                return i;
        return -1;
    }
	/**
	 * 克鲁斯卡尔(Kruskal)算法
	 * @return
	 */
	public ArrayList<EData> kruskal(Graph graph)
	{
		//获取所有的边
		ArrayList<EData> edges = getEdges(graph);
		//对所有的边进行排序
		Collections.sort(edges);
		// 用于保存"已有最小生成树"中每个顶点在该最小树中的终点。
		int[] vends = new int[graph.vertexSize];
		// 结果数组，保存kruskal最小生成树的边
		ArrayList<EData> rets = new ArrayList<>();
		for(int i=0; i<edges.size(); i++)
		{
			//获取边起点和终点
			int start = getPosition(edges.get(i).start, graph);
			int end = getPosition(edges.get(i).end, graph);
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
		
//			Queue<Integer> q = g.prim();
//			while(!q.isEmpty())
//			{
//				System.out.println(q.poll());
//			}
		MinTree minTree = new MinTree();
		ArrayList<EData> results = minTree.kruskal(g);
		for(EData e:results)
		{
			System.out.println(e.start+","+e.end);
		}
	}
}
