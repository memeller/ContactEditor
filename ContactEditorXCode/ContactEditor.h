/*
Contact editor by memeller@gmail.com
isSupported function by https://github.com/mateuszmackowiak
*/

#import "FlashRuntimeExtensions.h"



@interface ContactEditor : NSObject
{
    
}
FREObject getContactsSimple(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[]);
FREObject getContactDetails(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[]);
FREObject contactEditorIsSupported(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[] );
FREObject removeContact(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[]);
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
