package com.cheney.triangle;



public class YHTriangle
{
	public void triangle(int crow){
		int row=crow;
		int[][] triangle=new int[row][];
		for(int i=0;i<row;i++){
			triangle[i]=new int[i+1];
			for(int j=0;j<=i;j++){
				if(j==0||j==i){
					triangle[i][j]=1;
					if(j!=i||i==0){
						for(int k=row-i;k>1;k--){
							System.out.print(" ");
						}
					}
					
				}else{
					triangle[i][j]=triangle[i-1][j-1]+triangle[i-1][j];
				}
				
				System.out.print(triangle[i][j]+" ");
			}
			System.out.println();
		}
	}
}