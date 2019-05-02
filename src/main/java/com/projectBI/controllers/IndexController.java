package com.projectBI.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.projectBI.models.Enquete;
import com.projectBI.models.Funcionario;
import com.projectBI.repository.EnqueteRepository;
import com.projectBI.repository.FuncionarioRepository;

@Controller
public class IndexController {
	private String recebeLogin;
	private int qtdSim;
	private int qtdNao;
	private double mediaSim;
	private double mediaNao;
	private double somaTotalSim = 0;
	private double somaTotalNao = 0;

	@Autowired
	FuncionarioRepository funcionarioRepository;
	@Autowired
	EnqueteRepository enqueteRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping("/cadastrarUsuario")
	public String cadastro() {
		return "cadastro";
	}

	@RequestMapping(value = "/cadastrarUsuario", method = RequestMethod.POST)
	public String cadastro(String nome, String email, String senha, String tipo, RedirectAttributes attributes) {
		Funcionario funcionario = new Funcionario(nome, email, senha, tipo);
		funcionarioRepository.save(funcionario);
		attributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso");
		return "redirect:/cadastrarUsuario";
	}

	@RequestMapping(value="/", method=RequestMethod.POST)
	public String logar(Funcionario funcionario) {
		if(funcionarioRepository.findByEmail(funcionario.getEmail()) != null ) {
			if(funcionarioRepository.findByEmail(funcionario.getEmail()).getSenha().equals(funcionario.getSenha())) {
				recebeLogin = funcionario.getEmail();
				if(funcionarioRepository.findByEmail(funcionario.getEmail()).getTipo().equals("Gerente")){
					return "redirect:/gerente";	
				}else if(funcionarioRepository.findByEmail(funcionario.getEmail()).getTipo().equals("Comum")) {
					return "redirect:/comum";	
				}
				
				}
			
			return"redirect:/";
			}
		return "redirect:/";
		}
	@RequestMapping(value = "/gerente", method = RequestMethod.GET)
	public ModelAndView redirecionaGerente() {
		ModelAndView mv = new ModelAndView("gerente");
		Funcionario funcionario = funcionarioRepository.findByEmail(recebeLogin);
		mv.addObject("funcionario", funcionario);
		funcionario.setEmail("");
		return mv;
	}

	@RequestMapping(value = "/comum", method = RequestMethod.GET)
	public ModelAndView redirecionaComum() {
		ModelAndView mv = new ModelAndView("comum");
		Funcionario funcionario = funcionarioRepository.findByEmail(recebeLogin);
		mv.addObject("funcionario", funcionario);
		return mv;
	}

