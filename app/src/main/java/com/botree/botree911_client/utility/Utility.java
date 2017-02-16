package com.botree.botree911_client.utility;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bhavin on 1/2/17.
 */

public class Utility {

    /**
     * method is used for converting dp to px.
     *
     * @param dp
     * @return px
     */
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * method is used for checking internet is active or not
     *
     * @param mContext
     * @return boolean true for active false for not active
     */
    public static boolean isOnline(Context mContext) {

        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();

    }// End of isOnline()

    /**
     * method is used for checking string is valid or not
     *
     * @param string
     * @return boolean true for valid false for not valid
     */
    public static boolean isValidString(String string) {

        if(string != null && !string.isEmpty() && !string.equals("null")){
            return true;
        }

        return false;

    }// End of isOnline()

    /**
     * method is used for displaying message using Toast.
     *
     * @param mContext, message
     * @return null
     */
    public static void displayMessage(Context mContext, String message) {

        if(message != null){
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }

    }// End of displayMessage()

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {

        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;

    }// End of isEmailValid()

}
