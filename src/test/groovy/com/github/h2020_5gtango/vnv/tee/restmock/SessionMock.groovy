package com.github.h2020_5gtango.vnv.tee.restmock

import com.github.h2020_5gtango.vnv.tee.model.Session
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SessionMock {


    @PostMapping('/mock/gk/sessions')
    Session createSession(@RequestBody Login login) {
        new Session(
                session_began_at: new Date(),
                username: login.username,
                token: new Session.Token(
                        access_token: 'foo_bar',
                        expires_in: 1200,
                )
        )
    }

    static class Login {
        String username
        String password
    }
}
