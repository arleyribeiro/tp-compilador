package Util;

public class Util {

    public static boolean isNumeric(String type) {
        
    	if (type.equals("integer")) {
            return true;
        }
    	
        return false;
        
    }

    public static String getNumericType(String type1, String type2) {
        if (type1.equals("real") || type2.equals("real")) {
            return "real";
        }
        return "integer";
    }
    
    public static boolean canAssign(String type1, String type2) {
        
    	if (isNumeric(type1) && isNumeric(type2)) {
            
            if (type1.equals("integer") || type2.equals("integer")) {
                return true;
            }
        
    	} else {
           
    		if (type1.equals(type2)) {
                return true;
            }
        }
    	
        return false;
        
    }
    
}
