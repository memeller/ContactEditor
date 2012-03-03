package pl.mllr.extensions.contactEditor
{
	import flash.events.Event;
	
	public class ContactEditorEvent extends Event
	{
		public static const CONTACT_SELECTED:String = "contactSelected";
		
		private var _recordId:int =-1;
		public function ContactEditorEvent(type:String,_id:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			_recordId = int(_id);
			super(type, bubbles, cancelable);
		}
		
		/**
		 * the identyfication number of contact
		 */
		public function get recordId():int
		{
			return _recordId;
		}

	}
}