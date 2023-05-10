package org.exemple.data;

public class RoleDTO {
    private Integer id;
    private ERoleDTO name;

    public RoleDTO() {

    }

    public RoleDTO(ERoleDTO name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ERoleDTO getName() {
        return name;
    }

    public void setName(ERoleDTO name) {
        this.name = name;
    }
}
