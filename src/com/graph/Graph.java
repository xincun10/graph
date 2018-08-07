package com.graph;

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
	
	public boolean graphType;//ͼ������
	public boolean method;//ͼ��ʵ�ַ�ʽ
	public int vertexSize;//������Ŀ
	public int matrixMaxVertex;//ͼ�Ķ����������
	
	//�洢������Ϣ
	public Object[] vertexesArray;
	//�洢����֮�������ϵ�Ķ�ά����
	public int[][] edgesMatrix;
	//��¼�Ƿ񱻷��ʹ�
	public boolean[] visited;
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
	
}
