package com.ab.entity;

public class Product {

    private Integer pid;
    private String pname;
    private Double price;
    private Double qty;
    private String status;

    public Product() {
    }

    public Product(Integer pid, String pname, Double price, Double qty, String status) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.qty = qty;
        this.status = status;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                ", price=" + price +
                ", qty=" + qty +
                ", status='" + status + '\'' +
                '}';
    }
}
