package com.orlov.restaurantservice.utils;

import com.orlov.restaurantservice.dto.UserUUIDDto;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class SecurityUtils {
    public static UUID getContextUserUUID(){
        return UUID.fromString(((UserUUIDDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUuid());
    }
}
