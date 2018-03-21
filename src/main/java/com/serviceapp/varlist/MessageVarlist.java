/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.varlist;

/**
 *
 * @author prathibha_s
 */
public class MessageVarlist {
    public static final String COMMON_WARN_CHANGE_PASSWORD = "Your password will expire ";
    
    //login
    public static final String LOGIN_EMPTY_USERNAME = "Username or password cannot be empty";
    public static final String LOGIN_EMPTY_USERNAME_PASSWORD = "Username and password cannot be empty";
    public static final String LOGIN_EMPTY_PASSWORD = "Username or password cannot be empty";
    public static final String LOGIN_INVALID = "Your login attempt was not successful. Please try again.";
    public static final String LOGIN_ERROR_LOAD = "Cannot login. Please contact administrator";
    public static final String LOGIN_ERROR_INVALID = "Invalid username or password";
    public static final String LOGIN_DEACTIVE = "User has been deactivated. Please contact administrator";
    public static final String LOGIN_DEACTIVE_ROLE = "User Role has been deactivated. Please contact administrator";
    public static final String LOGIN_INVALID_ROLE = "Invalid user role. Please contact administrator";
    public static final String PASSWORDRESET_EMPTY_PASSWORD = "Current password cannot be empty";
    public static final String PASSWORDRESET_EMPTY_NEW_PASSWORD = "New password cannot be empty";
    public static final String PASSWORDRESET_EMPTY_COM_PASSWORD = "Retype new password cannot be empty";
    public static final String PASSWORDRESET_MATCH_PASSWORD = "Passwords mismatched.";
    public static final String PASSWORDRESET_INVALID_CURR_PWD = "Current password invalid";
    
    public static final String ERROR_ACCESS = "Access denied. Please login again.";
    public static final String ERROR_ACCESSPOINT = "You have been logged in from another machine. Access denied.";
    public static final String PASSWORD_CHANGE_SUCCESS = "Your password changed successfully. Please login with the new password.";
    public static final String ERROR_USER_INFO = "User session not found. Please login again.";
    public static final String ERROR_SESSION = "User session not found. Please login again.";
    
    public static final String COMMON_ERROR_PROCESS = "error occurred while processing";
    public static final String COMMON_NOT_EXISTS = "Record does not exists";
//    public static final String COMMON_AVAILABLE = "Record is available";
    public static final String COMMON_AVAILABLE_MERCHANT = "Merchant ID not exists";
    public static final String COMMON_NOT_AVAILABLE_MERCHANT = "Merchant ID already exists";
    public static final String COMMON_AVAILABLE_TERMINAL = "Terminal ID not exists";
    public static final String COMMON_NOT_AVAILABLE_TERMINAL = "Terminal ID already exists";
    public static final String COMMON_NOT_DELETE = "Record cannot be deleted";
    public static final String COMMON_ALREADY_EXISTS = "Record already exists";
    public static final String COMMON_ALREADY_IN_USE = "Record already in use";
    public static final String COMMON_SUCC_INSERT = "created successfully";
    public static final String COMMON_SUCC_UPDATE = "updated successfully";
    public static final String COMMON_SUCC_UPLOAD = "uploaded successfully";

    public static final String COMMON_SUCC_REJECT = "rejected successfully";
    public static final String COMMON_SUCC_APPROVED = "approved successfully";
    public static final String COMMON_SUCC_HOLD = "status change to hold successfully";
    public static final String COMMON_ERROR_REJECT = "Error occurred while rejecting";
    public static final String COMMON_ERROR_APPROVED = "Error occurred while approving";
    public static final String COMMON_ERROR_PINRESET = "Error occurred while reseting pin";
    public static final String COMMON_ERROR_ATTEMPTRESET = "Error occurred while reseting attempt count";

    public static final String COMMON_ERROR_UPDATE = "Error occurred while updating";
    public static final String COMMON_SUCC_DELETE = "deleted successfully";

    public static final String COMMON_SUCC_CONFIRM = "Confirmed successfully";
    public static final String ALREADY_CONFIRM = "Already confirmed";
    public static final String COMMON_ERROR_DELETE = "Error occurred while deleting";
    public static final String COMMON_SUCC_ASSIGN = "assigned successfully";
    public static final String COMMON_SUCC_ACTIVATE = "Activated successfully";
    public static final String COMMON_ERROR_ACTIVATE = "Error occurred while activating";
    
