package chess;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JOptionPane;

/**
 * 
 * <p>Title: Chess.java</p>
 * <p>Description:
 *  ��ʼ�㷨����
 *  ���������С�㷨�����Ϊ4
 *  ���峵10,��6,��5,��2,��3,ʿ2,��100 
 *  ��ζ�̬�������ӵ�����ֵ��������ô���������ﵽ������������д����������
 * <p>@author dedong
 * <p>@date 2017/ ����12:19:00
 *
 */
public class Chess {
	private int[][] data;
	private UChess root;//���ڵ�
	private int toGo;
	private final int rows=10;
	private final int cols=9;
	private int[] ucvalue={10,5,3,2,100,8,2};
	private boolean isAI;
	
	public Chess() {
		// TODO Auto-generated constructor stub
	}
	
	public Chess(int[][] subData){
		data=BackData(subData);                  //du lieu data ban co hien tai
	}//�����������з���
	
	public int[][] compute(int depth) {    // ktra con Tuong hay ko 00:43. neu mat thi goi den ChessDraw hien thi sorry,you lose
		hasWin(data);
		boolean isCon=false;
		for(int i=7;i<=9;i++)
			for(int j=3;j<=5;j++)
				if(data[i][j]==5){		
					isCon=true;					//isCon la bien de check Tuong con hay ko
					break;
				}
		if(isCon){
			return new ReWrite(data,depth).work();
		}
		else{
			return data;
		}
	}
	
	public void hasWin(int[][] data) {
		for(int i=0;i<=9;i++){
			for(int j=0;j<=8;j++){
				int label=data[i][j];
				switch (label) {
				case 8:
					//�ǳ�
					for(int h=i+1;h<rows;h++){
						if(data[h][j]==0)
							continue;
						else if(data[h][j]!=5)
							break;
						else{
							//��˧
							data[i][j]=0;
							data[h][j]=label;
							break;
						}
					}
					for(int h=i-1;h>=0;h--){
						if(data[h][j]==0)
							continue;
						else if(data[h][j]!=5)
							break;
						else{
							data[i][j]=0;
							data[h][j]=label;
							break;
						}
					}
					for(int h=j-1;h>=0;h--){
						if(data[i][h]==0)
							continue;
						else if(data[i][h]!=5)
							break;
						else {
							data[i][j]=0;
							data[i][h]=label;
						}
					}
					for(int h=j+1;h<cols;h++){
						if(data[i][h]==0)
							continue;
						else if(data[i][h]!=5)//д��,��д��2
							break;
						else {
							data[i][j]=0;
							data[i][h]=label;
							break;
						}
					}
					break;
				case 13:
					//����
					for(int h=i+1;h<rows;h++){
						if(data[h][j]==0)
							continue;
						else{
							//��һ����
							for(int k=h+1;k<rows;k++){
								if(data[k][j]==0)
									continue;
								else if(data[k][j]!=5)
									break;
								else {
									data[i][j]=0;
									data[k][j]=label;
									break;
								}
							}
							break;
						}
					}
					for(int h=i-1;h>=0;h--){
						if(data[h][j]==0)
							continue;
						else{
							for(int k=h-1;k>=0;k--){
								if(data[k][j]==0)
									continue;
								else if(data[k][j]!=5)
									break;
								else {
									data[i][j]=0;
									data[k][j]=label;
									break;
								}
							}
							break;
						}
					}
					for(int h=j-1;h>=0;h--){
						if(data[i][h]==0)
							continue;
						else{
							for(int k=h-1;k>=0;k--){
								if(data[i][k]==0)
									continue;
								else if(data[i][k]!=5)
									break;
								else {
									data[i][j]=0;
									data[i][k]=label;
									break;
								}
							}
							break;
						}
					}
					for(int h=j+1;h<cols;h++){
						if(data[i][h]==0)
							continue;
						else{
							for(int k=h+1;k<cols;k++){
								if(data[i][k]==0)
									continue;
								else if(data[i][k]!=5)
									break;
								else {
									data[i][j]=0;
									data[i][k]=label;
									break;
								}
							}
							break;
						}
					}
					break;
				case 9:
					//��
					if(i-1>=0 && data[i-1][j]==0){
						if(i-2>=0 && j+1<cols && data[i-2][j+1]==5){
							data[i][j]=0;
							data[i-2][j+1]=label;
						}else if(i-2>=0 && j-1>=0 && data[i-2][j-1]==5){
							data[i][j]=0;
							data[i-2][j-1]=label;
						}
					}
					if(j+1<cols && data[i][j+1]==0){
						if(i-1>=0 && j+2<cols && data[i-1][j+2]==5){
							data[i][j]=0;
							data[i-1][j+2]=label;
						}else if(i+1<rows && j+2<cols && data[i+1][j+2]==5){
							data[i][j]=0;
							data[i+1][j+2]=label;
						}
					}
					if(i+1<rows && data[i+1][j]==0){
						if(i+2<rows && j+1<cols && data[i+2][j+1]==5){
							data[i][j]=0;
							data[i+2][j+1]=label;
						}else if(i+2<rows && j-1>=0 && data[i+2][j-1]==5){
							data[i][j]=0;
							data[i+2][j-1]=label;
						}
					}
					if(j-1>=0 && data[i][j-1]==0){
						if(i-1>=0 && j-2>=0 && data[i-1][j-2]==5){
							data[i][j]=0;
							data[i-1][j-2]=label;
						}else if(i+1<rows && j-2>=0 && data[i+1][j-2]==5){
							data[i][j]=0;
							data[i+1][j-2]=label;
						}
					}
					break;
				case 12:
					//��
					for(int h=i+1;h<rows;h++){
						if(data[h][j]==0)
							continue;
						else if(data[h][j]!=5)
							break;
						else {
							data[i][j]=0;
							data[h][j]=label;
							break;
						}
					}
					break;
				case 14:
					//��
					if(i+1<rows && data[i+1][j]==5){
						data[i][j]=0;
						data[i+1][j]=label;
					}else if(j-1>=0 && data[i][j-1]==5){
						data[i][j]=0;
						data[i][j-1]=label;
					}else if(j+1<cols && data[i][j+1]==5){
						data[i][j]=0;
						data[i][j+1]=label;
					}
					break;
				default:
					break;
				}
			}
		}
	}
	
