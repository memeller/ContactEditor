package pl.mateuszmackowiak.nativeANE.contactManager;


import android.content.Intent;
import android.provider.ContactsContract;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class pickContact implements FREFunction {
	
	public static final String KEY ="pickContact";
	
	
	
	@Override
	public FREObject call(FREContext context, FREObject[] arg1) {
		try{
			PickContactHandler.contactContext = context;
			
			if(ContactEditorContext.pickContactHandler!=null)
				ContactEditorContext.pickContactHandler.finish();
			
			String mimeType=ContactsContract.Contacts.CONTENT_ITEM_TYPE;
			if(arg1!=null && arg1[0]!=null)
				mimeType = arg1[0].getAsString();
			
			
			if(mimeType.equals("finish")){
				ContactEditorContext.pickContactHandler.dispose();
				return null;
			}
			
			
			ContactEditorContext.intent = new Intent(Intent.ACTION_GET_CONTENT);
			ContactEditorContext.intent.setType(mimeType);
			
			Intent in = new Intent(context.getActivity(), PickContactHandler.class);
			context.getActivity().startActivity(in);
			
		}catch(Exception e){
			context.dispatchStatusEventAsync(ContactEditor.ERROR_EVENT,KEY+"   "+e.toString());
		}
		return null;
	}

}
