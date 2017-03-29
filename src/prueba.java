
public class prueba {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "aBc";
		for(int i=0;i<s.length();i++){
			String l = s.substring(i, i+1);
			if(l.matches("[A-Z]"))
				System.out.println("Si");
		}
	
	}

}
