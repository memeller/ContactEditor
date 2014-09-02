
# Contact Editor - AddressBook Native Extension
##_This project is no longer actively developed by its creator._##

I no longer work with iOS devices, so I am not able to test the extension on real devices. I have updated the project with recompiled .ane (AIR 14 and iOS 7.1 SDK), on Android 4.4 and iOS simulator everything runs ok. If anyone wants to continue our work, you are free to do so. Also this project is available under


### iOS and Android 
author: Paweł Meller & Mateusz Maćkowiak (https://github.com/mateuszmackowiak)



Features:

* iOS 6 support (info below) 
* setContactImage - sets new image used as contact photo
* getContactBitmapData - returns bitmap data of image used as contact photo
* addContactInWindow - shows native window for adding new contact
* showContactDetailsInWindow - shows native window for viewing contact details. If 'isEditable' parameter is set to true, enables editing of contact details.
* pickContact - shows system contact picker, that enables user to select desired contact from list. After selecting the picker is dismissed, and the selected recordId is sent using ContactEditorEvent.CONTACT_SELECTED 
* getContactCount - gets the number of contacts in AddressBook
* getContacts - gets contacts from AddressBook (name, lastname, compositename, phones, emails, recordid, birthdate, addresses)
* addcontact - adds contact to AddressBook
* removeContact - removes contact with specified recordId
* isSupported - returns if ane is supported on current platform, added on request from Mateusz  
added after feedback from Mateusz(https://github.com/mateuszmackowiak)
* getContactsSimple - gets contacts from AddressBook(compositename, recordid), shoud be faster than getContacts, because it requires less processing.
* getContactDetails - gets details for specified contact

Mateusz added implementation for Android platform and cleaned up some mess i left in files, so big thanks to him for his work! :)

As always android requires manifests. Just for accessing contacts informations:

    < uses-permission android:name="android.permission.READ_CONTACTS" />
  
But more if Your app is going to add or remove any info:

    < uses-permission android:name="android.permission.WRITE_CONTACTS" />
    #only if adding:
    < uses-permission android:name="android.permission.GET_ACCOUNTS" />
    
To pick a contact it is necessary to ad also : 
			
			<application>
				<activity android:name="pl.mateuszmackowiak.nativeANE.contactManager.PickContactHandler" android:theme="@android:style/Theme.Translucent.NoTitleBar"></activity>
			</application>
			
### ANE compilation
To compile the ane, osx widh iOS SDK is needed. This ane uses AddressBook framework, which has to be linked during the compilation (sample ane build.sh is provided in build directory, it already includes the link to AddressBook framework).

Tested on iPad, iOS 5.01 / Andorid 3.1,4.03

**2014:** Tested on 7.1 simulator, Android 4.4

### iOS 6/7 support ###
AddressBook access was changed starting from iOS 6, unfortunatelly with no backwards compatibility. I've added the updated code under ios6 branch, i've only had a chance to test it under ios simulator, everything worked well. To compile, AIR 3.5 or later is needed (or earlier version with ios6 sdk linked using platformsdk switch or flash builder 4.7). The compilation works (if you get any errors connected with 'Undefined symbols for architecture armv7:' you have not included ios6 sdk or are not using AIR 3.5), and the ane should also work without problems. Since iOS 6 also brought some changes to presentModalViewController and dismissModalViewControllerAnimated these function calls were also updated. This branch removes the old 'getContacts' method, which is no longer supported (use getContactsSimple + getContactDetails), due to changes in permissions a new function (hasPermission) has been introduced to determine the status of permissions to use address book. Again - ios6 branch is experimental, it has not been yet fully tested on real devices running iOS 6 (Apple, why u not bring iOS 6 to older devices?), so it should not be used in production environment.

###License
This project made available under the MIT License.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