    //--------------------- Password policy management-------------//
    public static final String PASSPOLICY_MINLEN_INVALID = "Minimum length should be equal or greater than ";
//    public static final String PASSPOLICY_MINLEN_INVALID = "Minimum length should be greater than ";
    public static final String PASSPOLICY_MAXLEN_INVALID = "Maximum length should not exceed ";
    public static final String PASSPOLICY_MINLEN_EMPTY = "Minimum length cannot be empty";
    public static final String PASSPOLICY_MAXLEN_EMPTY = "Maximum length cannot be empty";
    public static final String PASSPOLICY_MIN_MAX_LENGHT_DIFF = "Maximum length should be greater than the minimum length";
//    public static final String PASSPOLICY_SPECCHARS_EMPTY = "Entered special characters allowed";
    public static final String PASSPOLICY_SPECCHARS_EMPTY = "Minimum special characters cannot be empty";
    public static final String PASSPOLICY_SPECCHARS_SHOULD_BE_LESS = "Minimum special characters should be less than ";
    public static final String PASSPOLICY_MINSPECCHARS_EMPTY = "Minimum special characters cannot be empty";
    public static final String PASSPOLICY_MINUPPER_EMPTY = "Minimum upper case characters cannot be empty";
    public static final String PASSPOLICY_MINNUM_EMPTY = "Minimum numerical characters cannot be empty";
    public static final String PASSPOLICY_MINLOWER_EMPTY = "Minimum lower case characters cannot be empty";
    public static final String PASSPOLICY_SUCCESS_ADD = "Password policy successfully added";
    public static final String PASSPOLICY_SUCCESS_DELETE = "Password policy successfully deleted";
    public static final String PASSPOLICY_SUCCESS_UPDATE = "Password policy successfully updated";
    public static final String PASSPOLICY_STATUS_EMPTY = "Select status";
    public static final String PASSPOLICY_POLICYID_EMPTY = "Password policy ID cannot be empty";
    public static final String PASSPOLICY_REPEATE_CHARACTERS_EMPTY = "Allowed repeat characters cannot be empty";
    public static final String PASSPOLICY_REPEATE_CHARACTERS_ZERO = "Allowed repeat characters should be greater than 0";
    public static final String PASSPOLICY_INIT_PASSWORD_EXPIRY_STATUS_EMPTY = "Initial password expiry status cannot be empty";
    public static final String PASSPOLICY_PASSWORD_EXPIRY_PERIOD_EMPTY = "Password expiry period cannot be empty";
    public static final String PASSPOLICY_NO_OF_HISTORY_PASSWORD_EMPTY = "No. of history passwords cannot be empty";
    public static final String PASSPOLICY_MIN_PASSWORD_CHANGE_PERIOD_EMPTY = "Password expiry notification period cannot be empty";
    public static final String PASSPOLICY_IDLE_ACCOUNT_EXPIRY_PERIOD_EMPTY = "Idle account expiry period cannot be empty";
    public static final String PASSPOLICY_IDLE_ACCOUNT_EXPIRY_PERIOD_INVALID = "Idle account expiry period should be 10 days or above";
    public static final String PASSPOLICY_PASSWORD_EXPIRY_PERIOD_INVALID = "Password expiry period should be 10 days or above";
    public static final String PASSPOLICY_NO_OF_INVALID_LOGIN_ATTEMPTS_EMPTY = "No. of invalid login attempts cannot be empty";
    public static final String PASSPOLICY_NO_OF_HISTORY_PASSWORD_INVALID = "No. of history passwords should be 1 or above";
    
    
    
    public static final String SYSUSER_PASSWORD_TOO_SHORT = "Password is shorter than the expected minimum length ";
    public static final String SYSUSER_PASSWORD_TOO_LARGE = "Password is longer than the expected maximum length ";
    public static final String SYSUSER_PASSWORD_LESS_LOWER_CASE_CHARACTERS = "Lower case characters are less than required ";
    public static final String SYSUSER_PASSWORD_LESS_UPPER_CASE_CHARACTERS = "Upper case characters are less than required ";
    public static final String SYSUSER_PASSWORD_MORE_CHAR_REPEAT = "Password contains characters repeating more than allowed ";
    public static final String SYSUSER_PASSWORD_LESS_NUMERICAL_CHARACTERS = "Numerical characters are less than required ";
    public static final String SYSUSER_PASSWORD_LESS_SPACIAL_CHARACTERS = "Special characters are less than required ";
    public static final String SYSUSER_PASSWORD_MISSMATCH = "Passwords mismatched ";
    public static final String RESET_PASSWORD_SUCCESS = "Password reset successful ";
    public static final String SYSUSER_DEL_INVALID = " is already Logged-In, cannot be deleted! ";
    public static final String RESET_PASS_NEW_EXIST = "New password already exists in history ";
    public static final String RESET_PASS_CURRENT_EXIST = "Current password already exists in history ";
    public static final String RESET_PASS_SAME_NEW_CURRENT = "New password and current password cannot be the same ";
    
    public static final String SYSUSER_MGT_EMPTY_USERNAME = "Username cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_PASSWORD = "Password cannot be empty";
    public static final String SYSUSER_MGT_PASSWORD_MISSMATCH = "Confirm password mismatch with the password";
    public static final String SYSUSER_MGT_EMPTY_USERROLE = "User role cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_DUALAUTHUSER = "Dual auth user cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_STATUS = "Status cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_SERVICEID = "Service ID cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_EXPIRYDATE = "Password expiry date cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_DATEOFBIRTH = "Date of birth cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_FULLNAME = "Full name cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_ADDRESS1 = "Address cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_ADDRESS2 = "Address2 cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_CITY = "City cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_COANTACTNO = "Contact number cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_FAX = "Fax cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_EMAIL = "Email cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_NIC = "NIC cannot be empty";
    public static final String SYSUSER_MGT_INVALID_EMAIL = "Invalid email";
    public static final String SYSUSER_MGT_INVALID_NIC = "Invalid NIC";
    public static final String SYSUSER_MGT_INVALID_NIC_DOB = "Date of birth does not match with NIC";
    public static final String SYSUSER_MGT_INVALID_CONTACT_NO = "Invalid contact number ";
    
}
