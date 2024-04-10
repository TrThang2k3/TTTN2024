package com.poly.controllerUser;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poly.util.service.ParamService;
import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate.Param;
import com.poly.DTO.AccountDTO;
import com.poly.DTO.NftDTO;
import com.poly.DTO.TradingNftDTO;
import com.poly.entity.Account;
import com.poly.entity.Invoice;
import com.poly.entity.Nft;
import com.poly.entity.Publisher;
import com.poly.entity.Ticket;
import com.poly.entity.TradingNft;
import com.poly.service.AccountService;
import com.poly.service.AuthorityService;
import com.poly.service.InvoiceService;
import com.poly.service.NftService;
import com.poly.service.PublisherService;
import com.poly.service.TicketService;
import com.poly.service.TradingNftService;
import com.poly.util.service.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;

@Controller
public class UserController {
	@Autowired
	SessionService sessionService;
	@Autowired
	HttpServletRequest request;
	@Autowired
	AccountService accountService;
	@Autowired
	AuthorityService authService;
	@Autowired
	PublisherService publisherService;
	@Autowired
	TicketService ticketService;
	@Autowired
	ParamService paramService;
	@Autowired
	InvoiceService inService;
	@Autowired
	TradingNftService tradingService;
	@Autowired
	NftService nftService;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@GetMapping({ "/nextgen.com", "/nextgen.com/home" })
	public String index() {
		return "/template-user/home";
	}

	@PostMapping("/nextgen.com/account/purchase")
	public String purchase(Model model) {
		try {
			Integer ticketId = Integer.parseInt(request.getParameter("ticketId"));
			Ticket ticket = ticketService.findById(ticketId);
			model.addAttribute("ticket", ticket);
			Double priceInSol = Math.round(ticket.getPrice() / 4342169.884 * 1000000) / 1000000d;
			model.addAttribute("priceInSol", priceInSol);
		} catch (Exception e) {
			Integer tradingId = Integer.parseInt(request.getParameter("tradingId"));
			TradingNft tradingNft = tradingService.findById(tradingId);
			model.addAttribute("tradingNft", tradingNft);
			Double priceInSol = Math.round(tradingNft.getPrice() / 4342169.884 * 1000000) / 1000000d;
			model.addAttribute("priceInSol", priceInSol);
		}
		Account account = accountService.findById(getLogAcc().getId());
		model.addAttribute("account", account);
		return "/template-user/payment";
	}

	@GetMapping("/nextgen.com/ticket-gallery")
	public String ticket(Model model) {
		model.addAttribute("tickets", ticketService.findAll());
		List<TradingNft> tradingNfts = tradingService.findAllAvailable();
		List<TradingNftDTO> tradingNftDtos = new ArrayList<>();
		for (TradingNft trading : tradingNfts) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(trading.getNft().getCreateDate());
			cal.add(Calendar.DATE, trading.getNft().getTicket().getShelftime());
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
			LocalDate expiredDate = LocalDate.parse(formater.format(cal.getTime()));

			LocalDate today = LocalDate.now();
			Integer days = (int) ChronoUnit.DAYS.between(today, expiredDate);

			TradingNftDTO dto = new TradingNftDTO(trading.getId(), trading.getAccount(), trading.getNft(),
					trading.getPrice(), days);
			tradingNftDtos.add(dto);
		}
		model.addAttribute("tradingNftDtos", tradingNftDtos);
		
