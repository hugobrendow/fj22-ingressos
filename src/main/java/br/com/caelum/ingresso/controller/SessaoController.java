package br.com.caelum.ingresso.controller;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.form.SessaoForm;
import br.com.caelum.ingresso.validacao.GerenciadorDeSessao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class SessaoController {

    @Autowired
    private SalaDao salaDao;

    @Autowired
    private FilmeDao filmeDao;

    @Autowired
    private SessaoDao sessaoDao;

    @GetMapping("/sessao/{id}/lugares")
    public ModelAndView lugaresNaSessao(@PathVariable("id") Integer sessaoId){
        ModelAndView modelAndView = new ModelAndView("sessao/lugares");

        Sessao sessao = sessaoDao.findOne(sessaoId);

        modelAndView.addObject("sessao", sessao);

        return modelAndView;
    }

    @GetMapping("/admin/sessao")
    public ModelAndView form(@RequestParam("salaId") Integer salaId, SessaoForm form){

        form.setSalaId(salaId);

        ModelAndView modelAndView = new ModelAndView("sessao/sessao");

        modelAndView.addObject("sala", salaDao.findOne(salaId));
        modelAndView.addObject("filmes", filmeDao.findAll());
        modelAndView.addObject("form", form);

        return modelAndView;
    }

    @PostMapping(value= "/admin/sessao")
    @Transactional
    public ModelAndView salva(@Valid SessaoForm form, BindingResult result){

        if (result.hasErrors()) return form(form.getSalaId(), form);
        Sessao sessao = form.toSessao(salaDao, filmeDao);

        List<Sessao> sessoesDaSala = sessaoDao.buscaSessoesDaSala(sessao.getSala());
        GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoesDaSala);

        if (gerenciador.cabe(sessao)){
            sessaoDao.save(sessao);
            return new ModelAndView("redirect:/admin/sala/" + form.getSalaId() + "/sessoes");
        }
        return form(form.getSalaId(), form);
    }

}
