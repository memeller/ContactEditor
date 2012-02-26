
/*

ADOBE SYSTEMS INCORPORATED
Copyright 2011 Adobe Systems Incorporated
All Rights Reserved.

NOTICE:  Adobe permits you to use, modify, and distribute this file in accordance with the
terms of the Adobe license agreement accompanying it.  If you have received this file from a
source other than Adobe, then your use, modification, or distribution of it requires the prior
written permission of Adobe.

*/


package pl.mllr.extensions.contactEditor
{
	import flash.external.ExtensionContext;
	
	import mx.core.mx_internal;
	
	public class ContactEditor
	{
		private static var extContext:ExtensionContext = null;
		
		private static var _instance:ContactEditor = null;
		private static var _shouldCreateInstance:Boolean = false;
		
		/**
		 * initializes contacteditor
		 */
		public function ContactEditor()
		{
			extContext = ExtensionContext.createExtensionContext("pl.mllr.extensions.contactEditor",null);		
		}
		/**
		 *Adds contact to AddressBook 
		 * @param name first name
		 * @param lastname last name
		 * @param phone phone number (currently only home supported)
		 * @param company company name
		 * @param email email address (currently only home supported)
		 * @param website website (currently only home supported)
		 * 
		 */		
		public function addContact(name:String,lastname:String="",phone:String="",company:String="",email:String="",website:String=""):void
		{  
			
			extContext.call("addContact",name,lastname,phone,company,email,website) ;
		}
		/**
		 * gets all contacts from AddressBook 
		 * @return an array of contacts with following: name, lastname, compositename, birthdate, recordid, phones (array), emails (array);
		 * 
		 */		
		public function getContacts():Array
		{  
			
			return extContext.call("getContacts") as Array;
		}
		/**
		 *returns number of contacts in AddressBook 
		 * @return num of contacts
		 * 
		 */		
		public function getContactCount():int
		{
			return extContext.call("getContactCount") as int;
		}
		/**
		 *removes contact with specified recordId 
		 * @param recordId record identifier, as set in AddressBook on the device
		 * @return true if contact was removed, false if not found
		 * 
		 */		
		public function removeContact(recordId:int):Boolean
		{
			return extContext.call("removeContact",recordId) as Boolean;
		}
	}
}





