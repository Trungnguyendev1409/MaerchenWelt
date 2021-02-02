import java.util.Random;
import java.util.ArrayList;

public class Maerchenwelt{
	
	private int x;
	private int y;
	
	private VerwunschenerWald[][] karte; 
	private Oma oma;
	private Rotkaeppchen rotkaeppchen;
    private Wolf wolf;
    private Gefahr gefahren;
    
// CONSTRUCTOR	
	public Maerchenwelt (int x, int y, int gefahrenAnzahl, int baumAnzahl){

		this.x = x;
		this.y = y;
		
	// karte initialized
		this.karte = new VerwunschenerWald[x][y];
		
	// create and place object ROTKAEPPCHEN at (0,0)
		Position posOfRotkaeppchen = new Position(0,0);
		this.rotkaeppchen = new Rotkaeppchen(posOfRotkaeppchen);
		karte[0][0] = rotkaeppchen;
		
		
	// create and place object OMA randomly with Random()
		Random r = new Random();
		    // limit the permitted area to place Oma
		int maxX = x-1;
		int minX = x-8;
		int maxY = y-1;
		int minY = y-8;
            // place Oma with random value created above
		int oX = r.nextInt((maxX-minX) +1) + minX;
		int oY = r.nextInt((maxY-minY) +1) + minY; 
		Position posOfOma = new Position(oX,oY);
		this.oma = new Oma(posOfOma);
		karte[oX][oY] = oma;

	// create and place objects Gefahren randomly with Random() analogs aboves
		int count = 0;
		gefahrenAnzahl = 4;
		
		while (count < gefahrenAnzahl){
		    
		    int gX = r.nextInt(x);
		    int gY = r.nextInt(y);
		    
		    if(this.karte[gX][gY] == null){
		        
		        Position posOfGefahren = new Position(gX,gY);
		        this.gefahren = new Gefahr(posOfGefahren);
		        karte[gX][gY] = gefahren;
		        count ++;
		    }
		}
		

    // create and place objects BAUM randomly with Random()
		int currentNumberOfTree = 0;
		    // while loop to stop the creation of Baum as its number reaches the input value
		while (currentNumberOfTree < baumAnzahl){
			
			int bX = r.nextInt(x);
			int bY = r.nextInt(y);
            
            // condition to place trees not on the already covered spots
			if (this.karte[bX][bY] == null){
				
				Position posOfBaum = new Position(bX,bY);
				Baum newBaum = new Baum(posOfBaum);
				karte[bX][bY] = newBaum;
				currentNumberOfTree ++;
			}
		}
		
		
	// create and place objects Wolf randomly with Random() analogs aboves
	    int wX = r.nextInt(x);
		int wY = r.nextInt(y);
		
		if(this.karte[wX][wY] == null){
		    
		    Position posOfWolf = new Position(wX,wY);
		    this.wolf = new Wolf(posOfWolf);
		    karte[wX][wY] = wolf;
		    
		}
		
	// catch 2 Exceptions 
		try{
		    // when map too small
			if(x < 10 || y < 10) 
				throw new IllegalArgumentException("Vergrößern Sie den Verwunschenen Wald");
		}catch(IllegalArgumentException e){
			System.out.println(e.getMessage());
			}
            // too many trees and obstables
		try{
			if(baumAnzahl + gefahrenAnzahl > ((x*y)-3)) 
				throw new IllegalArgumentException("Reduzieren Sie die Anzahl der Bäume und Gefahren");
		}catch(IllegalArgumentException e){
			System.out.println(e.getMessage());
			}
	}
	
// END OF CONSTRUCTOR
	
