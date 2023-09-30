package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findByActivoTrue(Pageable paginacion);


    @Query("""
            select m from Medico m
            where m.activo= 1 
            and
            m.especialidad=:especialidad 
            and
            m.id not in(  
                select c.medico.id from Consulta c
                where
                c.fecha=:fecha
            )
            order by rand()
            limit 1
            """)
    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);


    @Query("""
            select m.activo 
            from Medico m
            where m.id=:idMedico
            """)
    Boolean findActivoById(Long idMedico);

    @Query("""
            select m from Medico m where m.activo = 1 and 
            m.especialidad = :especialida and 
            m.id not in(
                select c from Consulta c 
                where c.fecha=:time
            )
            order by rand()
            limit 1
            """)
    Medico selectMedicoConEspecialidaenFecha(Especialidad especialida, LocalDateTime time);
}
