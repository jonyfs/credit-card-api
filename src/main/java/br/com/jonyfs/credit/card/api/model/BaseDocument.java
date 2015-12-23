package br.com.jonyfs.credit.card.api.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

public class BaseDocument<ID extends Serializable> {

    @Id
    ID id;

    // public void setId(ID id) {
    // this.id = id;
    // }

    public ID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "BaseDocument [id=" + id + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        @SuppressWarnings("unchecked")
        BaseDocument<ID> other = (BaseDocument<ID>) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        return true;
    }

}