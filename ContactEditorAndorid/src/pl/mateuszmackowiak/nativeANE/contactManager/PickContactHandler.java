package pl.mateuszmackowiak.nativeANE.contactManager;


import com.adobe.fre.FREContext;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;



public class PickContactHandler extends Activity {
	
	private static final int REQUEST_CODE = 343222;
	
	public static FREContext contactContext=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

	    	super.onCreate(savedInstanceState);
	    	ContactEditorContext.pickContactHandler = this;
	    	
	        startActivityForResult(ContactEditorContext.intent, REQUEST_CODE);

    }
    public void dispose(){
    	ContactEditorContext.intent =null;
    	contactContext = null;
    	finish();
    }

    @Override
    public void finish(){
    	ContactEditorContext.pickContactHandler = null;
    	super.finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    	if (data != null) {
            Uri uri = data.getData();
            
            if (uri != null) {
                Cursor c = null;

                try {
                    c = getContentResolver().query(uri, null, null, null, null);
                    if (c != null && c.moveToFirst()) {
                        
                        int _id = c.getInt(c.getColumnIndex("contact_id"));
                       
                        contactContext.dispatchStatusEventAsync(ContactEditor.CONTACT_SELECTED,Integer.toString(_id));
                    }
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
            }
        }
    	
    	finish();
    }
}
