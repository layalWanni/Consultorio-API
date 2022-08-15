package br.com.uniamerica.api.service;

import br.com.uniamerica.api.entity.*;
import br.com.uniamerica.api.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;
    public Agenda findById(Long id){
        return this.agendaRepository.findById(id).orElse(new Agenda());
    }

    public Page<Agenda> listAll(Pageable pageable){return this.agendaRepository.findAll(pageable);
    }


    public void insert(Agenda agenda){
        this.validarFormulario(agenda);
        this.agendaRepository.save(agenda);
    }

    @Transactional
    public void saveTransactional (Agenda agenda){
        this.agendaRepository.save(agenda);
    }

    public void update (Long id, Agenda agenda){
        if(id == agenda.getId()){
            this.validarFormulario(agenda);
            this.saveTransactional(agenda);
        }
        else {
            throw new RuntimeException();
        }
    }

    public void updateStatus (Long id, Agenda agenda){
    }

    public void validarData(LocalDateTime dataAte ,LocalDateTime dataDe){
        if(dataDe.compareTo(dataAte) >= 0 ){
            throw new RuntimeException("Data inválida");
        }
    }

    private boolean finalDeSemana (LocalDateTime data){
        return data.getDayOfWeek() != DayOfWeek.SATURDAY
                &&
                data.getDayOfWeek() != DayOfWeek.MONDAY;
    }



    private boolean diaDaSemana(LocalDateTime dataAte, LocalDateTime dataDe){

        return dataDe.getHour() <= 8 && dataAte.getHour() >= 11
                &&
                dataDe.getHour() <= 9 || dataAte.getHour() >=   12
                ||
                dataDe.getHour() >= 14 || dataDe.getHour() <= 17
                &&
                dataAte.getHour() >= 15 || dataAte.getHour() <= 18;
    }

    public boolean validarEncaixe(Agenda agenda){
        if(agenda.getEncaixe() == true) {
        }
        return true;
    }

    public void alterarStatusAprovado(Agenda agenda, Secretaria secretaria){
        if(agenda.getStatus() == StatusAgenda.pendente && secretaria != null){
            agenda.setStatus(StatusAgenda.aprovado);
            saveTransactional(agenda);
        }
    }

    public void alterarStatusRejeitado(Agenda agenda, Secretaria secretaria){
        if(agenda.getStatus() == StatusAgenda.pendente && secretaria != null){
            agenda.setStatus(StatusAgenda.rejeitado);
            saveTransactional(agenda);
        }
    }


    public void alterarStatusCancelado(Agenda agenda, Secretaria secretaria){
        if(agenda.getStatus() == StatusAgenda.pendente && secretaria != null || agenda.getPaciente() != null ){
            agenda.setStatus(StatusAgenda.cancelado);
            saveTransactional(agenda);
        }
    }

    public void alterarStatusCo(Agenda agenda, Secretaria secretaria){
        if(agenda.getStatus() == StatusAgenda.pendente && secretaria != null || agenda.getPaciente() != null ){
            agenda.setStatus(StatusAgenda.cancelado);
            saveTransactional(agenda);
        }
    }




    public void validarFormulario(Agenda agenda){

        if(agenda.getDataDe() == null){
            throw new RuntimeException("Informe uma data de inicio");
        }

        if(agenda.getDataAte() == null){
            throw new RuntimeException("Informe uma data de término do atendimento");
        }

        if (agenda.getMedico() == null){
            throw new RuntimeException("Informe um Medico para o Agendamento");
        }
        if (agenda.getPaciente() == null){
            throw new RuntimeException("Informe um Paciente para o Agendamento");
        }

    }

    public void desativar(final Long id, final Agenda agenda) {
        if (id == agenda.getId()) {
            this.agendaRepository.desativar(agenda.getId());
        } else {
            throw new RuntimeException("Error: Informações inconsistente, tente novamento mais tarde;");
        }
    }
}
