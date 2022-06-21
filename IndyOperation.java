package autotrade;

public class IndyOperation {
	
	public Series cross(Series indy1, Series indy2) {
		Series signal = new Series();
		
		for(int i = 0 ;i < indy1.size() ; i ++) {
			if(i == 0 ) {
				signal.add(0);			
			}
			
			else if(indy1.get(i-1) < indy2.get(i-1) && indy1.get(i) > indy2.get(i)) {
				signal.add(1); // cross up
			} 
			else if(indy1.get(i-1) > indy2.get(i-1) && indy1.get(i) < indy2.get(i)) {
				signal.add(-1); // cross down
			} 
			else {
				signal.add(0);
			}
			
		}
		
		
		return signal;
		
	}
}
