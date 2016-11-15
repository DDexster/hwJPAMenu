package Entity;

import javax.persistence.*;

@Entity
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private String category;
    @Column
    private String name;
    @Column
    private double price;
    @Column
    private double weight;
    @Column
    private boolean isDiscount;

    public Menu() {
    }

    public Menu(String category, String name, double price, double weight, boolean isDiscount) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.isDiscount = isDiscount;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public Menu setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getName() {
        return name;
    }

    public Menu setName(String name) {
        this.name = name;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Menu setPrice(double price) {
        this.price = price;
        return this;
    }

    public double getWeight() {
        return weight;
    }

    public Menu setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public boolean isDiscount() {
        return isDiscount;
    }

    public Menu setDiscount(boolean discount) {
        isDiscount = discount;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Menu{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append(", weight=").append(weight);
        sb.append(", isDiscount=").append(isDiscount);
        sb.append('}');
        return sb.toString();
    }
}
