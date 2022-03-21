package com.hilton.assignment.validator;

import com.hilton.assignment.annotation.ValidIP;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Copyright Any portion of this assignment's code are not allowed to use in business or production.
 *
 * Custom ip4 and ip6 validator
 * @author Anjana
 *
 */

public class IpValidator implements
        ConstraintValidator<ValidIP, String> {
    // Regex for IPv4
    final String  ipv4Regex = "(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])";

    // Regex for IPv6
    final String  ipv6Regex = "((([0-9a-fA-F]){1,4})\\:){7}([0-9a-fA-F]){1,4}";

    @Override
    public void initialize(ValidIP ip) {
    }

    @Override
    public boolean isValid(String ip, ConstraintValidatorContext cxt) {
        return ip != null && (ip.matches(ipv4Regex) || ip.matches(ipv6Regex));
    }
}
