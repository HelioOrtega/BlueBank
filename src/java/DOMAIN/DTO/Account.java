
package DOMAIN.DTO;

public class Account {
    
    private String cpf;
    private int agency;
    private int number;
    
    public Account(String cpf, int agency, int number) {
        this.cpf = cpf;
        this.agency = agency;
        this.number = number;
    }
    
    public Account(String cpf, String agency, String number) {
        this.cpf = cpf;
        this.agency = Integer.parseInt(agency);
        this.number = Integer.parseInt(number);
    }
    
    public Account(int agency, int number) {
        this.agency = agency;
        this.number = number;
    }
    
    public Account(String agency, String number) {
        this.agency = Integer.parseInt(agency);
        this.number = Integer.parseInt(number);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getAgency() {
        return agency;
    }

    public void setAgency(int agency) {
        this.agency = agency;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
}
