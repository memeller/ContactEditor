//
//  ContactEditorAddContactHelper.m
//
//  Created by Paweł Meller, using NativeMail from P.Kościerzyński as help :)
//

#import "ContactEditorAddContactHelper.h"

@implementation ContactEditorAddContactHelper

static  NSString *event_name = @"contactAdded";


-(void) addContactInWindow
{

    ABNewPersonViewController *view = [[ABNewPersonViewController alloc] init];
    view.newPersonViewDelegate = self;
    
    UINavigationController *picker = [[UINavigationController alloc]
                                                       initWithRootViewController:view];
    [[[[UIApplication sharedApplication] keyWindow] rootViewController] presentModalViewController:picker animated:YES];
   
	[picker release];

}
- (void)newPersonViewController:(ABNewPersonViewController *)newPersonView didCompleteWithNewPerson:(ABRecordRef)person;
{
    if(person)
    {
        int personId = (int)ABRecordGetRecordID(person);
        DLog(@"Got person with id: %i",personId);
        NSString * s = [NSString stringWithFormat:@"%i", personId];
        FREDispatchStatusEventAsync(context, (uint8_t*)[event_name UTF8String], (uint8_t*) (uint8_t*)[s UTF8String]);
        [s release];
    }
    [[[[UIApplication sharedApplication] keyWindow] rootViewController] dismissModalViewControllerAnimated:YES];
    [[NSNotificationCenter defaultCenter] postNotificationName:UIDeviceOrientationDidChangeNotification object:nil];
}

-(void)setContext:(FREContext)ctx {
    context = ctx;
}


@end
