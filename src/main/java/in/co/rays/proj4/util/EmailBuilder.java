package in.co.rays.proj4.util;

import java.util.HashMap;

/**
 * EmailBuilder class is used to build different types
 * of HTML based email messages for ORS.
 * 
 * It supports:
 * <ul>
 *   <li>User Registration mail</li>
 *   <li>Forgot Password mail</li>
 *   <li>Change Password mail</li>
 * </ul>
 * 
 * @author Deepak Verma
 * @version 1.0
 */
public class EmailBuilder {

    /**
     * Builds registration success email HTML.
     * 
     * @param map contains login and password
     * @return formatted HTML email
     */
    public static String getUserRegistrationMessage(HashMap<String, String> map) {

        StringBuilder msg = new StringBuilder();

        msg.append("<html><body style='font-family:Arial;'>");
        msg.append("<h2 style='color:#2E86C1;'>Welcome to ORS</h2>");
        msg.append("<p>Dear <b>").append(map.get("login")).append("</b>,</p>");

        msg.append("<p>Your registration is successfully completed.</p>");

        msg.append("<p><b>Login Id:</b> ").append(map.get("login")).append("</p>");
        msg.append("<p><b>Password:</b> ").append(map.get("password")).append("</p>");

        msg.append("<p style='color:red;'>For security reasons, please change your password after login.</p>");

        msg.append("<hr>");
        msg.append("<p>For support contact:</p>");
        // Using HTML entities instead of raw emoji to avoid encoding issues
        msg.append("<p>&#x260E; +91 98273 60504</p>");   // telephone symbol
        msg.append("<p>&#x2709; hrd@sunrays.co.in</p>"); // envelope symbol

        msg.append("<br><p><b>Thanks &amp; Regards</b><br>ORS Team</p>");
        msg.append("</body></html>");

        return msg.toString();
    }

    /**
     * Builds forgot password email HTML.
     * 
     * @param map contains firstName, lastName, login and password
     * @return formatted HTML email
     */
    public static String getForgetPasswordMessage(HashMap<String, String> map) {

        StringBuilder msg = new StringBuilder();

        msg.append("<html><body style='font-family:Arial;'>");
        msg.append("<h2 style='color:#E67E22;'>Password Recovery</h2>");

        msg.append("<p>Hello <b>")
           .append(map.get("firstName")).append(" ")
           .append(map.get("lastName")).append("</b>,</p>");

        msg.append("<p>Your login credentials are:</p>");

        msg.append("<p><b>Login Id:</b> ").append(map.get("login")).append("</p>");
        msg.append("<p><b>Password:</b> ").append(map.get("password")).append("</p>");

        msg.append("<hr>");
        msg.append("<p>If you did not request this, please ignore this email.</p>");

        msg.append("<br><p><b>ORS Support Team</b></p>");
        msg.append("</body></html>");

        return msg.toString();
    }

    /**
     * Builds change password confirmation email HTML.
     * 
     * @param map contains firstName, lastName, login and new password
     * @return formatted HTML email
     */
    public static String getChangePasswordMessage(HashMap<String, String> map) {

        StringBuilder msg = new StringBuilder();

        msg.append("<html><body style='font-family:Arial;'>");
        msg.append("<h2 style='color:#27AE60;'>Password Changed Successfully</h2>");

        msg.append("<p>Dear <b>")
           .append(map.get("firstName")).append(" ")
           .append(map.get("lastName")).append("</b>,</p>");

        msg.append("<p>Your password has been updated successfully.</p>");

        msg.append("<p><b>Login Id:</b> ").append(map.get("login")).append("</p>");
        msg.append("<p><b>New Password:</b> ").append(map.get("password")).append("</p>");

        msg.append("<hr>");
        msg.append("<p>If this change was not done by you, immediately contact ORS support.</p>");

        msg.append("<br><p><b>ORS Team</b></p>");
        msg.append("</body></html>");

        return msg.toString();
    }
}
