// create an abstract class, the subs class belows will have all the attributes stated in this abstract class of VerwunschenerWald
abstract class VerwunschenerWald {
	
	protected Position position;
	protected int schaden = 0; 

	public VerwunschenerWald(Position position){
		this.position = position;
	}
	public Position getPosition(){
		return position;
	}
	public int getSchaden(){
		return schaden;
	}
	public abstract String getName();
}

// create class Rotkaeppchen using abstract class VerwunschenerWald and implements the function in Interface Person
	 class Rotkaeppchen extends VerwunschenerWald implements Person{

		private int gesundheit = 100; // declare and initiate new value of "gesundheit"
       
        public int getGesundheit(){
            return gesundheit;
        }
        public void setGesundheit(int gesundheit){
            this.gesundheit = gesundheit;
        }
        
        public Rotkaeppchen(Position position){
			super(position);
		}
		
        // method to set position of Rotkaeppchen 1 block higher in the map		
		public void geheHoch(){
			super.getPosition().setY(super.getPosition().getY()-1); 
		}
		
        // method to set position of Rotkaeppchen 1 block lower in the map		
		public void geheRunter(){
			super.getPosition().setY(super.getPosition().getY()+1); 
		}
        
        // set position of Rotkaeppchen 1 block to the right
        public void geheRechts(){
			super.getPosition().setX(super.getPosition().getX()+1); 
		}
		
		// set position of Rotkaeppchen 1 block to the Left
		public void geheLinks(){
			super.getPosition().setX(super.getPosition().getX()-1); 
		}

		// schadet the Rotkaeppchen
		public void gesundheitVerringern(int wert){  
			this.gesundheit = gesundheit - wert;
		}
		// method to check if Rotkaeppchen is still alive
		public boolean istNochLebendig(){ 
			if (this.gesundheit > 0){
				return true;
			}
			else{
				return false;
			}
		}
		public String getName(){return "R";} // print the String valiue "R" to represent Rotkaeppchen
		
	    // implementation of interface Person
		public void sprechen(Person konversationspartner, int zaehler){
				Oma oma = new Oma(position);
				konversationspartner = (Person) oma;
				
				if(zaehler == 1){
					System.out.println("Hallo, Oma");
				}else if(zaehler == 3)
					System.out.println("Tschüss, Oma");
				else{
					return;
				}
			}
		}


// create class Oma using abstract class VerwunschenerWald and implements the function in Interface Person
	class Oma extends VerwunschenerWald implements Person{

		public Oma (Position position){
			super(position);
		}
		// analog
		public String getName(){return "O";}
		
        // implementation of interface Person
		public void sprechen(Person konversationspartner, int zaehler){
			Rotkaeppchen rotkaeppchen = new Rotkaeppchen(position);
			konversationspartner = (Person) rotkaeppchen;
			
			if(zaehler == 2){
			    System.out.println("Hallo, Rotkäppchen");
			}
			else{
			    return;
			    
			}
		}
	}

// create class Baum using abstract class VerwunschenerWald
	 class Baum extends VerwunschenerWald{
        //analog
		public Baum (Position position){
			super(position);
		}
		public String getName(){return "B";}
	}

// create class Wolf using abstract class VerwunschenerWald
	class Wolf extends VerwunschenerWald{
        
		public Wolf(Position position){
			super(position);
			super.schaden = 5; // initiate schaden (this will allow the Wolf to damage Rotkaeppchen's gesundheit value)
		}
		public String getName(){return "W";}
	}
	
// create class Gefahr using abstract class VerwunschenerWald
	class Gefahr extends VerwunschenerWald{
    // analog to Wolf
		public Gefahr (Position position){
			super(position);
			super.schaden = 2;
		}
		public String getName(){return "G";}
	}













