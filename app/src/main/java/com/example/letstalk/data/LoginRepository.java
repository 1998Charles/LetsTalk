package com.example.letstalk.data;

import com.example.letstalk.data.model.LoggedInUser;
import com.example.letstalk.ui.login.LoginActivity;

import java.security.PrivateKey;
import java.util.HashMap;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private LoginActivity loginActivity;
    private HashMap savedInfo = new HashMap(10);

//    private String loginID = "loginID";
//    private String hostName = "hostName";
//    private String portNumber = "portNumber";

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String username, String password) {
        // handle login
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }





    public LoginRepository(String login, String host, String port)
    {
        setInfo("loginID", login);
        setInfo("hostName", host);
        setInfo("portNumber", port);
        //this.loginActivity = loginAct;
    }

    public void setInfo(String infoType, String info)
    {
        savedInfo.put(infoType, info);
    }

    public String getInfo(String infoType)
    {
        return (String) savedInfo.get(infoType);
    }

/*
    public String getLoginID()
    {
        return loginID;
    }

    public String getHostName()
    {
        return hostName;
    }

    public String getPortNumber()
    {
        return portNumber;
    }
*/


}