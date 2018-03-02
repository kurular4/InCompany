package com.yukselproje.okurular.incompany.Models.Weather;

public class Main{
	private double temp;
	private double tempMin;
	private int grndLevel;
	private double tempKf;
	private int humidity;
	private float  pressure;
	private double seaLevel;
	private double tempMax;

	public void setTemp(double temp){
		this.temp = temp;
	}

	public double getTemp(){
		return temp;
	}

	public void setTempMin(double tempMin){
		this.tempMin = tempMin;
	}

	public double getTempMin(){
		return tempMin;
	}

	public void setGrndLevel(int grndLevel){
		this.grndLevel = grndLevel;
	}

	public int getGrndLevel(){
		return grndLevel;
	}

	public void setTempKf(double tempKf){
		this.tempKf = tempKf;
	}

	public double getTempKf(){
		return tempKf;
	}

	public void setHumidity(int humidity){
		this.humidity = humidity;
	}

	public int getHumidity(){
		return humidity;
	}

	public void setPressure(int pressure){
		this.pressure = pressure;
	}

	public float getPressure(){
		return pressure;
	}

	public void setSeaLevel(double seaLevel){
		this.seaLevel = seaLevel;
	}

	public double getSeaLevel(){
		return seaLevel;
	}

	public void setTempMax(double tempMax){
		this.tempMax = tempMax;
	}

	public double getTempMax(){
		return tempMax;
	}

	@Override
 	public String toString(){
		return 
			"Main{" + 
			"temp = '" + temp + '\'' + 
			",temp_min = '" + tempMin + '\'' + 
			",grnd_level = '" + grndLevel + '\'' + 
			",temp_kf = '" + tempKf + '\'' + 
			",humidity = '" + humidity + '\'' + 
			",pressure = '" + pressure + '\'' + 
			",sea_level = '" + seaLevel + '\'' + 
			",temp_max = '" + tempMax + '\'' + 
			"}";
		}
}
