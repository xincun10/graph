package com.bianli;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.junit.Test;

import com.graph.Graph;

public class FS {

	/*
	 * ������Ⱥ͹�����ȱ����㷨
	 * �ǵݹ�ʵ��
	 *
	 */
	/**
	 * �ǵݹ鷽ʽʵ��������ȱ���
	 * @return
	 */
	public Queue<Integer> DFS(Graph graph)
	{
		//��ʱ�洢�ڵ�����
		Stack<Integer> stack = new Stack<Integer>();
		//�洢���ս��
		Queue<Integer> queue = new LinkedList<>();
		//��ʼ����������
		for(int i=0; i<graph.vertexSize; i++)
		{
			graph.visited[i] = false;
		}
		for(int i=0; i<graph.vertexSize; i++)
		{
			if(!graph.visited[i])
			{
				//û�з��ʹ��Ľڵ���ѹջ
				stack.push(i);
				//���ʱ�־��Ϊtrue
				graph.visited[i] = true;
				while(!stack.isEmpty())
				{
					//�ȵ�ջ�������
					int tmp = stack.pop();
					queue.add(tmp);
					//�����һ���ڵ㿪ʼ�������������ڵ���ɱߵĶ�ѹջ
					for(int j=graph.vertexSize-1; j>=0; j--)
					{
						if(graph.edgesMatrix[tmp][j]==1 && graph.visited[j]==false)
						{
							stack.push(j);
							graph.visited[j] = true;
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
	public Queue<Integer> BFS(Graph graph)
	{
		Queue<Integer> queue = new LinkedList<>();
		Queue<Integer> queue2 = new LinkedList<>();
		//��ʼ����������
		for(int i=0; i<graph.vertexSize; i++)
		{
			graph.visited[i] = false;
		}
		for(int i=0; i<graph.vertexSize; i++)
		{
			if(!graph.visited[i])
			{
				queue2.add(i);
				graph.visited[i] = true;
				//�ӵ�һ���ڵ㿪ʼ�������ͽڵ���ɱߵĶ����
				while(!queue2.isEmpty())
				{
					int tmp = queue2.poll();
					queue.add(tmp);
					for(int j=0; j<graph.vertexSize; j++)
					{
						if(graph.edgesMatrix[tmp][j]==1 && graph.visited[j]==false)
						{
							queue2.add(j);
							graph.visited[j] = true;
						}
					}
				}
			}
		}
		return queue;
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
		
		Queue<Integer> q = BFS(g);
		while(!q.isEmpty())
		{
			System.out.println(q.poll());
		}
	}
}
