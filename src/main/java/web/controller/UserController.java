package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;


    @GetMapping(value = "/admin/newUser")
    public String newUser(ModelMap model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("authenUser", user);
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", new User());
        return "index";
    }

    @PostMapping(value = "/admin/showList")
    public String create(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/admin/showList";
    }

    @GetMapping(value = "/admin/showList")
    public String showList(ModelMap model, Authentication authentication) {
        User authenUser = userService.findByUsername(authentication.getName());
        model.addAttribute("authUser", authenUser);
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        return "result";
    }

    @PostMapping(value = "/admin/updateAndSave")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateById(user, user.getId());
        return "redirect:/admin/showList";
    }

    @GetMapping(value = "/admin/updateAndSave")
    public String showUpdatedUser(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "redirect:/admin/showList";
    }

    @PostMapping(value = "/admin/delete")
    public String delete(@ModelAttribute("user") User user) {
        userService.deleteById(user.getId());
        return "redirect:/admin/showList";
    }

    @GetMapping(value = "/admin/delete")
    public String showDeletedUser(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "redirect:/admin/showList";
    }

    @GetMapping(value = "/user")
    public String user(Model model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("user", user);
        return "user";
    }


}
