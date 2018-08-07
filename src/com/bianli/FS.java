package com.bianli;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.junit.Test;

import com.graph.Graph;

public class FS {

	/*
	 * 深度优先和广度优先遍历算法
	 * 非递归实现
	 *
	 */
	/**
	 * 非递归方式实现深度优先遍历
	 * @return
	 */
	public Queue<Integer> DFS(Graph graph)
	{
		//临时存储节点数据
		Stack<Integer> stack = new Stack<Integer>();
		//存储最终结果
		Queue<Integer> queue = new LinkedList<>();
		//初始化访问数组
		for(int i=0; i<graph.vertexSize; i++)
		{
			graph.visited[i] = false;
		}
		for(int i=0; i<graph.vertexSize; i++)
		{
			if(!graph.visited[i])
			{
				//没有访问过的节点先压栈
				stack.push(i);
				//访问标志置为true
				graph.visited[i] = true;
				while(!stack.isEmpty())
				{
					//先弹栈，入队列
					int tmp = stack.pop();
					queue.add(tmp);
					//从最后一个节点开始遍历，跟弹出节点组成边的都压栈
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
	 * 非递归实现图的广度优先遍历
	 * @return
	 */
	public Queue<Integer> BFS(Graph graph)
	{
		Queue<Integer> queue = new LinkedList<>();
		Queue<Integer> queue2 = new LinkedList<>();
		//初始化访问数组
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
				//从第一个节点开始遍历，和节点组成边的都入队
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
		
		Queue<Integer> q = BFS(g);
		while(!q.isEmpty())
		{
			System.out.println(q.poll());
		}
	}
}
