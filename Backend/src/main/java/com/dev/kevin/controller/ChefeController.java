package com.dev.kevin.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dev.kevin.entity.ChefeEntity;
import com.dev.kevin.repository.ChefeRepository;



@Controller
@RequestMapping("/chefe")
public class ChefeController {

	@Autowired
	ChefeRepository chefeRepository;
	
	@GetMapping("/formChefe")
	public String form(Model model) {
		List<String> setores = Arrays.asList("Financeiro", "Rh", "TI", "Marketing");
		model.addAttribute("setores", setores);
		return "chefe/form";
	}
	
	
	@GetMapping("/cadastro")
	public String mostrarFormularioChefe() {
		return "cadastroChefe";
	}
	
	@PostMapping("/salvar")
	public String salvarChefe(@RequestParam String nome,@RequestParam String setor, Model model) {
		ChefeEntity chefe = new  ChefeEntity();
		
		chefe.setNome(nome);
		chefe.setSetor(setor);
		
		chefeRepository.save(chefe);
		
		model.addAttribute("message","Chefe cadastrado com sucesso");
		
		return "redirect:/chefe/lista";
	}
	
	@GetMapping("lista")
	public String listarChefes(Model model) {
		List<ChefeEntity> chefes = chefeRepository.findAll();
		model.addAttribute("chefes",chefes);
		return "listaChefes";
	}
		
	@PostMapping("/excluir/{idChefe}")
	public String excluirChefe(@PathVariable Long idChefe , Model model) {
		chefeRepository.deleteById(idChefe);
		
		model.addAttribute("message", "Chefe Excluido com sucesso");
		return "redirect:/chefe/lista";
	}
	
	
}
