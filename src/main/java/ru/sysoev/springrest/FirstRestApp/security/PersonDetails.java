package ru.sysoev.springrest.FirstRestApp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.sysoev.springrest.FirstRestApp.models.Person;

import java.util.Collection;
import java.util.Collections;

public class PersonDetails implements UserDetails {
    private Person person;

    public PersonDetails(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
        /*
        Этот метод возвращает коллекцию объектов GrantedAuthority,
        которые представляют права доступа пользователя.
        Это могут быть роли или конкретные разрешения, которые определяют, что пользователь может делать в системе.
        * */
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
        /*
        * Возвращает true, если учетная запись пользователя не истекла.
        * Если учетная запись истекла, пользователь не может аутентифицироваться.
         */
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
        /*
        Возвращает true, если учетная запись пользователя не заблокирована.
        Заблокированные учетные записи не могут аутентифицироваться.
        */
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
        /*
        * Возвращает true, если учетные данные (например, пароль) пользователя не истекли.
        */
    }

    @Override
    public boolean isEnabled() {
        return true;
        /*
        Возвращает true, если учетная запись пользователя включена.
        Отключенные учетные записи не могут аутентифицироваться.
        */
    }
    public Person getPerson() {
        return this.person;
    }
}
