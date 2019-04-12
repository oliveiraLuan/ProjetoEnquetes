package com.projectBI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.projectBI.models.Funcionario;
import com.projectBI.repository.FuncionarioRepository;

@Controller
public class IndexController {
	private String recebeLogin;
	
	@Autowired
	FuncionarioRepository funcionarioRepository;
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index() {
		return "index";
	}
	
	@RequestMapping("/cadastrarUsuario")
	public String cadastro() {
		return "cadastro";
	}
	
	@RequestMapping(value="/cadastrarUsuario", method=RequestMethod.POST)
	public String cadastro(String nome, String email, String senha, String tipo) {
		Funcionario funcionario = new Funcionario(nome, email, senha, tipo);
		funcionarioRepository.save(funcionario);
		return "redirect:/gerente";
	}
	@RequestMapping(value="/", method=RequestMethod.POST)
	public String logar(Funcionario funcionario) {
		if(funcionarioRepository.findByEmail(funcionario.getEmail()) != null) {
			if(funcionarioRepository.findByEmail(funcionario.getEmail()).getSenha().equals(funcionario.getSenha())) {
				recebeLogin = funcionario.getEmail();
				return "redirect:/gerente";
			}
			return"redirect:/";
			}
		return "redirect:/";
		}
	@RequestMapping(value="/gerente", method=RequestMethod.GET)
	public String redirecionaGerente() {
		return "gerente";
	}
	@RequestMapping(value="/listarUsuarios", method=RequestMethod.GET)
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("listarUsuarios");
		Iterable<Funcionario> funcionario = funcionarioRepository.findAll();
		mv.addObject("funcionario", funcionario);
		return mv;
	}
	
	@RequestMapping(value="/atualizarDados", method=RequestMethod.GET)
	public ModelAndView getDados() {
		ModelAndView mv = new ModelAndView("atualizarDados");
		Funcionario funcionario = funcionarioRepository.findByEmail(recebeLogin);
		mv.addObject("funcionario", funcionario);
		return mv;
	}
	@RequestMapping(value="/atualizarDados", method=RequestMethod.POST)
	public void setDados(Funcionario funcionario) {
		Funcionario f = funcionarioRepository.findByEmail(recebeLogin);
		f.setNome(funcionario.getNome());
		f.setEmail(funcionario.getEmail());
		funcionarioRepository.save(f);
	}
	
	@RequestMapping(value="/deletarUsuario", method=RequestMethod.POST)
	public void deletarUsuario(String email) {
		Funcionario f = funcionarioRepository.findByEmail(email);
		funcionarioRepository.delete(f);
	}
	
	@RequestMapping(value="/deletarUsuario", method=RequestMethod.GET)
	public String deletarUsuario() {
		return "deletarUsuario";
	}

	@RequestMapping(value="/criarEnquete", method=RequestMethod.POST)
	public String setEnquete() {
		
		return "criarEnquete";
	}
	@GetMapping("/criarEnquete")
	public ModelAndView arrayController(ModelAndView model) {
		String[] perguntas = {"Oi meu nome é Betina?", "E eu tenho o patrimônio acumulado em 1 milhão de reais"};
		model.addObject("perguntas", perguntas);
		return model;
	}
	
	
	
	
	

}
