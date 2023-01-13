package by.tms.schoolmanagementsystem.web.api.resource;

import by.tms.schoolmanagementsystem.entity.announcement.Announcement;
import by.tms.schoolmanagementsystem.entity.role.Role;
import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.service.NewsService;
import by.tms.schoolmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
public class UserResource {
    @Autowired
    private UserService userService;
    @Autowired
    private NewsService newsService;

    @PostMapping("/reg")
    ResponseEntity<?> registerUser(@RequestBody @Valid User user, BindingResult bindingResult){
        List<String> errors = new ArrayList<>();
        if(bindingResult.hasErrors()){
            errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        if(userService.existsByUsername(user.getUsername())){
            errors.add("User with such username already exists");
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        user.setRole(Role.Student);
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/announcement/my")
    ResponseEntity<?> getMyAnnouncement(@RequestHeader("Token") String token){
        User user = userService.findByToken(token);
        if(user==null) return ResponseEntity.badRequest().build();
        List<Announcement> all = newsService.getAll(user);
        all.forEach(a -> a.getAuthor().setPassword(""));
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @DeleteMapping("/announcement/{id}")
    ResponseEntity<?> deleteAnnouncement(@PathVariable int id, @RequestHeader("Token") String token){
        User user = userService.findByToken(token);
        Optional<Announcement> a = newsService.getById(id);
        if(user==null || a.isEmpty() || a.get().getAuthor().getId()!=user.getId())
            return ResponseEntity.badRequest().build();
        newsService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