	public VerwunschenerWald[][] getKarte(){
		return this.karte;
	}
	public Rotkaeppchen getRotkaeppchen(){
		return this.rotkaeppchen;
	}
	public Oma getOma(){
		return this.oma;
	}




// Method allows Rotkaeppchen to move 
	ArrayList<Position> beenTo = new ArrayList<Position>(); // ArrayList to store location of Rotkaeppchen as she moves around
	public ArrayList<Position> wegFinden(Position ziel){
		int zuegeAnzahl = 0;
        
    // while loop till Rotkaeppchen reaches Oma, dies or passes the allowed number of moves 
		while (!rotkaeppchen.getPosition().equals(ziel)){

			Random direction = new Random();
			int d = direction.nextInt(4);
				int rX = rotkaeppchen.getPosition().getX();
				int rY = rotkaeppchen.getPosition().getY();
				Position p = new Position(rX,rY);
				
        // Rotkaeppchen moves 1 block higher
			if(d==0 && (rotkaeppchen.getPosition().getY()-1) > 0){
                // condition to avoid trees
               if(karte[rX][rY-1]==null || karte[rX][rY-1].getName() == "R"  || karte[rX][rY-1].getName() != "B"){ 
					rotkaeppchen.geheHoch();
					
				// when encounters Wolf
					if(rotkaeppchen.getPosition().equals(wolf.getPosition())){
					    rotkaeppchen.gesundheitVerringern(wolf.getSchaden());
					    // checking health 
					    if(rotkaeppchen.istNochLebendig()==false){
					        break;
					    }
					}
				// encounters Gefahren
					else if(rotkaeppchen.getPosition().equals(gefahren.getPosition())){
					    rotkaeppchen.gesundheitVerringern(gefahren.getSchaden());
					    // checking health 
					    if(rotkaeppchen.istNochLebendig()==false){
					        break;
					    }
					}
					
				    karte[rX][rY] = rotkaeppchen; // locate Rotkaeepchen
				    beenTo.add(p); // add position to ArrayList
					zuegeAnzahl ++;
               }else{
                   beenTo.add(p);
					zuegeAnzahl ++;
                    }
		    	}
		// Rotkaeppchen moves 1 block lower	
			if(d==1 && (rotkaeppchen.getPosition().getY()+1 < y)){
			    // condition to avoid trees
				if(karte[rX][rY+1]==null ||karte[rX][rY+1].getName() == "R"  ||karte[rX][rY+1].getName() != "B"){
					rotkaeppchen.geheRunter();
					
					// when encounters Wolf
					if(rotkaeppchen.getPosition().equals(wolf.getPosition())){
					    rotkaeppchen.gesundheitVerringern(wolf.getSchaden());
					    // checking health 
					    if(rotkaeppchen.istNochLebendig()==false){
					        break;
					    }
					}
					// encounters Gefahren
					else if(rotkaeppchen.getPosition().equals(gefahren.getPosition())){
					    rotkaeppchen.gesundheitVerringern(gefahren.getSchaden());
					    // checking health 
					    if(rotkaeppchen.istNochLebendig()==false){
					        break;
					    }
					}
					karte[rX][rY] = rotkaeppchen;
					beenTo.add(p);
					zuegeAnzahl ++;
				}else{
                   beenTo.add(p);					
                   zuegeAnzahl ++;
               }
			}
			
			if(d==2 && (rotkaeppchen.getPosition().getX()-1) > 0){
			//   System.out.println("x: " + rX +"," + "y: "+rY + ","+"d: " + d);
			    if(karte[rX-1][rY]==null ||karte[rX-1][rY].getName() == "R"  ||karte[rX-1][rY].getName() != "B"){
					rotkaeppchen.geheLinks();
					
					// when encounters Wolf
					if(rotkaeppchen.getPosition().equals(wolf.getPosition())){
					    rotkaeppchen.gesundheitVerringern(wolf.getSchaden());
					    // checking health 
					    if(rotkaeppchen.istNochLebendig()==false){
					        break;
					    }
					}// encounters Gefahren
					else if(rotkaeppchen.getPosition().equals(gefahren.getPosition())){
					    rotkaeppchen.gesundheitVerringern(gefahren.getSchaden());
                          
                        // checking health 
					if(rotkaeppchen.istNochLebendig()==false){
					        break;
					    }
					}
					karte[rX][rY] = rotkaeppchen;
					beenTo.add(p);
					zuegeAnzahl ++;
			    }else{
                   beenTo.add(p);
					zuegeAnzahl ++;
               }
			}
			
			if(d == 3 && (rotkaeppchen.getPosition().getX()+1) < x){
			 //   System.out.println("x: " + rX +"," + "y: "+rY + ","+"d: " + d);
			    if(karte[rX+1][rY]==null || karte[rX+1][rY].getName() == "R"  ||karte[rX+1][rY].getName() != "B"){
				
					rotkaeppchen.geheRechts();
					// when encounters Wolf
					if(rotkaeppchen.getPosition().equals(wolf.getPosition())){
					    rotkaeppchen.gesundheitVerringern(wolf.getSchaden());
					    
					    if(rotkaeppchen.istNochLebendig()==false){
					        break;
					    }
					}// encounters Gefahren
					else if(rotkaeppchen.getPosition().equals(gefahren.getPosition())){
					    rotkaeppchen.gesundheitVerringern(gefahren.getSchaden());

					    if(rotkaeppchen.istNochLebendig()==false){
					        break;
					    }
					}
					karte[rX][rY] = rotkaeppchen;
					beenTo.add(p);
					zuegeAnzahl ++;
			    }else{
                   beenTo.add(p);
					zuegeAnzahl ++;
					
               }
			}
			if (zuegeAnzahl >= 500){
				break; // breaks when reaches allowed amount of moves
			}
		}
		
		if(rotkaeppchen.getPosition().equals(ziel)){
		    rotkaeppchen.setGesundheit(100); // restores Gesundheit when Rotkaeppchen reaches Oma
		}
		 
		beenTo.add(rotkaeppchen.getPosition());
		return (beenTo); // returns traveled route of Rotkaeppchen
		}
		

// print Wald
	public void printWald(){
                // Rahmen: linke obere Ecke
        System.out.print("+");
                // Rahmen: erste Zeile
            for(int i = 0; i < x; i++){
                System.out.print("-");
                }
                // Rahmen: rechte obere Ecke
                System.out.println("+");
            for(int j = 0; j < y; j++) {
                // Rahmen: linker Rand
                System.out.print("|");
                // Die eigentliche Karte
            	for(int i = 0; i < x; i++) {
                	if (karte[i][j] != null ){
                		System.out.print(karte[i][j].getName());
                	}
                	else{
                		System.out.print(" ");
                		}
            		}
                // Rahmen: rechter Rand
                System.out.println("|");
                }
                // Rahmen: linke untere Ecke
                	System.out.print("+");
                // Rahmen: letzte Zeile
            for(int i = 0; i < x; i++){
                System.out.print("-");
                }
                // Rahmen: rechte untere Ecke
            System.out.println("+");
        }

// the triger for the happening of everything

