package by.tms.schoolmanagementsystem;

import by.tms.schoolmanagementsystem.entity.lesson.Plan;
import by.tms.schoolmanagementsystem.entity.lesson.TimeBlock;
import by.tms.schoolmanagementsystem.entity.lesson.TimeTerm;
import by.tms.schoolmanagementsystem.entity.role.Role;
import by.tms.schoolmanagementsystem.entity.role.UserRole;
import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.repository.PlanRepository;
import by.tms.schoolmanagementsystem.repository.RoleRepository;
import by.tms.schoolmanagementsystem.repository.TimeTermRepository;
import by.tms.schoolmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.event.EventListener;

import java.time.DayOfWeek;

@SpringBootApplication
public class SchoolManagementSystemApplication {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TimeTermRepository timeTermRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(SchoolManagementSystemApplication.class, args);
    }

    @EventListener
    public void loadRoles(ApplicationReadyEvent event) {
        UserRole student = roleRepository.save(new UserRole(Role.Student));
        UserRole admin = roleRepository.save(new UserRole(Role.Admin));
        UserRole teacher = roleRepository.save(new UserRole(Role.Teacher));

        TimeTerm first = timeTermRepository.save(new TimeTerm(TimeBlock.FIRST_TERM));
        TimeTerm second = timeTermRepository.save(new TimeTerm(TimeBlock.SECOND_TERM));
        TimeTerm third = timeTermRepository.save(new TimeTerm(TimeBlock.THIRD_TERM));
        TimeTerm fourth = timeTermRepository.save(new TimeTerm(TimeBlock.FOURTH_TERM));


        for (DayOfWeek day : DayOfWeek.values()) {
            planRepository.save(new Plan(day, first));
            planRepository.save(new Plan(day, second));
            planRepository.save(new Plan(day, third));
            planRepository.save(new Plan(day, fourth));
        }

        userRepository.save(
                User.builder()
                        .name("Admin")
                        .email("someemail@gmail.com")
                        .userRole(admin)
                        .username("AdminAdmin")
                        .password("Admin123").
                        build()
        );
        userRepository.save(
                User.builder()
                        .name("Anton")
                        .email("someemail@gmail.com")
                        .userRole(student)
                        .username("AntonStudent")
                        .password("Anton123").
                        build()
        );
        userRepository.save(
                User.builder()
                        .name("Tomasz")
                        .email("someemail@gmail.com")
                        .userRole(teacher)
                        .username("TomaszTeacher")
                        .password("Tomasz123").
                        build()
        );

    }

}
