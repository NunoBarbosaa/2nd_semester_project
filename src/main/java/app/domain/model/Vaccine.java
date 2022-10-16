package app.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Vaccine implements Comparable<Vaccine>{
    private VaccineType vaccineType;
    private String name;
    private int lotNUmber;
    private VaccinationProcess vaccinationProcess;

    public Vaccine(String name, int lotNumber, VaccineType vaccineType,VaccinationProcess vaccinationProcess){
        this.name=name;
        this.lotNUmber=lotNumber;
        this.vaccineType=vaccineType;
        this.vaccinationProcess=vaccinationProcess;
    }

    public VaccineType getVaccineType() {
        return this.vaccineType;
    }

    public void setVaccineType(VaccineType vaccineType) {
        this.vaccineType = vaccineType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLotNUmber() {
        return this.lotNUmber;
    }

    public void setLotNUmber(int lotNUmber) {
        this.lotNUmber = lotNUmber;
    }

    public VaccinationProcess getVaccinationProcess() {
        return this.vaccinationProcess;
    }

    public void setVaccinationProcess(VaccinationProcess vaccinationProcess) {
        this.vaccinationProcess = vaccinationProcess;
    }


    @Override
    public int compareTo(Vaccine o) {
        if(o.vaccineType.getDisease()==this.vaccineType.getDisease()){
            return this.name.compareTo(o.getName());
        }else{
            return this.vaccineType.getDisease().compareTo(o.vaccineType.getDisease());
        }
    }
}
