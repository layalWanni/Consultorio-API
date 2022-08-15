package br.com.uniamerica.api.service;

import br.com.uniamerica.api.entity.Medico;
import br.com.uniamerica.api.entity.Paciente;
import br.com.uniamerica.api.entity.Secretaria;
import br.com.uniamerica.api.entity.TipoAtendimento;
import br.com.uniamerica.api.repository.PacienteRepository;
import br.com.uniamerica.api.repository.SecretariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente findById(Long id) {
        return this.pacienteRepository.findById(id).orElse(new Paciente());
    }

    public Page<Paciente> listAll(Pageable pageable){return this.pacienteRepository.findAll(pageable);
    }


    public void insert(Paciente paciente){
        this.validarFormulario(paciente);
        this.pacienteRepository.save(paciente);
    }

    @Transactional
    public void saveTransactional (Paciente paciente){
        this.pacienteRepository.save(paciente);
    }

    public void update (Long id, Paciente paciente){
        if(id == paciente.getId()){
            this.validarFormulario(paciente);
            this.saveTransactional(paciente);
        }
        else {
            throw new RuntimeException();
        }
    }

    public void updateStatus (Long id, Paciente paciente){


    }

    public void validarFormulario(Paciente paciente){

        if(paciente.getTipoAtendimento().equals(TipoAtendimento.convenio)) {
            if (paciente.getConvenio().getId() == null) {
                throw new RuntimeException("Tipo Atendimento = Convenio. Convenio não informado. ");
            }
            if (paciente.getNumeroCartaoConvenio() == null) {
                throw new RuntimeException("Tipo Atendimento = Convenio. Cartão do convenio não informado. ");
            }
            if (paciente.getDataVencimento() == null) {
                throw new RuntimeException(" Data de vencimento do cartão não informado. ");
            }
            if(paciente.getDataVencimento() == null ){
                throw new RuntimeException (" Data de vencimento do cartão do convenio não informado. ");
            }
        }
        if(paciente.getTipoAtendimento().equals(TipoAtendimento.particular) ){
            paciente.setConvenio(null);
            paciente.setNumeroCartaoConvenio(null);
            paciente.setDataVencimento(null);
        }

    }

    @Transactional
    public void desativar(Long id, Paciente paciente) {
        if (id == paciente.getId()) {
            this.pacienteRepository.desativar(paciente.getId());
        }
        else {
            throw new RuntimeException("Error: Não foi possivel editar a Secretaria, valores inconsistentes.");
        }
    }

}
