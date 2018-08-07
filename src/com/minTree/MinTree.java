package com.minTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import com.graph.Graph;

public class MinTree {
	public static final int MAX_VALUE = Integer.MAX_VALUE;

	/*
	 * ����С������
	 * 
	 */
	/**
	 * Prim�㷨
	 * @return
	 */
	public Queue<Integer> prim(Graph graph)
	{
		Queue<Integer> queue = new LinkedList<>();
		//��С���Ķ���
		int start = 0;
		//�洢����i���������СȨֵ
		int[] weight = new int[graph.vertexSize];
		//��ʼ�������Ȩֵ����
		//��ÿ�������Ȩֵ��ʼ��Ϊ��start�����㵽�ö����Ȩֵ
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
			//����weight���飬�ҳ����е���Сֵ���뵽������
			for(int i=0; i<graph.vertexSize; i++)
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
			for(int i=0; i<graph.vertexSize; i++)
			{
				//�ڵ�û�мӵ����е��У���С���¼���ڵ�Ȩֵ�������
				if(weight[i]!=0 && weight[i]>graph.edgesMatrix[index][i])
				{
					weight[i] = graph.edgesMatrix[index][i];
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
	public static class EData implements Comparable<EData>
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
     * ��ȡi���յ�
     */
    private int getEnd(int[] vends, int i) {
        while (vends[i] != 0)
            i = vends[i];
        return i;
    }
    /*
     * ���ض����������е�λ��
     */
    private int getPosition(Object o, Graph graph) {
        for(int i=0; i<graph.vertexSize; i++)
            if(graph.vertexesArray[i].equals(o))
                return i;
        return -1;
    }
	/**
	 * ��³˹����(Kruskal)�㷨
	 * @return
	 */
	public ArrayList<EData> kruskal(Graph graph)
	{
		//��ȡ���еı�
		ArrayList<EData> edges = getEdges(graph);
		//�����еı߽�������
		Collections.sort(edges);
		// ���ڱ���"������С������"��ÿ�������ڸ���С���е��յ㡣
		int[] vends = new int[graph.vertexSize];
		// ������飬����kruskal��С�������ı�
		ArrayList<EData> rets = new ArrayList<>();
		for(int i=0; i<edges.size(); i++)
		{
			//��ȡ�������յ�
			int start = getPosition(edges.get(i).start, graph);
			int end = getPosition(edges.get(i).end, graph);
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
