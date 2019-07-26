package com.hisd3.hismk2.security

import com.hisd3.hismk2.config.Constants
import org.springframework.data.domain.AuditorAware
import org.springframework.stereotype.Component

@Component
class SpringSecurityAuditorAware implements AuditorAware<String> {
    @Override
    Optional<String> getCurrentAuditor() {
        return Constants.SYSTEM_ACCOUNT
    }
}
