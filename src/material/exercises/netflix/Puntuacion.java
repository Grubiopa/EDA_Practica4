package material.exercises.netflix;

public class Puntuacion{
	private float maxvalue = 5;
	private float minvalue = 0;
	private float value;
	
	public Puntuacion(float puntuacion){
		if(puntuacion>maxvalue)
			this.value=5;
		if(puntuacion<minvalue)
			this.value=0;
		if(puntuacion>minvalue && puntuacion<maxvalue)
			this.value=puntuacion;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		if(value>maxvalue)
			this.value=5;
		if(value<minvalue)
			this.value=0;
		if(value>minvalue && value<maxvalue)
			this.value=value;
	}
	
}
