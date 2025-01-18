package com.orlov.orderservice.utils;

import com.orlov.orderservice.dto.UserUUIDDto;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class SecurityUtils {
    public static UUID getContextUserUUID(){
        return UUID.fromString(((UserUUIDDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUuid());
    }
}
