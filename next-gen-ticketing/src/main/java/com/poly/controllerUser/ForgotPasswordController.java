package com.poly.controllerUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.entity.Account;
import com.poly.service.AccountService;
import com.poly.service.EmailService;

@Controller
public class ForgotPasswordController {

	@Autowired
	AccountService accountService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	BCryptPasswordEncoder PE;
	
	 @GetMapping(value = "/fgpw/view")
	    public String showRegistrationForm(Model model) {
	        model.addAttribute("account", new Account());
	        return "template-user/forgotpassword";
	    }
	 
	 @RequestMapping(value = "/fgpw/view", method = RequestMethod.POST)
		public String forgotPassword(@RequestParam("email") String email, Model model) {
			Account account = accountService.findByEmail(email);
	        if (account != null) {
	    
	            String newPassword = generateRandomPassword();

	            //account.setPassword(newPassword);
	            account.setPassword(PE.encode(newPassword));
	            accountService.update(account);

	            emailService.sendConfirmationEmailforgotPassword(account.getEmail(), account.getLastName(), newPassword);


	            model.addAttribute("message", "The new password will be sent via your email!");
	        } else {
	            model.addAttribute("error", "User does not exist!");
	        }
			
			return "user/forgotPassword";
		}
		
		private String generateRandomPassword() {
			String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+=-";
		    StringBuilder password = new StringBuilder();
		    int length = 5; 

		    for (int i = 0; i < length; i++) {
		        int index = (int) (Math.random() * chars.length());
		        password.append(chars.charAt(index));
		    }

		    return password.toString();
	    }
	
}
