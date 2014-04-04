package com.dha.contagion;

public class Result {

	int max=0;
	int min=0;
	Pos posBlank=new Pos(7,7);
	Pos posRed=new Pos(7,7);
	Pos posBlue=new Pos(7,7);
	
	public Pos getPosBlank(){
		return this.posBlank;
	}
	
	public String toString(){
		 return ("max, min ="+String.valueOf(max)+String.valueOf(min)+
				 " Blank="+this.posBlank.x + ", "+this.posBlank.y+
				 " Blue="+this.posBlue.x + ", "+this.posBlue.y+
				 " Red="+this.posRed.x + ", "+this.posRed.y);
	}
	
}
