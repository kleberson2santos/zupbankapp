package com.zupbank.bank.controller.dto;

import com.zupbank.bank.controller.input.ClientInput;
import com.zupbank.bank.domain.event.OnRegistrationCompleteEvent;
import com.zupbank.bank.domain.exception.EntidadeNaoEncontradaException;
import com.zupbank.bank.repository.AccountRespository;
import com.zupbank.bank.service.AccountService;
import com.zupbank.bank.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    @Autowired
    EnvioEmailService envioEmailService;

    @Autowired
    AccountRespository accountRespository;

    @Autowired
    AccountService accountService;

    @Autowired
    ApplicationEventPublisher eventPublisher;


    //TODO: Endpoint Aberto ou Authenticado?
    @RequestMapping(method = RequestMethod.POST, path = "/first-access")
    public void firstAccess(@RequestBody ClientInput clientInput, HttpServletRequest request) {

//        var accountDTO = new AccountDto();
        /*//TODO: salvar o cpf enviado
        var account = accountRespository.findByProposalClientEmailContaining(clientInput.getEmail())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Não foi encontrada uma conta para o email informado."));

        Mensagem mensagem = Mensagem.builder()
                .assunto("ZupBank - Registre seu primeiro acesso!")
                .destinatario(clientInput.getEmail())
                .corpo("Cliente com cpf começando com " + clientInput.getCpf().substring(0, 3) + "." +
                        "<br> Use o token " +account.getToken() + "</strong>"+
                        " para registrar suas credenciais de acesso.")
                .build();
        envioEmailService.enviar(mensagem);*/

//        try {
        //TODO: possivel endpoint para cadastrar a conta ao inves de cadastrar  a conta na aprovacao da proposta
//            Account registered = accountService.registerNewUserAccount(accountDTO);

        var account = accountRespository.findByProposalClientEmailContaining(clientInput.getEmail())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Não foi encontrada uma conta para o email informado."));

        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(account,
                request.getLocale(), appUrl));
//        } catch (AccountAlreadyExistException aaeEx) {
//
//            throw new AccountAlreadyExistException("Uma conta de email já existe");
//        } catch (Exception ex) {
//            throw new NegocioException("Houve um erro ao registrar a conta.");
//        }

//        return null;

    }

    @RequestMapping(method = RequestMethod.POST, path = "/register/{token}")
    public void register(@PathVariable String token) {

        System.err.println("Validar Token:" + token);

    }


}