		try {
			model.addAttribute("logAccId", getLogAcc().getId());
		} catch (Exception e) {
			model.addAttribute("logAccId", 0);
		}
		return "/template-user/ticket";
	}

	@GetMapping("/nextgen.com/account/profile")
	public String profile(Model model) {
		Account account = accountService.findById(getLogAcc().getId());
		model.addAttribute("account", account);

		// thông tin tài khoản (không mật khẩu)
		AccountDTO dto = new AccountDTO();
		dto.setId(account.getId());
		dto.setFirstName(account.getFirstName());
		dto.setLastName(account.getLastName());
		dto.setDayOfBirth(account.getDayOfBirth());
		dto.setEmail(account.getEmail());
		dto.setPhone(account.getPhone());
		model.addAttribute("accountDto", dto);

		// lịch sử giao dịch
		model.addAttribute("history", inService.findByBuyer(account));

		// danh sách nft vé đã mua
		List<Nft> nfts = nftService.getNotExpiredNftsByAccount(account);
		List<NftDTO> nftDtos = new ArrayList<>();
		for (Nft nft : nfts) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(nft.getCreateDate());
			cal.add(Calendar.DATE, nft.getTicket().getShelftime());
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
			LocalDate expiredDate = LocalDate.parse(formater.format(cal.getTime()));

			LocalDate today = LocalDate.now();
			Integer days = (int) ChronoUnit.DAYS.between(today, expiredDate);
			
			Boolean isTranding = tradingService.existsAvailableNftTrading(nft);
			NftDTO nftDto = new NftDTO(nft, days, isTranding);
			nftDtos.add(nftDto);
		}
		model.addAttribute("nftDtos", nftDtos);

		return "/template-user/profile";
	}

	@PostMapping("/nextgen.com/account/profile/update/{id}")
	public String updateAccount(@PathVariable("id") Integer id, @ModelAttribute Account updateAccount) {
		updateAccount.setId(id);
		accountService.update(updateAccount);
		return "/template-user/profile";
	}

	@PostMapping("/trading-nft")
	public String tradingNft() {
		Integer nftId = Integer.parseInt(request.getParameter("nftId"));
		Float price = Float.parseFloat(request.getParameter("price"));

		TradingNft entry = new TradingNft();
		entry.setAccount(getLogAcc());
		entry.setNft(nftService.findById(nftId));
		entry.setPrice(price);
		tradingService.create(entry);
		return "redirect:/nextgen.com/account/profile";
	}

	@PostMapping("/trading-cancel/{nftId}")
	public String cancelTradingNft(@PathVariable("nftId") Integer nftId) {
		TradingNft entry = tradingService.findAvailableTradingNft(nftId);
		tradingService.deleteById(entry.getId());
		return "redirect:/nextgen.com/account/profile";
	}

//	@GetMapping("/nextgen.com/account/payment")
//	public String newTicket() {
//		return "/template-user/payment";
//	}

	@GetMapping("/nextgen.com/signup")
	public String register(Model model) {
		Account account = new Account();
		model.addAttribute("account", account);
		return "/template-user/register";
	}

	@PostMapping("/signup")
	public String register(Model model, @Validated @ModelAttribute("account") Account account, BindingResult result,
			HttpServletRequest req) {
		String password = paramService.getString("password", "");
		String confirmPassword = paramService.getString("confirm-password", "");
		if (!password.equals(confirmPassword)) {
			model.addAttribute("message", "Passwords are not duplicates");
			return "/template-user/register";
		}
		if (result.hasErrors()) {
			model.addAttribute("message", "Check your registration information again");
			System.out.println(result);
		} else {
			try {
				account.setPassword(passwordEncoder.encode(password));
				accountService.create(account);
				authService.create(account, "Customer");
				model.addAttribute("message", "Registration successfully");
			} catch (Exception e) {
				model.addAttribute("message", "Email is existed!");
			}
		}
		return "/template-user/register";
	}

	@CrossOrigin("*")
	@ResponseBody
	@RequestMapping("/nextgen.com/rest/authentication")
	public Object getAuthentication() {
		return sessionService.get("authentication");
	}

	public Account getLogAcc() {
		Map<String, Object> authentication = new HashMap<>();
		authentication = sessionService.get("authentication");
		Object auth = authentication.get("account");
		Account account = (Account) auth;
		return account;
	}
}
