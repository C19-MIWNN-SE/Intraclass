package nl.miwnn.ch19.DaMaGe.IntraClass.mapper;

import nl.miwnn.ch19.DaMaGe.IntraClass.dto.CohortDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Cohort;
import org.springframework.stereotype.Component;

/**
 * @author G. Neuteboom
 * !!! Purpose for class !!!
 */
@Component
public class CohortMapper {

    public Cohort toCohort(CohortDTO cohortDTO){
        Cohort cohort = new Cohort();

        cohort.setId(cohortDTO.getId());
        cohort.setName(cohortDTO.getName());
        cohort.setDescription(cohortDTO.getDescription());
        cohort.setStartDate(cohortDTO.getStartDate());
        cohort.setEndDate(cohortDTO.getEndDate());
        cohort.setParticipants(cohortDTO.getParticipant());

        return cohort;
    }

}