	@RequestMapping(value = "/listarUsuarios", method = RequestMethod.GET)
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("listarUsuarios");
		Iterable<Funcionario> funcionario = funcionarioRepository.findAll();
		mv.addObject("funcionario", funcionario);
		return mv;
	}

	@RequestMapping(value = "/atualizarDados", method = RequestMethod.GET)
	public ModelAndView getDados() {
		ModelAndView mv = new ModelAndView("atualizarDados");
		Funcionario funcionario = funcionarioRepository.findByEmail(recebeLogin);
		mv.addObject("funcionario", funcionario);
		return mv;
	}

	@RequestMapping(value = "/atualizarDados", method = RequestMethod.POST)
	public void setDados(Funcionario funcionario, RedirectAttributes attributes) {
		Funcionario f = funcionarioRepository.findByEmail(recebeLogin);
		f.setNome(funcionario.getNome());
		f.setEmail(funcionario.getEmail());
		f.setSenha(funcionario.getSenha());
		funcionarioRepository.save(f);
		attributes.addFlashAttribute("mensagem", "Dados atualizados com sucesso!");
	}

	@RequestMapping(value = "/deletarUsuario", method = RequestMethod.POST)
	public String deletarUsuario(String email, RedirectAttributes attributes) {
		Funcionario f = funcionarioRepository.findByEmail(email);
		if (f != null) {
			if (funcionarioRepository.findByEmail(email).equals(f)) {
				funcionarioRepository.delete(f);
				attributes.addFlashAttribute("mensagem", "Usuário deletado com sucesso");
			} else {
				attributes.addFlashAttribute("mensagem", "Usuário não encontrado");
				return "deletarUsuario";
				
			}
		}

		return "deletarUsuario";
	}

	@RequestMapping(value = "/deletarUsuario", method = RequestMethod.GET)
	public String deletarUsuario() {
		return "deletarUsuario";
	}

	@RequestMapping(value = "/criarEnquete", method = RequestMethod.POST)
	public String setEnquete(@RequestParam("iter") List<String> to) {
		Enquete enquete = new Enquete();
		qtdSim = 0;
		qtdNao = 0;
		for (String resposta : to) {
			if (resposta.equals("Sim")) {
				qtdSim = qtdSim + 1;
				enquete.setRespostaSim(qtdSim);
			} else {
				qtdNao = qtdNao + 1;
				enquete.setRespostaNao(qtdNao);
			}

		}

		enqueteRepository.save(enquete);
		enquete.setRespostaSim(0);
		enquete.setRespostaNao(0);

		return "criarEnquete";
	}

	@GetMapping("/criarEnquete")
	public ModelAndView arrayController(ModelAndView model) {
		String[] perguntas = { "Demora na resposta mediante acionamento da Olitel.", "Tudo é reativo.",
				"Não existe rotina dos Tecnicos.", "Nunca foi feito manuntenção Preventiva.",
				"Nunca foi feito apresentação de Indicadores.",
				"Não existe divulgação de Check List Diário da Central.", "Nunca foi informado o Capacity da Central.",
				"Não existe controle de Ramais Ativos.", "Não existe monitoração dos Hardwares da Central.",
				"Não existe controle e gerenciamento dos Troncos e Canais.",
				"Não existe controle e gerenciamento das licenças.",
				"Nunca foi informado ou apresentado uma Topologia do ambienete.",
				"Nunca foi apresentado By Face de nenhuma filial.",
				"Nunca foi feito ou apresentado nenhuma documentação de Survey.",
				"Cliente não sabe se existe ou não SLA, e caso exista, cliente não sabe quanto é.",
				"Não existe report sobre chamados na Olitel.",
				"Demora na resposta da Olitel quando envolve especialista.", "Tempo de apresentação da Solução.",
				"Solução sempre atrelada a área comercial.", "Solução só chega com proposta comercial.",
				"Demora no atendimento de troca e manutenção de Hardware.", "Olitel só responde se Alessandro cobra.",
				"Olitel só dá prioridade depois que Alessandro Cobra.", "Transparencia Contratual.",
				"Residentes são dedicados.", "Residentes são esforçados.", "Residentes representam a Olitel.",
				"Comercial sempre disponível quando acionado independente do horário e problema.",
				"Ausência de Gestão Contratual.",
				"Falta de clareza e visibilidade do senso de urgência sobre o Back do Fabricante.",
				"Falta de Gestão dos Residentes.", "Não existe cadência com Tecban e Residentes.",
				"Falta Senso de Prioridade.", "Pontualidade dos residentes é satisfatória",
				"Ótimo Atendimento dos residentes.",
				"Falta de Treinamento da Equipe Residente / Transferencia de conhecimento da Equipe especialista para diminuir acionamento dependentes da Olitel.",
				"Acionamento e Desenvolvimento dos Chamados para Regionais são perfeitos.", "Falta Escalation",
				"Não conhece Fluxograma Olitel", "Poucas Visitas da Olitel",
				"Demora na ação do Especialista quando em campo devido deslocamento.",
				"Falta de Ferramenta adequada / basica para os Residentes",
				"Gestão do Residente fica por conta da Tecban" };
		model.addObject("perguntas", perguntas);
		return model;
	}

	@GetMapping("/displayBarGraph")
	public String barGraph(Model model) {
		for (Enquete somatoria : enqueteRepository.findAll()) {
			somaTotalSim += somatoria.getRespostaSim();
			somaTotalNao += somatoria.getRespostaNao();
		}

		mediaSim = (somaTotalSim / (somaTotalNao + somaTotalSim)) * 100;
		mediaNao = (somaTotalNao / (somaTotalNao + somaTotalSim)) * 100;

		Map<String, Double> surveyMap = new LinkedHashMap<>();
		surveyMap.put("Sim", (Double) mediaSim);
		surveyMap.put("Não", (Double) mediaNao);
		model.addAttribute("surveyMap", surveyMap);

		return "relatorios";
	}

	@GetMapping("/displayPieChart")
	public String pieChart(Model model) {

		for (Enquete somatoria : enqueteRepository.findAll()) {
			somaTotalSim += somatoria.getRespostaSim();
			somaTotalNao += somatoria.getRespostaNao();
		}

		mediaSim = (somaTotalSim / (somaTotalNao + somaTotalSim)) * 100;
		mediaNao = (somaTotalNao / (somaTotalNao + somaTotalSim)) * 100;

		model.addAttribute("Sim", mediaSim);
		model.addAttribute("Nao", mediaNao);

		return "pieChart";
	}

}
