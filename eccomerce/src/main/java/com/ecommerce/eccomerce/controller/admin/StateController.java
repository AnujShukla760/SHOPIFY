package com.ecommerce.eccomerce.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.eccomerce.entity.State;
import com.ecommerce.eccomerce.service.CountryService;
import com.ecommerce.eccomerce.service.StateService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class StateController {

	private final StateService stateService;
	private final CountryService countryService;

	@GetMapping("/stateForm")
	public String StateForm(Model model) {
		State state = new State();
		model.addAttribute("state", state);

		List<State> allState = stateService.findAllState();
		model.addAttribute("allState", allState);

		model.addAttribute("countries", countryService.findAllCountry());

		return "admin/state";
	}

	@PostMapping("/saveState")
	public String saveState(@ModelAttribute("state") State state, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttribute) {

		if (state.getCountry().getId() == null) {
			bindingResult.rejectValue("country.id", null, "Hey! You have to choose country first!");
		}

		if (bindingResult.hasErrors()) {
			List<State> allState = stateService.findAllState();
			model.addAttribute("allState", allState);
			model.addAttribute("countries", countryService.findAllCountry());
			return "admin/state";
		}

		stateService.saveState(state);
		redirectAttribute.addFlashAttribute("success", "State saved successfully");
		return "redirect:stateForm";
	}

	@GetMapping("/editState")
	public String editState(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttribute) {
		State state = stateService.findStateById(id);
		model.addAttribute("state", state);

		List<State> allState = stateService.findAllState();
		model.addAttribute("allState", allState);

		model.addAttribute("countries", countryService.findAllCountry());

		return "admin/state";

	}

	@GetMapping("/deleteState")
	public String deleteState(@RequestParam("id") Long id, RedirectAttributes redirectAttribute) {
		stateService.deleteStateById(id);
		redirectAttribute.addFlashAttribute("warning", "State has been deleted!");
		return "redirect:stateForm";

	}
	
	@GetMapping("/findStateListByCountryId")
	public String findStateListByCountryId(@RequestParam Long countryId, Model model) {
		System.out.println(countryId);
		
		List<State> stateList = stateService.findStatesByCountryId(countryId);
		model.addAttribute("stateList", stateList);
		model.addAttribute("state", "stateId");
		return "admin/ajax/allAjax";

	}

}
