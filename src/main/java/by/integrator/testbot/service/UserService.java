package by.integrator.testbot.service;

import by.integrator.testbot.models.User;
import by.integrator.testbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    public void save(User user){
        repository.save(user);
    }

    public boolean existByTelegramId(Integer id){
        return repository.findByTelegramId(id).isPresent();
    }

    public User getUser(Integer id){
        return repository.findByTelegramId(id).orElse(null);
    }
}
