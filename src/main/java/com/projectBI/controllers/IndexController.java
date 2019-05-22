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
	private int qtdComercialSim;
	private int qtdComercialNao;
	private int qtdResidenteSim;
	private int qtdResidenteNao;
	private int qtdGestaoSim;
	private int qtdGestaoNao;
	private int qtdOperacionalSim;
	private int qtdOperacionalNao;
	private double mediaSim;
	private double mediaNao;
	private double mediaSimComercial;
	private double mediaNaoComercial;
	private double mediaSimResidente;
	private double mediaNaoResidente;
	private double mediaSimGestao;
	private double mediaNaoGestao;
	private double mediaSimOperacional;
	private double mediaNaoOperacional;
	private double somaTotalSim = 0;
	private double somaTotalNao = 0;
	private double somaTotalSimComercial = 0;
	private double somaTotalNaoComercial = 0;
	private double somaTotalSimResidente = 0;
	private double somaTotalNaoResidente = 0;
	private double somaTotalSimGestao = 0;
	private double somaTotalNaoGestao = 0;
	private double somaTotalSimOperacional = 0;
	private double somaTotalNaoOperacional = 0;
	
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
	public String logar(Funcionario funcionario, RedirectAttributes attributes) {
		if(funcionarioRepository.findByEmail(funcionario.getEmail()) != null ) {
			if(funcionarioRepository.findByEmail(funcionario.getEmail()).getSenha().equals(funcionario.getSenha())) {
				recebeLogin = funcionario.getEmail();
				if(funcionarioRepository.findByEmail(funcionario.getEmail()).getTipo().equals("Gerente")){
					return "redirect:/gerente";	
				}else if(funcionarioRepository.findByEmail(funcionario.getEmail()).getTipo().equals("Comum")) {
					return "redirect:/comum";	
				}
				
				}
			else {
				attributes.addFlashAttribute("mensagem", "Usuário ou senha inválidos");
				return"redirect:/";
			}
			
			return"redirect:/";
			}
		return "redirect:/";
		}
	@RequestMapping(value = "/gerente", method = RequestMethod.GET)
	public ModelAndView redirecionaGerente(RedirectAttributes attributes) {
		ModelAndView mv = new ModelAndView("gerente");
		Funcionario funcionario = funcionarioRepository.findByEmail(recebeLogin);
		if(funcionario != null) {
			mv.addObject("funcionario", funcionario);
			funcionario.setEmail("");
			return mv;
		}else {
			attributes.addFlashAttribute("acessonegado", "Acesso negado");
			mv.setViewName("acessonegado");
			return mv;
		}
		
		
	}

	@RequestMapping(value = "/comum", method = RequestMethod.GET)
	public ModelAndView redirecionaComum(RedirectAttributes attributes) {
		ModelAndView mv = new ModelAndView("comum");
		Funcionario funcionario = funcionarioRepository.findByEmail(recebeLogin);
		if(funcionario != null) {
			mv.addObject("funcionario", funcionario);
			funcionario.setEmail("");
			return mv;
		}else {
			attributes.addFlashAttribute("acessonegado", "Acesso negado");
			mv.setViewName("acessonegado");
			return mv;
		}
		

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
	public String setDados(Funcionario funcionario, RedirectAttributes attributes) {
		Funcionario f = funcionarioRepository.findByEmail(recebeLogin);
		f.setNome(funcionario.getNome());
		f.setEmail(funcionario.getEmail());
		f.setSenha(funcionario.getSenha());
		funcionarioRepository.save(f);
		recebeLogin = f.getEmail();
		attributes.addFlashAttribute("mensagem", "Dados atualizados com sucesso!");
		return "redirect:/atualizarDados";
		
	}

	@RequestMapping(value = "/deletarUsuario", method = RequestMethod.POST)
	public String deletarUsuario(String email, RedirectAttributes attributes) {
		Funcionario f = funcionarioRepository.findByEmail(email);
		if (f != null) {
			if (funcionarioRepository.findByEmail(email).equals(f)) {
				funcionarioRepository.delete(f);
				attributes.addFlashAttribute("mensagem", "Usuário deletado com sucesso");
				return "redirect:/deletarUsuario";
			} else {
				attributes.addFlashAttribute("mensagem", "Usuário não encontrado");
				return "redirect:/deletarUsuario";
			}
		}

		return "redirect:/deletarUsuario";
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
		qtdComercialSim = 0;
		qtdComercialNao = 0;
		qtdResidenteSim = 0;
		qtdResidenteNao = 0;
		qtdGestaoSim = 0;
		qtdGestaoNao = 0;
		qtdOperacionalSim = 0;
		qtdOperacionalNao = 0;
		
		for (String resposta : to) {
			if (resposta.equals("Sim")) {
				qtdSim = qtdSim + 1;
				enquete.setRespostaSim(qtdSim);
				for(int i = 0; i <= to.size(); i++) {
					if(i <= 11) {
						qtdComercialSim = qtdComercialSim + 1;
						enquete.setRespostasPositivasComercial(qtdComercialSim);
					}else if(i > 11 && i <= 16){
						qtdResidenteSim = qtdResidenteSim + 1;
						enquete.setRespostasPositivasResidente(qtdResidenteSim);
					}else if(i > 16 && i <= 31 ) {
						qtdGestaoSim = qtdGestaoSim + 1;
						enquete.setRespostasPositivasGestao(qtdGestaoSim);
					}else if(i > 31 && i < 42) {
						qtdOperacionalSim = qtdOperacionalSim + 1;
						enquete.setRespostasPositivasOperacional(qtdOperacionalSim);
					}
				}
			}
			else {
				qtdNao = qtdNao + 1;
				enquete.setRespostaNao(qtdNao);
				for(int i = 0; i <= to.size(); i++) {
					if(i <= 11) {
						qtdComercialNao = qtdComercialNao + 1;
						enquete.setRespostasNegativasComercial(qtdComercialNao);
					}else if(i > 11 && i <= 16){
						qtdResidenteNao = qtdResidenteNao + 1;
						enquete.setRespostasNegativasResidente(qtdResidenteNao);
					}else if(i > 16 && i <= 31 ) {
						qtdGestaoNao = qtdGestaoNao + 1;
						enquete.setRespostasNegativasGestao(qtdGestaoNao);
					}else if(i > 31 && i <= 42) {
						qtdOperacionalNao = qtdOperacionalNao + 1;
						enquete.setRespostasNegativasOperacional(qtdOperacionalNao);
					}
				}
			}
		}
		
		
		
		enqueteRepository.save(enquete);
		enquete.setRespostaSim(0);
		enquete.setRespostaNao(0);

		return "criarEnquete";
	}

	@GetMapping("/criarEnquete")
	public ModelAndView arrayController(ModelAndView model) {
		String[] perguntas = {"O SLA já foi divulgado?",
				"O tempo de resposta quando há envolvimento do Especialista é satisfatório?",
				"O tempo para apresentação da Solução é satisfatório?",
				"A solução sempre esta atrelada a area comercial. Acha isso positivo?",
				"A solução costuma chegar com proposta comercial. Acha isso positivo?",
				"Existe transparência contratual?",
				"O departamento comercial está sempre disponível quando acionado?",
				"Existe Gestão contratual?",
				"Existe clareza sobre a importancia de se ter o Back do Fabricante?",
				"Gosta do Escalation?",
				"Conhece o fluxograma para atendimento dos chamados abertos?",
				"Está satisfeito com a quantidade de visitas realizadas na sua empresa?",
				"Os Residentes são dedicados?","Os Residentes são esforçados?",
				"Os Residentes representam a empresa contratada de modo satisfatório?",
				"Acha os residentes pontuais?","Gosta do atendimento dos residentes?",
				"Existe proatividade?","Existe rotina técnica?",
				"É feito manutenção preventiva?","É feito apresentação de indicadores?",
				"É feito divulgação do Check List Diário da Central?","O Capacity da Central é divulgado?",
				"Existe controle de ramais ativos?","Existe monitoração dos Hardwares da Central?",
				"Existe controle e gerenciamento dos Troncos e Canais?","Existe Controle e gerenciamento de Licenças?",
				"Já foi apresentado uma topologia do ambiente?","Já foi apresentado By Face de alguma filial?",
				"Já receberam algum documento de Survey?","Existe Gestão dos Residentes?",
				"Está satisfeito com a cadencia da empresa contratada com os residentes e nos reports da operação?", 
				"O tempo de resposta mediante o acionamento é satisfatório?", "Existe report sobre os chamados abertos?",
				"Em caso de troca de hardware ou manutenção o tempo é satisfatório?","A resposta acontece independente do cargo do solicitante?",
				"Existe prioridade independente do cargo do solicitante?", "Está satisfeito com o atual senso de prioridade?",
				"Acha a equipe dos residentes bem treinada?","Gosta do atendimento feito nas regionais?",
				"O tempo de deslocamento para atendimento dos chamados é satisfatório?",
				"Está satisfeito com as ferramentas disponibilizadas para os residentes?"
		};
		
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
	@GetMapping("/declaracoesPositivas")
	public String declaracoesPositivas(Model model) {
		for (Enquete somatoria : enqueteRepository.findAll()) {
			somaTotalSimComercial += somatoria.getRespostasPositivasComercial();
			somaTotalSimResidente += somatoria.getRespostasPositivasResidente();
			somaTotalSimGestao += somatoria.getRespostasPositivasGestao();
			somaTotalSimOperacional += somatoria.getRespostasPositivasOperacional();	
		}
		
		mediaSimComercial = (somaTotalSimComercial /(somaTotalSimComercial + somaTotalSimResidente + somaTotalSimGestao + somaTotalSimOperacional )) * 100;
		mediaSimResidente = (somaTotalSimResidente /(somaTotalSimComercial + somaTotalSimResidente + somaTotalSimGestao + somaTotalSimOperacional)) * 100;
		mediaSimGestao = (somaTotalSimGestao /(somaTotalSimComercial + somaTotalSimResidente + somaTotalSimGestao + somaTotalSimOperacional)) * 100;
		mediaSimOperacional = (somaTotalSimOperacional /(somaTotalSimComercial + somaTotalSimResidente + somaTotalSimGestao + somaTotalSimOperacional)) * 100;
		model.addAttribute("mediaSimComercial", mediaSimComercial);
		model.addAttribute("mediaSimResidente", mediaSimResidente);
		model.addAttribute("mediaSimGestao", mediaSimGestao);
		model.addAttribute("mediaSimOperacional", mediaSimOperacional);
		
		return "declaracoesPositivas";
	}
	@GetMapping("/declaracoesNegativas")
	public String declaracoesNegativas(Model model) {
		for (Enquete somatoria : enqueteRepository.findAll()) {
			somaTotalNaoComercial += somatoria.getRespostasNegativasComercial();
			somaTotalNaoResidente += somatoria.getRespostasNegativasResidente();
			somaTotalNaoGestao += somatoria.getRespostasNegativasGestao();
			somaTotalNaoOperacional += somatoria.getRespostasNegativasOperacional();
		}
		
		mediaNaoComercial = (somaTotalNaoComercial / (somaTotalNaoComercial + somaTotalNaoResidente + somaTotalNaoGestao + somaTotalNaoOperacional)) * 100;
		mediaNaoResidente = (somaTotalNaoResidente / (somaTotalNaoComercial + somaTotalNaoResidente + somaTotalNaoGestao + somaTotalNaoOperacional)) * 100;
		mediaNaoGestao = (somaTotalNaoGestao / (somaTotalNaoComercial + somaTotalNaoResidente + somaTotalNaoGestao + somaTotalNaoOperacional)) * 100;
		mediaNaoOperacional = (somaTotalNaoOperacional / (somaTotalNaoComercial + somaTotalNaoResidente + somaTotalNaoGestao + somaTotalNaoOperacional)) * 100;
		
		model.addAttribute("mediaNaoComercial", mediaNaoComercial);
		model.addAttribute("mediaNaoResidente", mediaNaoResidente);
		model.addAttribute("mediaNaoGestao", mediaNaoGestao);
		model.addAttribute("mediaNaoOperacional", mediaNaoOperacional);
		
		return "declaracoesNegativas";
	}
	
	
	
	
	@RequestMapping(value="/sair", method=RequestMethod.GET)
	public String sair(){
		recebeLogin = null;
		return "redirect:/";
	}
	

}
