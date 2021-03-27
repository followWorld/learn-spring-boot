package com.learn.bean;

import java.util.Objects;

/**
 * ClassName: Permissions <br/>
 * Description: <br/>
 * date: 2021/1/15 14:10<br/>
 *
 * @author WLSH<br />
 */
public class Permissions {

    private String id;
    private String permissionsName;

    public Permissions(String id, String permissionsName) {
        this.id = id;
        this.permissionsName = permissionsName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermissionsName() {
        return permissionsName;
    }

    public void setPermissionsName(String permissionsName) {
        this.permissionsName = permissionsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Permissions that = (Permissions) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(permissionsName, that.permissionsName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, permissionsName);
    }
}
