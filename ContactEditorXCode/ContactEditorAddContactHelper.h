//
//  ContactEditorHelper.h
//
//  Created by Paweł Meller, using NativeMail from P.Kościerzyński as help :)
//

#import	 "FlashRuntimeExtensions.h"


@interface ContactEditorAddContactHelper : NSObject<ABNewPersonViewControllerDelegate> {
	FREContext context;
}

-(void) addContactInWindow;

-(void)setContext:(FREContext)ctx;

@end
