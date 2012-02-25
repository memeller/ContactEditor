//
//  ContactEditor.h
//  ContactEditor
//
//  Created by me on 18.02.2012.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#include <AddressBook/AddressBook.h>
#import "FlashRuntimeExtensions.h"



@interface ContactEditor : NSObject
{
    
}

FREObject addContact(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[]);
FREObject getContacts(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[]);
FREObject getContactCount(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[]);
void ContactEditorContextInitializer(void* extData, const uint8_t* ctxType, FREContext ctx, 
                                     uint32_t* numFunctionsToTest, const FRENamedFunction** functionsToSet);
void ContactEditorContextFinalizer(FREContext ctx);
void ContactEditorExtInitializer(void** extDataToSet, FREContextInitializer* ctxInitializerToSet, 
                                 FREContextFinalizer* ctxFinalizerToSet);

void ContactEditorExtFinalizer(void* extData);

@end