    public void start(){
    	printWald();
    	// Rotkaeppchen starts moving to find way
    	wegFinden(oma.getPosition());
        
        // checking if R have reached O
    	if(beenTo.get(beenTo.size()-1).equals(oma.getPosition())){
    		System.out.println("Rotkäppchen ist bei Oma angekommen.");
    		
    		// the conversation when meet up with Oma
    		for(int zaehler = 1; zaehler <= 4; zaehler ++){
    		    rotkaeppchen.sprechen(oma, zaehler);
    		    oma.sprechen(rotkaeppchen, zaehler);
    		    }
    	    }
    	    else{
    	    // got lost
    		System.out.println("Rotkäppchen hat sich auf dem Weg zur Oma verlaufen.");
    	    }

    	// find the way back
    	wegFinden(beenTo.get(0));
    	
    	      // reaches home
    	if(rotkaeppchen.getPosition().equals(beenTo.get(0))){
    	    System.out.println("Rotkäppchen ist wieder zuhause angekommen.");
        }
             // gesundheit = 0
        else if(rotkaeppchen.istNochLebendig()==false){
            System.out.println("Rotkäppchen ist nicht wieder zuhause angekommen.");
        }
             // could'nt find the way back
        else{
    		System.out.println("Rotkäppchen hat sich auf dem Heimweg verlaufen.");
    	}
    }
}












