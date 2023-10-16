package br.com.nathaliacarletti.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Modificador
 * public
 * private
 * protected
 */

 @RestController
 @RequestMapping("/users")
public class UserController {
    /**
     * Strng
     * Integer
     * Double
     * Float
     * Char
     * Date
     * Void
     */
    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel){
        var user = this.userRepository.findByUsername(userModel.getUsername());
        System.out.println(user);
        if(user != null){
            //Mensagem de erro
            //Status code
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário ja existe");
        }
        var passwordHarshed =  BCrypt.withDefaults()
        .hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHarshed);
        var userCreated = this.userRepository.save(userModel);
        
        return ResponseEntity.status(HttpStatus.OK).body(userCreated);
    }
    
}
