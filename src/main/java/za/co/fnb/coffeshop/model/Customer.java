package za.co.fnb.coffeshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

import javax.persistence.*;

@Setter
@Getter

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String password;

    public Customer(String name, String surname, String username, String password){
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Customer)){
            return false;
        }
        Customer customer = (Customer) o;
        return Objects.equals(this.id, customer.id) &&
                Objects.equals(this.name, customer.name) &&
                Objects.equals(this.surname, customer.surname) &&
                Objects.equals(this.username, customer.username) &&
                Objects.equals(this.password, customer.password);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.name, this.surname, this.username, this.password);
    }

    @Override
    public String toString(){
        return "Customer{" + "id= " + this.id +
                ", name=" + this.name + '\'' +
                ", surname=" + this.surname + '\'' +
                ", username=" + this.username + '\'' +
                ", password=" + this.password + '\'' + '}';
    }

}
