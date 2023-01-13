package by.tms.schoolmanagementsystem.web.api.resource;

import by.tms.schoolmanagementsystem.entity.role.Role;
import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.service.LessonService;
import by.tms.schoolmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/lesson")
public class LessonResource {
    @Autowired
    private LessonService lessonService;
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    ResponseEntity<?> getAllLessons(@RequestHeader("Token") String token){
        User user = userService.findByToken(token);
        if (user==null) return ResponseEntity.badRequest().build();
        if(user.getRole()== Role.Student){
            return new ResponseEntity<>(lessonService.getAllForStudent(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(lessonService.getAllForTeacher(user), HttpStatus.OK);
        }
    }
}
