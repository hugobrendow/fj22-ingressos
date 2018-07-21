package br.com.caelum.ingresso.validacao;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.descontos.DescontoParaBancos;
import br.com.caelum.ingresso.model.descontos.DescontoParaEstudantes;
import br.com.caelum.ingresso.model.descontos.SemDesconto;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

public class DescontoTest {


        @Test
        public void naoDeveConcederDescontoParaIngressoNormal(){

            Sala sala = new Sala("Eldorado - IMAX", new BigDecimal("20.5"));
            Filme filme = new Filme("Gorilaz", Duration.ofMinutes(120),"Ação", new BigDecimal(12));
            Sessao sessao =  new Sessao(LocalTime.parse("10:00:00"),filme, sala);
            Ingresso ingresso =  new Ingresso(sessao, new SemDesconto());

            BigDecimal precoEsperado = new BigDecimal("32.50");

            Assert.assertEquals(precoEsperado, ingresso.getPreco());

        }

    @Test
    public void deveConcederDesconto50PorentoParaIngressoDeEstudante(){

        Sala sala = new Sala("Eldorado - IMAX", new BigDecimal("20.5"));
        Filme filme = new Filme("Gorilaz", Duration.ofMinutes(120),"Ação", new BigDecimal(12));
        Sessao sessao =  new Sessao(LocalTime.parse("10:00:00"),filme, sala);
        Ingresso ingresso =  new Ingresso(sessao, new DescontoParaEstudantes());

        BigDecimal precoEsperado = new BigDecimal("16.25");

        Assert.assertEquals(precoEsperado, ingresso.getPreco());

    }

    @Test
    public void deveConcederDesconto50PorentoParaIngressoDeClientesDeBanco(){

        Sala sala = new Sala("Eldorado - IMAX", new BigDecimal("20.5"));
        Filme filme = new Filme("Gorilaz", Duration.ofMinutes(120),"Ação", new BigDecimal(12));
        Sessao sessao =  new Sessao(LocalTime.parse("10:00:00"),filme, sala);
        Ingresso ingresso =  new Ingresso(sessao, new DescontoParaBancos());

        BigDecimal precoEsperado = new BigDecimal("22.75");

        Assert.assertEquals(precoEsperado, ingresso.getPreco());

    }
}
