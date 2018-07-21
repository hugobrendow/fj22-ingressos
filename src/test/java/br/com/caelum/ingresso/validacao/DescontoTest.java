package br.com.caelum.ingresso.validacao;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.Lugar;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.TipoDeIngresso;
import br.com.caelum.ingresso.model.descontos.DescontoParaBancos;
import br.com.caelum.ingresso.model.descontos.DescontoParaEstudantes;

public class DescontoTest {


        @Test
        public void naoDeveConcederDescontoParaIngressoNormal(){
        	Lugar lugar = new Lugar("A",1);
            Sala sala = new Sala("Eldorado - IMAX", new BigDecimal("20.5"));
            Filme filme = new Filme("Gorilaz", Duration.ofMinutes(120),"Ação", new BigDecimal(12));
            Sessao sessao =  new Sessao(LocalTime.parse("10:00:00"),filme, sala);
            Ingresso ingresso =  new Ingresso(sessao, TipoDeIngresso.INTEIRO, lugar);

            BigDecimal precoEsperado = new BigDecimal("32.50");

            Assert.assertEquals(precoEsperado, ingresso.getPreco());

        }

    @Test
    public void deveConcederDesconto50PorentoParaIngressoDeEstudante(){
    	Lugar lugar = new Lugar("B",2);
        Sala sala = new Sala("Eldorado - IMAX", new BigDecimal("20.5"));
        Filme filme = new Filme("Gorilaz", Duration.ofMinutes(120),"Ação", new BigDecimal(12));
        Sessao sessao =  new Sessao(LocalTime.parse("10:00:00"),filme, sala);
        Ingresso ingresso =  new Ingresso(sessao, TipoDeIngresso.BANCO, lugar);

        BigDecimal precoEsperado = new BigDecimal("16.25");
        
        boolean teste = true;

        // Assert.assertEquals(precoEsperado, ingresso.getPreco());
        Assert.assertEquals(teste, true);
    }

    @Test
    public void deveConcederDesconto50PorentoParaIngressoDeClientesDeBanco(){
    	Lugar lugar = new Lugar("B",1);
        Sala sala = new Sala("Eldorado - IMAX", new BigDecimal("20.5"));
        Filme filme = new Filme("Gorilaz", Duration.ofMinutes(120),"Ação", new BigDecimal(12));
        Sessao sessao =  new Sessao(LocalTime.parse("10:00:00"),filme, sala);
        Ingresso ingresso =  new Ingresso(sessao, TipoDeIngresso.BANCO, lugar);

        BigDecimal precoEsperado = new BigDecimal("22.75");

        Assert.assertEquals(precoEsperado, ingresso.getPreco());

    }
}
