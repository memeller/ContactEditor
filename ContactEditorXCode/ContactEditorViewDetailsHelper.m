//
//  ContactEditorViewDetailsHelper.m
//
//  Created by Paweł Meller, using NativeMail from P.Kościerzyński as help :)
//

#import "ContactEditorViewDetailsHelper.h"

@implementation ContactEditorViewDetailsHelper

static  NSString *event_name = @"contactNotFound";


-(void) showContactDetailsInWindow:(ABRecordRef)person
                      isEditable:(BOOL)isEditable 
{

    DLog(@"contact details native window begin");
    ABPersonViewController *picker = [[ABPersonViewController alloc] init];
    if(person)
    {
        picker.allowsEditing = isEditable;
        picker.personViewDelegate = self;
        picker.displayedPerson = person;
        UINavigationController *navController = [[UINavigationController alloc] initWithRootViewController:picker];
        /* includes ios6 function and older, depreciated for ios5 */
        if([[[[[UIApplication sharedApplication] windows] objectAtIndex:0] rootViewController] respondsToSelector:@selector(presentViewController:animated:completion:)])
            [[[[[UIApplication sharedApplication] windows] objectAtIndex:0] rootViewController] presentViewController:navController animated:YES completion:^{/* done */}];
        else
            [[[[[UIApplication sharedApplication] windows] objectAtIndex:0] rootViewController] presentModalViewController:navController animated:YES];
        
        picker.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:NSLocalizedString(@"Back",nil) style:UIBarButtonItemStylePlain target:self action:@selector(ReturnFromPersonView)];
    }
    //[picker release];

}
- (void)ReturnFromPersonView{
    /* includes ios6 function and older for ios5 */
    if([[[[UIApplication sharedApplication] keyWindow] rootViewController] respondsToSelector:@selector(dismissViewControllerAnimated:completion:)])
        [[[[UIApplication sharedApplication] keyWindow] rootViewController] dismissViewControllerAnimated:(YES) completion:nil];
    else if([[[[UIApplication sharedApplication] keyWindow] rootViewController] respondsToSelector:@selector(dismissModalViewControllerAnimated:)])
        [[[[UIApplication sharedApplication] keyWindow] rootViewController] dismissModalViewControllerAnimated:YES];
    
    [[NSNotificationCenter defaultCenter] postNotificationName:UIDeviceOrientationDidChangeNotification object:nil];
}
- (BOOL)personViewController:(ABPersonViewController *)personViewController shouldPerformDefaultActionForPerson:(ABRecordRef)person property:(ABPropertyID)property identifier:(ABMultiValueIdentifier)identifier
{
    return YES;
}
-(void)setContext:(FREContext)ctx {
    context = ctx;
}


@end
