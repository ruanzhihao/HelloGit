package com.java.ssm.cotroller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.ssm.entity.User;
import com.java.ssm.entity.UserModel;
import com.java.ssm.service.UserDaoService;
import com.sun.org.apache.bcel.internal.generic.RETURN;

@Controller
public class UserController {
	@Autowired
	private UserDaoService userDaoService;

	@GetMapping(value = "addUser")
	public String addUser() {
		return "insertUserForm";
	}

	@GetMapping(value = "updateUserForm")
	public String returnUpdateForm() {
		return "updateUserForm";
	}

	@RequestMapping(value = "saveUser")
	public String insertUser(UserModel userModel, RedirectAttributes redirectAttributes) {
		User user = new User();
		user.setUserName(userModel.getUserName());
		user.setUserPassword(userModel.getUserPassword());
		user.setEmail(userModel.getEmail());
		user.setRoleName(userModel.getRoleName());
		int i = userDaoService.insertUserService(user);
		if (i == 1) {
			redirectAttributes.addFlashAttribute("message", "用户信息保存成功");
		}
		return "redirect:/selectCurUser" + ".do";
	}

	@RequestMapping(value = "selectCurUser")
	public String selectUser(Model model) {
		User user = userDaoService.selectCurUserService();
		model.addAttribute("User", user);
		return "userInfoView";
	}

	@RequestMapping(value = "selectAllUser")
	public ModelAndView selectAllUser() {
		ModelAndView mav = new ModelAndView();
		List<User> list = userDaoService.selectAllUserService();
		mav.addObject("Users", list);
		mav.setViewName("userList");
		return mav;
	}

	@RequestMapping(value = "deleteUser/{id}")
	public String deleteUser(@PathVariable Integer id) {
		userDaoService.deleteUser(id);
		return "redirect:/selectAllUser";
	}

	@RequestMapping(value = "updateUser")
	public String updateUser(User user) {
		userDaoService.updateUser(user);
		return "redirect:/selectAllUser";
	}

	@RequestMapping(value = "selectUser/{id}")
	public String selectUser(@PathVariable Integer id, Model model) {
		User user = userDaoService.selectUserService(id);
		model.addAttribute("User", user);
		return "userInfoView";
	}
}
