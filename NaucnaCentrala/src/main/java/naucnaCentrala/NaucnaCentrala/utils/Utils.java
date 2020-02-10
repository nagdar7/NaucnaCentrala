package naucnaCentrala.NaucnaCentrala.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import naucnaCentrala.NaucnaCentrala.model.FormSubmissionDto;

public class Utils {
    public static HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (FormSubmissionDto temp : list) {
            try {
                map.put(temp.getFieldId(), temp.getFieldValue().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication()).map(authentication -> {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                return springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                return (String) authentication.getPrincipal();
            }
            return null;
        });
    }
}