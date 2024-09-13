package com.aline.aline.utilities;

import com.aline.aline.cache.ThreadLocalCache;
import com.aline.aline.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static String getCurrentUserUserID(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof User user){
            return user.getId().toString();
        }

        return null;
    }

    public static User getLoggedInUser(){
        return (User) ThreadLocalCache.get("loggedInUser");
    }
}
