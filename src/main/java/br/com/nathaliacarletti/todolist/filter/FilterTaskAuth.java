package br.com.nathaliacarletti.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.nathaliacarletti.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // toda classe que eu quero que o sprint gerencia precisa ter esse component. classe mais generica de gerenciamento
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
       //throw new UnsupportedOperationException("Unimplemented method 'doFilterInternal'");
       
        var servletPath = request.getServletPath();
        
        if(servletPath.startsWith("/tasks/")) {
        // Pegar autenticação
                var authorization = request.getHeader("Authorization");
                
                System.out.println(authorization);
                //if(authorization != null){
                var authEncoded =  authorization.substring("Basic".length()).trim();
                
                byte[] authDecode = Base64.getDecoder().decode(authEncoded);

                var authString = new String(authDecode);

                String[] credentials = authString.split(":");
                String username = credentials[0];
                String password = credentials[1];

                System.out.println("Authorization2");
               /* if(authorization != null)*/
                System.out.println(authString);
                System.out.println(username);
                System.out.println(password); 

                var user = this.userRepository.findByUsername(username);
                System.out.println(user);

                if(user == null){
                    
                System.out.println("usuario nulo"); 
                    response.sendError(401);
                }else{
                    //validar senha
                    System.out.println(password.toCharArray()); 
                    System.out.println(user.getPassword()); 

                    var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                    if(passwordVerify.verified){
                        //segue viagem
                        request.setAttribute("idUser", user.getId());
                        filterChain.doFilter(request, response);
                    }else{
                        response.sendError(401);
                    }
                }
            //}

        //Validar Usuario
        //Validar senha
        
    }else{
       filterChain.doFilter(request, response);
    }
    
    
    /**implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
      //Executar alguma ação
      System.out.println("Chegou no filtro");
      chain.doFilter(request, response);
      
    }*/

        }

    
}
