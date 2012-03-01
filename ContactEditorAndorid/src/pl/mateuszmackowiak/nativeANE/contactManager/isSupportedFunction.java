package pl.mateuszmackowiak.nativeANE.contactManager;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREWrongThreadException;

/**
*
* @author Mateusz Ma�kowiak
*/
public class isSupportedFunction implements FREFunction{
    
    public static final String KEY = "contactEditorIsSupported";

    @Override
    public FREObject call(FREContext context, FREObject[] args) {
        FREObject b = null;
        try{
            b = FREObject.newObject(true);
        }catch (FREWrongThreadException e){
            e.printStackTrace();
        }
        return b;
	}
}
