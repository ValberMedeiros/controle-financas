package br.com.dev.valber.medeiros.controleficancas.repository;

import br.com.dev.valber.medeiros.controleficancas.domain.User;

public interface UserRepository {

    public User findUserByLogin(String login);

}
