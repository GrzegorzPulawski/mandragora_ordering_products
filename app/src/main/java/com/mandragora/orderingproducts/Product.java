package com.mandragora.orderingproducts;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.mandragora.orderingproducts.ui.Department;
import com.mandragora.orderingproducts.ui.Unit;

import java.io.Serializable;
import java.util.Objects;
@Entity( tableName = "products")
public class Product implements Serializable {
    @PrimaryKey( autoGenerate = true)
    private int id;
    private String name;
    private float quantity;
    private boolean toOrder;
    private Unit unit;
    private Department department;
    @Ignore
    public Product(int id, String name, float quantity, Unit unit, Department department){
        this.name = name;
        this.quantity = quantity;
        this.toOrder = false;
        this.unit = unit;
        this.department = department;
    }
    public Product(String name, float quantity, Unit unit, Department department) {
        this.name = name;
        this.quantity = quantity;
        this.toOrder =false;
        this.unit = unit;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public boolean isToOrder() {
        return toOrder;
    }

    public void setToOrder(boolean toOrder) {
        this.toOrder = toOrder;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Product{" +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", toOrder=" + toOrder +
                ", unit=" + unit +
                ", department=" + department +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Float.compare(quantity, product.quantity) == 0 && toOrder == product.toOrder && Objects.equals(name, product.name) && unit == product.unit && department == product.department;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantity, toOrder, unit, department);
    }
}