	//�ú�����Ҫ��д
	public void BestMove() {
		//����aiִ�к���,���ֵ�ai�ж�,������
		root=new UChess();
		root.depth=0;
		root.innerData=BackData(data);
		//������ͼ
		Queue<UChess> all=new LinkedList<Chess.UChess>();
		int count=0;
		all.offer(root);
		while(!all.isEmpty()){
			System.out.println(++count);
			UChess uc=all.poll();
//			Move(uc, uc.depth, (uc.depth%2==0?true:false));
			for(int i=0;i<uc.chidern.size();i++){
				all.offer(uc.chidern.get(i));
				}
			}
		if(count>=200000)
			return;
		System.exit(1);
		complishSelf(root);
		writeValue(root, root.chidern);
	}
	
	//�õݹ���и�ֵ���������ײ�Ļ�����ô���и�ֵ���������alphabeta�ж��Ƿ��֦
	
	
//	public void Move(UChess uc,int depth,boolean AI) {
//		//����һ��״̬�µ����׿�ʼ����,�Ƿ��ֵ�ai�ж�
//		//4����list����
//		isAI=AI;
//		if(depth==maxDepth){
//			//���һ��
//			uc.currentValue=evalute(uc);
//			return;
//		}
//		ArrayList<UChess> Cthis=new ArrayList<>();
//		if(AI){
//			int[][] pos=findPos(uc.innerData, 8);//�ҳ���λ��
//			if(pos[0][0]>=0){
//				//�ƶ���һ�����Ŀ���λ��
//				int[][] canPos=canMove(uc.innerData, pos[0][0], pos[0][1], 8);
//				arrange(canPos, uc, Cthis, depth, pos[0][0], pos[0][1], 8);
//			}
//			if(pos[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, pos[1][0], pos[1][1], 8);
//				arrange(canPos, uc, Cthis, depth, pos[1][0], pos[1][1], 8);
//			}
//			
//			int[][] posMa=findPos(uc.innerData, 9);//�����λ��
//			if(posMa[0][0]>=0){
//				//�ƶ���һ����Ŀ���λ��
//				int[][] canPos=canMove(uc.innerData, posMa[0][0], posMa[0][1], 9);
//				arrange(canPos, uc, Cthis, depth, posMa[0][0], posMa[0][1], 9);
//			}
//			if(posMa[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posMa[1][0], posMa[1][1], 9);
//				arrange(canPos, uc, Cthis, depth, posMa[1][0], posMa[1][1], 9);
//			}
//			
//			int[][] posXi=findPos(uc.innerData, 10);
//			if(posXi[0][0]>=0){
//				//�ƶ���һ�����Ŀ���λ��
//				int[][] canPos=canMove(uc.innerData, posXi[0][0], posXi[0][1], 10);
//				arrange(canPos, uc, Cthis, depth, posXi[0][0], posXi[0][1], 10);
//			}
//			if(posXi[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posXi[1][0], posXi[1][1], 10);
//				arrange(canPos, uc, Cthis, depth, posXi[1][0], posXi[1][1], 10);
//			}
//			
//			int[][] posSh=findPos(uc.innerData, 11);
//			if(posSh[0][0]>=0){
//				//�ƶ���һ�����Ŀ���λ��
//				int[][] canPos=canMove(uc.innerData, posSh[0][0], posSh[0][1], 11);
//				arrange(canPos, uc, Cthis, depth, posSh[0][0], posSh[0][1], 11);
//			}
//			if(posSh[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posSh[1][0], posSh[1][1], 11);
//				arrange(canPos, uc, Cthis, depth, posSh[1][0], posSh[1][1], 11);
//			}
//			
//			int[][] posJi=findPos(uc.innerData, 12);
//			if(posJi[0][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posJi[0][0], posJi[0][1], 12);
//				arrange(canPos, uc, Cthis, depth, posJi[0][0], posJi[0][1], 12);
//			}
//			
//			int[][] posPa=findPos(uc.innerData, 13);
//			if(posPa[0][0]>=0){
//				//�ƶ���һ���ڵĿ���λ��
//				int[][] canPos=canMove(uc.innerData, posPa[0][0], posPa[0][1], 13);
//				arrange(canPos, uc, Cthis, depth, posPa[0][0], posPa[0][1], 13);
//			}
//			if(posPa[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posPa[1][0], posPa[1][1], 13);
//				arrange(canPos, uc, Cthis, depth, posPa[1][0], posPa[1][1], 13);
//			}
//			
//			int[][] posBi=findPos(uc.innerData, 14);
//			if(posBi[0][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posBi[0][0], posBi[0][1], 14);
//				arrange(canPos, uc, Cthis, depth, posBi[0][0], posBi[0][1], 14);
//			}
//			if(posBi[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posBi[1][0], posBi[1][1], 14);
//				arrange(canPos, uc, Cthis, depth, posBi[1][0], posBi[1][1], 14);
//			}
//			if(posBi[2][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posBi[2][0], posBi[2][1], 14);
//				arrange(canPos, uc, Cthis, depth, posBi[2][0], posBi[2][1], 14);
//			}
//			if(posBi[3][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posBi[3][0], posBi[3][1], 14);
//				arrange(canPos, uc, Cthis, depth, posBi[3][0], posBi[3][1], 14);
//			}
//			if(posBi[4][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posBi[4][0], posBi[4][1], 14);
//				arrange(canPos, uc, Cthis, depth, posBi[4][0], posBi[4][1], 14);
//			}
//		}else{
//			int[][] pos=findPos(uc.innerData, 1);//�ҳ���λ��
//			if(pos[0][0]>=0){
//				//�ƶ���һ�����Ŀ���λ��
//				int[][] canPos=canMove(uc.innerData, pos[0][0], pos[0][1], 1);
//				arrange(canPos, uc, Cthis, depth, pos[0][0], pos[0][1], 1);
//			}
//			if(pos[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, pos[1][0], pos[1][1], 1);
//				arrange(canPos, uc, Cthis, depth, pos[1][0], pos[1][1], 1);
//			}
//			
//			int[][] posMa=findPos(uc.innerData, 2);//�����λ��
//			if(posMa[0][0]>=0){
//				//�ƶ���һ����Ŀ���λ��
//				int[][] canPos=canMove(uc.innerData, posMa[0][0], posMa[0][1], 2);
//				arrange(canPos, uc, Cthis, depth, posMa[0][0], posMa[0][1], 2);
//			}
//			if(posMa[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posMa[1][0], posMa[1][1], 2);
//				arrange(canPos, uc, Cthis, depth, posMa[1][0], posMa[1][1], 2);
//			}
//			
//			int[][] posXi=findPos(uc.innerData, 3);
//			if(posXi[0][0]>=0){
//				//�ƶ���һ�����Ŀ���λ��
//				int[][] canPos=canMove(uc.innerData, posXi[0][0], posXi[0][1], 3);
//				arrange(canPos, uc, Cthis, depth, posXi[0][0], posXi[0][1], 3);
//			}
//			if(posXi[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posXi[1][0], posXi[1][1], 3);
//				arrange(canPos, uc, Cthis, depth, posXi[1][0], posXi[1][1], 3);
//			}
//			
//			int[][] posSh=findPos(uc.innerData, 4);
//			if(posSh[0][0]>=0){
//				//�ƶ���һ�����Ŀ���λ��
//				int[][] canPos=canMove(uc.innerData, posSh[0][0], posSh[0][1], 4);
//				arrange(canPos, uc, Cthis, depth, posSh[0][0], posSh[0][1],4);
//			}
//			if(posSh[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posSh[1][0], posSh[1][1], 4);
//				arrange(canPos, uc, Cthis, depth, posSh[1][0], posSh[1][1], 4);
//			}
//			
//			int[][] posSu=findPos(uc.innerData, 5);
//			if(posSu[0][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posSu[0][0], posSu[0][1], 5);
//				arrange(canPos, uc, Cthis, depth, posSu[0][0], posSu[0][1], 5);
//			}
//			
//			int[][] posPa=findPos(uc.innerData, 6);
//			if(posPa[0][0]>=0){
//				//�ƶ���һ�����Ŀ���λ��
//				int[][] canPos=canMove(uc.innerData, posPa[0][0], posPa[0][1], 6);
//				arrange(canPos, uc, Cthis, depth, posPa[0][0], posPa[0][1], 6);
//			}
//			if(posPa[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posPa[1][0], posPa[1][1], 6);
//				arrange(canPos, uc, Cthis, depth, posPa[1][0], posPa[1][1], 6);
//			}
//			
//			int[][] posBi=findPos(uc.innerData, 7);
//			if(posBi[0][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posBi[0][0], posBi[0][1], 7);
//				arrange(canPos, uc, Cthis, depth, posBi[0][0], posBi[0][1], 7);
//			}
//			if(posBi[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posBi[1][0], posBi[1][1], 7);
//				arrange(canPos, uc, Cthis, depth, posBi[1][0], posBi[1][1], 7);
//			}
//			if(posBi[2][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posBi[2][0], posBi[2][1], 7);
//				arrange(canPos, uc, Cthis, depth, posBi[2][0], posBi[2][1], 7);
//			}
//			if(posBi[3][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posBi[3][0], posBi[3][1], 7);
//				arrange(canPos, uc, Cthis, depth, posBi[3][0], posBi[3][1], 7);
//			}
//			if(posBi[4][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posBi[4][0], posBi[4][1], 7);
//				arrange(canPos, uc, Cthis, depth, posBi[4][0], posBi[4][1], 7);
//			}
//		}
//		if(Cthis.size()>0)
//			uc.chidern=Cthis;
//		else{
//			if(uc.depth%2==0){
//				uc.currentValue=-1000;
//			}else{
//				uc.currentValue=1000;
//			}
//		}
//	}
	
