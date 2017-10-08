package mailchimp

import mailchimp.method.v2_0.lists.ListMethod
import mailchimp.method.v2_0.lists.MergeVarListMethod

/**
 * Created by sajedur on 1/3/2016.
 */
class Test {
    public static void main(String[] args) {
        ListMethod method = new ListMethod()
        method.apikey = "bcc534a46d190974ae4d8310b47a763d-us9";
        MailChimpClient client = new MailChimpClient()
        println client.execute(method)

        MergeVarListMethod method2 = new MergeVarListMethod()
        method2.apikey = "bcc534a46d190974ae4d8310b47a763d-us9";
        method2.id = ["3f6098aeb3"]
        println client.execute(method2)
    }
}
