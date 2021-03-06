package com.founder.bdyx.modules.sys.model;

import javax.persistence.*;

@Table(name = "f_sys_menu_role")
public class SysMenuRole {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(insertable=false)
    private Integer id;

    @Column(name = "menu_id")
    private Integer menuId;

    @Column(name = "role_id")
    private Integer roleId;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return menu_id
     */
    public Integer getMenuId() {
        return menuId;
    }

    /**
     * @param menuId
     */
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    /**
     * @return role_id
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}