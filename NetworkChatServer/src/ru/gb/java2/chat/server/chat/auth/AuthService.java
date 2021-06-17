package ru.gb.java2.chat.server.chat.auth;

import java.util.Set;

public class AuthService {
   /* private static Map<User,String> USERS =Map.of(
            new User("login1", "pass1", "username1"), "username1",
            new User("login2", "pass3", "username2"), "username2",
            new User("login3", "pass3", "username3"), "username3"
    );*/
    Set<User> USERS = Set.of(
           new User("login1", "pass1", "username1",false),
           new User("login2", "pass2", "username2",false),
           new User("login3", "pass3", "username3",false)

   );

    public String getUsernameByLoginAndaPassword(String login, String password){
//        return  USERS.get((new User(login,password,null)));
        User requiredUser = new User(login,password);
        for (User user : USERS) {
            if(requiredUser.equals(user) & !user.isConnect()){
                user.setConnect(true);
                return user.getUsername();
            }
        }

        return null;
    }
}
