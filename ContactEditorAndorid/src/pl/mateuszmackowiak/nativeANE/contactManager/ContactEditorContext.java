package pl.mateuszmackowiak.nativeANE.contactManager;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;


import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

public class ContactEditorContext extends FREContext{
	
	public static PickContactHandler pickContactHandler=null;
	
	
	public static Intent intent;
	
    @Override
    public void dispose(){
    	if(pickContactHandler!=null)
    		pickContactHandler.dispose();
    }
    
    @Override
    public Map<String, FREFunction> getFunctions()
    {
        Map<String, FREFunction> map = new HashMap<String, FREFunction>();
        map.put(getAllContacts.KEY, new getAllContacts());
        map.put(getContactLength.KEY, new getContactLength());
        map.put(removeContact.KEY, new removeContact());
        map.put(newContact.KEY, new newContact());
        map.put(pickContact.KEY, new pickContact());
        map.put(getContactsSimple.KEY, new getContactsSimple());
        map.put(getContactDetails.KEY, new getContactDetails());
        map.put(isSupportedFunction.KEY, new isSupportedFunction());
        return map;
        
    }
    

}