	public void arrange(int[][] canPos,UChess uc,ArrayList<UChess> Cthis,int depth,int ir,int jc,int label) {
		for(int i=0;i<canPos.length;i++){
			int[][] subData=BackData(uc.innerData);
			subData[ir][jc]=0;
			subData[canPos[i][0]][canPos[i][1]]=label;
			if((isJiang(subData) && isAI) || (isShuai(subData) && !isAI))//��������
				continue;//��Ӧ��
			UChess subuc=new UChess();
			subuc.parent=uc;
			subuc.depth=uc.depth+1;
			subuc.innerData=subData;
			Cthis.add(subuc);
		}
	}
	
	public boolean isJiang(int[][] subuc) {
		//���Ƿ񱻳�����
		int jx = -1;
		int jy = -1;
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				if(subuc[i][j]==12){
					jx=i;
					jy=j;
				}
			}
		}
		if(jx==-1)
			return true;
		//�ҵ�������λ��
		//���ſ��Ƿ��жԷ��ĳ�
		for(int h=jy-1;h>=0;h--){
			if(subuc[jx][h]==0)
				continue;
			else if(subuc[jx][h]==1){
				return true;
			}else {
				break;
			}
		}
		for(int h=jy+1;h<cols;h++){
			if(subuc[jx][h]==0)
				continue;
			else if(subuc[jx][h]==1)
				return true;
			else {
				break;
			}
		}
		//���ſ�
		if(jx-1>=0){
			for(int h=jx-1;h>=0;h--){
				if(subuc[h][jy]==0)
					continue;
				else if(subuc[h][jy]==1)
					return true;
				else {
					break;
				}
			}
		}
		for(int h=jx+1;h<rows;h++){
			if(subuc[h][jy]==0)
				continue;
			else if(subuc[h][jy]==1)
				return true;
			else {
				break;
			}
		}
		
		//���Ƿ��ڽ���
		for(int h=jy-1;h>=0;h--){
			if(subuc[jx][h]==0)
				continue;
			else{
				//��һ������
				if(h-1>=0){
					for(int k=h-1;k>=0;k--){
						if(subuc[jx][k]==0)
							continue;
						else if(subuc[jx][k]==6)
							return true;
						else {
							break;
						}
					}
				}
				break;
			}
		}
		for(int h=jy+1;h<cols;h++){
			if(subuc[jx][h]==0)
				continue;
			else{
				if(h+1<cols){
					for(int k=h+1;k<cols;k++){
						if(subuc[jx][k]==0)
							continue;
						else if(subuc[jx][k]==6)
							return true;
						else {
							break;
						}
					}
				}
				break;
			}
		}
		if(jx-1>=0){
			for(int h=jx-1;h>=0;h--){
				if(subuc[h][jy]==0)
					continue;
				else {
					if(h-1>=0){
						for(int k=h-1;k>=0;k--){
							if(subuc[k][jy]==0)
								continue;
							else if(subuc[k][jy]==6)
								return true;
							else {
								break;
							}
						}
					}
					break;
				}
			}
		}
			for(int h=jx+1;h<rows;h++){
				if(subuc[h][jy]==0)
					continue;
				else{
					if(h+1<rows){
						for(int k=h+1;k<rows;k++){
							if(subuc[k][jy]==0)
								continue;
							else if(subuc[k][jy]==6)
								return true;
							else {
								break;
							}
						}
					}
					break;
				}
			}
			
			//���Ƿ�����
			if(jx-1>=0 && subuc[jx-1][jy+1]==0){
				if((jx-2>=0 && subuc[jx-2][jy+1]==2) || subuc[jx-1][jy+2]==2){
					return true;
				}
			}
		
			if(jx-1>=0 && subuc[jx-1][jy-1]==0){
				if((jx-2>=0 && subuc[jx-2][jy-1]==2) || subuc[jx-1][jy-2]==2){
					return true;
				}
			}
			
			if(subuc[jx+1][jy+1]==0){
				if(subuc[jx+2][jy+1]==2 || subuc[jx+1][jy+2]==2){
					return true;
				}
			}
			
			if(subuc[jx+1][jy-1]==0){
				if(subuc[jx+2][jy-1]==2 || subuc[jx+1][jy-2]==2){
					return true;
				}
			}
			
			//���Ƿ񱻶����˧����
			for(int h=jx+1;h<rows;h++){
				if(subuc[h][jy]==0)
					continue;
				else if(subuc[h][jy]==5)
					return true;
				else {
					break;
				}
			}
			
			//���Ƿ񱻶���ı�����
			if(subuc[jx+1][jy]==7 || subuc[jx][jy-1]==7 || subuc[jx][jy+1]==7)
				return true;
			
			return false;
		}
	
	public boolean isShuai(int[][] subuc) {
		//���Ƿ񱻳�����
		int jx = -1;
		int jy = -1;
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				if(subuc[i][j]==5){
					jx=i;
					jy=j;
				}
			}
		}
		if(jx==-1)
			return true;
		//�ҵ�������λ��
		//���ſ��Ƿ��жԷ��ĳ�
		for(int h=jy-1;h>=0;h--){
			if(subuc[jx][h]==0)
				continue;
			else if(subuc[jx][h]==8){
				return true;
			}else {
				break;
			}
		}
		for(int h=jy+1;h<cols;h++){
			if(subuc[jx][h]==0)
				continue;
			else if(subuc[jx][h]==8)
				return true;
			else {
				break;
			}
		}
		//���ſ�
			for(int h=jx-1;h>=0;h--){
				if(subuc[h][jy]==0)
					continue;
				else if(subuc[h][jy]==8)
					return true;
				else {
					break;
				}
			}
		
		if(jx+1<rows){
		for(int h=jx+1;h<rows;h++){
			if(subuc[h][jy]==0)
				continue;
			else if(subuc[h][jy]==8)
				return true;
			else {
				break;
			}
		}
		}
		
		//���Ƿ��ڽ���
		for(int h=jy-1;h>=0;h--){
			if(subuc[jx][h]==0)
				continue;
			else{
				//��һ������
				if(h-1>=0){
					for(int k=h-1;k>=0;k--){
						if(subuc[jx][k]==0)
							continue;
						else if(subuc[jx][k]==13)
							return true;
						else {
							break;
						}
					}
				}
				break;
			}
		}
		for(int h=jy+1;h<cols;h++){
			if(subuc[jx][h]==0)
				continue;
			else{
				if(h+1<cols){
					for(int k=h+1;k<cols;k++){
						if(subuc[jx][k]==0)
							continue;
						else if(subuc[jx][k]==13)
							return true;
						else {
							break;
						}
					}
				}
				break;
			}
		}

			for(int h=jx-1;h>=0;h--){
				if(subuc[h][jy]==0)
					continue;
				else {
					if(h-1>=0){
						for(int k=h-1;k>=0;k--){
							if(subuc[k][jy]==0)
								continue;
							else if(subuc[k][jy]==13)
								return true;
							else {
								break;
							}
						}
					}
					break;
				}
			}
		
			if(jx+1<rows){
			for(int h=jx+1;h<rows;h++){
				if(subuc[h][jy]==0)
					continue;
				else{
					if(h+1<rows){
						for(int k=h+1;k<rows;k++){
							if(subuc[k][jy]==0)
								continue;
							else if(subuc[k][jy]==13)
								return true;
							else {
								break;
							}
						}
					}
					break;
				}
			}
			}
			
			//���Ƿ�����
			if(subuc[jx-1][jy+1]==0){
				if(subuc[jx-2][jy+1]==2 || subuc[jx-1][jy+2]==2){
					return true;
				}
			}
		
			if(subuc[jx-1][jy-1]==0){
				if(subuc[jx-2][jy-1]==2 || subuc[jx-1][jy-2]==2){
					return true;
				}
			}
			
			if(jx+1<rows && subuc[jx+1][jy+1]==0){
				if((jx+2<rows && subuc[jx+2][jy+1]==2) || subuc[jx+1][jy+2]==2){
					return true;
				}
			}
			
			if(jx+1<rows && subuc[jx+1][jy-1]==0){
				if((jx+2<rows && subuc[jx+2][jy-1]==2) || subuc[jx+1][jy-2]==2){
					return true;
				}
			}
			
			//���Ƿ񱻶����˧����
			for(int h=jx-1;h>=0;h--){
				if(subuc[h][jy]==0)
					continue;
				else if(subuc[h][jy]==12)
					return true;
				else {
					break;
				}
			}
			
			//���Ƿ񱻶���ı�����
			if(subuc[jx-1][jy]==14 || subuc[jx][jy-1]==14 || subuc[jx][jy+1]==14)
				return true;
			
			return false;
	}
	
	public int evalute(UChess uc) {
		//�����ai��˵����������
		int[] init=new int[15];
		int[] now=new int[15];
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				init[data[i][j]]++;
			}
		}
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				now[uc.innerData[i][j]]++;
			}
		}
		
		int value=ucvalue[0]*(now[8]-init[8])+ucvalue[1]*(now[9]-init[9])+ucvalue[2]*(now[10]-init[10])+ucvalue[3]*(now[11]-init[11])+
				ucvalue[4]*(now[12]-init[12])
				+ucvalue[5]*(now[13]-init[13])+ucvalue[6]*(now[14]-init[14])-ucvalue[0]*(now[1]-init[1])-ucvalue[1]*(now[2]-init[2])-
				ucvalue[2]*(now[3]-init[3])-ucvalue[3]*(now[4]-init[4])
				-ucvalue[4]*(now[5]-init[5])-ucvalue[5]*(now[6]-init[6])-ucvalue[6]*(now[7]-init[7]);
		return value;
	}
	
	private int[][] findPos(int[][] data,int label) {
		int[][] pos=new int[5][2];
		for(int i=0;i<pos.length;i++)
			pos[i][0]=-1;
		int more=0;
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				if(data[i][j]==label){
					pos[more][0]=i;
					pos[more++][1]=j;
				}
			}
		}
		return pos;
	}
	
	private int[][] canMove(int[][] data,int i,int j,int label) {
		//���ֿ����ƶ��ĵ�
		java.util.List<Integer> listx=new ArrayList<>();
		java.util.List<Integer> listy=new ArrayList<>();
		switch (label) {
		case 8:
		case 1:
			//�ǳ�
			//�۲������߿����ƶ���Զ��
			if(j-1>=0){
			for(int h=j-1;h>=0;h--){
					if(data[i][h]==0){
						listx.add(i);
						listy.add(h);
					}else if((label<=7 && data[i][h]>=8) || (label>=8 && data[i][h]<=7)){
						listx.add(i);
						listy.add(h);
						break;//����
					}else{
						//�Լ���
						break;
					}
				}
			}
			
			//�۲�����ұ߿����ƶ�����Զ��
			if(j+1<cols){
				for(int h=j+1;h<cols;h++){
					if(data[i][h]==0){
						listx.add(i);
						listy.add(h);
					}else if((label<=7 && data[i][h]>=8) || (label>=8 && data[i][h]<=7)){
						listx.add(i);
						listy.add(h);
						break;
					}else {
						break;
					}
				}
			}

			//�۲������������
			if(i-1>=0){
				for(int k=i-1;k>=0;k--){
					if(data[k][j]==0){
						listx.add(k);
						listy.add(j);
					}else if((label<=7 && data[k][j]>=8) || (label>=8 && data[k][j]<=7)){
						listx.add(k);
						listy.add(j);
						break;
					}else {
						break;
					}
				}
			}

			//�۲������������
			if(i+1<rows){
				for(int k=i+1;k<rows;k++){
					if(data[k][j]==0){
						listx.add(k);
						listy.add(j);
					}else if((label<=7 && data[k][j]>=8) || (label>=8 && data[k][j]<=7)){
						listx.add(k);
						listy.add(j);
						break;
					}else {
						break;
					}
				}
			}
			break;
			
		case 6:
		case 13:
			//����
			if(j-1>=0){
				for(int h=j-1;h>=0;h--){
					if(data[i][h]==0){
						listx.add(i);
						listy.add(h);
					}else {
						if(h-1>=0){
							for(int k=h-1;k>=0;k--){
								if(data[i][k]==0){
									continue;
								}
								else if((label<=7 && data[i][k]>=8)||(label>=8 && data[i][k]<=7)){
									listx.add(i);
									listy.add(k);
									break;
								}else{
									break;
								}
							}
						}
						break;
					}
				}
			}
			
			if(j+1<cols){
				for(int h=j+1;h<cols;h++){
					if(data[i][h]==0){
						listx.add(i);
						listy.add(h);
					}else{
						if(h+1<cols){
							for(int k=h+1;k<cols;k++){
								if(data[i][k]==0)
									continue;
								else if((label<=7 && data[i][k]>=8)||(label>=8 && data[i][k]<=7)){
									listx.add(i);
									listy.add(k);
									break;
								}else {
									break;
								}
							}
						}
						break;
					}
				}
			}
			
			if(i-1>=0){
				for(int h=i-1;h>=0;h--){
					if(data[h][j]==0){
						listx.add(h);
						listy.add(j);
					}else{
						if(h-1>=0){
							for(int k=h-1;k>=0;k--){
								if(data[k][j]==0)
									continue;
								else if((label<=7 && data[k][j]>=8)||(label>=8 && data[k][j]<=7)){
									listx.add(k);
									listy.add(j);
									break;
								}else {
									break;
								}
							}
						}
						break;
					}
				}
			}
			
			if(i+1<rows){
				for(int h=i+1;h<rows;h++){
					if(data[h][j]==0){
						listx.add(h);
						listy.add(j);
					}else{
						if(h+1<cols){
							for(int k=h+1;k<rows;k++){
								if(data[k][j]==0)
									continue;
								else if((label<=7 && data[k][j]>=8)||(label>=8 && data[k][j]<=7)){
									listx.add(k);
									listy.add(j);
									break;
								}else {
									break;
								}
							}
						}
						break;
					}
				}
			}
			break;
			
		case 2:
		case 9:
			//����
			//��������
			if(i-2>=0 && j+1<cols){
				if(data[i-1][j]==0){
					if(data[i-2][j+1]==0 || (label<=7 && data[i-2][j+1]>=8) || (label>=8 && data[i-2][j+1]<=7)){
						listx.add(i-2);
						listy.add(j+1);
					}
				}
			}
			//������
			if(i-1>=0 && j+2<cols){
				if(data[i][j+1]==0){
					if(data[i-1][j+2]==0 || (label<=7 && data[i-1][j+2]>=8) || (label>=8 && data[i-1][j+2]<=7)){
						listx.add(i-1);
						listy.add(j+2);
					}
				}
			}
			//������
			if(i+1<rows && j+2<cols){
				if(data[i][j+1]==0){
					if(data[i+1][j+2]==0 || (label<=7 && data[i+1][j+2]>=8) || (label>=8 && data[i+1][j+2]<=7)){
						listx.add(i+1);
						listy.add(j+2);
					}
				}
			}
			//������
			if(i+2<rows && j+1<cols){
				if(data[i+1][j]==0){
					if(data[i+2][j+1]==0 || (label<=7 && data[i+2][j+1]>=8) || (label>=8 && data[i+2][j+1]<=7)){
						listx.add(i+2);
						listy.add(j+1);
					}
				}
			}
			//������
			if(i+1<rows && j-2>=0){
				if(data[i][j-1]==0){
					if(data[i+1][j-2]==0 || (label<=7 && data[i+1][j-2]>=8) || (label>=8 && data[i+1][j-2]<=7)){
						listx.add(i+1);
						listy.add(j-2);
					}
				}
			}
			//������
			if(i+2<rows && j-1>=0){
				if(data[i+1][j]==0){
					if(data[i+2][j-1]==0 || (label<=7 && data[i+2][j-1]>=8) || (label>=8 && data[i+2][j-1]<=7)){
						listx.add(i+2);
						listy.add(j-1);
					}
				}
			}
			//������
			if(i-1>=0 && j-2>=0){
				if(data[i][j-1]==0){
					if(data[i-1][j-2]==0 || (label<=7 && data[i-1][j-2]>=8) || (label>=8 && data[i-1][j-2]<=7)){
						listx.add(i-1);
						listy.add(j-2);
					}
				}
			}
			//������
			if(i-2>=0 && j-1>=0){
				if(data[i-1][j]==0){
					if(data[i-2][j-1]==0 || (label<=7 && data[i-2][j-1]>=8) || (label>=8 && data[i-2][j-1]<=7)){
						listx.add(i-2);
						listy.add(j-1);//��ͬ��
					}
				}
			}
			break;
			
		case 10:
			//����
			//����
			if(i-2>=0 && j+2<cols){
				if(data[i-1][j+1]==0){
					if(data[i-2][j+2]<=7){
						listx.add(i-2);
						listy.add(j+2);
					}
				}
			}
			//����
			if(i+2<rows/2 && j+2<cols){
				if(data[i+1][j+1]==0){
					if(data[i+2][j+2]<=7){
						listx.add(i+2);
						listy.add(j+2);
					}
				}
			}
			//����
			if(i+2<rows/2 && j-2>=0){
				if(data[i+1][j-1]==0){
					if(data[i+2][j-2]<=7){
						listx.add(i+2);
						listy.add(j-2);
					}
				}
			}
			//����
			if(i-2>=0 && j-2>=0){
				if(data[i-1][j-1]==0){
					if(data[i-2][j-2]<=7){
						listx.add(i-2);
						listy.add(j-2);
					}
				}
			}
			break;
		case 3:
			//��
			//����
			if(i-2>=rows/2 && j+2<cols){
				if(data[i-1][j+1]==0){
					if(data[i-2][j+2]==0 ||  data[i-2][j+2]>=8){
						listx.add(i-2);
						listy.add(j+2);
					}
				}
			}
			//����
			if(i+2<rows && j+2<cols){
				if(data[i+1][j+1]==0){
					if(data[i+2][j+2]==0 || data[i+2][j+2]>=8){
						listx.add(i+2);
						listy.add(j+2);
					}
				}
			}
			//����
			if(i+2<rows && j-2>=0){
				if(data[i+1][j-1]==0){
					if(data[i+2][j-2]==0 || data[i+2][j-2]>=8){
						listx.add(i+2);
						listy.add(j-2);
					}
				}
			}
			//����
			if(i-2>=rows/2 && j-2>=0){
				if(data[i-1][j-1]==0){
					if(data[i-2][j-2]==0 || data[i-2][j-2]>=8){
						listx.add(i-2);
						listy.add(j-2);
					}
				}
			}
			break;
		case 4:
			//ʿ
			//����
			if(i-1>=7 && j+1<=5){
				if(data[i-1][j+1]==0 || data[i-1][j+1]>=8){
					listx.add(i-1);
					listy.add(j+1);
				}
			}
			//����
			if(i+1<rows && j+1<=5){
				if(data[i-1][j+1]==0 || data[i-1][j+1]>=8){
					listx.add(i+1);
					listy.add(j+1);
				}
			}
			//����
			if(i+1<rows && j-1>=3){
				if(data[i+1][j-1]==0 || data[i+1][j-1]>=8){
					listx.add(i+1);
					listy.add(j-1);
				}
			}
			//����
			if(i-1>=7 && j-1>=3){
				if(data[i-1][j-1]==0 || data[i-1][j-1]>=8){
					listx.add(i-1);
					listy.add(j-1);
				}
			}
			break;
		case 11:
			//��ɫʿ
			//����
			if(i-1>=0 && j+1<=5){
				if(data[i-1][j+1]<=7){
					listx.add(i-1);
					listy.add(j+1);
				}
			}
			//����
			if(i+1<=2 && j+1<=5){
				if(data[i+1][j+1]<=7){
					listx.add(i+1);
					listy.add(j+1);
				}
			}
			//����
			if(i+1<=2 && j-1>=3){
				if(data[i+1][j-1]<=7){
					listx.add(i+1);
					listy.add(j-1);
				}
			}
			//����
			if(i-1>=0 && j-1>=3){
				if(data[i-1][j-1]<=7){
					listx.add(i-1);
					listy.add(j-1);
				}
			}
			break;
		case 5:
			//˧
			//��
			if(i-1<=7){
				if(data[i-1][j]==0 || data[i-1][j]>=8){
					listx.add(i-1);
					listy.add(j);
				}
			}
			//��
			if(i+1<rows){
				if(data[i+1][j]==0 || data[i+1][j]>=8){
					listx.add(i+1);
					listy.add(j);
				}
			}
			//��
			if(j-1>=3){
				if(data[i][j-1]==0 || data[i][j-1]>=8){
					listx.add(i);
					listy.add(j-1);
				}
			}
			//��
			if(j+1<=5){
				if(data[i][j+1]==0 || data[i][j+1]>=8){
					listx.add(i);
					listy.add(j+1);
				}
			}
			break;
		case 12:
			//��
			//��
			if(i-1>=0){
				if(data[i-1][j]<=7){
					listx.add(i-1);
					listy.add(j);
				}
			}
			//��
			if(i+1<=2){
				if(data[i+1][j]<=7){
					listx.add(i+1);
					listy.add(j);
				}
			}
			//��
			if(j-1>=3){
				if(data[i][j-1]<=7){
					listx.add(i);
					listy.add(j-1);
				}
			}
			//��
			if(j+1<=5){
				if(data[i][j+1]<=7){
					listx.add(i);
					listy.add(j+1);
				}
			}
			break;
		case 7:
			//��
			if(i>4){
				//û�й���
				if(data[i-1][j]==0 || data[i-1][j]>=8){
					listx.add(i-1);
					listy.add(j);
				}
			}else{
				//��
				if(i-1>=0){
					if(data[i-1][j]==0 || data[i-1][j]>=8){
						listx.add(i-1);
						listy.add(j);
					}
				}
				//��
				if(j-1>=0){
					if(data[i][j-1]==0 || data[i][j-1]>=8){
						listx.add(i);
						listy.add(j-1);
					}
				}
				//��
				if(j+1<cols){
					if(data[i][j+1]==0 || data[i][j+1]>=8){
						listx.add(i);
						listy.add(j+1);
					}
				}
			}
			break;
		case 14:
			//��
			if(i<5){
				//û�й���
				if(data[i+1][j]<=7){
					listx.add(i+1);
					listy.add(j);
				}
			}else{
				//��
				if(i+1<rows){
					if(data[i+1][j]<=7){
						listx.add(i+1);
						listy.add(j);
					}
				}
				//��
				if(j-1>=0){
					if(data[i][j-1]<=7){
						listx.add(i);
						listy.add(j-1);
					}
				}
				//��
				if(j+1<cols){
					if(data[i][j+1]<=7){
						listx.add(i);
						listy.add(j+1);
					}
				}
			}
			break;
		default:
			break;
		}
		int len=listx.size();
		int[][] furMov=new int[len][2];
		for(int h=0;h<len;h++){
			furMov[h][0]=listx.get(h);
			furMov[h][1]=listy.get(h);
		}
		return furMov;
	}
	
	public int[][] BackData(int[][] data) {
		int[][] sub=new int[data.length][data[0].length];
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[0].length;j++){
				sub[i][j]=data[i][j];//��ȸ���
			}
		}
		return sub;
	}
	
	public void complishSelf(UChess ch) {
		//���ƺ������
		for(int i=0;i<ch.chidern.size();i++){
			if(ch.chidern.get(i).chidern.size()>0 && ch.chidern.get(i).chidern.get(0).currentValue==2<<15){
				//��Ҷ�ӽڵ����û�и�ֵ
				complishSelf(ch.chidern.get(i));//��
			}
			//�����Ѿ���ֵ�˿�ʼaction
			if(ch.chidern.get(i).currentValue==2<<15){
				writeValue(ch.chidern.get(i), ch.chidern.get(i).chidern);
//				System.out.println("����"+ch.chidern.get(i).depth+" ��"+i+"��"+" value="+ch.chidern.get(i).currentValue);
			}
		}
	}
	
	public void writeValue(UChess currentCh,ArrayList<UChess> chidern) {//ȡ�����е�ֵ
		int value;
		toGo=-1;
		if(currentCh.depth%2==0){//ȡ���ֵ
			value=Integer.MIN_VALUE;
			for(int i=0;i<chidern.size();i++){
				if(chidern.get(i).currentValue>value){
					value=chidern.get(i).currentValue;
					toGo=i;
				}
			}
		}else{
			//ȡ��Сֵ
			value=Integer.MAX_VALUE;
			for(int i=0;i<chidern.size();i++){
				if(chidern.get(i).currentValue<value){
					value=chidern.get(i).currentValue;
					toGo=i;
				}
			}
		}
		currentCh.currentValue=value;
		currentCh.step=toGo;
	}
	
	private class UChess{
		//����
		private ArrayList<UChess> chidern=new ArrayList<>();//����
		private int currentValue=2<<15;//��ǰֵ
		private int depth;//��ǰ��ȣ������ʼ�����Ϊ0,ż��ȡ���ֵ����ȡ��Сֵ
		@SuppressWarnings("unused")
		private UChess parent;//���ڵ�
		private int[][] innerData;//ÿ�������ƶ���Ӧһ��ͼ����
		private int step;//�ߵڼ�������
	}
	
