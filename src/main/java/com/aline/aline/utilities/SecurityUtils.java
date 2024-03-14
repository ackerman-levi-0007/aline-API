package com.aline.aline.utilities;

import com.aline.aline.entities.User;
import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static ObjectId getCurrentUserUserID(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof User user){
            return user.getId();
        }

        return null;
    }
}
