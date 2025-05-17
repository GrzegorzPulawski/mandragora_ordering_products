package com.mandragora.orderingproducts.ui;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mandragora.orderingproducts.Product;

import java.util.List;
@Dao
public interface ProductDAO {
    @Query("SELECT * FROM products")
    List<Product> getAll();
    @Insert
    void insert(Product product);
    @Insert
    void insertAll(Product...products);
    @Query("DELETE FROM Products WHERE id =:productId")
    void deleteById(int productId);
    @Update
    void update(Product product);
    @Query("SELECT * FROM  Products WHERE department =:department")
    List<Product> getByDepartment(Department department);


}