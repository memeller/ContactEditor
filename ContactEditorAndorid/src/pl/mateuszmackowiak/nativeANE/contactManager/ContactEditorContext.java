package pl.mateuszmackowiak.nativeANE.contactManager;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

public class ContactEditorContext extends FREContext{
	public static final String KEY = "ContactEditorContext";
	
    @Override
    public void dispose(){
    	
    }
    
    @Override
    public Map<String, FREFunction> getFunctions()
    {
		Log.i(KEY, "getFunctions");
       
        Map<String, FREFunction> map = new HashMap<String, FREFunction>();
        map.put(getAllContacts.KEY, new getAllContacts());
        map.put(getContactLength.KEY, new getContactLength());
        map.put(removeContact.KEY, new removeContact());
        map.put(newContact.KEY, new newContact());
        map.put(getContactsSimple.KEY, new getContactsSimple());
        map.put(getContactDetails.KEY, new getContactDetails());
        map.put(isSupportedFunction.KEY, new isSupportedFunction());
        return map;
        
    }
    

}
