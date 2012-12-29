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
    /* includes ios6 function and older, depreciated for ios5 */
    if([[[[[UIApplication sharedApplication] windows] objectAtIndex:0] rootViewController] respondsToSelector:@selector(presentViewController:animated:completion:)])
        [[[[[UIApplication sharedApplication] windows] objectAtIndex:0] rootViewController] presentViewController:picker animated:YES completion:^{/* done */}];
    else
        [[[[[UIApplication sharedApplication] windows] objectAtIndex:0] rootViewController] presentModalViewController:picker animated:YES];
    
   
	//[picker release];

}
- (void)newPersonViewController:(ABNewPersonViewController *)newPersonView didCompleteWithNewPerson:(ABRecordRef)person;
{
    if(person)
    {
        int personId = (int)ABRecordGetRecordID(person);
        DLog(@"Got person with id: %i",personId);
        NSString * s = [NSString stringWithFormat:@"%i", personId];
        FREDispatchStatusEventAsync(context, (uint8_t*)[event_name UTF8String], (uint8_t*) (uint8_t*)[s UTF8String]);
        //[s release];
    }
    /* includes ios6 function and older for ios5 */
    if([[[[UIApplication sharedApplication] keyWindow] rootViewController] respondsToSelector:@selector(dismissViewControllerAnimated:completion:)])
        [[[[UIApplication sharedApplication] keyWindow] rootViewController] dismissViewControllerAnimated:(YES) completion:nil];
    else if([[[[UIApplication sharedApplication] keyWindow] rootViewController] respondsToSelector:@selector(dismissModalViewControllerAnimated:)])
        [[[[UIApplication sharedApplication] keyWindow] rootViewController] dismissModalViewControllerAnimated:YES];
    
    [[NSNotificationCenter defaultCenter] postNotificationName:UIDeviceOrientationDidChangeNotification object:nil];
}

-(void)setContext:(FREContext)ctx {
    context = ctx;
}


@end
