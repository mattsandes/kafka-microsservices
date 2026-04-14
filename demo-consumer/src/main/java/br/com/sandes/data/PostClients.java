package br.com.sandes.data;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "postClient_tb")
public class PostClients {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID clientId;
    private String userName;

    public PostClients(UUID id, UUID clientId, String userName) {
        this.id = id;
        this.clientId = clientId;
        this.userName = userName;
    }

    public PostClients(UUID clientId, String userName) {
        this.clientId = clientId;
        this.userName = userName;
    }

    public PostClients() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PostClients that = (PostClients) o;
        return Objects.equals(id, that.id) && Objects.equals(clientId, that.clientId) && Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, userName);
    }
}
