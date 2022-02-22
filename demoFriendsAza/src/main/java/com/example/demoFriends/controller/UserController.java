package com.example.demoFriends.controller;

import com.example.demoFriends.model.User;
import com.example.demoFriends.repo.UserRepository;
import com.example.demoFriends.service.FileUploadUtil;
import com.example.demoFriends.service.UserNotFoundException;
import com.example.demoFriends.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    UserRepository userRepository;
    public List<String> event=new ArrayList<String>();
    private List<Integer> theatre = new ArrayList<>();
    private List<Integer> movie = new ArrayList<>();
    private List<Integer> mountains = new ArrayList<>();
    public List<String> friends = new ArrayList<>();

    @Autowired
    private UserService service;

    public void addingevents(){
        event.add("IITU Commencement");
        event.add("Movie evening");
        event.add("Trip to Big Almaty Lake");
    }

    @GetMapping("/{id}/edit")
    public String editProfile(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            User user = service.get(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
            model.addAttribute("friends", friends);
            return "user_profile";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "profile";
        }
    }

    @PostMapping("/{id}/save")
    public String saveProfile(@PathVariable("id") Integer id,
                              @RequestParam("image") MultipartFile multipartFile,
                              Model model, User user,
                              RedirectAttributes ra) throws UserNotFoundException, IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        user.setPhotos(fileName);
        User savedUser = userRepository.save(user);
        String uploadDir = "user-photos/" + savedUser.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        service.get(id);
        service.save(user);
        if(friends.isEmpty()) {
            model.addAttribute("fr", "You have no friends");
        }
        model.addAttribute("friends", friends);
        model.addAttribute("user", user);
        ra.addFlashAttribute("message", "The user saved successfully!");
        return "profile";
    }


    @GetMapping("/{id}/profile")
    public String showProfile(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            User user = service.get(id);
            model.addAttribute("user", user);
            model.addAttribute("friends", friends);
            model.addAttribute("pageTitle", user.getFirst_name() + " " + user.getLast_name());
            return "profile";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/users/{id}")
    public String showUserList(Model model, User user, @PathVariable("id") Integer id) throws UserNotFoundException {
        List<User> listUsers = service.listAll();
        user=service.get(id);
        model.addAttribute("user", user);
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/users/new")
    public String showNewForm(Model model) {
        model.addAttribute("user", new User());
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes ra) {
        service.save(user);
        ra.addFlashAttribute("message", "The user registered successfully!");
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model,
                        RedirectAttributes ra) {
        try {
            User user = service.login(username, password);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Hello, " + username);
            return "home";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/users/new";
        }
    }
    @GetMapping("/logout")
    public String logout(){
        return "home";
    }

    @GetMapping("/users/addfriend/{id}")
    public String addingFriends(@PathVariable("id") Integer id, Model model, User user, RedirectAttributes ra) throws UserNotFoundException {
        user=service.get(id);
        model.addAttribute("user", user);
        if(!friends.contains(user)){
            friends.add(user.getFirst_name() + " "+user.getLast_name());}
        else{
            ra.addFlashAttribute("mess", "He is already your friend");
        }
        System.out.println(friends.toString());
        return "redirect:/users/{id}";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            User user = service.get(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
            return "user_form";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The user ID: " + id + " has been deleted successfully");
            return "user_form";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/{id}/notifications")
    public String notifications(Model model, @PathVariable("id") Integer id) throws UserNotFoundException {
        User user=service.get(id);
        model.addAttribute("user", user);
        System.out.println(user.toString());
        System.out.println(theatre.toString());
        System.out.println(movie.toString());
        System.out.println(mountains.toString());
        if(theatre.contains(user.getId())){
            model.addAttribute("theatre", "Ongoing event: IITU commencement");
            System.out.println("Theatre added");
        }
        if (movie.contains(user.getId())) {
            model.addAttribute("cinema", "Ongoing event: movie evening");
            System.out.println("Cinema added");
        }
        if(mountains.contains(user.getId())){
            model.addAttribute("mountains", "Ongoing event: trip to Big Almaty Lake");
            System.out.println("Mountains added");
        }
        if(!theatre.contains(user.getId()) && !movie.contains(user.getId()) && !mountains.contains(user.getId())){
            model.addAttribute("party", "Haven't been added yet");
            System.out.println("why so?");
        }
        return "notifications";
    }

    @GetMapping
    public String welcome(){
        return "greeting";
    }

    @GetMapping("/{id}")
    public String home(Model model, @PathVariable("id") Integer id) throws UserNotFoundException {
        User user=service.get(id);
        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping("/{id}/events")
    public String events(Model model, @PathVariable("id") Integer id, RedirectAttributes ra) throws UserNotFoundException {
        User user = service.get(id);
        addingevents();
        model.addAttribute("user", user);
        model.addAttribute("date", "29/12/21");
        model.addAttribute("theatre", event.get(0));
        model.addAttribute("cinema", event.get(1));
        model.addAttribute("mountains", event.get(2));
        return "Events";
    }

    @GetMapping("/{id}/addedtotheatre")
    public String addtheatre(Model model, User user, @PathVariable("id") Integer id) throws UserNotFoundException{
        user=service.get(id);
        theatre.add(user.getId());
        model.addAttribute("user", user);
        return "redirect:/{id}/events";
    }

    @GetMapping("/{id}/addedtocinema")
    public String  addcinema(Model model, User user, @PathVariable("id") Integer id) throws UserNotFoundException{
        user=service.get(id);
        movie.add(user.getId());
        model.addAttribute("user", user);
        return "redirect:/{id}/events";
    }

    @GetMapping("/{id}/addedtomountains")
    public String addmountains(Model model,User user,  @PathVariable("id") Integer id) throws UserNotFoundException{
        user=service.get(id);
        mountains.add(user.getId());
        model.addAttribute("user", user);
        return "redirect:/{id}/events";
    }

    @GetMapping("/all")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}