//	public void test() {
//		UChess root=new UChess();
//		UChess f1=new UChess();
//		UChess f2=new UChess();
//		UChess f3=new UChess();
//		UChess s1=new UChess();
//		UChess s2=new UChess();
//		UChess s3=new UChess();
////		UChess s4=new UChess();
////		UChess s5=new UChess();
////		UChess s6=new UChess();
//		UChess t1=new UChess();
//		UChess t2=new UChess();
//		UChess t3=new UChess();
//		UChess t4=new UChess();
////		UChess t5=new UChess();
////		UChess t6=new UChess();
////		UChess t7=new UChess();
////		UChess t8=new UChess();
////		UChess t9=new UChess();
////		UChess  t10=new UChess();
////		UChess t11=new UChess();
////		UChess t12=new UChess();
//		t1.currentValue=-7;
//		t1.parent=s1;
//		t2.currentValue=2;
//		t2.parent=s1;
//		t3.currentValue=3;
//		t3.parent=s3;
//		t4.currentValue=-1;
//		t4.parent=s3;
////		t5.currentValue=1;
////		t5.parent=s3;
////		t6.currentValue=-5;
////		t6.parent=s3;
////		t7.currentValue=7;
////		t7.parent=s4;
////		t8.currentValue=-2;
////		t8.parent=s4;
////		t9.currentValue=3;
////		t9.parent=s5;
////		t10.currentValue=-2;
////		t10.parent=s5;
////		t11.currentValue=-3;
////		t11.parent=s6;
////		t12.currentValue=4;
////		t12.parent=s6;
//		t1.depth=t2.depth=t3.depth=t4.depth=3;
//		s1.chidern.add(t1);
//		s1.chidern.add(t2);
//		s3.chidern.add(t3);
//		s3.chidern.add(t4);
//		s2.currentValue=-1000;
////		s3.chidern.add(t5);
////		s3.chidern.add(t6);
////		s4.chidern.add(t7);
////		s4.chidern.add(t8);
////		s5.chidern.add(t9);
////		s5.chidern.add(t10);
////		s6.chidern.add(t11);
////		s6.chidern.add(t12);
//		s1.parent=f1;
//		s2.parent=f1;
//		s3.parent=f3;
////		s4.parent=f2;
////		s5.parent=f3;
////		s6.parent=f3;
//		s1.depth=s2.depth=s3.depth=2;
//		f1.chidern.add(s1);
//		f1.chidern.add(s2);
//		f3.chidern.add(s3);
////		f2.chidern.add(s4);
////		f3.chidern.add(s5);
////		f3.chidern.add(s6);
//		f1.parent=root;
//		f2.parent=root;
//		f2.currentValue=1000;
//		f3.parent=root;
//		f1.depth=f2.depth=f3.depth=1;
//		root.depth=0;
//		root.chidern.add(f1);
//		root.chidern.add(f2);
//		root.chidern.add(f3);
//		this.root=root;
//		complishSelf(root);
//		writeValue(root, root.chidern);
//		System.out.println("==========");
//	}
}
