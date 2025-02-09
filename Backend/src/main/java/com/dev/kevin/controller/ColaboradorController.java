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
import com.dev.kevin.entity.ColaboradorEntity;
import com.dev.kevin.model.ColaboradorModel;
import com.dev.kevin.repository.ChefeRepository;
import com.dev.kevin.util.SenhaUtil;

import jakarta.persistence.EntityNotFoundException;


@Controller
@RequestMapping("/colaborador")
public class ColaboradorController {

    @Autowired
    ColaboradorModel colaboradorModel;
    @Autowired
    ChefeRepository chefeRepository;

    
    @GetMapping("/form")
    public String form(Model model) {
    	List<String> setores = Arrays.asList("Financeiro", "Rh", "TI", "marketing");
    	model.addAttribute("setores", setores);
    	return "colaborador/form";
    }
    
    // Rota para renderizar a lista de colaboradores (GET)
    @GetMapping("/listar")
    public String listarTodos(Model model) {
        List<ColaboradorEntity> colaboradores = colaboradorModel.findAll();
        model.addAttribute("colaboradores", colaboradores);
        return "listagem"; // Retorna a página listagem.html
    }

    @GetMapping("/detalhes/{idColaborador}")
    public String detalhes(@PathVariable("idColaborador") long idColaborador, Model model) {
        ColaboradorEntity colaborador = colaboradorModel.findById(idColaborador);
        model.addAttribute("colaborador", colaborador);
        return "detalhes";
    }

    // Rota para renderizar o formulário de cadastro
    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro"; // Renderiza o arquivo cadastro.html
    }

    // Rota para processar o formulário de cadastro (POST)
    @PostMapping("/salvar")
    public String salvarColaborador(@RequestParam String nome, @RequestParam String senha, @RequestParam String setor, Model model) {
        // Validar a senha
        String mensagemDeValidacao = SenhaUtil.validacaoSenha(senha);

        // Se a senha não atender aos requisitos, exibe a mensagem de erro
        if (!mensagemDeValidacao.equals("Senha muito forte.") && !mensagemDeValidacao.equals("Senha forte.")) {
            model.addAttribute("mensagemErro", mensagemDeValidacao);
            return "cadastro"; // Redireciona de volta para o formulário de cadastro
        }

        // Se a senha for válida, cria e salva o colaborador
        ColaboradorEntity colaboradorEntity = new ColaboradorEntity();
        colaboradorEntity.setNome(nome);
        colaboradorEntity.setSenha(senha);
        colaboradorEntity.setSetor(setor);

        ChefeEntity chefe = chefeRepository.findBySetor(setor);
        
        if(chefe == null) {
        	throw new EntityNotFoundException("Nenhum chefe encontrado nesse setor ");
        }
        colaboradorEntity.setChefe(chefe);
        
        colaboradorModel.save(colaboradorEntity); // Salva o colaborador no banco de dados

        model.addAttribute("message", "Cadastro realizado com sucesso!");
        return "redirect:/colaborador/listar"; // Redireciona para a lista de colaboradores após o cadastro
    }

    @PostMapping("/excluir/{idColaborador}")
    public String excluirColaborador(@PathVariable Long idColaborador) {
        try {
            colaboradorModel.excluirColaborador(idColaborador);
        } catch (Exception e) {
            // Exceção caso não encontre o colaborador, ou outro erro
            return "redirect:/colaborador?error=Erro ao excluir colaborador";
        }

        // Redireciona para a lista de colaboradores após a exclusão
        return "redirect:/colaborador";
    }

    // Método para pegar um colaborador pelo ID
    @GetMapping("/{idColaborador}")
    public ColaboradorEntity get(@PathVariable("idColaborador") long idColaborador) {
        return colaboradorModel.findById(idColaborador);
    }
    
    @PostMapping("/associaChefe/{idColaborador}")
    public String associaChefe(@PathVariable Long idColaborador, Model model) {
    	try {
			ColaboradorEntity colaborador = colaboradorModel.associaColaborador(idColaborador);
			model.addAttribute("message", "Colaborador associado com sucesso ao chefe do setor");
			model.addAttribute("colaborador",colaborador);
			return "redirect:/colaborador";
		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
			return "errorPage";
		}
    }

}



