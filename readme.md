# Contact Editor - AddressBook Native Extension #
### now for iOS and Android ###
author: memeller@gmail.com & Mateusz Maćkowiak (https://github.com/mateuszmackowiak)
Currently supported:

* getContactCount - gets the number of contacts in AddressBook
* getContacts - gets contacts from AddressBook (name, lastname, compositename, phones, emails, recordid, birthdate)
* addcontact - adds contact to AddressBook
* removeContact - removes contact with specified recordId
* isSupported - returns if ane is supported on current platform, added on request from Mateusz  
added after feedback from Mateusz(https://github.com/mateuszmackowiak)
* getContactsSimple - gets contacts from AddressBook(compositename, recordid), shoud be faster than getContacts, because it requires less processing.
* getContactDetails - gets details for specified contact

Mateusz added implementation for Android platform and cleaned up some mess i left in files, so big thanks to him for his work! :)

### ANE compilation ###
To compile the ane, osx widh iOS SDK is needed. This ane uses AddressBook framework, which has to be linked during the compilation (sample ane build.sh is provided in build directory, it already includes the link to AddressBook framework).

Not much, but still better than accesing the sql database directly ;)
Tested on iPad, iOS 5.01