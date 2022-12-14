package br.com.uniamerica.api.controller;

import br.com.uniamerica.api.entity.Agenda;
import br.com.uniamerica.api.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/api/agendas")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;


    @GetMapping("/{idAgenda}")
    public ResponseEntity<Agenda> findById(
            @PathVariable("idAgenda") Long idAgenda
    ){
        return ResponseEntity.ok().body(this.agendaService.findById(idAgenda));
    }

    @GetMapping
    public ResponseEntity<Page<Agenda>> listByAllPage(
            Pageable pageable
    ){
        return ResponseEntity.ok().body(this.agendaService.listAll(pageable));
    }


    @PostMapping
    public ResponseEntity<?> insert(
            @RequestBody Agenda agenda
    ){
        try {
            this.agendaService.insert(agenda);
            return ResponseEntity.ok().body("Agenda Cadastrada com Sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idAgenda}")
    public ResponseEntity<?> update(
            @PathVariable Long idAgenda,
            @RequestBody Agenda agenda
    ){
        try {
            this.agendaService.update(idAgenda, agenda);
            return ResponseEntity.ok().body("Agenda Atualizada com Sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/desativar/{idAgenda}")
    public ResponseEntity<?> desativar(
            @PathVariable Long idAgenda,
            @RequestBody Agenda agenda
    ){
        try {
            this.agendaService.desativar(idAgenda, agenda);
            return ResponseEntity.ok().body("Agenda Desativada com Sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}